package mge.mengge.lessontable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class mondayActivity extends Activity {
	private Button englishBT;
	private Button jusuanjiBT;
	private Button osBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.monday_layout);

		englishBT = (Button) findViewById(R.id.monday_english_bt);
		jusuanjiBT = (Button) findViewById(R.id.monday_jisuanjizuchengyuanli_bt);
		osBT = (Button) findViewById(R.id.monday_operatesystem_bt);

		englishBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		jusuanjiBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		osBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}
}
