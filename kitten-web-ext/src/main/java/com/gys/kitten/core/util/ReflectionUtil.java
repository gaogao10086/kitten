package com.gys.kitten.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description 反射工具类
 * @author kitten
 * @CreateData 20120419
 * @modifyList 
 * 
 */
public class ReflectionUtil {
	
	/**
	 * Description 循环向上转型，获取对象的DeclaredMethod
	 * @param object 子类对象
	 * @param methodName 父类中的方法
	 * @param parameterTypes 父类中的方法参数类型
	 * @return 父类中的方法对象
	 * @author GaoYS
	 * @CreateData 20120419
	 * @modifyList 
	 * 
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes){
		for(Class<?> clazz = object.getClass(); clazz !=Object.class; clazz = clazz.getSuperclass()){
			try {
				Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}
		return null;
	}
	
	/**
	 * Description 直接调用对象方法，而忽略修饰符(private,protected,default)
	 * @param object 子类对象
	 * @param methodName 父类中的方法
	 * @param parameterTypes 父类中的方法参数类型
	 * @param parameters 父类中的方法参数
	 * @return 父类中方法的执行结果
	 * @author GaoYS
	 * @CreateData 20120419
	 * @modifyList 
	 * 
	 */
	public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters){
		Object obj = null;
//		根据对象、方法名和对应的方法参数通过反射调用上面的方法获得Method对象
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
//		抑制JAVA对方法进行检查，主要是针对私有方法而言
		method.setAccessible(true);
		try {
			if(method !=null){
				obj = method.invoke(object, parameters);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * Description 循环向上转型,获得对象的DeclaredField
	 * @param object 子类对象
	 * @param fieldName 父类中的属性名
	 * @return 父类中的属性对象
	 * @author GaoYS
	 * @CreateData 20120419
	 * @modifyList 
	 * 
	 */
	public static Field getDeclaredField(Object object, String fieldName){
		for(Class<?> clazz = object.getClass(); clazz !=Object.class; clazz = clazz.getSuperclass()){
			try {
				Field field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}
	
	/**
	 * Description 直接设置对象属性值，忽略修饰符(private,protected)，也不经过setter
	 * @param object 子类对象
	 * @param fieldName 父类中的属性名
	 * @param value 设置值
	 * @author GaoYS
	 * @CreateData 20120419
	 * @modifyList 
	 * 
	 */
	public static void setFieldValue(Object object, String fieldName, Object value){
		Field field = getDeclaredField(object,fieldName);
//		抑制JAVA对其的检查
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Description 直接获取对象属性值，忽略修饰符(private,protected)，也不经过getter
	 * @param object 子类对象
	 * @param fieldName 父类中的属性名
	 * @return 父类中的属性值
	 * @author GaoYS
	 * @CreateData 20120419
	 * @modifyList 
	 * 
	 */
	public static Object getFieldValue(Object object, String fieldName){
		Object obj = null;
		Field field = getDeclaredField(object,fieldName);
//		抑制JAVA对其的检查
		field.setAccessible(true);
		try {
			obj = field.get(object);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
}
