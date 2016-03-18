package cn.mengge.youthdiary;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import db.server.CreateSQL;
import db.server.MySQLHelper;

public class HistoryActivity extends Activity {
	private ListView list_lv;
	private int choose_position;
	private List<String> list;
	private CreateSQL readSql;
	private CharSequence[] charSequence = { "查看", "修改", "删除" };

	private RelativeLayout layout;
	private SharedPreferences theme_pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		setActionBar();
		init();
	}

	// 初始化控件并设置监听事件
	private void init() {

		theme_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		if (theme_pref.getString("THEME", "ORIGIN").equals("TRANSPARENT")) {
			layout = (RelativeLayout) findViewById(R.id.history_layout);
			themeTransparent();
		}

		readSql = new CreateSQL(HistoryActivity.this, "Diary.db", 1);
		list = readSql.readDiaryTitle("DiaryDb");

		list_lv = (ListView) findViewById(R.id.history_list_lv);
		// list = new ArrayList();
		// list = showAllFile();
		list_lv.setAdapter(new ArrayAdapter(HistoryActivity.this,
				android.R.layout.simple_expandable_list_item_1, list));
		list_lv.setOnItemClickListener(new list_lvHandlerListener());
	}

	// ListView点击监听事件
	private final class list_lvHandlerListener implements
			AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			choose_position = position;
			AlertDialog.Builder operate_dialog = new AlertDialog.Builder(
					HistoryActivity.this);
			operate_dialog.setTitle(R.string.history_dl_tips_text);
			operate_dialog.setItems(charSequence,
					new operate_dialogHandlerListener());
			AlertDialog dialog = operate_dialog.create();
			dialog.show();
		}
	}

	// 操作对话框监听事件
	private final class operate_dialogHandlerListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case 0:
				Intent view_intent = getIntent();
				view_intent.putExtra("FILE_NAME", list.get(choose_position)
						.toString());
				view_intent.setClass(HistoryActivity.this, ViewActivity.class);
				startActivity(view_intent);
				break;
			case 1:
				Intent change_intent = getIntent();
				change_intent.putExtra("FILE_NAME", list.get(choose_position)
						.toString());
				change_intent.setClass(HistoryActivity.this,
						ChangeActivity.class);
				startActivity(change_intent);
				break;
			case 2:
				AlertDialog.Builder confirm_dialog = new AlertDialog.Builder(
						HistoryActivity.this);
				confirm_dialog.setTitle(R.string.history_dl_confirm_del);
				confirm_dialog
						.setMessage(R.string.history_dl_confirm_del_msg_text);
				confirm_dialog.setPositiveButton(
						R.string.history_dl_confirm_del_positive_text,
						new confirm_dlPositiveHandlerListener());
				confirm_dialog.setNegativeButton(
						R.string.history_dl_confirm_delnn_negetive_text,
						new confirm_dlNegativeHandlerListener());
				AlertDialog dialog1 = confirm_dialog.create();
				dialog1.show();
				break;
			}
		}
	}

	// 删除对话框确认按钮监听事件
	private final class confirm_dlPositiveHandlerListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// FileService fileService = new FileService(getBaseContext());
			// fileService.deleteFileFromSD("/YouthDiary/MyDiary/",
			// list.get(choose_position).toString());
			String[] fileName = { list.get(choose_position).toString() };
			if (readSql.deleteDiary("DiaryDb", fileName)) {
				Toast.makeText(HistoryActivity.this,
						R.string.history_dl_success_del_text,
						Toast.LENGTH_SHORT).show();
				finish();
				Intent this_intent = new Intent(HistoryActivity.this,
						HistoryActivity.class);
				startActivity(this_intent);
			}
			Toast.makeText(HistoryActivity.this,
					R.string.history_dl_success_del_text, Toast.LENGTH_SHORT)
					.show();
		}
	}

	// 删除对话框取消按钮监听事件
	private final class confirm_dlNegativeHandlerListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}

	// // 列出所有文件
	// private List showAllFile() {
	// String path = Environment.getExternalStorageDirectory()
	// + "/YouthDiary/MyDiary/";
	// File file = new File(path);
	// if (file.exists()) {
	// List<String> list = new ArrayList();
	// for (int i = 0; i < file.list().length; i++) {
	// list.add(file.list()[i]);
	// }
	// return list;
	// }
	// return null;
	// }

	// 设置ActionBar标题
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.history_action_bar_title_text);
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
