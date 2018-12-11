package com.iamza.proxyapp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	
	private static final String[] logs={"1.log","2.log","3.log","4.log","5.log","6.log"};

	/**
	 * 修改权限
	 * @param path 路径
	 * @param per 权限
	 * @param isR 是否应用到子文件
	 */
	public static void chmod(String path, String per, boolean isR){
		try {
			String strR = isR ? "-R " : "";
			
			List<String> commnandList = new ArrayList<String>();
			commnandList.add("chmod " + strR + per + " " + path);
			
			UtilsShell.execCommand(commnandList, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
	/**
	 * 获取SD卡
	 */
	public static String getAndroidPath(String SDpath) {
		String path = Environment.getExternalStorageDirectory().getPath()
				+ SDpath;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	

	/**
	 *  复制微信需要的图片到sd卡
	 * 
	 * @param context
	 */
	public static void copyLogs(Context context){
		File file = new File(Environment.getExternalStorageDirectory()+"/xa/logs");
		if (!file.exists()) {
			file.mkdirs();
		}
		for (int i = 0; i < logs.length; i++) {
			FileUtil.copyFromAsset(context, logs[i], file.toString(), logs[i]);
		}
	}
	

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean fileIsExists(String path) {
		boolean isExists = false;
		try {
			File file = new File(path);
			if (file.exists()) {
				return true;
			}
		} catch (Exception e) {

		}
		return isExists;
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public static void deleteFiles(String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 获取跟目�?
	 */
	public static String getSd() {
		String sdPath = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			sdPath = Environment.getDataDirectory().getAbsolutePath() + "/";
		}
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

		}
		return sdPath;
	}

	/**
	 * 从assets复制文件到指定位
	 * 
	 * @param myContext
	 * @param assetName
	 * @param saveDir
	 * @param saveName
	 * @return
	 */
	public static String copyFromAsset(Context myContext, String assetName,
			String saveDir, String saveName) {
		String filePath = saveDir + "/" + saveName;
		if (fileIsExists(filePath)) {
			try {
				InputStream is = myContext.getResources().getAssets()
						.open(assetName);
				String assetMd5 = UtilsMD5.getInputStreamMD5(is, true);
				String dataMd5 = UtilsMD5.getMD5File(filePath);
				if (!assetMd5.equals(dataMd5)) {
					new File(filePath).delete();
					copyFileByAsset(myContext, assetName, saveDir, filePath);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return filePath;
		} else {
			copyFileByAsset(myContext, assetName, saveDir, filePath);
			return filePath;
		}
	}

	/**
	 * 从Asset复制到指定目录
	 * 
	 * @param myContext
	 * @param assetName
	 * @param saveDir
	 * @param filePath
	 */
	public static void copyFileByAsset(Context myContext, String assetName,
			String saveDir, String filePath) {
		File dir = new File(saveDir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			File file = null;
			if (!(file = new File(filePath)).exists()) {
				file.createNewFile();
			}
			InputStream is = myContext.getResources().getAssets()
					.open(assetName);
			FileOutputStream fos = new FileOutputStream(filePath);
			byte[] buffer = new byte[7168];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("hujian", "==========" + e.getMessage());
		}
	}

	/**
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readFromFile(Context context, String path)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		try {
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (IOException e) {
			Log.e("ReadStream", "读取异常");
			return "";
		} finally {
			br.close();
		}
	}

	/**
	 * 流读取文�?拿到
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readFromAssets(Context context, String name)
			throws IOException {
		InputStream openRawResource = context.getAssets().open(name);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				openRawResource));
		try {
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (IOException e) {
			Log.e("ReadStream", "读取文件流失");
			return "";
		} finally {
			br.close();
		}
	}


	public static ArrayList<String> getLuaFile(String SDpath) {
		ArrayList<String> filelist = null;
		File file = new File(SDpath);
		if (!file.exists()) {
			file.mkdirs();
		}
		filelist = new ArrayList<String>();
		File[] listFiles = file.listFiles();
		for (File fileitem : listFiles) {
			if (fileitem.isFile()) {
				String name = fileitem.getName();
				int length = name.length();
				if (".lua".equals(name.substring(length - 4, length))) {
					filelist.add(name);
				}
			}
		}
		return filelist;
	}

	/**
	 * 从sd卡复制文件到指定位
	 * 
	 * @param assetName
	 * @param saveDir
	 * @param saveName
	 * @return
	 */
	public static String copyFromFile(String fileName, String saveDir,
			String saveName) {
		String filePath = saveDir + "/" + saveName;
		File dir = new File(filePath);
		if (dir.exists()) {
			dir.delete();
			Log.i("hujian", "============delete===");
		}
		Log.i("hujian", "============dir===" + dir);
		try {
			File file = new File(filePath);
			file.createNewFile();
			InputStream is = new FileInputStream(new File(fileName));
			FileOutputStream fos = new FileOutputStream(filePath);
			byte[] buffer = new byte[7168];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			Log.i("hujian", "ex===" + e.getMessage());
			e.printStackTrace();
		}
		return filePath;
	}

	public static void rmoveLibsFormTmp(String file) {

		File dir = new File(file);
		if (dir.exists()) {
			dir.delete();
			Log.i("hujian", "============delete===");
		}
	}
}
