package cn.mengge.youthdiary;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by MGE on 2016/2/26.
 */
public class WebBroswerActivity extends Activity {
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar();
		init();
	}

	private void init() {
		setContentView(R.layout.activity_web_broswer);
		webView = (WebView) findViewById(R.id.web_broswer);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://www.ntu.edu.cn");
	}

	// 设置ActionBar标题
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.web_broswer_action_bar_title_text);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
	}

	// ActionBar返回箭头点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
