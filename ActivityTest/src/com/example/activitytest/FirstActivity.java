package com.example.activitytest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Log.d("FirstActivity", this.toString());
		Log.d("FirstActivity", "Task id is" + getTaskId());

		

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);

		Button button1 = (Button) findViewById(R.id.button_1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(FirstActivity.this, "You clicked Button1",
						Toast.LENGTH_SHORT).show();
			}
		});

		Button destoryBt = (Button) findViewById(R.id.destory_bt);
		destoryBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		Button switchBt = (Button) findViewById(R.id.switch_bt);
		switchBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(FirstActivity.this,
						SecondActivity.class);
				startActivity(intent);
			}
		});

		Button inSwitchBt = (Button) findViewById(R.id.switch_bt_2);
		inSwitchBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						"com.example.activitytest.ACTION_START");
				intent.addCategory("com.example.activitytest.MY_CATEGORY");
				startActivity(intent);

			}
		});

		Button openBroswerBt = (Button) findViewById(R.id.open_broswer);
		openBroswerBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.ntu.edu.cn"));
				startActivity(intent);
			}
		});

		Button openDialBt = (Button) findViewById(R.id.open_dial);
		openDialBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:1008611"));
				startActivity(intent);

			}
		});

		Button showDataBt = (Button) findViewById(R.id.show_data);
		showDataBt.setOnClickListener(new OnClickListener() {
			String data = "Hello SecondActivity";

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstActivity.this,
						SecondActivity.class);
				intent.putExtra("extra_data", data);
				startActivity(intent);
			}
		});

		Button returnDataBt = (Button) findViewById(R.id.return_data);
		returnDataBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstActivity.this,
						SecondActivity.class);
				startActivityForResult(intent, 1);
			}
		});

		Button standardButton = (Button) findViewById(R.id.standrad_launch);
		standardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstActivity.this,
						SecondActivity.class);
				startActivity(intent);
			}
		});
		
		Button bestLaunch=(Button)findViewById(R.id.best_launch);
		bestLaunch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FirstActivity.actionStart(FirstActivity.this, "data1", "data2");
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				String returnData = data.getStringExtra("return_data");
				Log.d("FirstActivity", returnData);
				Toast.makeText(FirstActivity.this, returnData,
						Toast.LENGTH_LONG).show();
			}
			break;
		default:
			Log.d("FirstActivity", Integer.toString(resultCode));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_item:
			Toast.makeText(FirstActivity.this, "You clicked add",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_item:
			Toast.makeText(FirstActivity.this, "You clicked remove",
					Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		Log.d("FirstActivity", "onRestart");
	}
	
	public static void actionStart(Context context, String data1, String data2) {
		Intent intent=new Intent(context, SecondActivity.class);
		intent.putExtra("param1", data1);
		intent.putExtra("param2", data2);
		context.startActivity(intent);
	}
}
