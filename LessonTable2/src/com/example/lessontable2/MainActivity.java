package com.example.lessontable2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;

import sqlserver.SQLServer;

public class MainActivity extends Activity {

	private ListView listView;
	private String[] weekday = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五",
			"星期六" };

	private ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		listener();
	}

	private void init() {

		SharedPreferences preferences = getSharedPreferences("SQLsettint",
				MODE_PRIVATE);
		Editor editor = preferences.edit();
		int version = preferences.getInt("SQLfullTable", -1);
		SQLServer.createSQL(this, version);
		editor.putInt("SQLfullTable", 1);
		editor.commit();

		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		String today = weekday[week - 1];
		weekday[week - 1] = "今天是  -->  " + today + "  !";

		arrayAdapter = new ArrayAdapter<>(MainActivity.this,
				android.R.layout.simple_list_item_1, weekday);
		listView = (ListView) findViewById(R.id.table_listview);
		listView.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.changge_lesson:
			Intent intent = new Intent(MainActivity.this,
					ChangeLessonInfo.class);
			startActivity(intent);
			break;
		case R.id.add_lesson:
			Toast.makeText(this, "AddLesson", 0).show();
			break;
		case R.id.delete_lesson:
			Toast.makeText(this, "DeleteLesson", 0).show();
			break;
		case R.id.clear_lesson_table:
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					MainActivity.this);
			dialog.setTitle("提示:")
					.setMessage("你确定要清空课表吗?\n此次操作将不可恢复!")
					.setCancelable(false)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									SQLServer
											.deleteAllLesson(MainActivity.this);
									Toast.makeText(MainActivity.this, "课表清空完成",
											0).show();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	private void listener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this, Lessons.class);

				switch (position) {
				case 1:
					intent.putExtra("WEEK", "monday");
					startActivity(intent);
					break;
				case 2:
					intent.putExtra("WEEK", "tuesday");
					startActivity(intent);
					break;
				case 3:
					intent.putExtra("WEEK", "wednesday");
					startActivity(intent);
					break;
				case 4:
					intent.putExtra("WEEK", "thursday");
					startActivity(intent);
					break;
				case 5:
					intent.putExtra("WEEK", "friday");
					startActivity(intent);
					break;
				case 6:
					AlertDialog.Builder saturdayDialog = new AlertDialog.Builder(
							MainActivity.this);
					saturdayDialog
							.setTitle(R.string.table_tips_text)
							.setMessage(R.string.table_nolesson_text)
							.setCancelable(false)
							.setPositiveButton(R.string.table_possitive_text,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).show();
					break;
				case 0:
					AlertDialog.Builder SundayDialog = new AlertDialog.Builder(
							MainActivity.this);
					SundayDialog
							.setTitle(R.string.table_tips_text)
							.setMessage(R.string.table_nolesson_text)
							.setCancelable(false)
							.setPositiveButton(R.string.table_possitive_text,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).show();
					break;
				}
			}
		});
	}
}
