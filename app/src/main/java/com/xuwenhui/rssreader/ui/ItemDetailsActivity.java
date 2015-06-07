package com.xuwenhui.rssreader.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xuwenhui.rssreader.R;

/**
 * 每个RSSItem详细显示页面
 * Created by xwh on 2015/6/2.
 */
public class ItemDetailsActivity extends Activity {

    //各种组件
    private TextView textView_title;
    private WebView webView_webContent;
    private TextView textView_link;
    private Button button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);

        //初始化组件
        initView();

        //组件数据加载
        setViewData();
    }

    private void initView() {
        textView_title = (TextView) findViewById(R.id.title);
        webView_webContent = (WebView) findViewById(R.id.webContent);
        textView_link = (TextView) findViewById(R.id.link);
        button_back = (Button) findViewById(R.id.back);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setViewData() {
        //获得启动的Intent
        Intent startIntent = getIntent();
        if (startIntent != null) {
            Bundle bundle = startIntent.getExtras();
            if (bundle == null) {
                Toast.makeText(this, "加载出错", Toast.LENGTH_LONG).show();
            } else {
                //设置标题和发布时间
                String title = bundle.getString("title") + "\n" + bundle.getString("pubDate");
                textView_title.setText(title);
                //设置内容
                String webContent = bundle.getString("description");
                webView_webContent.loadDataWithBaseURL(null, webContent, "text/html", "utf-8", null);

                WebSettings settings = webView_webContent.getSettings();
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                //settings.setBuiltInZoomControls(true);
                //settings.setSupportZoom(true);
                //设置网站链接
                String link = "详细信息请访问以下网址：\n" + bundle.getString("link");
                textView_link.setText(link);
            }
        }
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
