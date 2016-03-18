package sqlserver;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 此类为数据库的服务类, 主要提供了对数据库的CRUD(增, 删, 改, 查)基本操作
 * 
 * @author MengGE 2016/03/17
 * 
 */
public class SQLServer implements SQLTableName, SQLString {

	/**
	 * 创建数据库, 三张表---时间表, 课表, 课程(表)
	 * 
	 * @param context
	 *            调用此方法的上下文对象
	 * @param version
	 *            表的版本, 若==-1,则只在程序首次运行的时候创建,之后则不再创建
	 */
	public static void createSQL(Context context, int version) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		if (version == -1) {
			createTimeTable(dbHelper);
			createLessonTable(dbHelper);
			createClassTable(dbHelper);
		}

	}

	/**
	 * 更新课程(表)中某个课程的全部信息
	 * 
	 * @param context
	 *            调用此方法的上下文对象
	 * @param lessonName
	 *            课程名称
	 * @param jieci
	 *            节次,如:1~3
	 * @param weekNum
	 *            周数
	 * @param classRoom
	 *            教室
	 * @param classes
	 *            合班数
	 * @param xueshi
	 *            学时
	 * @param xuefen
	 *            学分
	 * @param teacher
	 *            教师
	 * @param testMethod
	 *            考核方式
	 * @param others
	 *            其他
	 */
	public static void updateAllLessonInfo(Context context, String lessonName,
			String jieci, int weekNum, String classRoom, int classes,
			int xueshi, int xuefen, String teacher, String testMethod,
			String others) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(LESSON_NAME, lessonName);
		values.put(JIECI, jieci);
		values.put(WEEK_MUN, weekNum);
		values.put(CLASSROOM, classRoom);
		values.put(CLASSES, classes);
		values.put(XUEFEN, xuefen);
		values.put(XUESHI, xueshi);
		values.put(TEACHER, teacher);
		values.put(OTHERS, others);
		values.put(TEST_METHOD, testMethod);
		db.update(LessonTable, values, LESSON_NAME + "=?",
				new String[] { lessonName });
		db.close(); // 关闭数据库
	}

	/**
	 * 修改课程(表)中某个课程的某个信息
	 * 
	 * @param context
	 *            调用此方法的上下文对象
	 * @param lessonName
	 *            课程名称
	 * @param infoName
	 *            要修改的信息名称
	 * @param content
	 *            信息内容
	 */
	public static void updateSingleLessonInfo(Context context,
			String lessonName, String infoName, String content) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();

		if (infoName.equals(WEEK_MUN) || infoName.equals(CLASSES)
				|| infoName.equals(XUEFEN) || infoName.equals(XUESHI)) {
			int con = Integer.valueOf(content);
			values.put(infoName, con);
		} else {
			values.put(infoName, content);
		}
		db.update(LessonTable, values, LESSON_NAME + "=?",
				new String[] { lessonName });
		db.close(); // 关闭数据库
	}

	/**
	 * 删除表中所有数据
	 * 
	 * @param context
	 *            调用此方法的当前上下文对象
	 */
	public static void deleteAllLesson(Context context) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(LessonTable, "id>?", new String[] { "0" });

		ContentValues values = new ContentValues();
		values.put(MONDAY, "");
		values.put(TUESDAY, "");
		values.put(WEDNESDAY, "");
		values.put(THURSDAY, "");
		values.put(FRIDAY, "");
		values.put(SATURDAT, "");
		values.put(SUNDAY, "");
		db.update(ClassTable, values, "id>?", new String[] { "0" });
		db.close(); // 关闭数据库
	}

	/**
	 * 删除某个课程
	 * 
	 * @param context
	 *            上下文对象
	 * @param lessonName
	 *            要删除的课程名称
	 */
	public static void deleteSingleLesson(Context context, String lessonName) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		db.delete(LessonTable, LESSON_NAME + "=?", new String[] { lessonName });

		db.close(); // 关闭数据库

	}

	/**
	 * 返回某个课程的具体信息
	 * 
	 * @param onItemClickListener
	 * @param lesson
	 * @return
	 */
	public static String showLessonDetails(Context context, String lesson) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(LessonTable, null, null, null, null, null,
				null);

		String details = "";
		while (cursor.moveToNext()) {
			if (cursor.getString(cursor.getColumnIndex(LESSON_NAME)).equals(
					lesson)) {
				details += "节次: "
						+ cursor.getString(cursor.getColumnIndex(JIECI)) + "\n";
				details += "周数: "
						+ cursor.getInt(cursor.getColumnIndex(WEEK_MUN)) + "\n";
				details += "时间: "
						+ cursor.getString(cursor.getColumnIndex("time"))
						+ "\n";
				details += "教室: "
						+ cursor.getString(cursor.getColumnIndex(CLASSROOM))
						+ "\n";
				details += "学时: "
						+ cursor.getInt(cursor.getColumnIndex(XUESHI)) + "\n";
				details += "学分: "
						+ cursor.getInt(cursor.getColumnIndex(XUEFEN)) + "\n";
				details += "考核方式: "
						+ cursor.getString(cursor.getColumnIndex(TEST_METHOD))
						+ "\n";
				details += "教师:　"
						+ cursor.getString(cursor.getColumnIndex(TEACHER))
						+ "\n";
				details += "合班数: "
						+ cursor.getInt(cursor.getColumnIndex(CLASSES)) + "\n";
				details += "其他: "
						+ cursor.getString(cursor.getColumnIndex(OTHERS));
				break;
			}
		}
		cursor.close(); // 关闭游标器
		db.close(); // 关闭数据库
		return details;
	}

	/**
	 * 返回某个星期的课程
	 * 
	 * @param context
	 *            上下文对象
	 * @param week
	 *            星期几
	 * @return 课程列表
	 */
	public static List<String> showLessons(Context context, String week) {

		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db
				.query(ClassTable, null, null, null, null, null, null);
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String temp = cursor.getString(cursor.getColumnIndex(week));

			if (temp.length() != 0) {
				list.add(temp);
			}
		}

		cursor.close(); // 关闭游标器
		db.close(); // 关闭数据库
		return list;
	}

	/**
	 * 向课表中添加课程
	 * 
	 * @param context
	 *            调用此方法的上下文对象
	 * @param week
	 *            要添加到星期几
	 * @param jieci
	 *            添加到某节课
	 * @param lessonName
	 *            课程名称
	 */
	public static void addData(Context context, String week, int jieci,
			String lessonName) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(week, lessonName);

		db.update(ClassTable, values, "id=?",
				new String[] { Integer.toString(jieci) });

		db.close(); // 关闭数据库
	}

	/**
	 * 向课程(表)中添加一门新的课程
	 * 
	 * @param context
	 *            调用此方法的上下文对象
	 * @param lessonName
	 *            课程名称
	 * @param jieci
	 *            节次,如:1~3
	 * @param weekNum
	 *            周数
	 * @param classRoom
	 *            教室
	 * @param classes
	 *            合班数
	 * @param xueshi
	 *            学时
	 * @param xuefen
	 *            学分
	 * @param teacher
	 *            教师
	 * @param testMethod
	 *            考核方式
	 * @param others
	 *            其他
	 */
	public void addData(Context context, String lessonName, String jieci,
			int weekNum, String classRoom, int classes, int xueshi, int xuefen,
			String teacher, String testMethod, String others) {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,
				DATABASE_NAME, null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(LESSON_NAME, lessonName);
		values.put(JIECI, jieci);
		values.put(WEEK_MUN, weekNum);
		values.put(CLASSROOM, classRoom);
		values.put(CLASSES, classes);
		values.put(XUEFEN, xuefen);
		values.put(XUESHI, xueshi);
		values.put(TEACHER, teacher);
		values.put(OTHERS, others);
		values.put(TEST_METHOD, testMethod);
		db.insert(LessonTable, null, values);
		db.close(); // 关闭数据库
	}

	/**
	 * 创建计算机143班大二下学期的标准课表
	 * 
	 * @param dbHelper
	 *            数据库对象
	 */
	private static void createClassTable(MyDatabaseHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		// 第一,二节课
		values.put(MONDAY, ENGLISH4);
		values.put(TUESDAY, "");
		values.put(WEDNESDAY, THEORY_OF_DATABASE);
		values.put(THURSDAY, THEORY_OF_COMPUTER_MADE);
		values.put(FRIDAY, "");
		values.put(SATURDAT, "");
		values.put(SUNDAY, "");
		db.insert(ClassTable, null, values);
		db.insert(ClassTable, null, values);
		values.clear();

		// 第三节课
		values.put(MONDAY, ENGLISH4);
		values.put(TUESDAY, JAVA_WEB_DEV);
		values.put(WEDNESDAY, "");
		values.put(THURSDAY, MAOZEDONG);
		values.put(FRIDAY, "");
		values.put(SATURDAT, "");
		values.put(SUNDAY, "");
		db.insert(ClassTable, null, values);
		values.clear();

		// 第四,五节课
		values.put(MONDAY, THEORY_OF_COMPUTER_MADE);
		values.put(TUESDAY, JAVA_WEB_DEV);
		values.put(WEDNESDAY, ENGLISH4);
		values.put(THURSDAY, MAOZEDONG);
		values.put(FRIDAY, "");
		values.put(SATURDAT, "");
		values.put(SUNDAY, "");
		db.insert(ClassTable, null, values);
		db.insert(ClassTable, null, values);
		values.clear();

		// 第六,七节课
		values.put(MONDAY, OPERATE_SYSTEM);
		values.put(TUESDAY, PE4);
		values.put(WEDNESDAY, "");
		values.put(THURSDAY, OPERATE_SYSTEM);
		values.put(FRIDAY, THEORY_OF_DATABASE);
		values.put(SATURDAT, "");
		values.put(SUNDAY, "");
		db.insert(ClassTable, null, values);
		db.insert(ClassTable, null, values);
		values.clear();

		// 第八,九,十,十一,十二节课
		values.put(MONDAY, "");
		values.put(TUESDAY, "");
		values.put(WEDNESDAY, "");
		values.put(THURSDAY, "");
		values.put(FRIDAY, "");
		values.put(SATURDAT, "");
		values.put(SUNDAY, "");
		db.insert(ClassTable, null, values);
		db.insert(ClassTable, null, values);
		db.insert(ClassTable, null, values);
		db.insert(ClassTable, null, values);
		db.insert(ClassTable, null, values);

		db.close(); // 关闭数据库
	}

	/**
	 * 创建课程(表)--里面包含所有的课程和每门课程的课程信息
	 * 
	 * @param dbHelper
	 *            数据库对象
	 */
	private static void createLessonTable(MyDatabaseHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();

		// 大学英语(四)
		values.put(LESSON_NAME, ENGLISH4);
		values.put(JIECI, ENGLISH4);
		values.put(WEEK_MUN, 16);
		values.put(CLASSROOM, ENGLISH4);
		values.put(CLASSES, 2);
		values.put(XUEFEN, 5);
		values.put(XUESHI, 5);
		values.put(TEACHER, ENGLISH4);
		values.put(OTHERS, ENGLISH4);
		values.put(TEST_METHOD, ENGLISH4);
		db.insert(LessonTable, null, values);
		values.clear();

		// 计算机组成原理
		values.put(LESSON_NAME, THEORY_OF_COMPUTER_MADE);
		values.put(JIECI, ENGLISH4);
		values.put(WEEK_MUN, WEEK_MUN);
		values.put(CLASSROOM, ENGLISH4);
		values.put(CLASSES, 5);
		values.put(XUEFEN, 5);
		values.put(XUESHI, 5);
		values.put(TEACHER, ENGLISH4);
		values.put(OTHERS, ENGLISH4);
		values.put(TEST_METHOD, ENGLISH4);
		db.insert(LessonTable, null, values);
		values.clear();

		// 数据库原理及应用
		values.put(LESSON_NAME, THEORY_OF_DATABASE);
		values.put(JIECI, ENGLISH4);
		values.put(WEEK_MUN, 5);
		values.put(CLASSROOM, ENGLISH4);
		values.put(CLASSES, 5);
		values.put(XUEFEN, 5);
		values.put(XUESHI, 5);
		values.put(TEACHER, ENGLISH4);
		values.put(OTHERS, ENGLISH4);
		values.put(TEST_METHOD, ENGLISH4);
		db.insert(LessonTable, null, values);
		values.clear();

		// 操作系统
		values.put(LESSON_NAME, OPERATE_SYSTEM);
		values.put(JIECI, ENGLISH4);
		values.put(WEEK_MUN, 5);
		values.put(CLASSROOM, ENGLISH4);
		values.put(CLASSES, 5);
		values.put(XUEFEN, 5);
		values.put(XUESHI, 5);
		values.put(TEACHER, ENGLISH4);
		values.put(OTHERS, ENGLISH4);
		values.put(TEST_METHOD, ENGLISH4);
		db.insert(LessonTable, null, values);
		values.clear();

		// Java Web开发技术教程
		values.put(LESSON_NAME, JAVA_WEB_DEV);
		values.put(JIECI, ENGLISH4);
		values.put(WEEK_MUN, 5);
		values.put(CLASSROOM, ENGLISH4);
		values.put(CLASSES, 5);
		values.put(XUEFEN, 5);
		values.put(XUESHI, 5);
		values.put(TEACHER, ENGLISH4);
		values.put(OTHERS, ENGLISH4);
		values.put(TEST_METHOD, ENGLISH4);
		db.insert(LessonTable, null, values);
		values.clear();

		// 体育(四)
		values.put(LESSON_NAME, PE4);
		values.put(JIECI, ENGLISH4);
		values.put(WEEK_MUN, 5);
		values.put(CLASSROOM, ENGLISH4);
		values.put(CLASSES, 5);
		values.put(XUEFEN, 5);
		values.put(XUESHI, 5);
		values.put(TEACHER, ENGLISH4);
		values.put(OTHERS, ENGLISH4);
		values.put(TEST_METHOD, ENGLISH4);
		db.insert(LessonTable, null, values);
		values.clear();

		// 毛泽东思想与中国特色社会主义理论体系概论
		values.put(LESSON_NAME, MAOZEDONG);
		values.put(JIECI, ENGLISH4);
		values.put(WEEK_MUN, 5);
		values.put(CLASSROOM, ENGLISH4);
		values.put(CLASSES, 5);
		values.put(XUEFEN, 5);
		values.put(XUESHI, 5);
		values.put(TEACHER, ENGLISH4);
		values.put(OTHERS, ENGLISH4);
		values.put(TEST_METHOD, ENGLISH4);
		db.insert(LessonTable, null, values);
		values.clear();

		db.close(); // 关闭数据库
	}

	/**
	 * 创建时间表--夏季时间表--冬季时间表
	 * 
	 * @param dbHelper
	 *            数据库对象
	 */
	private static void createTimeTable(MyDatabaseHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SUMMUR_TIME, SUMMUR_ONE);
		values.put(WINTER_TIME, WINTER_ONE);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_TWO);
		values.put(WINTER_TIME, WINTER_TWO);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_THREE);
		values.put(WINTER_TIME, WINTER_THREE);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_FOUR);
		values.put(WINTER_TIME, WINTER_FOUR);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_FIVE);
		values.put(WINTER_TIME, WINTER_FIVE);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_SIX);
		values.put(WINTER_TIME, WINTER_SIX);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_SEVEN);
		values.put(WINTER_TIME, WINTER_SEVEN);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_EIGHT);
		values.put(WINTER_TIME, WINTER_EIGHT);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_NINE);
		values.put(WINTER_TIME, WINTER_NINE);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_TEN);
		values.put(WINTER_TIME, WINTER_TEN);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_ELEVEN);
		values.put(WINTER_TIME, WINTER_ELEVEN);
		db.insert(TimeTable, null, values);
		values.clear();

		values.put(SUMMUR_TIME, SUMMUR_TEWLVE);
		values.put(WINTER_TIME, WINTER_TWELVE);
		db.insert(TimeTable, null, values);
		values.clear();

		db.close();

	}
}
