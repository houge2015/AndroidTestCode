package cn.mengge.youthdiary;

import cn.mengge.youthdiary.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
	private LinearLayout layout;
	private SharedPreferences theme_pref;
	private Button about_me;
	private TextView version_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		setActionBar();
		init();
	}

	// 初始化控件并设置监听事件
	private void init() {
		theme_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		if (theme_pref.getString("THEME", "ORIGIN").equals("TRANSPARENT")) {
			layout = (LinearLayout) findViewById(R.id.about_us_layout);
			themeTransparent();
		}

		about_me = (Button) findViewById(R.id.about_us_bt);
		about_me.setOnClickListener(new about_meButtonHandlerListener());

		version_name = (TextView) findViewById(R.id.about_us_version_tv);
		version_name.setText("版本:" + getVersionName());

	}

	public String getVersionName() {
		PackageManager packageManager = this.getPackageManager();
		try {
			PackageInfo info = packageManager.getPackageInfo(
					this.getPackageName(), 0);
			String version = info.versionName;

			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	// 图片按钮监听事件
	private final class about_meButtonHandlerListener implements
			View.OnClickListener {
		public void onClick(View view) {
			AlertDialog.Builder about_me_dl = new AlertDialog.Builder(
					AboutUsActivity.this);
			about_me_dl.setTitle(R.string.about_us_dl_author_text);
			about_me_dl.setMessage(R.string.about_us_dl_message_text);
			about_me_dl.setPositiveButton(R.string.about_us_dl_positive_text,
					new about_me_dlHandlerListener());
			about_me_dl.setCancelable(false);
			AlertDialog dialog = about_me_dl.create();
			dialog.show();
		}
	}

	// 对话框确定按钮监听事件
	private final class about_me_dlHandlerListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialogInterface, int i) {
			dialogInterface.dismiss();
		}
	}

	// 设置ActionBar标题
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.about_us_action_bar_title_text);
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
