package com.devnote.models;

import java.sql.Blob;

public class Filebean {
    int id;
    String title;
    Object content;

    @Override
    public String toString(){
        return title;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
