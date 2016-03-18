package cn.mge.ntu.get_check_code;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.nettest.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private EditText xh;
	private EditText sfzh;
	private EditText checkCodeEditText;
	private EditText passwd;
	private Button sendRequest;
	private Button login;
	private TextView returnData;
	private ImageView checkCode;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				byte[] data1 = (byte[]) msg.obj;
				Bitmap bitmap = BitmapFactory.decodeByteArray(data1, 0,
						data1.length);
				checkCode.setImageBitmap(bitmap);
				break;
			case 1:
				String data=(String) msg.obj;
				returnData.setText(data);
				break;
				
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		xh = (EditText) findViewById(R.id.xh_ed);
		sfzh = (EditText) findViewById(R.id.sfzh_ed);
		checkCodeEditText = (EditText) findViewById(R.id.check_code_ed);
		passwd = (EditText) findViewById(R.id.passwd_ed);

		login = (Button) findViewById(R.id.login_bt);
		returnData = (TextView) findViewById(R.id.return_data_tx);
		login.setOnClickListener(this);

		sendRequest = (Button) findViewById(R.id.send_bt);
		checkCode = (ImageView) findViewById(R.id.image_vw);
		sendRequest.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_bt:
			new Thread(new Runnable() {

				@Override
				public void run() {
					HttpURLConnection connection = null;
					try {
						String checkCode = Integer.toString((int) Math.random());
						URL url = new URL(
								"http://jwgl.ntu.edu.cn/cjcx/checkImage.aspx?a="
										+ checkCode);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("POST");
						connection.setDoInput(true);
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);

						InputStream in = connection.getInputStream();
						byte[] buffer = new byte[1024];
						int len = 0;
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						while ((len = in.read(buffer)) != -1) {
							bos.write(buffer, 0, len);
						}
						bos.close();

						byte[] data = bos.toByteArray();

						Message msg = new Message();
						msg.what = 0;
						msg.obj = data;
						handler.sendMessage(msg);

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (connection != null) {
							connection.disconnect();
						}
					}
				}
			}).start();

			break;
		case R.id.login_bt:
			  String xuehao = xh.getText().toString();
			  String shenfenzheng = sfzh.getText().toString();
			  String password = passwd.getText().toString();
			  String checkCodeString = checkCodeEditText.getText().toString();
			
			final String code="xh="+xuehao+"&sfzh="+shenfenzheng+"&kl="+password+"&yzm="+checkCodeString;
			final byte[] data=code.getBytes();
			new Thread(new Runnable() {

				@Override
				public void run() {
					HttpURLConnection connection = null;
					try {
						URL url=new URL("http://jwgl.ntu.edu.cn/cjcx/");
						connection=(HttpURLConnection) url.openConnection();
						connection.setConnectTimeout(8000);
					
						connection.setDoInput(true);
						connection.setDoOutput(true); 
						connection.setRequestMethod("POST");
						connection.setUseCaches(false);
						
						 //设置请求体的类型是文本类型
			            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			            //设置请求体的长度
			            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
						DataOutputStream outputStream=new DataOutputStream(connection.getOutputStream());
						outputStream.write(data);
						String result=null;
						int recode=connection.getResponseCode();
						if (recode==connection.HTTP_OK) {
							InputStream inputStream=connection.getInputStream();
							
							ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
							byte[] data1=new byte[1024];
							int len =0 ;
							while ((len=inputStream.read(data1))!=-1) {
								byteArrayOutputStream.write(data1,0, len);
							}
							result=new String(byteArrayOutputStream.toByteArray());
	
						}

						Message message = new Message();
						message.what = 1;
						message.obj = result;
						handler.sendMessage(message);
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						if (connection!=null) {
							connection.disconnect();
						}
					}

				}
			}).start();
			break;
		}
	}
}
