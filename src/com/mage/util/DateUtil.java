package com.mage.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return (Date) sdf.parse(str);
	}
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date(0);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}

}
