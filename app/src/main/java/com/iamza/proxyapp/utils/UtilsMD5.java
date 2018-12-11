package com.iamza.proxyapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class UtilsMD5 {

	@SuppressWarnings("unused")
	private static final int VERSION = 3;

	public static String getMD5String(String string) {
		return getMD5String(string.getBytes());
	}

	public static String getMD5String(byte[] bytes) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(bytes);
			return byteArrayToHex(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();

			return "";
		}
	}

	public static String getMD5File(String file) {
		return getMD5File(new File(file));
	}

	public static String getMD5File(File file) {
		try {
			return getInputStreamMD5(new FileInputStream(file), true);
		} catch (Exception e) {
			e.printStackTrace();

			return "";
		}
	}

	public static String getInputStreamMD5(InputStream input, boolean isClose) {
		DigestInputStream digestInputStream = null;
		try {
			// 拿到一个MD5转换器（同样，这里可以换成SHA1）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			// 使用DigestInputStream
			digestInputStream = new DigestInputStream(input, messageDigest);
			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer = new byte[128 * 1024];
			while (digestInputStream.read(buffer) > 0) {
			}

			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();

			// 拿到结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();

			// 同样，把字节数组转换成字符串
			return byteArrayToHex(resultByteArray);
		} catch (Exception e) {
			return "";
		} finally {
			try {
				digestInputStream.close();
			} catch (Exception e) {
			}

			if (isClose) {
				try {
					input.close();
				} catch (Exception e) {
				}
			}
		}
	}

	// 下面这个函数用于将字节数组换成成16进制的字符串
	private static String byteArrayToHex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + "";
			}
		}

		return hs;
	}
}
