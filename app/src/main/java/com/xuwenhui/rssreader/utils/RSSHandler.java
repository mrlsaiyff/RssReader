package com.xuwenhui.rssreader.utils;

import com.xuwenhui.rssreader.entity.RSSFeed;
import com.xuwenhui.rssreader.entity.RSSItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 对RSS源进行SAX解析
 * Created by xwh on 2015/6/2.
 */
public class RSSHandler extends DefaultHandler {

    //RSS源
    private RSSFeed mRSSFeed;
    //RSS源下的RSS项目
    private RSSItem mRSSItem;
    //节点内容
    private StringBuffer mStringBuffer;

    public RSSHandler() {
        mStringBuffer = new StringBuffer();
    }

    /**
     * 对外提供的方法:返回解析完成的RSSFeed
     *
     * @return mRSSFeed
     */
    public RSSFeed getFeed() {
        return mRSSFeed;
    }

    //开始解析文档
    @Override
    public void startDocument() throws SAXException {
    }

    //文档解析结束
    @Override
    public void endDocument() throws SAXException {
    }

    //开始解析节点
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        mStringBuffer = new StringBuffer();
        if (localName.equals("channel")) {
            mRSSFeed = new RSSFeed();
            return;
        }
        if (localName.equals("item")) {
            mRSSItem = new RSSItem();
            return;
        }
    }

    //节点解析结束
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //处理channel频道的信息
        if (mRSSItem == null) {
            if (localName.equals("title")) {
                mRSSFeed.setTitle(mStringBuffer.toString());
                return;
            }
            if (localName.equals("link")) {
                mRSSFeed.setLink(mStringBuffer.toString());
                return;
            }
            if (localName.equals("description")) {
                mRSSFeed.setDescription(mStringBuffer.toString());
                return;
            }
        }
        //处理item项目的信息
        else {
            if (localName.equals("item")) {
                mRSSFeed.addItem(mRSSItem);
                return;
            }
            if (localName.equals("title")) {
                mRSSItem.setTitle(mStringBuffer.toString());
                return;
            }
            if (localName.equals("link")) {
                mRSSItem.setLink(mStringBuffer.toString());
                return;
            }
            if (localName.equals("description")) {
                mRSSItem.setDescription(mStringBuffer.toString());
                return;
            }
            if (localName.equals("pubDate")) {
                mRSSItem.setPubDate(mStringBuffer.toString());
                return;
            }
        }
    }

    //处理节点内容
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String string = new String(ch, start, length);
        mStringBuffer.append(string);
    }
}
