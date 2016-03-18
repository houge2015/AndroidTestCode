package com.example.lessontable2;

import java.util.List;

import sqlserver.SQLServer;
import sqlserver.SQLTableName;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lessons extends Activity implements SQLTableName {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lessons);

		listView = (ListView) findViewById(R.id.lessons_show_lv);

		Intent intent = getIntent();
		String week = intent.getStringExtra("WEEK");

		final List<String> list = SQLServer.showLessons(this, week);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, list);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String lesson = list.get(position);
				String details = "";
				details = SQLServer.showLessonDetails(Lessons.this, lesson);

				AlertDialog.Builder dialog = new AlertDialog.Builder(
						Lessons.this);
				dialog.setTitle(lesson + "信息:")
						.setMessage(details)
						.setCancelable(false)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).show();
			}
		});
	}
}
