package com.yxliu.demo.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Category extends LitePalSupport implements Serializable {
    private Integer id;
    private String name;
    private Integer code;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
