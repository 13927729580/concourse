package org.cinchapi.concourse.server.storage;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.Iterator;
import java.util.List;

import javax.annotation.concurrent.ThreadSafe;

import org.cinchapi.concourse.server.io.FileSystem;
import org.cinchapi.concourse.util.Integers;
import org.cinchapi.concourse.util.LongBitSet;
import org.cinchapi.vendor.jsr166e.StampedLock;

import com.google.common.collect.Lists;

@ThreadSafe
public class Inventory {

    /**
     * Return a new {@link Inventory} that is persisted to the
     * {@code backingStore} in calls to {@link #sync()}.
     * 
     * @param backingStore
     * @return the Inventory
     */
    public static Inventory create(String backingStore) {
        return new Inventory(backingStore);
    }

    /**
     * The amount of memory and disk space allocated each time we need to extend
     * the backing store. This value is rounded up to the nearest power of two
     * for the most efficient I/O.
     */
    private static final int MEMORY_MAPPING_SIZE = 8 * 20000;

    /**
     * The location where the inventory is stored on disk.
     */
    private final String backingStore;

    /**
     * The bitset that contains the read-efficient version of the data in the
     * inventory.
     */
    private final LongBitSet bitSet;

    /**
     * A memory mapped buffer that is used to handle writes to the backing
     * store.
     */
    private MappedByteBuffer content;

    /**
     * A collection of dirty writes that have not been synced to disk yet.
     */
    private transient volatile List<Long> dirty = Lists
            .newArrayListWithExpectedSize(1);

    /**
     * Concurrency control
     */
    private final StampedLock lock = new StampedLock();

    /**
     * Construct a new instance. If the {@code backingStore} has data then it
     * will be read into memory.
     * 
     * @param backingStore
     */
    private Inventory(String backingStore) {
        this.backingStore = backingStore;
        this.bitSet = LongBitSet.create();
        this.content = FileSystem.map(backingStore, MapMode.READ_ONLY, 0,
                FileSystem.getFileSize(backingStore));
        while (content.position() < content.capacity()) {
            long record = content.getLong();
            if(record == 0 && content.getLong() == 0) { // if there is a null
                                                        // (0) record, check to
                                                        // see if the next one
                                                        // is also null which
                                                        // will tell us we've
                                                        // read too far
                content.position(content.position() - 16);
                break;
            }
            else {
                bitSet.set(record);
            }
        }
        map0(content.position(), MEMORY_MAPPING_SIZE);
    }

    /**
     * Add an {@code record} to the inventory.
     * 
     * @param record
     */
    public void add(long record) {
        long stamp = lock.writeLock();
        try {
            if(bitSet.set(record)) {
                dirty.add(record);
            }
        }
        finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Return {@code true} if the {@code record} exists within the inventory.
     * 
     * @param record
     * @return {@code true} if the record is contained
     */
    public boolean contains(long record) {
        long stamp = lock.tryOptimisticRead();
        boolean result = bitSet.get(record);
        if(lock.validate(stamp)) {
            return result;
        }
        else {
            stamp = lock.readLock();
            try {
                return bitSet.get(record);
            }
            finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * Perform an fsync and flush any dirty writes to disk.
     */
    public void sync() {
        long stamp = lock.writeLock();
        try {
            if(!dirty.isEmpty()) {
                Iterator<Long> it = dirty.iterator();
                while (it.hasNext()) {
                    if(content.remaining() < 8) {
                        map0(content.position(), content.position() * 2);
                    }
                    content.putLong(it.next());
                }
                content.force();
                dirty.clear();
            }
        }
        finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * Map the {@link #backingStore} in memory for the specified
     * {@code position} for at least {@code length} bytes.
     * 
     * @param position
     * @param length
     */
    private void map0(long position, int length) {
        FileSystem.unmap(content);
        content = FileSystem.map(backingStore, MapMode.READ_WRITE, position,
                Integers.nextPowerOfTwo(length));
    }
}
