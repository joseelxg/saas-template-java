package com.hailas.common.core.domain;

import java.util.List;

/**
 * 树状结构
 * Created by wulin on 2017/11/23.
 */
public class Tree {
    private String text;
    private String value;
    private List<Tree> children;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }
}
