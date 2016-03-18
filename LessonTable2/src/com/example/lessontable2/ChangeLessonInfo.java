package com.example.lessontable2;

import java.util.List;

import sqlserver.SQLServer;
import sqlserver.SQLString;
import sqlserver.SQLTableName;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeLessonInfo extends Activity implements OnClickListener,
		SQLTableName, SQLString {
	private String[] weekListCh = { _SUNDAY, _MONDAY, _TUESDAY, _WEDNESDAY,
			_THURSDAY, _FRIDAY, _SATURDAY };
	private String[] weekListEng = { SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
			THURSDAY, FRIDAY, SATURDAT };

	private List<String> list;
	private int weekLocation;
	private String lessonName;

	private Button jieciBt, weekNumBt, timeBt, classRoomBt, xueshiBt, xuefenBt,
			testMethodBt, teacherBt, classesBt, othersBt, submitAllBt;
	private EditText jieciEd, weekNumEd, timeEd, classRoomEd, xueshiEd,
			xuefenEd, testMethodEd, teacherEd, classesEd, othersEd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_lesson_info);
		init();
		listener();
		weekAndLessonChoose();
	}

	private void init() {
		// Buttons
		jieciBt = (Button) findViewById(R.id.jieci_bt);
		weekNumBt = (Button) findViewById(R.id.week_num_bt);
		timeBt = (Button) findViewById(R.id.time_bt);
		classRoomBt = (Button) findViewById(R.id.class_room_bt);
		xueshiBt = (Button) findViewById(R.id.xueshi_bt);
		xuefenBt = (Button) findViewById(R.id.xuefen_bt);
		testMethodBt = (Button) findViewById(R.id.test_method_bt);
		teacherBt = (Button) findViewById(R.id.teacher_bt);
		classesBt = (Button) findViewById(R.id.classed_bt);
		othersBt = (Button) findViewById(R.id.others_bt);
		submitAllBt = (Button) findViewById(R.id.submit_all_bt);

		// EditTexts
		jieciEd = (EditText) findViewById(R.id.jieci_ed);
		weekNumEd = (EditText) findViewById(R.id.week_num_ed);
		timeEd = (EditText) findViewById(R.id.time_ed);
		classRoomEd = (EditText) findViewById(R.id.class_room_ed);
		xueshiEd = (EditText) findViewById(R.id.xueshi_ed);
		xuefenEd = (EditText) findViewById(R.id.xuefen_ed);
		testMethodEd = (EditText) findViewById(R.id.test_method_ed);
		teacherEd = (EditText) findViewById(R.id.teacher_ed);
		classesEd = (EditText) findViewById(R.id.classed_ed);
		othersEd = (EditText) findViewById(R.id.others_ed);
	}

	private void listener() {
		jieciBt.setOnClickListener(this);
		weekNumBt.setOnClickListener(this);
		timeBt.setOnClickListener(this);
		classRoomBt.setOnClickListener(this);
		xueshiBt.setOnClickListener(this);
		xuefenBt.setOnClickListener(this);
		testMethodBt.setOnClickListener(this);
		teacherBt.setOnClickListener(this);
		classesBt.setOnClickListener(this);
		othersBt.setOnClickListener(this);
		submitAllBt.setOnClickListener(this);
	}

	private void weekAndLessonChoose() {
		AlertDialog.Builder weekDialog = new AlertDialog.Builder(
				ChangeLessonInfo.this);
		weekDialog.setTitle("请选择星期:").setCancelable(false)
				.setItems(weekListCh, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						weekLocation = which;

						list = SQLServer.showLessons(ChangeLessonInfo.this,
								weekListEng[which]);
						if (list.size() == 0) {
							Toast.makeText(
									ChangeLessonInfo.this,
									weekListCh[weekLocation]
											+ "没有课哦!\n赶紧去添加几节课吧!", 1).show();
						} else {

							Toast.makeText(ChangeLessonInfo.this,
									"快去试试左侧的提交按钮吧\n它可以让你单独修改某个信息哦!", 1).show();
						}
						dialog.dismiss();
					}
				}).show();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.jieci_bt:
			if (jieciEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}
			break;
		case R.id.week_num_bt:
			if (weekNumEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.time_bt:
			if (timeEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.class_room_bt:
			if (classRoomEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.xueshi_bt:
			if (xueshiEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.xuefen_bt:
			if (xuefenEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.test_method_bt:
			if (testMethodEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.teacher_bt:
			if (teacherEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.classed_bt:
			if (classesEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.others_bt:
			if (othersEd.getText().length() == 0) {
				Toast.makeText(ChangeLessonInfo.this, "请添加一些信息后再提交!", 0).show();
			} else {

			}

			break;
		case R.id.submit_all_bt:

			break;
		}
	}
}
