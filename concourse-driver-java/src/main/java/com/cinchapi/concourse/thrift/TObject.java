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
package com.cinchapi.concourse.thrift;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.server.AbstractNonblockingServer.*;

import com.cinchapi.concourse.Link;
import com.cinchapi.concourse.Timestamp;
import com.cinchapi.concourse.annotate.DoNotInvoke;
import com.cinchapi.concourse.util.ByteBuffers;
import com.cinchapi.concourse.util.Convert;
import com.cinchapi.concourse.util.Numbers;
import com.cinchapi.concourse.util.RegexPatterns;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;

@SuppressWarnings({ "cast", "rawtypes", "serial", "unchecked", "unused" })
/**
 * A lightweight wrapper for a typed Object that has been encoded
 * as binary data.
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-2-22")
public class TObject implements
        org.apache.thrift.TBase<TObject, TObject._Fields>,
        java.io.Serializable,
        Cloneable,
        Comparable<TObject> {

    /**
     * Return a {@link Comparator} for {@link TObject} values.
     * 
     * @return a {@link TObject} {@link Comparator}
     */
    public static Comparator<TObject> comparator() {
        // TODO: This is based on the Value#Sorter defined in concourse-server.
        // At some point, it will make sense to use this as a canonical base and
        // implement the Value comparator using this one.
        return (v1, v2) -> {
            if((v1 == POSITIVE_INFINITY && v2 == POSITIVE_INFINITY)
                    || (v1 == NEGATIVE_INFINITY && v2 == NEGATIVE_INFINITY)) {
                return 0;
            }
            else if(v1 == POSITIVE_INFINITY) {
                return 1;
            }
            else if(v2 == POSITIVE_INFINITY) {
                return -1;
            }
            else if(v1 == NEGATIVE_INFINITY) {
                return -1;
            }
            else if(v2 == NEGATIVE_INFINITY) {
                return 1;
            }
            else {
                Object o1 = Convert.thriftToJava(v1);
                Object o2 = Convert.thriftToJava(v2);
                if(o1 instanceof Number && o2 instanceof Number
                        && ((!(o1 instanceof Link) && !(o2 instanceof Link))
                                || (o1 instanceof Link
                                        && o2 instanceof Link))) {
                    return Numbers.compare((Number) o1, (Number) o2);
                }
                else if(o1 instanceof Number) {
                    return -1;
                }
                else if(o2 instanceof Number) {
                    return 1;
                }
                else if(o1 instanceof Timestamp && o2 instanceof Timestamp) {
                    return Longs.compare(((Timestamp) o1).getMicros(),
                            ((Timestamp) o2).getMicros());
                }
                else {
                    // NOTE: Timestamp's #toString may change depending upon the
                    // configured formatter so we use the #toString for the
                    // internal micros for consistency.
                    String o1s = o1 instanceof Timestamp
                            ? Long.toString(((Timestamp) o1).getMicros())
                            : o1.toString();
                    String o2s = o2 instanceof Timestamp
                            ? Long.toString(((Timestamp) o2).getMicros())
                            : o2.toString();
                    // CON-667: Order strings in a manner that causes lowercase
                    // and uppercase versions of the "same" string to be grouped
                    // together with uppercase appearing "first"
                    int c = o1s.compareToIgnoreCase(o2s);
                    if(c == 0) {
                        c = o1s.compareTo(o2s);
                    }
                    return c;
                }
            }
        };
    }

    /**
     * Perform any necessary aliasing on the {@code values} and {@code operator}
     * based on the combination of the two
     * 
     * @param operator
     * @param values
     * @return the aliased parameters
     */
    public static Aliases alias(Operator operator, TObject... values) {
        // Transform the operator to its functional alias, given the
        // transformations that will be made to the value(s).
        Operator original = operator;
        switch (operator) {
        case LIKE:
            operator = Operator.REGEX;
            break;
        case NOT_LIKE:
            operator = Operator.NOT_REGEX;
            break;
        case LINKS_TO:
            operator = Operator.EQUALS;
            break;
        default:
            break;
        }

        // Transform the values based on the original operator, if necessary.
        TObject[] ovalues = new TObject[values.length];
        for (int i = 0; i < ovalues.length; ++i) {
            TObject value = values[i];
            try {
                switch (original) {
                case REGEX:
                case NOT_REGEX:
                case LIKE:
                case NOT_LIKE:
                    value = Convert.javaToThrift(
                            ((String) Convert.thriftToJava(value)).replaceAll(
                                    RegexPatterns.PERCENT_SIGN_WITHOUT_ESCAPE_CHAR,
                                    ".*").replaceAll(
                                            RegexPatterns.PERCENT_SIGN_WITH_ESCAPE_CHAR,
                                            "%"));
                    break;
                case LINKS_TO:
                    value = Convert.javaToThrift(
                            Link.to(((Number) Convert.thriftToJava(value))
                                    .longValue()));
                    break;
                default:
                    break;
                }

            }
            catch (ClassCastException e) {/* ignore */}
            ovalues[i] = value;
        }

        return new Aliases(operator, ovalues);
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    /**
     * A constant representing the smallest possible TObject. This shouldn't be
     * used in normal operations, but should only be used to indicate an
     * infinite range.
     */
    public static final TObject NEGATIVE_INFINITY = Convert
            .javaToThrift(Long.MIN_VALUE);
    /**
     * Represents a null object that can be passed across the wire.
     */
    public static final TObject NULL = new TObject(ByteBuffer.allocate(1),
            Type.NULL);

    /**
     * A constant representing the largest possible TObject. This shouldn't be
     * used in normal operations, but should only be used to indicate an
     * infinite range.
     */
    public static final TObject POSITIVE_INFINITY = Convert
            .javaToThrift(Long.MAX_VALUE);

    private static final org.apache.thrift.protocol.TField DATA_FIELD_DESC = new org.apache.thrift.protocol.TField(
            "data", org.apache.thrift.protocol.TType.STRING, (short) 1);
    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
            "TObject");
    private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField(
            "type", org.apache.thrift.protocol.TType.I32, (short) 2);

    /**
     * The byte for UTF-8 whitespace.
     */
    private static byte WHITESPACE = " ".getBytes(StandardCharsets.UTF_8)[0];

    static {
        schemes.put(StandardScheme.class, new TObjectStandardSchemeFactory());
        schemes.put(TupleScheme.class, new TObjectTupleSchemeFactory());
    }

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
                _Fields.class);
        tmpMap.put(_Fields.DATA,
                new org.apache.thrift.meta_data.FieldMetaData("data",
                        org.apache.thrift.TFieldRequirementType.REQUIRED,
                        new org.apache.thrift.meta_data.FieldValueMetaData(
                                org.apache.thrift.protocol.TType.STRING,
                                true)));
        tmpMap.put(_Fields.TYPE,
                new org.apache.thrift.meta_data.FieldMetaData("type",
                        org.apache.thrift.TFieldRequirementType.REQUIRED,
                        new org.apache.thrift.meta_data.EnumMetaData(
                                org.apache.thrift.protocol.TType.ENUM,
                                Type.class)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData
                .addStructMetaDataMap(TObject.class, metaDataMap);
    }

    public ByteBuffer data; // required

    /**
     * 
     * @see Type
     */
    public Type type; // required

    /**
     * A cached reference to the Java representation for this TObject. This
     * value is set in the {@link Convert#javaToThrift(Object)} method so that
     * subsequent inverse conversions are more efficient.
     */
    private Object java = null;

    /**
     * A cached reference to the server-side wrapper for this TObject that
     * exists in order to prevent duplicate objects from being created during
     * back-and-forth conversions. This value is set by the server whenever this
     * object is first converted to the server-side representation.
     */
    private Object serverWrapper = null;

    public TObject() {
        this.type = Type.STRING;
    }

    public TObject(ByteBuffer data, Type type) {
        this.data = data;
        this.type = type;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public TObject(TObject other) {
        if(other.isSetData()) {
            this.data = org.apache.thrift.TBaseHelper.copyBinary(other.data);
        }
        if(other.isSetType()) {
            this.type = other.type;
        }
    }

    public ByteBuffer bufferForData() {
        return ByteBuffers.asReadOnlyBuffer(data);
    }

    /**
     * Cache the server value that wraps this TObject.
     * 
     * <p>
     * <strong>WARNING:</strong> This method should not be called directly.
     * </p>
     * 
     * @param serverValue the wrapper
     * @return this, for chaining
     */
    @DoNotInvoke
    public TObject cacheServerWrapper(Object serverValue) {
        this.serverWrapper = serverValue;
        return this;
    }

    @Override
    public void clear() {
        this.data = null;
        this.type = Type.STRING;

    }

    @Override
    public int compareTo(TObject other) {
        return comparator().compare(this, other);
    }

    /**
     * Compare this {@link TObject} to the {@code other} one if both are
     * {@link #isCharSequenceType() character sequence types} where case
     * matters. Otherwise, simply {@link #compareTo(TObject) compare} the two
     * {@link TObject}s.
     * 
     * @param other
     * @return an integer that describes the case insensitive relative ordering
     *         of this {@link TObject} and the {@code other} one
     */
    public int compareToIgnoreCase(TObject other) {
        if(isCharSequenceType() && other.isCharSequenceType()) {
            return Convert.thriftToJava(this).toString().compareToIgnoreCase(
                    Convert.thriftToJava(other).toString());
        }
        else {
            return compareTo(other);
        }
    }

    public TObject deepCopy() {
        return new TObject(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TObject) {
            TObject other = (TObject) obj;
            return bufferForData().equals(other.bufferForData())
                    && getInternalType() == other.getInternalType();
        }
        return false;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public byte[] getData() {
        setData(org.apache.thrift.TBaseHelper.rightSize(data));
        return data == null ? null : data.array();
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
        case DATA:
            return getData();

        case TYPE:
            return getType();

        }
        throw new IllegalStateException();
    }

    /**
     * Get the java representation, if it is already available. This method will
     * not perform a conversion from Thrift to Java. If you want to be sure you
     * can get the Java format for a TObject, use the
     * {@link Convert#thriftToJava(TObject)} method.
     * 
     * @return the java representation or {@code null}
     */
    @Nullable
    public Object getJavaFormat() {
        return java;
    }

    /**
     * Get the cache for the server value that wraps this TObject, if it is
     * already available, otherwise return {@code null}.
     * 
     * <p>
     * <strong>WARNING:</strong> This method should not be called directly.
     * </p>
     * 
     * @return the cached copy of the server value or {@code null}
     */
    @Nullable
    @DoNotInvoke
    public Object getServerWrapper() {
        return serverWrapper;
    }

    /**
     * 
     * @see Type
     */
    public Type getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(
                new int[] { data.hashCode(), getInternalType().ordinal() });
    }

    /**
     * Return {@code true} of this {@link TObject} satisfies {@code operator} in
     * relation to the {@code values}.
     * 
     * @param operator
     * @param values
     * @return {@code true} if the comparison is valid
     */
    public boolean is(Operator operator, TObject... values) {
        return is(comparator()::compare, operator, values);
    }

    /**
     * Check to see if this TObject represents a "blank" value (e.g. empty
     * string, null, etc). This method is efficient because it only looks at the
     * TObject's binary data as opposed to converting the object to a Java
     * representation beforehand.
     * 
     * @return {@code true} if this TObject is "blank"
     */
    public boolean isBlank() {
        if(this == NULL || type == Type.NULL) {
            return true;
        }
        else if(data.capacity() == 0) {
            return true;
        }
        else if(type == Type.STRING || type == Type.TAG) {
            byte[] bytes = ByteBuffers.toByteArray(data);
            for (int i = 0; i < bytes.length; ++i) {
                if(bytes[i] != WHITESPACE) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Return {@code true} if the value {@link #getType() type} is a character
     * sequence.
     * 
     * @return {@code true} if the value type is a character sequence
     */
    public boolean isCharSequenceType() {
        Type type = getType();
        return type == Type.STRING || type == Type.TAG;
    }

    /**
     * Return {@code true} of this {@link TObject} satisfies {@code operator} in
     * relation to the {@code values} regardless of case.
     * 
     * @param operator
     * @param values
     * @return {@code true} if the comparison is valid
     */
    public boolean isIgnoreCase(Operator operator, TObject... values) {
        return is((t1, t2) -> t1.compareToIgnoreCase(t2), operator, values);
    }

    /**
     * Returns true if field corresponding to fieldID is set (has been assigned
     * a value) and false otherwise
     */
    public boolean isSet(_Fields field) {
        if(field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
        case DATA:
            return isSetData();
        case TYPE:
            return isSetType();
        }
        throw new IllegalStateException();
    }

    /**
     * Returns true if field data is set (has been assigned a value) and false
     * otherwise
     */
    public boolean isSetData() {
        return this.data != null;
    }

    /**
     * Returns true if field type is set (has been assigned a value) and false
     * otherwise
     */
    public boolean isSetType() {
        return this.type != null;
    }

    /**
     * Return {@code true} if this TObject and {@code other} have the same
     * {@code type} and are equal.
     * 
     * @param other
     * @return {@code true} if this matches {@code other}.
     */
    public boolean matches(TObject other) {
        return type == other.type && equals(other);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot)
            throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public TObject setData(byte[] data) {
        setData(data == null ? (ByteBuffer) null : ByteBuffer.wrap(data));
        return this;
    }

    public TObject setData(ByteBuffer data) {
        this.data = data;
        return this;
    }

    public void setDataIsSet(boolean value) {
        if(!value) {
            this.data = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
        case DATA:
            if(value == null) {
                unsetData();
            }
            else {
                setData((ByteBuffer) value);
            }
            break;

        case TYPE:
            if(value == null) {
                unsetType();
            }
            else {
                setType((Type) value);
            }
            break;

        }
    }

    /**
     * Set the java representation.
     * 
     * @param java
     * @return this, for chaining
     */
    public TObject setJavaFormat(Object java) {
        this.java = java;
        return this;
    }

    /**
     * 
     * @see Type
     */
    public TObject setType(Type type) {
        this.type = type;
        return this;
    }

    public void setTypeIsSet(boolean value) {
        if(!value) {
            this.type = null;
        }
    }

    @Override
    public String toString() {
        if(type == Type.NULL) {
            return "null";
        }
        else {
            return Convert.thriftToJava(this).toString();
        }
    }

    public void unsetData() {
        this.data = null;
    }

    public void unsetType() {
        this.type = null;
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        if(data == null) {
            throw new org.apache.thrift.protocol.TProtocolException(
                    "Required field 'data' was not present! Struct: "
                            + toString());
        }
        if(type == null) {
            throw new org.apache.thrift.protocol.TProtocolException(
                    "Required field 'type' was not present! Struct: "
                            + toString());
        }
        // check for sub-struct validity
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot)
            throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    /**
     * Return the {@link Type} that is used for internal operations.
     * 
     * @return the internal type
     */
    protected Type getInternalType() { // visible for testing
        if(type == Type.TAG) {
            return Type.STRING;
        }
        else {
            return getType();
        }
    }

    /**
     * Return {@code true} of this {@link TObject} satisfies {@code operator} in
     * relation to the {@code values}.
     * 
     * @param comparer a {@link BiFunction} that compares two {@link TObject}s
     *            and returns an Integer that describes their relative order
     *            similar to a {@link Comparator}
     * @param operator
     * @param values
     * @return {@code true} if the comparison is valid
     */
    private boolean is(BiFunction<TObject, TObject, Integer> comparer,
            Operator operator, TObject... values) {
        Aliases aliases = alias(operator, values);
        values = aliases.values();
        operator = aliases.operator();
        TObject v1 = values[0];
        switch (operator) {
        case EQUALS:
            return comparer.apply(this, v1) == 0;
        case NOT_EQUALS:
            return comparer.apply(this, v1) != 0;
        case GREATER_THAN:
            return comparer.apply(this, v1) > 0;
        case GREATER_THAN_OR_EQUALS:
            return comparer.apply(this, v1) >= 0;
        case LESS_THAN:
            return comparer.apply(this, v1) < 0;
        case LESS_THAN_OR_EQUALS:
            return comparer.apply(this, v1) <= 0;
        case BETWEEN:
            Preconditions.checkArgument(values.length > 1);
            TObject v2 = values[1];
            return comparer.apply(v1, this) <= 0
                    && comparer.apply(v2, this) > 0;
        case REGEX:
            return Convert.thriftToJava(this).toString()
                    .matches(Convert.thriftToJava(v1).toString());
        case NOT_REGEX:
            return !Convert.thriftToJava(this).toString()
                    .matches(Convert.thriftToJava(v1).toString());
        default:
            throw new UnsupportedOperationException();
        }
    }

    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {
        try {
            read(new org.apache.thrift.protocol.TCompactProtocol(
                    new org.apache.thrift.transport.TIOStreamTransport(in)));
        }
        catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(
                    new org.apache.thrift.transport.TIOStreamTransport(out)));
        }
        catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    /**
     * The set of fields this struct contains, along with convenience methods
     * for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        DATA((short) 1, "data"),
        /**
         * 
         * @see Type
         */
        TYPE((short) 2, "type");

        /**
         * Find the _Fields constant that matches name, or null if its not
         * found.
         */
        public static _Fields findByName(String name) {
            return byName.get(name);
        }

        /**
         * Find the _Fields constant that matches fieldId, or null if its not
         * found.
         */
        public static _Fields findByThriftId(int fieldId) {
            switch (fieldId) {
            case 1: // DATA
                return DATA;
            case 2: // TYPE
                return TYPE;
            default:
                return null;
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, throwing an exception
         * if it is not found.
         */
        public static _Fields findByThriftIdOrThrow(int fieldId) {
            _Fields fields = findByThriftId(fieldId);
            if(fields == null)
                throw new IllegalArgumentException(
                        "Field " + fieldId + " doesn't exist!");
            return fields;
        }

        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

        static {
            for (_Fields field : EnumSet.allOf(_Fields.class)) {
                byName.put(field.getFieldName(), field);
            }
        }

        private final String _fieldName;
        private final short _thriftId;

        _Fields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        public String getFieldName() {
            return _fieldName;
        }

        public short getThriftFieldId() {
            return _thriftId;
        }
    }

    /**
     * A container class that holds operational parameters based on conversions
     * in the {@link #alias(Operator, TObject[])} method.
     * 
     * @author Jeff Nelson
     */
    @Immutable
    public static final class Aliases {

        private final Operator operator;
        private final TObject[] values;

        /**
         * @param operator
         * @param values
         */
        private Aliases(Operator operator, TObject[] values) {
            this.operator = operator;
            this.values = values;
        }

        /**
         * Return the {@link #operator}.
         * 
         * @return the operator
         */
        public Operator operator() {
            return operator;
        }

        /**
         * Return the {@link #values}.
         * 
         * @return the values
         */
        public TObject[] values() {
            return values;
        }
    }

    private static class TObjectStandardScheme extends StandardScheme<TObject> {

        public void read(org.apache.thrift.protocol.TProtocol iprot,
                TObject struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if(schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                case 1: // DATA
                    if(schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                        struct.data = iprot.readBinary();
                        struct.setDataIsSet(true);
                    }
                    else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
                                schemeField.type);
                    }
                    break;
                case 2: // TYPE
                    if(schemeField.type == org.apache.thrift.protocol.TType.I32) {
                        struct.type = Type.findByValue(iprot.readI32());
                        struct.setTypeIsSet(true);
                    }
                    else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
                                schemeField.type);
                    }
                    break;
                default:
                    org.apache.thrift.protocol.TProtocolUtil.skip(iprot,
                            schemeField.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be
            // checked in the validate method
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot,
                TObject struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if(struct.data != null) {
                oprot.writeFieldBegin(DATA_FIELD_DESC);
                oprot.writeBinary(struct.data);
                oprot.writeFieldEnd();
            }
            if(struct.type != null) {
                oprot.writeFieldBegin(TYPE_FIELD_DESC);
                oprot.writeI32(struct.type.getValue());
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class TObjectStandardSchemeFactory implements SchemeFactory {
        public TObjectStandardScheme getScheme() {
            return new TObjectStandardScheme();
        }
    }

    private static class TObjectTupleScheme extends TupleScheme<TObject> {

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot,
                TObject struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            struct.data = iprot.readBinary();
            struct.setDataIsSet(true);
            struct.type = Type.findByValue(iprot.readI32());
            struct.setTypeIsSet(true);
        }

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot,
                TObject struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            oprot.writeBinary(struct.data);
            oprot.writeI32(struct.type.getValue());
        }
    }

    private static class TObjectTupleSchemeFactory implements SchemeFactory {
        public TObjectTupleScheme getScheme() {
            return new TObjectTupleScheme();
        }
    }

}
