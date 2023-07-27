package com.yuchao.toolsforcostengineerbackend.checkbillquota.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RowType {
    TITLE(0, "分部标题"), BILL(1, "清单"), QUOTA(2, "定额");

    private Integer id;
    private String name;

    private RowType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private RowType(Integer id) {
        this.id = id;
    }

    private RowType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
