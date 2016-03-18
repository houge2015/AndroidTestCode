package cn.mengge.youthdiary;

import android.app.ActionBar;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

import db.server.CreateSQL;

public class DiaryActivity extends Activity {
	private EditText write_ed;
	private Button store_bt;
	private Button clear_bt;
	// private final String app_main_path = "/YouthDiary";
	// private final String diary_path = "/MyDiary";
	private SharedPreferences setting_pref;
	private MediaPlayer bg_music;

	private LinearLayout layout;

	private static long INTERVAL = 2000; // 两次返回键的事件间隔
	private long mFirstBackKeyPressTime = -1; // 第一次按下返回键的时间
	private long mLastBackKeyPressTime = -1; // 第二次按下返回键的时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);
		setActionBar();
		init();
	}

	// 初始化控件并设置监听事件
	private void init() {
		setting_pref = getSharedPreferences("SETTING", MODE_PRIVATE);

		if (setting_pref.getString("THEME", "ORIGIN").equals("TRANSPARENT")) {
			layout = (LinearLayout) findViewById(R.id.diary_layout);
			themeTransparent();
		}

		bg_music = MediaPlayer.create(DiaryActivity.this, R.raw.moon);
		if (setting_pref.getBoolean("BG_MUSIC_SWITCH", true)) {
			bg_music.start();
			bg_music.setLooping(true);
		}

		write_ed = (EditText) findViewById(R.id.diary_write_ed);

		store_bt = (Button) findViewById(R.id.diary_store_bt);
		store_bt.setOnClickListener(new store_btHandlerListener());

		clear_bt = (Button) findViewById(R.id.diary_clear_bt);
		clear_bt.setOnClickListener(new clear_btHandlerListener());
	}

	// 创建选项菜单，此方法由Activity自动创建
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.diary_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.diary_menu_play:
			if (setting_pref.getBoolean("BG_MUSIC_SWITCH", true)) {
				bg_music.start();
				Toast.makeText(DiaryActivity.this,
						R.string.diary_menu_start_play_text, Toast.LENGTH_LONG)
						.show();
			} else
				Toast.makeText(DiaryActivity.this,
						R.string.diary_menu_open_bg_play_setting_text,
						Toast.LENGTH_LONG).show();
			break;
		case R.id.diary_menu_pause:
			bg_music.pause();
			Toast.makeText(DiaryActivity.this,
					R.string.diary_menu_pause_play_text, Toast.LENGTH_LONG)
					.show();
			break;
		case R.id.diary_menu_setting:
			Intent setting_intent = new Intent();
			setting_intent.setClass(DiaryActivity.this, SettingActivity.class);
			startActivity(setting_intent);
			break;
		case R.id.diary_menu_history:
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				Intent history_intent = new Intent();
				history_intent.setClass(DiaryActivity.this,
						HistoryActivity.class);
				startActivity(history_intent);
			} else
				Toast.makeText(DiaryActivity.this,
						R.string.diary_menu_null_sd_text, Toast.LENGTH_LONG)
						.show();
			break;
		case R.id.diary_menu_about_us:
			Intent about_us_intent = new Intent();
			about_us_intent.setClass(DiaryActivity.this, AboutUsActivity.class);
			startActivity(about_us_intent);
			break;
		case R.id.diary_menu_web_broswer:
			Intent web_broswer = new Intent();
			web_broswer.setClass(DiaryActivity.this, WebBroswerActivity.class);
			startActivity(web_broswer);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 保存按钮的监听事件
	private final class store_btHandlerListener implements View.OnClickListener {
		public void onClick(View view) {

			if (write_ed.getText().toString().length() == 0)
				Toast.makeText(DiaryActivity.this,
						R.string.diary_null_content_text, Toast.LENGTH_LONG)
						.show();

			else {
				Calendar calendar = Calendar.getInstance();
				String year = Integer.toString(calendar.get(Calendar.YEAR));
				String month = Integer
						.toString(calendar.get(Calendar.MONTH) + 1);
				String day = Integer.toString(calendar
						.get(Calendar.DAY_OF_MONTH));
				String hour = Integer.toString(calendar
						.get(Calendar.HOUR_OF_DAY));
				String minute = Integer.toString(calendar.get(Calendar.MINUTE));
				String second = Integer.toString(calendar.get(Calendar.SECOND));

				// String file_name = "/" + calendar.get(Calendar.YEAR) + "年"
				// + (calendar.get(Calendar.MONTH) + 1) + "月"
				// + calendar.get(Calendar.DAY_OF_MONTH) + "日"
				// + calendar.get(Calendar.HOUR) + "时"
				// + calendar.get(Calendar.MINUTE) + "分"
				// + calendar.get(Calendar.SECOND) + "秒";

				CreateSQL createSQL = new CreateSQL(DiaryActivity.this,
						"Diary.db", 1);
				String time = calendar.get(Calendar.YEAR) + "年"
						+ (calendar.get(Calendar.MONTH) + 1) + "月"
						+ calendar.get(Calendar.DAY_OF_MONTH) + "日"
						+ calendar.get(Calendar.HOUR_OF_DAY) + "时"
						+ calendar.get(Calendar.MINUTE) + "分"
						+ calendar.get(Calendar.SECOND) + "秒";

				String title = year + month + day + hour + minute + second;
				String content = write_ed.getText().toString();

				createSQL.addDiary("DiaryDb", time, title, content);

				// FileService fileService = new FileService(getBaseContext());
				//
				// boolean flag = fileService.saveFile2SDcard(app_main_path
				// + diary_path, file_name, write_ed.getText().toString());
				//
				// if (flag) {
				write_ed.setText("");
				Toast.makeText(DiaryActivity.this,
						R.string.diary_success_store_text, Toast.LENGTH_LONG)
						.show();
				// } else
				// Toast.makeText(DiaryActivity.this,
				// R.string.diary_fail_store_text, Toast.LENGTH_SHORT)
				// .show();
			}
		}
	}

	// 清空按钮的监听事件
	private final class clear_btHandlerListener implements View.OnClickListener {
		public void onClick(View view) {
			write_ed.setText("");
			Toast.makeText(DiaryActivity.this,
					R.string.diary_success_clear_text, Toast.LENGTH_SHORT)
					.show();
		}
	}

	// 设置ActionBar标题
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.diary_action_bar_title_text);
	}

	// 按返回按钮退出
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mFirstBackKeyPressTime == -1) { // 还未按下过返回键
			mFirstBackKeyPressTime = System.currentTimeMillis(); // 设置第一次按下的时间为当前系统时间
			Toast.makeText(DiaryActivity.this,
					R.string.diary_press_again_exit_text, Toast.LENGTH_LONG)
					.show();
		} else {
			mLastBackKeyPressTime = System.currentTimeMillis(); // 设置第二次按下的时间为当前系统时间
			if ((mLastBackKeyPressTime - mFirstBackKeyPressTime) <= INTERVAL) { // 判断2次时间间隔是否在2秒内
				finish(); // 退出当前Activity
				System.exit(0); // 退出程序
				super.onBackPressed();
			} else { // 间隔超过2秒需再次按下
				mFirstBackKeyPressTime = System.currentTimeMillis();
				Toast.makeText(DiaryActivity.this,
						R.string.diary_press_again_exit_text, Toast.LENGTH_LONG)
						.show();
			}
		}
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
