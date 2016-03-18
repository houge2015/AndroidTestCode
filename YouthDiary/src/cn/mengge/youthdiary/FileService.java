package cn.mengge.youthdiary;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ContentHandler;

/**
 * Created by MGE on 2016/2/9.
 */
public class FileService {

	private Context context;

	/**
	 * 默认构造方法
	 */
	public FileService() {
	}

	/**
	 * 常用构造方法
	 * 
	 * @param context
	 *            Activity的上下文对象
	 */
	public FileService(Context context) {
		this.context = context;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param dir_name
	 *            文件夹名字
	 * @return 是否创建成功
	 */
	public boolean makeDir(String path, String dir_name) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File dir = new File(Environment.getExternalStorageDirectory()
					+ path + dir_name);
			if (!dir.exists()) {
				dir.mkdirs();
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 * 保存文件到SD卡(不包括换行)
	 * 
	 * @param path
	 *            文件保存路径
	 * @param file_name
	 *            文件名
	 * @param content
	 *            文件内容
	 * @return 是否保存成功
	 */
	public boolean saveFile2SD(String path, String file_name, String content) {
		if (!(Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())))
			return false;

		File file = new File(Environment.getExternalStorageDirectory() + path
				+ file_name);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					file));
			bufferedWriter.write(content);
			if (bufferedWriter != null)
				bufferedWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 保存文件到SD卡(包括换行)
	 * 
	 * @param path
	 *            文件保存路径
	 * @param file_name
	 *            文件名
	 * @param content
	 *            文件内容
	 * @return 是否保存成功
	 */
	public boolean saveFile2SDcard(String path, String file_name, String content) {
		if (!Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
			return false;
		File file = new File(Environment.getExternalStorageDirectory() + path
				+ file_name);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content.getBytes());
			if (fileOutputStream != null)
				fileOutputStream.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 从SD卡读取文件内容(不包括换行)
	 * 
	 * @param path
	 *            文件路径
	 * @param file_name
	 *            文件名
	 * @return 返回文件内容
	 */
	public String readFileFromSD(String path, String file_name) {

		File file = new File(Environment.getExternalStorageDirectory() + path
				+ file_name);

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					file));
			String content = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ((content = bufferedReader.readLine()) != null) {
				stringBuffer.append(content);
			}
			if (bufferedReader != null)
				bufferedReader.close();
			return stringBuffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param path
	 * @param file_name
	 * @return
	 */
	public String readFileFromSDcard(String path, String file_name) {
		File file = new File(Environment.getExternalStorageDirectory() + path
				+ file_name);
		String content = "";
		try {
			FileReader fileReader = new FileReader(file);
			int len = fileReader.read();
			while (len >= 0) {
				content += (char) len;
				len = fileReader.read();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 从SD删除文件
	 * 
	 * @param path
	 *            文件路径
	 * @param file_name
	 *            文件名
	 * @return 是否删除成功
	 */
	public boolean deleteFileFromSD(String path, String file_name) {
		File file = new File(Environment.getExternalStorageDirectory() + path
				+ file_name);
		file.delete();
		return true;
	}
}