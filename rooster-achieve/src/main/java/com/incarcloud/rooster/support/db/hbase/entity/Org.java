package com.incarcloud.rooster.support.db.hbase.entity;

import com.incarcloud.rooster.support.abs.AbstractEntityObject;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Org
 *
 * @author Aaric, created on 2017-05-27T14:23.
 * @since 1.0-SNAPSHOT
 */
public class Org extends AbstractEntityObject {

    public static final String TABLE_NAME = "org";
    public static final String FAMILY_NAME = "key";

    private Integer id;
    private Integer parentId;
    private String name;
    private String code;
    private Integer type;

    public Org() {
    }

    public Org(Integer id, String name, String code, Integer type) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.type = type;
    }

    public Org(Integer id, Integer parentId, String name, String code, Integer type) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.code = code;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
