package mge.mengge.lessontable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mondayBT;
	private Button tuesdayBT;
	private Button wednesdayBT;
	private Button thursdayBT;
	private Button fridayBT;
	private Button saturdayBT;
	private Button sundayBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		listener();
	}

	private void init() {
		mondayBT = (Button) findViewById(R.id.table_monday_bt);
		tuesdayBT = (Button) findViewById(R.id.table_tuesday_bt);
		wednesdayBT = (Button) findViewById(R.id.table_wednesday_bt);
		thursdayBT = (Button) findViewById(R.id.table_thursday_bt);
		fridayBT = (Button) findViewById(R.id.table_firday_bt);
		saturdayBT = (Button) findViewById(R.id.table_saturday_bt);
		sundayBT = (Button) findViewById(R.id.table_sunday_bt);
	}

	private void listener() {
		mondayBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						mondayActivity.class);
				startActivity(intent);
			}
		});
		tuesdayBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(MainActivity.this,
						tuesdayActivity.class);
				startActivity(intent1);

			}
		});
		wednesdayBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(MainActivity.this,
						wednesdayActivity.class);
				startActivity(intent2);

			}
		});
		thursdayBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent3 = new Intent(MainActivity.this,
						thursdayActivity.class);
				startActivity(intent3);
			}
		});
		fridayBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent4 = new Intent(MainActivity.this,
						fridayActivity.class);
				startActivity(intent4);

			}
		});
		saturdayBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						MainActivity.this);
				dialog.setTitle("提示:").setMessage("今天没有课哦,好好休息吧!").show();
			}
		});
		sundayBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						MainActivity.this);
				dialog.setTitle("提示:").setMessage("今天没有课哦,好好休息吧!").setPositiveButton("", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
