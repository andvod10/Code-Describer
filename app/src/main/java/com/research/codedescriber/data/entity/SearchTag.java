package com.research.codedescriber.data.entity;

import org.jetbrains.annotations.NotNull;

public class SearchTag {
    private int idOfSearchedEntry;
    private String searchData;

    public SearchTag(int idOfSearchedEntry, String searchData) {
        this.idOfSearchedEntry = idOfSearchedEntry;
        this.searchData = searchData;
    }

    public SearchTag() {}

    public void setIdOfSearchedEntry(int idOfSearchedEntry) {
        this.idOfSearchedEntry = idOfSearchedEntry;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public int getIdOfSearchedEntry() {
        return idOfSearchedEntry;
    }

    public String getSearchData() {
        return searchData;
    }

    @NotNull
    @Override
    public String toString() {
        return idOfSearchedEntry + " - " + searchData;
    }
}
