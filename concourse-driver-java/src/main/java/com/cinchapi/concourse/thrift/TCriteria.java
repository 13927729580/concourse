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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.server.AbstractNonblockingServer.*;

@SuppressWarnings({ "cast", "rawtypes", "serial", "unchecked", "unused" })
/**
 * A representation for a Criteria that can be passed over the wire via
 * Thrift. Once passed over the write, the server goes through the list
 * of TSymbols and converts them to actual Symbol objects which can then
 * be used in the shunting-yard algorithm.
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-2-22")
public class TCriteria implements
        org.apache.thrift.TBase<TCriteria, TCriteria._Fields>,
        java.io.Serializable,
        Cloneable,
        Comparable<TCriteria> {
    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
            "TCriteria");
    private static final org.apache.thrift.protocol.TField SYMBOLS_FIELD_DESC = new org.apache.thrift.protocol.TField(
            "symbols", org.apache.thrift.protocol.TType.LIST, (short) 1);

    static {
        schemes.put(StandardScheme.class, new TCriteriaStandardSchemeFactory());
        schemes.put(TupleScheme.class, new TCriteriaTupleSchemeFactory());
    }

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
                _Fields.class);
        tmpMap.put(_Fields.SYMBOLS,
                new org.apache.thrift.meta_data.FieldMetaData("symbols",
                        org.apache.thrift.TFieldRequirementType.REQUIRED,
                        new org.apache.thrift.meta_data.ListMetaData(
                                org.apache.thrift.protocol.TType.LIST,
                                new org.apache.thrift.meta_data.StructMetaData(
                                        org.apache.thrift.protocol.TType.STRUCT,
                                        TSymbol.class))));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData
                .addStructMetaDataMap(TCriteria.class, metaDataMap);
    }

    public List<TSymbol> symbols; // required

    public TCriteria() {}

    public TCriteria(List<TSymbol> symbols) {
        this();
        this.symbols = symbols;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public TCriteria(TCriteria other) {
        if(other.isSetSymbols()) {
            List<TSymbol> __this__symbols = new ArrayList<TSymbol>(
                    other.symbols.size());
            for (TSymbol other_element : other.symbols) {
                __this__symbols.add(new TSymbol(other_element));
            }
            this.symbols = __this__symbols;
        }
    }

    public void addToSymbols(TSymbol elem) {
        if(this.symbols == null) {
            this.symbols = new ArrayList<TSymbol>();
        }
        this.symbols.add(elem);
    }

    @Override
    public void clear() {
        this.symbols = null;
    }

    @Override
    public int compareTo(TCriteria other) {
        if(!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;

        lastComparison = Boolean.valueOf(isSetSymbols())
                .compareTo(other.isSetSymbols());
        if(lastComparison != 0) {
            return lastComparison;
        }
        if(isSetSymbols()) {
            lastComparison = org.apache.thrift.TBaseHelper
                    .compareTo(this.symbols, other.symbols);
            if(lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public TCriteria deepCopy() {
        return new TCriteria(this);
    }

    @Override
    public boolean equals(Object that) {
        if(that == null)
            return false;
        if(that instanceof TCriteria)
            return this.equals((TCriteria) that);
        return false;
    }

    public boolean equals(TCriteria that) {
        if(that == null)
            return false;

        boolean this_present_symbols = true && this.isSetSymbols();
        boolean that_present_symbols = true && that.isSetSymbols();
        if(this_present_symbols || that_present_symbols) {
            if(!(this_present_symbols && that_present_symbols))
                return false;
            if(!this.symbols.equals(that.symbols))
                return false;
        }

        return true;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
        case SYMBOLS:
            return getSymbols();

        }
        throw new IllegalStateException();
    }

    public List<TSymbol> getSymbols() {
        return this.symbols;
    }

    public java.util.Iterator<TSymbol> getSymbolsIterator() {
        return (this.symbols == null) ? null : this.symbols.iterator();
    }

    public int getSymbolsSize() {
        return (this.symbols == null) ? 0 : this.symbols.size();
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();

        boolean present_symbols = true && (isSetSymbols());
        list.add(present_symbols);
        if(present_symbols)
            list.add(symbols);

        return list.hashCode();
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
        case SYMBOLS:
            return isSetSymbols();
        }
        throw new IllegalStateException();
    }

    /**
     * Returns true if field symbols is set (has been assigned a value) and
     * false otherwise
     */
    public boolean isSetSymbols() {
        return this.symbols != null;
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot)
            throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
        case SYMBOLS:
            if(value == null) {
                unsetSymbols();
            }
            else {
                setSymbols((List<TSymbol>) value);
            }
            break;

        }
    }

    public TCriteria setSymbols(List<TSymbol> symbols) {
        this.symbols = symbols;
        return this;
    }

    public void setSymbolsIsSet(boolean value) {
        if(!value) {
            this.symbols = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TCriteria(");
        boolean first = true;

        sb.append("symbols:");
        if(this.symbols == null) {
            sb.append("null");
        }
        else {
            sb.append(this.symbols);
        }
        first = false;
        sb.append(")");
        return sb.toString();
    }

    public void unsetSymbols() {
        this.symbols = null;
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        if(symbols == null) {
            throw new org.apache.thrift.protocol.TProtocolException(
                    "Required field 'symbols' was not present! Struct: "
                            + toString());
        }
        // check for sub-struct validity
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot)
            throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
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
        SYMBOLS((short) 1, "symbols");

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
            case 1: // SYMBOLS
                return SYMBOLS;
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

    private static class TCriteriaStandardScheme
            extends StandardScheme<TCriteria> {

        public void read(org.apache.thrift.protocol.TProtocol iprot,
                TCriteria struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if(schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                case 1: // SYMBOLS
                    if(schemeField.type == org.apache.thrift.protocol.TType.LIST) {
                        {
                            org.apache.thrift.protocol.TList _list0 = iprot
                                    .readListBegin();
                            struct.symbols = new ArrayList<TSymbol>(
                                    _list0.size);
                            TSymbol _elem1;
                            for (int _i2 = 0; _i2 < _list0.size; ++_i2) {
                                _elem1 = new TSymbol();
                                _elem1.read(iprot);
                                struct.symbols.add(_elem1);
                            }
                            iprot.readListEnd();
                        }
                        struct.setSymbolsIsSet(true);
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
                TCriteria struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if(struct.symbols != null) {
                oprot.writeFieldBegin(SYMBOLS_FIELD_DESC);
                {
                    oprot.writeListBegin(new org.apache.thrift.protocol.TList(
                            org.apache.thrift.protocol.TType.STRUCT,
                            struct.symbols.size()));
                    for (TSymbol _iter3 : struct.symbols) {
                        _iter3.write(oprot);
                    }
                    oprot.writeListEnd();
                }
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class TCriteriaStandardSchemeFactory
            implements SchemeFactory {
        public TCriteriaStandardScheme getScheme() {
            return new TCriteriaStandardScheme();
        }
    }

    private static class TCriteriaTupleScheme extends TupleScheme<TCriteria> {

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot,
                TCriteria struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            {
                org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(
                        org.apache.thrift.protocol.TType.STRUCT,
                        iprot.readI32());
                struct.symbols = new ArrayList<TSymbol>(_list5.size);
                TSymbol _elem6;
                for (int _i7 = 0; _i7 < _list5.size; ++_i7) {
                    _elem6 = new TSymbol();
                    _elem6.read(iprot);
                    struct.symbols.add(_elem6);
                }
            }
            struct.setSymbolsIsSet(true);
        }

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot,
                TCriteria struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            {
                oprot.writeI32(struct.symbols.size());
                for (TSymbol _iter4 : struct.symbols) {
                    _iter4.write(oprot);
                }
            }
        }
    }

    private static class TCriteriaTupleSchemeFactory implements SchemeFactory {
        public TCriteriaTupleScheme getScheme() {
            return new TCriteriaTupleScheme();
        }
    }

}
