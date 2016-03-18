package cn.mengge.youthdiary;

import db.server.CreateSQL;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewActivity extends Activity {
	private TextView name_tx;
	private TextView content_tx;

	private LinearLayout layout;
	private SharedPreferences theme_pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		setActionBar();
		init();
	}

	// 初始化控件并设置监听事件
	private void init() {
		theme_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		if (theme_pref.getString("THEME", "ORIGIN").equals("TRANSPARENT")) {
			layout = (LinearLayout) findViewById(R.id.view_layout);
			themeTransparent();
		}

		name_tx = (TextView) findViewById(R.id.view_name_tx);
		content_tx = (TextView) findViewById(R.id.view_content_tx);
		Intent name_intent = getIntent();
		String file_name = name_intent.getStringExtra("FILE_NAME");

		CreateSQL readContent = new CreateSQL(ViewActivity.this, "Diary.db", 1);
		String content = readContent.readDiaryContent("DiaryDb", file_name);

		// FileService fileService = new FileService(getBaseContext());
		// String content =
		// fileService.readFileFromSDcard("/YouthDiary/MyDiary/",
		// file_name);
		name_tx.setText(file_name);
		content_tx.setText(content);
	}

	// 设置ActionBar标题
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.view_action_bar_title_text);
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

	// 透明主题
	private void themeTransparent() {
		// 获取WallpaperManager 壁纸管理器
		WallpaperManager wallpaperManager = WallpaperManager
				.getInstance(getBaseContext()); // 获取壁纸管理器
		// 获取当前壁纸
		Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		// 设置 背景
		if (Build.VERSION.SDK_INT >= 16) {
			layout.setBackground(wallpaperDrawable);
		} else {
			layout.setBackgroundDrawable(wallpaperDrawable);
		}
	}
}
