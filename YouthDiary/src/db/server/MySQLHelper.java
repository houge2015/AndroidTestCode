package db.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.CellIdentityCdma;

public class MySQLHelper extends SQLiteOpenHelper {
	private static final String CreateDiaryDb = "create table DiaryDb(id integer primary key autoincrement, time text, title text, content text)";

	/**
	 * �������ݿ�
	 * 
	 * @param context
	 *            activity �������Ķ���
	 * @param name
	 *            ���ݿ�����
	 * @param factory
	 *            �α�--һ��Ĭ��ֵΪnull
	 * @param version
	 *            ���ݿ�汾
	 */
	public MySQLHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CreateDiaryDb);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
