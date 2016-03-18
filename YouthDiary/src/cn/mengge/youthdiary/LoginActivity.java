package cn.mengge.youthdiary;

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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import db.server.CreateSQL;

public class LoginActivity extends Activity {
	private Button login_bt;
	private EditText user_name_ed;
	private EditText passwd_ed;
	private CheckBox rember_cb;
	private SharedPreferences user_passwd_pref;
	private SharedPreferences.Editor user_name_pref_editor;
	private AlertDialog.Builder first_login_tips_dl;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// makeAppDir();
		init();
	}

	// 初始化控件并设置监听事件
	private void init() {
		user_passwd_pref = getSharedPreferences("SETTING", MODE_PRIVATE);
		user_name_pref_editor = user_passwd_pref.edit();

		if (user_passwd_pref.getString("THEME", "ORIGIN").equals("TRANSPARENT")) {
			layout = (LinearLayout) findViewById(R.id.login_layout);
			themeTransparent();
		}

		login_bt = (Button) findViewById(R.id.login_login_bt);
		login_bt.setOnClickListener(new loginButtonHandlerListener());

		user_name_ed = (EditText) findViewById(R.id.login_user_name_ed);
		passwd_ed = (EditText) findViewById(R.id.login_passwd_ed);
		rember_cb = (CheckBox) findViewById(R.id.login_rember_cb);

		if (user_passwd_pref.getBoolean("FIRSTLOGIN", true)) {
			first_login_tips_dl = new AlertDialog.Builder(LoginActivity.this);
			first_login_tips_dl
					.setTitle(R.string.login_dialog_first_login_title_text);
			first_login_tips_dl
					.setMessage(R.string.login_dialog_first_login_message_text);
			first_login_tips_dl.setPositiveButton(
					R.string.login_dialog_positive_text,
					new dialog_positiveButtonListener());
			first_login_tips_dl.setCancelable(false);
			AlertDialog dialog = first_login_tips_dl.create();
			dialog.show();
		}

		if (user_passwd_pref.getBoolean("REMBER", false)) {
			rember_cb.setChecked(true);
			user_name_ed.setText(user_passwd_pref.getString("USERNMAE",
					"youth_diary").toString());
			passwd_ed.setText(user_passwd_pref.getString("PASSWD",
					"yd1234567890").toString());
		} else {
			rember_cb.setChecked(false);
			user_name_ed.setText("");
			passwd_ed.setText("");
		}

	}

	// 登录按钮的监听事件
	private final class loginButtonHandlerListener implements
			View.OnClickListener {

		public void onClick(View view) {

			String user_name = user_name_ed.getText().toString();
			String passwd = passwd_ed.getText().toString();

			if (user_name.length() == 0 || passwd.length() == 0) {
				Toast.makeText(LoginActivity.this,
						R.string.login_null_user_passed_text, Toast.LENGTH_LONG)
						.show();
			} else {
				if (user_name.equals(user_passwd_pref.getString("USERNMAE",
						"youth_diary").toString())
						&& passwd.equals(user_passwd_pref.getString("PASSWD",
								"yd1234567890").toString())) {
					if (rember_cb.isChecked()) {
						user_name_pref_editor.putBoolean("REMBER", true);
					} else {
						user_name_pref_editor.putBoolean("REMBER", false);
					}
					prefCommitAll();// 提交数据

					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, DiaryActivity.class);
					startActivity(intent);
					Toast.makeText(LoginActivity.this,
							R.string.login_login_success_text,
							Toast.LENGTH_LONG).show();
					finish();
				} else
					Toast.makeText(LoginActivity.this,
							R.string.login_error_user_passwd_text,
							Toast.LENGTH_LONG).show();
			}
		}
	}

	// 对话框的确定按钮的点击事件
	private final class dialog_positiveButtonListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialogInterface, int i) {
			user_name_pref_editor.putBoolean("FIRSTLOGIN", false);
			dialogInterface.dismiss();
		}
	}

	// 创建程序文件夹YouthDiary_MyDiary YouthDiary_data_music YouthDiary_data_pica_pic
	// private void makeAppDir() {
	// if (Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED)) {
	// File main_dir = new File(Environment.getExternalStorageDirectory()
	// + "/YouthDiary");
	// if (!main_dir.exists()) {
	// main_dir.mkdirs();
	// }
	// File diary_dir = new File(Environment.getExternalStorageDirectory()
	// + "/YouthDiary/MyDiary");
	// if (!diary_dir.exists()) {
	// diary_dir.mkdirs();
	// }
	// File data_dir = new File(Environment.getExternalStorageDirectory()
	// + "/YouthDiary/data");
	// if (!data_dir.exists()) {
	// data_dir.mkdirs();
	// }
	// File music_dir = new File(Environment.getExternalStorageDirectory()
	// + "/YouthDiary/data/music");
	// if (!music_dir.exists()) {
	// music_dir.mkdirs();
	// }
	// File pic_dir = new File(Environment.getExternalStorageDirectory()
	// + "/YouthDiary/data/pic");
	// if (!pic_dir.exists()) {
	// pic_dir.mkdirs();
	// }
	// } else
	// Toast.makeText(LoginActivity.this, R.string.login_null_sd_text,
	// Toast.LENGTH_LONG).show();
	// }

	// 提交数据
	private void prefCommitAll() {
		user_name_pref_editor.commit();
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
