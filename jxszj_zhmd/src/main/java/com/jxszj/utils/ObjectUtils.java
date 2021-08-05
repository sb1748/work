package com.jxszj.utils;

import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectUtils {

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    降序
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月4日 下午2:29:39
	 *&#64;param array
	 *&#64;return
	 * </pre>
	 */
	public static double[] getArray(double[] array) {
		double[] arr = array;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1 - i; j++) {
				if (arr[j] < arr[j + 1]) {
					double temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		return arr;
	}

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    获取元素第一次出现的位置
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月4日 下午2:29:51
	 *&#64;param array
	 *&#64;param chs
	 *&#64;return
	 * </pre>
	 */
	public static int getIndex(double[] array, double chs) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == chs) {
				return i;
			}
		}
		return -1; // 若数组中没有则返回-1
	}

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将double四舍五入保留两位小数
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月4日 下午2:29:09
	 *&#64;param d
	 *&#64;return
	 * </pre>
	 */
	public static double formatDouble(double d) {
		return (double) Math.round(d * 100) / 100;
	}

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    将double类型转换成百分比
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月4日 下午2:28:53
	 *&#64;param d
	 *&#64;param FractionDigits
	 *&#64;return
	 * </pre>
	 */
	public static String getPercentFormat(double d, int FractionDigits) {
		NumberFormat nf = java.text.NumberFormat.getPercentInstance();
		// nf.setMaximumIntegerDigits(IntegerDigits);// 小数点前保留几位
		nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
		String str = nf.format(d);
		return str;
	}

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    Object转Double
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月8日 上午10:52:03
	 *&#64;param obj
	 *&#64;return
	 * </pre>
	 */
	public static double getObjectToDouble(Object obj) {
		if (obj == null || obj.toString().equals("")) {
			return 0.0;
		}
		Double dob = Double.valueOf(obj.toString());
		return dob;
	}

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    Object转int
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月8日 上午10:52:03
	 *&#64;param obj
	 *&#64;return
	 * </pre>
	 */
	public static int getObjectToInteger(Object obj) {
		if (obj == null || obj.toString().equals("")) {
			return 0;
		}
		int i = Integer.valueOf(obj.toString());
		return i;
	}

	/**
	 * 
	 * <pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    提取字符串中的数字
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2019年4月26日 下午4:37:17
	 *&#64;param str
	 *&#64;return
	 * </pre>
	 */
	public static int getInt(String str) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		int i = Integer.valueOf(m.replaceAll("").trim());
		return i;
	}

	public static String getString(Object obj) {
		if (obj == null || "".equals(obj.toString())) {
			return "";
		} else {
			return obj.toString();
		}
	}
	
	/**
	 * 
	 *<pre>
	 *<b>Description:</b> 
	 *    将Object转换成Double
	 *<b>Author:</b> yanwei
	 *<b>Date:</b> 2021年3月3日 下午2:43:17
	 *@param obj
	 *@return
	 *</pre>
	 */
	public static Double getDouble(Object obj){
		if (obj == null || "".equals(obj.toString())) {
			return 0.0;
		} else {
			return Double.valueOf(obj.toString());
		}
	}

}
