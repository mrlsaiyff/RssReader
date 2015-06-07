package com.xuwenhui.rssreader.entity;

import com.xuwenhui.rssreader.utils.PubDateHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RSS源(RSS频道)
 * Created by xwh on 2015/6/2.
 */
public class RSSFeed {

    //频道的标题
    private String title;
    //频道的超链接
    private String link;
    //频道的描述
    private String description;

    //频道下item的集合
    private List<RSSItem> itemList;

    //构造函数
    public RSSFeed() {
        title = null;
        link = null;
        description = null;
        itemList = new ArrayList<>();
    }

    //添加item
    public void addItem(RSSItem item) {
        itemList.add(item);
    }

    //获取location位置上的item
    public RSSItem getItem(int location) {
        return itemList.get(location);
    }

    //为ListView配置所需Item
    public List<Map<String, Object>> getAllItemsForListView() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", itemList.get(i).getTitle());
            //时间格式处理：GMT转换成自定义时间格式
            map.put("pubDate", PubDateHandler.GMTToLocale(itemList.get(i).getPubDate()));
            list.add(map);
        }
        return list;
    }


    /**
     * 以下均是自动生成的get、set方法
     * 包括的成员变量：title、link、description
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

}
