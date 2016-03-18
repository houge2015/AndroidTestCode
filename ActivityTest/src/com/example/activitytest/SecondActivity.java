package com.example.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class SecondActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("SecondActivity", "Task id is" + getTaskId());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_layout);
		Intent intent = getIntent();

		// Toast.makeText(SecondActivity.this,
		// intent.getStringExtra("extra_data"), Toast.LENGTH_LONG).show();
		Toast.makeText(SecondActivity.this, intent.getStringExtra("param1"),
				Toast.LENGTH_LONG).show();
		Toast.makeText(SecondActivity.this, intent.getStringExtra("param2"),
				Toast.LENGTH_LONG).show();

		Button button2 = (Button) findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("return_data", "Hello FirstActivity");
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		Button thirdButton = (Button) findViewById(R.id.singleInstance_bt);
		thirdButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SecondActivity.this,
						ThirdActivity.class);
				startActivity(intent);

			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("return_data", "Hello FirstActivity");
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		Log.d("SecondActivity", "onDestory");
	}
}
