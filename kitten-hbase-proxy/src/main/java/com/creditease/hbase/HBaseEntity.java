package com.creditease.hbase;

import java.util.Arrays;

/**
 * @author: GaoYS
 * @CreateData: 2015/8/19 18:39
 */
public class HBaseEntity {
    private String tableName;
    private String family;
    private byte[] key;
    private String qualifiers;
    private String values;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(String qualifiers) {
        this.qualifiers = qualifiers;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "HBaseEntity{" +
                "tableName='" + tableName + '\'' +
                ", family='" + family + '\'' +
                ", key=" + Arrays.toString(key) +
                ", qualifiers='" + qualifiers + '\'' +
                ", values='" + values + '\'' +
                '}';
    }
}