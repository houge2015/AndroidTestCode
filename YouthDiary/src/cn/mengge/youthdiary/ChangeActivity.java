package cn.mengge.youthdiary;

import db.server.CreateSQL;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeActivity extends Activity {
	private TextView name_tx;
	private EditText content_ed;
	private Button store_bt;
	private Button cancel_bt;
	private String content;
	private String file_name;

	private LinearLayout layout;
	private SharedPreferences theme_pref;

	CreateSQL changeContent = new CreateSQL(ChangeActivity.this, "Diary.db", 1);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		setActionBar();
		init();
	}

	// 初始化控件并设置监听事件
	private void init() {

		theme_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		if (theme_pref.getString("THEME", "ORIGIN").equals("TRANSPARENT")) {
			layout = (LinearLayout) findViewById(R.id.change_layout);
			themeTransparent();
		}

		name_tx = (TextView) findViewById(R.id.change_name_tx);
		content_ed = (EditText) findViewById(R.id.change_write_ed);
		store_bt = (Button) findViewById(R.id.change_store_bt);
		store_bt.setOnClickListener(new store_btHandlerListener());
		cancel_bt = (Button) findViewById(R.id.change_clear_bt);
		cancel_bt.setOnClickListener(new cancel_btHandlerListener());

		Intent name_intent = getIntent();
		file_name = name_intent.getStringExtra("FILE_NAME");
		content = changeContent.readDiaryContent("DiaryDb", file_name);

		// FileService fileService = new FileService(getBaseContext());
		// content = fileService.readFileFromSDcard("/YouthDiary/MyDiary/",
		// file_name);
		name_tx.setText(file_name);
		content_ed.setText(content);
	}

	private final class store_btHandlerListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			AlertDialog.Builder confirm_store_dl = new AlertDialog.Builder(
					ChangeActivity.this);
			confirm_store_dl.setTitle(R.string.change_dl_title_text);
			confirm_store_dl.setMessage(R.string.change_dl_store_message_text);
			confirm_store_dl.setPositiveButton(
					R.string.change_dl_positive_text,
					new confirm_dl_storePosHandlerListener());
			confirm_store_dl.setNegativeButton(
					R.string.change_dl_negative_text,
					new confirm_dl_storeNegHandlerListener());
			confirm_store_dl.setCancelable(false);
			AlertDialog dialog = confirm_store_dl.create();
			dialog.show();
		}
	}

	private final class confirm_dl_storePosHandlerListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {

			String content = content_ed.getText().toString();
			String[] title = { file_name };
			changeContent.changeDiaryContent("DiaryDb", title, content);
			// FileService fileService = new FileService(getBaseContext());
			// fileService.saveFile2SDcard("/YouthDiary/MyDiary/", file_name,
			// content_ed.getText().toString());
			dialog.dismiss();
			Toast.makeText(ChangeActivity.this,
					R.string.change_success_change_text, Toast.LENGTH_LONG)
					.show();
		}
	}

	private final class confirm_dl_storeNegHandlerListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}

	private final class cancel_btHandlerListener implements
			View.OnClickListener {
		@Override
		public void onClick(View v) {
			AlertDialog.Builder confirm_cancel_dl = new AlertDialog.Builder(
					ChangeActivity.this);
			confirm_cancel_dl.setTitle(R.string.change_dl_title_text);
			confirm_cancel_dl
					.setMessage(R.string.change_dl_cancel_confirm_text);
			confirm_cancel_dl.setPositiveButton(
					R.string.change_dl_positive_text,
					new confirm_dl_cancelPosHandlerListener());
			confirm_cancel_dl.setNegativeButton(
					R.string.change_dl_negative_text,
					new confirm_dl_cancelNegHandlerListener());
			confirm_cancel_dl.setCancelable(false);
			AlertDialog dialog = confirm_cancel_dl.create();
			dialog.show();
		}
	}

	private final class confirm_dl_cancelPosHandlerListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			content_ed.setText(content);
			dialog.dismiss();
			Toast.makeText(ChangeActivity.this,
					R.string.change_success_cancel_text, Toast.LENGTH_LONG)
					.show();
		}
	}

	private final class confirm_dl_cancelNegHandlerListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}

	// 设置ActionBar标题
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.change_action_bar_title_text);
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
