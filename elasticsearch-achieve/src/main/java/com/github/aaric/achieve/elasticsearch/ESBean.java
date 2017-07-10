package com.github.aaric.achieve.elasticsearch;

/**
 * ESBean
 *
 * @author Aaric, created on 2017-07-10T11:44.
 * @since 1.0-SNAPSHOT
 */
public class ESBean {

    private String index;
    private String type;
    private String id;
    private String data;

    public ESBean() {
    }

    public ESBean(String index, String type, String id, String data) {
        this.index = index;
        this.type = type;
        this.id = id;
        this.data = data;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ESBean{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
