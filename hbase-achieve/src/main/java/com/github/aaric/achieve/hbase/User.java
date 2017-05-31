package com.github.aaric.achieve.hbase;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * User
 *
 * @author Aaric, created on 2017-05-25T09:18.
 * @since 1.0-SNAPSHOT
 */
public class User {

    /**
     * Table name
     */
    public static final String TABLE_NAME = "user";

    /**
     * Family name
     */
    public static final String FAMILY_NAME = "key";

    /**
     * Properties
     */
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_EMAIL = "email";
    public static final String PROPERTY_PASSWORD = "password";

    private Integer id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
