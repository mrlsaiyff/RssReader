package com.xuwenhui.rssreader.entity;

/**
 * RSS源(RSS频道)的一个项目
 * Created by xwh on 2015/6/2.
 */
public class RSSItem {

    //项目的标题
    private String title;
    //项目的超链接
    private String link;
    //项目的描述
    private String description;
    //发布时间
    private String pubDate;

    public RSSItem() {
        title = null;
        link = null;
        description = null;
        pubDate = null;
    }

    /**
     * 以下均是自动生成的get、set方法
     * 包括的成员变量：title、link、description、pubDate
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
