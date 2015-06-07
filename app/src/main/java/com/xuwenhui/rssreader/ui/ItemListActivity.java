package com.xuwenhui.rssreader.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.xuwenhui.rssreader.R;
import com.xuwenhui.rssreader.entity.RSSFeed;
import com.xuwenhui.rssreader.utils.PubDateHandler;
import com.xuwenhui.rssreader.utils.RSSHandler;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 列出RSS源下所有Items的页面
 * Created by xwh on 2015/6/2.
 */
public class ItemListActivity extends Activity implements AdapterView.OnItemClickListener {

    //RSS源的地址
    private String RSS_URL = "";
    //RSS源
    private RSSFeed feed = null;
    //消息传递
    private Handler mHandler = null;
    //进度框
    private ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载进度框
        loadProgressDialog();

        //获得启动的Intent里的RSS_URL
        getIntentRSSURL();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    mProgressDialog.cancel();
                    //显示RSS内容到ListView
                    showListView();
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                //SAX解析网络RSS资源
                feed = getFeed(RSS_URL);
                mHandler.sendEmptyMessage(0x123);
            }
        }.start();
    }

    private void loadProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ItemListActivity.this);
            mProgressDialog.setTitle("请等待");
            mProgressDialog.setMessage("正在拼命加载中···");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    private void getIntentRSSURL() {
        //获得启动的Intent
        Intent startIntent = getIntent();
        if (startIntent != null) {
            Bundle bundle = startIntent.getExtras();
            if (bundle == null) {
                Toast.makeText(this, "加载出错", Toast.LENGTH_LONG).show();
            } else {
                RSS_URL = bundle.getString("RSS_URL");
            }
        }
    }

    private RSSFeed getFeed(String urlString) {
        try {
            URL url = new URL(urlString);
            InputStream is = url.openStream();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            RSSHandler rssHandler = new RSSHandler();
            parser.parse(is, rssHandler);

            /*AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("tech_rss.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            RSSHandler rssHandler = new RSSHandler();
            parser.parse(is,rssHandler);*/
            return rssHandler.getFeed();
        } catch (Exception e) {
            return null;
        }
    }

    private void showListView() {
        ListView itemList = (ListView) findViewById(R.id.listView_index);

        if (feed == null) {
            Toast.makeText(this, "请检查网络连接", Toast.LENGTH_LONG).show();
            mProgressDialog.cancel();
            return;
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                feed.getAllItemsForListView(),
                android.R.layout.simple_list_item_2,
                new String[]{"title", "pubDate"},
                new int[]{android.R.id.text1, android.R.id.text2});
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(this);
        itemList.setSelection(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ItemDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("title", feed.getItem(position).getTitle());
        bundle.putString("link", feed.getItem(position).getLink());
        bundle.putString("description", feed.getItem(position).getDescription());
        //时间格式处理：GMT转换成自定义时间格式
        bundle.putString("pubDate", PubDateHandler.GMTToLocale(feed.getItem(position).getPubDate()));

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
