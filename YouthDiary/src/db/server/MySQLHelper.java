package db.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.CellIdentityCdma;

public class MySQLHelper extends SQLiteOpenHelper {
	private static final String CreateDiaryDb = "create table DiaryDb(id integer primary key autoincrement, time text, title text, content text)";

	/**
	 * 创建数据库
	 * 
	 * @param context
	 *            activity 的上下文对象
	 * @param name
	 *            数据库名字
	 * @param factory
	 *            游标--一般默认值为null
	 * @param version
	 *            数据库版本
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
