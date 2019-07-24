package com.mes.common;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

/**
 *	包含string的处理
 */
public class CommonUtils {
	
	@Test
	public void test() {
		//54125e57-b344-4c2f-b969-09d19c9f68e7
		System.out.println(CommonUtils.uuid());
//		62ED973AEB3D4AAD8EF71100EC3AC717
	}

	//返回一个不重复的字符串
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-","").toUpperCase();
	}
	
	//map转换成bean
	public static <T> T toBean(Map map,Class<T> clazz) {
		try {
			//通过clazz创建实例
			T bean=clazz.newInstance();
			//使用beanutils.populute封装map数据到bean中
			//日期格式转换
			ConvertUtils.register(new DateConverter("yyyy-MM-dd"),java.util.Date.class);
			BeanUtils.populate(bean, map);//map.put(“date”,string);
										//bean-date:Date
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e+"封装bean出了问题");
		}
		
	}
}
