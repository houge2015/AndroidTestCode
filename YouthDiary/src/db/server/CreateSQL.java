package db.server;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 对数据库的操作--CRUD(增删改查)
 * 
 * @author MGE
 * 
 */
public class CreateSQL {

	private MySQLHelper dbHelper;
	private Context context;
	private String sqlName;
	private int version;

	/**
	 * 构造方法--传入数据库参数
	 * 
	 * @param context
	 *            上下文对象
	 * @param sqlName
	 *            数据库名称
	 * @param version
	 *            数据库版本
	 */
	public CreateSQL(Context context, String sqlName, int version) {
		this.context = context;
		this.sqlName = sqlName;
		this.version = version;
	}

	/**
	 * 在数据库某个表中添加日记
	 * 
	 * @param tableName
	 *            要添加日记的表名
	 * @param time
	 *            日记的时间
	 * @param title
	 *            日记的标题
	 * @param content
	 *            日记的内容
	 */
	public void addDiary(String tableName, String time, String title,
			String content) {
		dbHelper = new MySQLHelper(context, sqlName, null, version);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("time", time);
		values.put("title", title);
		values.put("content", content);
		db.insert(tableName, null, values);
	}

	/**
	 * 读取数据库中某表日记的标题
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 */
	public List<String> readDiaryTitle(String tableName) {

		dbHelper = new MySQLHelper(context, sqlName, null, version);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(tableName, null, null, null, null, null, null,
				null);
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(cursor.getColumnIndex("title")));
		}

		cursor.close();

		return list;
	}

	/**
	 * 阅读数据库某表中的某标题的内容
	 * 
	 * @param tableName
	 *            表名
	 * @param title
	 *            标题
	 * @return
	 */
	public String readDiaryContent(String tableName, String title) {
		dbHelper = new MySQLHelper(context, sqlName, null, version);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String content = "";
		Cursor cursor = db.query(tableName, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			if (cursor.getString(cursor.getColumnIndex("title")).equals(title)) {
				content = cursor.getString(cursor.getColumnIndex("content"));
				break;
			}
		}
		cursor.close();
		return content;

	}

	/**
	 * 修改数据库中某表的内容
	 * 
	 * @param tableName
	 *            表名
	 * @param title
	 *            标题
	 * @param content
	 *            要修改的内容
	 */
	public void changeDiaryContent(String tableName, String[] title,
			String content) {
		dbHelper = new MySQLHelper(context, sqlName, null, version);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("content", content);
		db.update(tableName, values, "title==?", title);

	}

	/**
	 * 删除数据库中某表的标题为title的记录
	 * 
	 * @param tableName
	 *            表名
	 * @param title
	 *            标题
	 * @return
	 */
	public boolean deleteDiary(String tableName, String[] title) {

		boolean flag = false;
		dbHelper = new MySQLHelper(context, sqlName, null, version);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		db.delete(tableName, "title==?", title);
		flag = true;
		return flag;

	}

}