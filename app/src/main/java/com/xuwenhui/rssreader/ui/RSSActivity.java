package com.xuwenhui.rssreader.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xuwenhui.rssreader.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展示所有RSS资源的页面
 * Created by xwh on 2015/6/7.
 */
public class RSSActivity extends Activity implements AdapterView.OnItemClickListener {

    //RSS源地址
    private String[] RSS_URL = new String[]{
            "http://n.rss.qq.com/rss/tech_rss.php",
            "http://36kr.com/feed",
            "http://9.douban.com/rss/technology",
            "http://movie.douban.com/feed/review/movie",
            "http://www.douban.com/feed/group/Gia-club/discussion"
    };
    //RSS源名字
    private String[] RSS_Name = new String[]{"腾讯", "36氪", "九点", "豆瓣电影", "我们爱讲冷笑话"};
    //RSS源图片
    private int[] RSS_image = new int[]{
            R.drawable.logo_tecent,
            R.drawable.logo_36kr,
            R.drawable.logo_jiudian,
            R.drawable.logo_douban,
            R.drawable.logo_lengxiaohua
    };

    //RSS列表视图
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        mListView = (ListView) findViewById(R.id.listView_rss);

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < RSS_URL.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("RSS_Name", RSS_Name[i]);
            map.put("RSS_image", RSS_image[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.list_source,
                new String[]{"RSS_Name", "RSS_image"},
                new int[]{R.id.text_rss_name, R.id.image_rss_img});
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mListView.setSelection(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ItemListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("RSS_URL", RSS_URL[position]);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
