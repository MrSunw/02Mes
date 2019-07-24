package com.mes.common;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter{
	private String format;
	public DateConverter(String format) {
		this.format=format;
	}
	@Override
	public Object convert(Class type, Object value) {
		try {
		//我们遇到的需要转换的值是null，直接返回null
		if(value==null) return null;
		//接受到值并且需要转换成date类型，这个值如果不是String，不转
		if(!(value instanceof String)) {
			return value;
		}
		//把值转换成string
		String val=(String)value;
		//日期格式化
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		//返回转换了格式的日期
		return sdf.parse(val);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
