package db.server;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * �����ݿ�Ĳ���--CRUD(��ɾ�Ĳ�)
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
	 * ���췽��--�������ݿ����
	 * 
	 * @param context
	 *            �����Ķ���
	 * @param sqlName
	 *            ���ݿ�����
	 * @param version
	 *            ���ݿ�汾
	 */
	public CreateSQL(Context context, String sqlName, int version) {
		this.context = context;
		this.sqlName = sqlName;
		this.version = version;
	}

	/**
	 * �����ݿ�ĳ����������ռ�
	 * 
	 * @param tableName
	 *            Ҫ����ռǵı���
	 * @param time
	 *            �ռǵ�ʱ��
	 * @param title
	 *            �ռǵı���
	 * @param content
	 *            �ռǵ�����
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
	 * ��ȡ���ݿ���ĳ���ռǵı���
	 * 
	 * @param tableName
	 *            ����
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
	 * �Ķ����ݿ�ĳ���е�ĳ���������
	 * 
	 * @param tableName
	 *            ����
	 * @param title
	 *            ����
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
	 * �޸����ݿ���ĳ�������
	 * 
	 * @param tableName
	 *            ����
	 * @param title
	 *            ����
	 * @param content
	 *            Ҫ�޸ĵ�����
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
	 * ɾ�����ݿ���ĳ��ı���Ϊtitle�ļ�¼
	 * 
	 * @param tableName
	 *            ����
	 * @param title
	 *            ����
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