package com.hailas.common.core.domain;

import java.util.*;

/**
 * @author wulin
 * @version 1.0.0
 * @created 2020/1/8.
 */
public class IndexList<T> {
    private List<Index<T>> list;
    private Map<String,Integer> map;

    public IndexList() {
        this.list = new ArrayList<>();
        map = new HashMap<>();
    }

    public void addItem(String name, T o){
        Integer i = map.get(name);
        if (i!=null){
            list.get(i).addItem(o);
        }else {
            Index<T> index = new Index<>(name);
            index.addItem(o);
            list.add(index);
            map.put(name,list.size()-1);
        }
    }

    public List<Index<T>> getList() {
        return list;
    }

    public void setList(List<Index<T>> list) {
        this.list = list;
    }

    public void sort() {
        Collections.sort(list, new Comparator<Index<T>>() {
            @Override
            public int compare(Index<T> o1, Index<T> o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
}
