package com.jw.tcdialogplugin;

public class TcBean {
    private String title;
    private String content;
    private Boolean isUseing;

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

    public Boolean getUseing() {
        return isUseing;
    }

    public void setUseing(Boolean useing) {
        isUseing = useing;
    }

    public TcBean(String title, String content, Boolean isUseing) {
        this.title = title;
        this.content = content;
        this.isUseing = isUseing;
    }
}