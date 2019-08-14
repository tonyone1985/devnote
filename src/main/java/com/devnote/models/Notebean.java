package com.devnote.models;

public class Notebean {
    int id;
    String title;
    String content;

    @Override
    public String toString(){
        //return id+":"+title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
