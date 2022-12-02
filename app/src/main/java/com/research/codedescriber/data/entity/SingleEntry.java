package com.research.codedescriber.data.entity;

import org.jetbrains.annotations.NotNull;

public class SingleEntry {
    private int id;
    private String code;
    private String description;

    public SingleEntry(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public SingleEntry() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @NotNull
    @Override
    public String toString() {
        return code + " - " + description;
    }
}
