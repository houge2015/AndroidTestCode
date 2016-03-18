package cn.mengge.youthdiary;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity extends Activity {
	private LinearLayout set_layout;
	private Switch music_bg_sw;
	private RadioGroup theme_rg;
	private RadioButton theme_origin_rb;
	private RadioButton theme_transparent_rb;
	private EditText user_name_ed;
	private EditText passwd_ed;
	private EditText passwd_again_ed;
	private Button confirm_bt;
	private Button cancel_bt;
	private SharedPreferences setting_pref;
	private SharedPreferences.Editor setting_pref_editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setActionBar();
		init();
	}

	// 初始化控件并设置监听事件
	private void init() {
		setting_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		setting_pref_editor = setting_pref.edit();

		theme_rg = (RadioGroup) findViewById(R.id.setting_theme_switch_rg);
		theme_rg.setOnCheckedChangeListener(new theme_rgHandlerListener());
		theme_origin_rb = (RadioButton) findViewById(R.id.setting_theme_origin_rb);
		theme_transparent_rb = (RadioButton) findViewById(R.id.setting_theme_transparent_rb);
		if (setting_pref.getString("THEME", "ORIGIN").equals("ORIGIN")) {
			theme_origin_rb.setChecked(true);
		}
		if (setting_pref.getString("THEME", "ORIGIN").equals("TRANSPARENT")) {
			set_layout = (LinearLayout) findViewById(R.id.setting_layout);
			themeTransparent();
			theme_transparent_rb.setChecked(true);
		}

		music_bg_sw = (Switch) findViewById(R.id.setting_switch_music_tb);
		music_bg_sw
				.setOnCheckedChangeListener(new music_bg_tbHandlerListener());
		music_bg_sw
				.setChecked(setting_pref.getBoolean("BG_MUSIC_SWITCH", true));
		user_name_ed = (EditText) findViewById(R.id.setting_user_name_ed);
		passwd_ed = (EditText) findViewById(R.id.setting_passwd_ed);
		passwd_again_ed = (EditText) findViewById(R.id.setting_confirm_passwd_ed);
		confirm_bt = (Button) findViewById(R.id.setting_flash_bt);
		confirm_bt.setOnClickListener(new confirm_btHandlerListener());
		cancel_bt = (Button) findViewById(R.id.setting_canncel_bt);
		cancel_bt.setOnClickListener(new cancel_btHandlerListener());
	}

	private final class music_bg_swHandlerListener implements
			CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (music_bg_sw.isChecked()) {
				setting_pref_editor.putBoolean("BG_MUSIC_SWITCH", true);
				Toast.makeText(SettingActivity.this,
						R.string.setting_bg_music_on_text, Toast.LENGTH_LONG)
						.show();
			} else {
				setting_pref_editor.putBoolean("BG_MUSIC_SWITCH", false);
				Toast.makeText(SettingActivity.this,
						R.string.setting_bg_music_off_text, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	private final class music_bg_tbHandlerListener implements
			CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (music_bg_sw.isChecked()) {
				setting_pref_editor.putBoolean("BG_MUSIC_SWITCH", true);
				Toast.makeText(SettingActivity.this,
						R.string.setting_bg_music_on_text, Toast.LENGTH_LONG)
						.show();
			} else {
				setting_pref_editor.putBoolean("BG_MUSIC_SWITCH", false);
				Toast.makeText(SettingActivity.this,
						R.string.setting_bg_music_off_text, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	private final class theme_rgHandlerListener implements
			RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (theme_origin_rb.getId() == checkedId) {
				setting_pref_editor.putString("THEME", "ORIGIN");
				Toast.makeText(SettingActivity.this,
						R.string.setting_theme_switch_success_text,
						Toast.LENGTH_LONG).show();
			}

			if (theme_transparent_rb.getId() == checkedId) {
				setting_pref_editor.putString("THEME", "TRANSPARENT");
				Toast.makeText(SettingActivity.this,
						R.string.setting_theme_switch_success_text,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private final class confirm_btHandlerListener implements
			View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (user_name_ed.getText().length() == 0
					&& passwd_ed.getText().length() == 0) {
				Toast.makeText(SettingActivity.this,
						R.string.setting_change_null_user_passed_text,
						Toast.LENGTH_LONG).show();
			}
			if (user_name_ed.getText().length() != 0
					&& passwd_ed.getText().length() == 0) {
				setting_pref_editor.putString("USERNMAE", user_name_ed
						.getText().toString());
				setting_pref_editor.putBoolean("REMBER", false);
				user_name_ed.setText("");
				Toast.makeText(SettingActivity.this,
						R.string.setting_change_user_success_text,
						Toast.LENGTH_LONG).show();
			}
			if (user_name_ed.getText().length() == 0
					&& passwd_ed.getText().length() != 0) {
				if (passwd_again_ed.getText().length() != 0) {
					if (passwd_again_ed.getText().toString()
							.equals(passwd_ed.getText().toString())) {
						setting_pref_editor.putString("PASSWD", passwd_ed
								.getText().toString());
						setting_pref_editor.putBoolean("REMBER", false);
						passwd_ed.setText("");
						passwd_again_ed.setText("");
						Toast.makeText(SettingActivity.this,
								R.string.setting_change_passwd_success_text,
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(
								SettingActivity.this,
								R.string.setting_change_error_again_passwd_text,
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(SettingActivity.this,
							R.string.setting_change_null_again_passwd_text,
							Toast.LENGTH_LONG).show();
				}
			}
			if (user_name_ed.getText().length() != 0
					&& passwd_ed.getText().length() != 0) {
				if (passwd_again_ed.getText().length() != 0) {
					if (passwd_again_ed.getText().toString()
							.equals(passwd_ed.getText().toString())) {
						setting_pref_editor.putString("USERNMAE", user_name_ed
								.getText().toString());
						setting_pref_editor.putString("PASSWD", passwd_ed
								.getText().toString());
						setting_pref_editor.putBoolean("REMBER", false);
						user_name_ed.setText("");
						passwd_ed.setText("");
						passwd_again_ed.setText("");
						Toast.makeText(SettingActivity.this,
								R.string.setting_change_success_text,
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(
								SettingActivity.this,
								R.string.setting_change_error_again_passwd_text,
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(SettingActivity.this,
							R.string.setting_change_null_again_passwd_text,
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private final class cancel_btHandlerListener implements
			View.OnClickListener {
		@Override
		public void onClick(View v) {
			user_name_ed.setText("");
			passwd_ed.setText("");
			passwd_again_ed.setText("");
			Toast.makeText(SettingActivity.this,
					R.string.setting_change_success_cancel_text,
					Toast.LENGTH_LONG).show();
		}
	}

	// 提交数据
	private void prefCommitAll() {
		setting_pref_editor.commit();
	}

	// 设置ActionBar标题
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.setting_action_bar_title_text);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
	}

	// ActionBar返回箭头点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			prefCommitAll();
			finish();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		prefCommitAll();
		finish();
	}

	// 透明主题
	private void themeTransparent() {
		// 获取WallpaperManager 壁纸管理器
		WallpaperManager wallpaperManager = WallpaperManager
				.getInstance(getBaseContext()); // 获取壁纸管理器
		// 获取当前壁纸
		Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		// 将Drawable,转成Bitmap
		// Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
		//
		// float step = 0;
		// //计算出屏幕的偏移量
		// step = (bm.getWidth() - 480) / (7 - 1);
		// //截取相应屏幕的Bitmap
		// Bitmap pbm = Bitmap.createBitmap(bm, (int) (5 * step), 0, 480, 854);
		// //设置 背景
		// if(Build.VERSION.SDK_INT>=16){
		// set_layout.setBackground(new BitmapDrawable(pbm));
		// }else {
		// set_layout.setBackgroundDrawable(new BitmapDrawable(pbm));
		// }

		if (Build.VERSION.SDK_INT >= 16) {
			set_layout.setBackground(wallpaperDrawable);
		} else {
			set_layout.setBackgroundDrawable(wallpaperDrawable);
		}
	}
}
