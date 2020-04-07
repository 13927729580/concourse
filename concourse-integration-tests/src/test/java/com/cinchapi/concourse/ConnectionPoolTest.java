/*
 * Copyright (c) 2013-2020 Cinchapi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cinchapi.concourse;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;

import com.cinchapi.common.base.CheckedExceptions;
import com.cinchapi.concourse.server.concurrent.Threads;
import com.cinchapi.concourse.test.ConcourseIntegrationTest;
import com.cinchapi.concourse.util.Environments;
import com.cinchapi.concourse.util.TestData;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Unit tests for {@link com.cinchapi.concourse.ConnectionPool}.
 *
 * @author Jeff Nelson
 */
public abstract class ConnectionPoolTest extends ConcourseIntegrationTest {

    protected static final int POOL_SIZE = 3;
    protected static final String USERNAME = "admin";
    protected static final String PASSWORD = "admin";

    /**
     * The {@link com.cinchapi.concourse.ConnectionPool} which is instantiated
     * and clean up before and
     * after each test.
     */
    protected ConnectionPool connections = null;

    @Override
    protected void afterEachTest() {
        super.afterEachTest();
        try {
            connections.close();
            connections = null;
        }
        catch (Exception e) {}
    }

    @Override
    protected void beforeEachTest() {
        super.beforeEachTest();
        connections = getConnectionPool();
    }

    @Test
    public void testHasAvailableConnection() {
        Assert.assertTrue(connections.hasAvailableConnection());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotReturnConnectionNotRequestedFromPool() {
        connections.release(Concourse.connect(SERVER_HOST, SERVER_PORT,
                USERNAME, PASSWORD));
    }

    @Test
    public void testConnectionPoolIsConnectedToCorrectEnvironment() {
        String env = null;
        while (Strings.isNullOrEmpty(env)) {
            env = Environments.sanitize(TestData.getString());
        }
        ConnectionPool pool = getConnectionPool(env);
        Assert.assertEquals(env, pool.request().getServerEnvironment());
    }

    @Test
    public void testSimultaneousClose() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                connections.forceClose();
            }
        });
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                connections.forceClose();
            }

        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Assert.assertTrue(connections.isClosed());
    }

    @Test
    public void testSimultaneousCloseAndForceClose()
            throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    connections.close();
                }
                catch (IllegalStateException e) {
                    if(e.getMessage().equals("Connection pool is closed")) {
                        // Ignore because the race between t1 and t2 ended with
                        // t2 winning.
                    }
                    else {
                        throw e;
                    }
                }
                catch (Exception e) {
                    throw CheckedExceptions.wrapAsRuntimeException(e);
                }
            }

        });
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                connections.forceClose();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Assert.assertTrue(connections.isClosed());
    }

    @Test
    public void testForceCloseWithLeasedConnection() {
        connections.request();
        connections.forceClose();
        Assert.assertTrue(connections.isClosed());
    }

    @Test
    public void testRequestReleaseRaceCondition() {
        AtomicBoolean done = new AtomicBoolean(false);
        AtomicBoolean passed = new AtomicBoolean(true);
        List<Thread> threads = Lists.newArrayList();
        for (int i = 0; i < 10; ++i) {
            Thread t = new Thread(() -> {
                while (!done.get()) {
                    Concourse connection = connections.request();
                    try {
                        connection.add("name", "jeff");
                    }
                    finally {
                        try {
                            connections.release(connection);
                        }
                        catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            passed.set(false);
                        }
                    }
                }
            });
            t.start();
            threads.add(t);
        }
        Threads.sleep(1000);
        done.set(true);
        Assert.assertTrue(passed.get());
        threads.forEach(t -> {
            try {
                t.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Return a {@link com.cinchapi.concourse.ConnectionPool} to use in a unit
     * test.
     *
     * @return the ConnectionPool
     */
    protected abstract ConnectionPool getConnectionPool();

    /**
     * Return a {@link com.cinchapi.concourse.ConnectionPool} connected to
     * {@code env} to use in a
     * unit test.
     *
     * @param env
     * @return the ConnectionPool
     */
    protected abstract ConnectionPool getConnectionPool(String env);

}
