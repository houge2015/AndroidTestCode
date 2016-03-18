package sqlserver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper implements SQLTableName {

	private static final String CLASS_TABLE = "create table " + ClassTable
			+ "(id integer primary key autoincrement, " + "monday text, "
			+ "tuesday text, " + "wednesday text, " + "thursday text, "
			+ "friday text, " + "saturday text, " + "sunday text)";

	private static final String TIME_TABLE = "create table "
			+ TimeTable
			+ " (id integer primary key autoincrement, summerTime text, winterTime text)";

	private static final String LESSON_TABLE = "create table "
			+ LessonTable
			+ " (id integer primary key autoincrement, lessonName text, jieci text, weekNum integer, time text, classRoom text, xueShi integer, xueFen integer, teacher text, testMethod text, classes integer, others text )";

	private Context context;

	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CLASS_TABLE);
		db.execSQL(TIME_TABLE);
		db.execSQL(LESSON_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
