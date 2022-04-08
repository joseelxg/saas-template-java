package com.hailas.common.core.domain;

/**
 * 国家实体
 * Created by wulin on 2017/6/13.
 */
public class Country {

    private int id;
    private String name;
    private String cname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
