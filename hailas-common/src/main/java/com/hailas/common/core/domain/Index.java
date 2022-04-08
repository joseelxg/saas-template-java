package com.hailas.common.core.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wulin
 * @version 1.0.0
 * @created 2020/1/8.
 */
public class Index<T> {
    private String name;
    private List<T> list;

    public Index() {
    }

    public Index(String name) {
        this.name = name;
        list = new LinkedList<>();
    }

    public void addItem(T o){
        list.add(o);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
