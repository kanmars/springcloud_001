package cn.com.sn.frame.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import cn.com.sn.frame.exception.SnCommonException;

/**
 * @ClassName: MapObjTransUtils
 * @Description: 对象转化工具类
 * @author baolong
 * @date 2012年5月20日 上午10:42:44
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class MapObjTransUtils {
	
	/**
	 * 将hashMap转化为一个指定的对象，根据对象中的set方法，在hashMap中获取对应的键值对，将值传入指定对象的set方法参数中，
	 * 返回一个所要求的对象。注意：无法处理联合主键：即对象中包含另一个对象的问题（除了在map中包含其所要设置对象的情况）。
	 *
	 * @param s
	 *            需要生成对象的class
	 * @param hashMap
	 *            参数hashMap
	 * @return
	 * @throws Exception 
	 */
	public static Object classMapToObject(Class s, Map hashMap) throws Exception {
		Object result = null;
		try {
			// 结果
			result = s.newInstance();
			// 方法名
			String methodName;
			// 变量名
			String varName;
			// 方法第四个字母
			char char3;
			// 从map中获取的值
			Object value;
			// set方法参数
			Class[] methodArguments;
			// set方法参数第一个参数名
			String fstArgName;
			// 值的Class名
			String valueClassName = null;
			for (Method m : s.getMethods()) {
				methodName = m.getName();
				char3 = methodName.charAt(3);
				if (methodName.startsWith("set") && char3 >= 'A' && char3 <= 'Z') {
					// 变量名
					varName = ((char) (methodName.charAt(3) + ('a' - 'A'))) + methodName.substring(4);
					value = hashMap.get(varName);
					// 方法参数
					methodArguments = m.getParameterTypes();
					// 第一个方法参数的名称
					fstArgName = methodArguments[0].getName();
					// 试图直接执行
					if (value != null) {
						valueClassName = value.getClass().getName();
						// 对大数型进行处理
						if (fstArgName.equals("java.math.BigDecimal") && (!valueClassName.equals("java.math.BigDecimal"))) {
							// 如果值为空，继续遍历
							if (StringUtils.isEmpty(value.toString())) {
								continue;
							}
							// 获取值
							value = new BigDecimal(value.toString());
						}
						// 对short型进行处理
						if (fstArgName.equals("java.lang.Short") && (!valueClassName.equals("java.lang.Short"))) {
							if (StringUtils.isEmpty(value.toString())) {
								continue;
							}
							// 获取值
							value = new Short(value.toString());
						}
						// 对Integer型进行处理
						if (fstArgName.equals("java.lang.Integer") && (!valueClassName.equals("java.lang.Integer"))) {
							if (StringUtils.isEmpty(value.toString())) {
								continue;
							}
							// 获取值
							value = new Integer(value.toString());
						}
						// 执行方法
						m.invoke(result, value);
					}
				}
			}
		} catch (InstantiationException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生InstantiationException异常"+ e.toString());
		} catch (IllegalAccessException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalAccessException异常"+ e.toString());

		} catch (IllegalArgumentException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalArgumentException异常"+ e.toString());

		} catch (InvocationTargetException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生InvocationTargetException异常"+ e.toString());
		}
		return result;
	}

	/**
	 * 通过调用一个对象中的get方法,将一个对象转化为一个hashMap<br>
	 * 返回不带class属性的hashMap,对于联合主键将返回联合主键本身
	 *
	 * @param o
	 *            需要转换为hashMap的对象
	 * @return
	 * @throws Exception 
	 */
	public static HashMap objectToMap(Object o) throws Exception {
		// 返回信息
		return objectToMap(o, false);
	}

	/**
	 * 返回选择是否带class属性的hashMap方法
	 *
	 * @param o
	 *            需要转换为hashMap的对象
	 * @param hasClass
	 *            此参数为true则返回的hashMap带有class的键值对,
	 *            此参数为false则返回的hashMap不带有class的键值对
	 * @return
	 * @throws Exception 
	 */
	public static HashMap objectToMap(Object o, boolean hasClass) throws Exception {
		HashMap result = new HashMap();
		// 方法名
		String methodName;
		// 变量名
		String varName;
		char char3;
		Object value;
		// 遍历map
		try {
			for (Method m : o.getClass().getMethods()) {
				methodName = m.getName();
				// 如果是getClass方法，继续
				if (methodName.equals("getClass") && !hasClass) {
					continue;
				}
				// 获取所有的get方法
				char3 = methodName.charAt(3);
				if (methodName.startsWith("get") && char3 >= 'A' && char3 <= 'Z') {
					varName = ((char) (char3 + ('a' - 'A'))) + methodName.substring(4);
					// 获取get方法的值
					value = m.invoke(o, null);
					// 设置字段名、及其对应的值
					result.put(varName, value);
				}
			}
		} catch (IllegalArgumentException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.objectToMap内发生IllegalArgumentException异常"+ e.toString());
		} catch (IllegalAccessException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.objectToMap内发生IllegalAccessException异常"+ e.toString());
		} catch (InvocationTargetException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.objectToMap内发生InvocationTargetException异常"+ e.toString());
		}
		// 返回map
		return result;
	}

	/**
	 * 对arg1进行插入，将arg2的数据插入到arg1中,如class a1{String id;String name,String sex,int
	 * age},class a2{String id,String name} <br>
	 * 现有两个对象a1{sex=A,age=20},a2{id=1,name=kkk}<br>
	 * 调用objToObj(a1,a2)将会把a2中的属性id=1,name=kkk赋值到a1中,<br>
	 * 结果为a1{id=1,name=kkk,sex=A,age=20},a2{id=1,name=kkk}<br>
	 * 需要注意的是:arg2的get方法的返回类型必须与arg1的set方法类型匹配
	 *
	 * @param arg1
	 *            被插入的对象
	 * @param arg2
	 *            插入的对象
	 * @throws Exception 
	 */
	public static void objToObj(Object arg1, Object arg2) throws Exception {
		// 原理：获取到arg2里所有的get方法(class除外),根据方法名寻找arg1中对应的set方法，如果找到则取出arg2的value,调用arg1的set方法设置arg1的值
		if (arg1 == null || arg2 == null) {
			return;
		}
		try {
			// get方法名
			String arg2GetName = null;
			// 方法名
			String methodEndName = null;
			// set方法名
			Method arg1SetMethod = null;
			// 返回值类型
			Class arg2ReturnType = null;
			Object value = null;
			// 通过反射机制获取某个类所有方法
			for (Method getMethod : arg2.getClass().getMethods()) {
				// 获取get方法
				arg2GetName = getMethod.getName();
				if (!arg2GetName.startsWith("get")) {
					continue;
				}
				// 过滤
				if (arg2GetName.equals("getClass")) {
					continue;
				}
				// 获取字段属性名
				methodEndName = arg2GetName.substring(3);
				// 获取字段类型
				arg2ReturnType = getMethod.getReturnType();

				try {
					// 获取set方法名
					arg1SetMethod = arg1.getClass().getMethod("set" + methodEndName, arg2ReturnType);
					// 如果set方法存在，获取对应的get方法名
					if (arg1SetMethod != null) {
						value = getMethod.invoke(arg2, null);
						// 如果值不为空，执行set方法
						if (value != null)
							arg1SetMethod.invoke(arg1, value);
					}
				} catch (NoSuchMethodException e) {
					// 异常处理
					System.out.println("试图寻找来自arg2的get" + methodEndName+ "方法在arg1中对应的set" + methodEndName+ "方法，未找到，已人工处理，继续下个方法");
				}
			}
		} catch (SecurityException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.objToObj内发生SecurityException异常"+ e.toString());
		} catch (IllegalArgumentException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.objToObj内发生IllegalArgumentException异常"+ e.toString());
		} catch (IllegalAccessException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.objToObj内发生IllegalAccessException异常"+ e.toString());
		} catch (InvocationTargetException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.objToObj内发生InvocationTargetException异常"+ e.toString());
		}
	}

	/**
	 * 在arg1中查找所有的set方法，找到get方法后试图在arg2中get其属性名对应的value,如果找到值，则将值在arg1上执行set方法;<br>
	 * 对Integer,BigDecimal,Short型号的数据单独处理
	 *
	 * @param arg1
	 * @param arg2
	 * @throws Exception 
	 */
	public static void mapToObj(Object arg1, Map arg2) throws Exception {
		try {
			// 方法名
			String methodName;
			// 变量名
			String varName;
			// 方法第四个字母
			char char3;
			// 从map中获取的值
			Object value;
			// set方法参数
			Class[] methodArguments;
			// set方法参数第一个参数名
			String fstArgName;
			// 值的Class名
			String valueClassName = null;
			// 循环遍历
			for (Method m : arg1.getClass().getMethods()) {
				methodName = m.getName();
				char3 = methodName.charAt(3);
				// 获取set方法
				if (methodName.startsWith("set") && char3 >= 'A' && char3 <= 'Z') {
					varName = ((char) (methodName.charAt(3) + ('a' - 'A'))) + methodName.substring(4);
					value = arg2.get(varName);
					if (value != null) {
						// 方法参数
						methodArguments = m.getParameterTypes();
						// 第一个方法参数的名称
						fstArgName = methodArguments[0].getName();
						// 试图直接执行
						if (value != null) {
							valueClassName = value.getClass().getName();
							// 对大数型进行处理
							if (fstArgName.equals("java.math.BigDecimal") && (!valueClassName.equals("java.math.BigDecimal"))) {
								if (StringUtils.isEmpty(value.toString())) {
									continue;
								}
								// 获取值
								value = new BigDecimal(value.toString());
							}
							// 对short型进行处理
							if (fstArgName.equals("java.lang.Short")&& (!valueClassName.equals("java.lang.Short"))) {
								if (StringUtils.isEmpty(value.toString())) {
									continue;
								}
								// 获取值
								value = new Short(value.toString());
							}
							// 对Integer型进行处理
							if (fstArgName.equals("java.lang.Integer") && (!valueClassName.equals("java.lang.Integer"))) {
								if (StringUtils.isEmpty(value.toString())) {
									continue;
								}
								// 获取值
								value = new Integer(value.toString());
							}
							// 通过反射机制执行方法
							m.invoke(arg1, value);
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalAccessException异常"+ e.toString());
		} catch (IllegalArgumentException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalArgumentException异常"+ e.toString());
		} catch (InvocationTargetException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生InvocationTargetException异常"+ e.toString());
		}
	}

	/**
	 * 在obj中寻找set(String)方法，如果找到方法，则判断对应的get()方法返回值是否是null并且返回值类型为String,如果是,则调用该对象的set(""
	 * )方法
	 *
	 * @param obj
	 * @throws Exception 
	 */
	public static void tranObjNullToSS(Object obj) throws Exception {
		// 反射机制
		Class oc = obj.getClass();
		// 变量名
		String valName = null;
		// get方法名
		String getMethodName = null;
		// set方法名
		String setMethdoName = null;
		Class[] paramters = null;
		String paramName = null;
		// 变量名
		Method getMethod = null;
		Object value = null;
		try {
			// 循环遍历
			for (Method m : oc.getMethods()) {
				valName = m.getName();
				// 处理set方法
				if (valName.startsWith("set")) {
					paramters = m.getParameterTypes();
					if (paramters[0] != null) {
						paramName = paramters[0].getName();
						// 获取参数类型
						if (paramName.equals("java.lang.String")) {
							try {
								getMethod = oc.getMethod("g" + valName.substring(1), null);
								if (getMethod != null) {
									value = getMethod.invoke(obj, null);
									// get方法返回值的类型
									if(value==null&&getMethod.getReturnType().getName().equals("java.lang.String")){
										// 反射机制执行方法
										m.invoke(obj, "");
									}
								}
							} catch (SecurityException e) {
								// 异常处理
								System.out.println("MapToObj.tranObjNullToSS方法发生SecurityException异常，已人工处理");
							} catch (NoSuchMethodException e) {
								// 异常处理
								System.out.println("MapToObj.tranObjNullToSS方法发生NoSuchMethodException异常，已人工处理");
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalArgumentException异常"+ e.toString());
		} catch (IllegalAccessException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalAccessException异常"+ e.toString());
		} catch (InvocationTargetException e) {
			// 异常处理
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生InvocationTargetException异常"+ e.toString());
		}
	}
	/**
	 * 对参数传入的HashMap进行清理，去除value=null的键值对，去除value=空字符串的键值对
	 * @param hashMap
	 * @throws SnCommonException
	 */
	public static void clearSpaceFromHashMap(Map hashMap) throws SnCommonException {
		Set set=new HashSet();
		// 循环遍历
		for(Entry e:(Set<Entry>)hashMap.entrySet()){
			// 获取key值
			Object k=e.getKey();
			// 获取value
			Object v=e.getValue();
			if(v==null){
				// 增加到set集合中，待去除
				set.add(k);
				continue;
			}
			if(v instanceof String){
				if(((String)v).trim().equals("")){
					// 增加到set集合中，待去除
					set.add(k);
					continue;
				}
			}
		}
		for(Object o:set){
			// 去除空
			hashMap.remove(o);
		}
	}
	/**
	 * 对参数传入的HashMap进行清理，取出value=null的键值对。
	 * @param hashMap
	 * @throws SnCommonException
	 */
	public static void clearNullFromHashMap(Map hashMap) throws SnCommonException {
		Set set=new HashSet();
		for(Entry e:(Set<Entry>)hashMap.entrySet()){
			// 获取key
			Object k=e.getKey();
			// 获取value
			Object v=e.getValue();
			if(v==null){
				set.add(k);
				continue;
			}
		}
		for(Object o:set){
			// 去除空信息
			hashMap.remove(o);
		}
	}
	/**
	 * 对参数传入的HashMap进行清理，对String型的数据进行trim
	 * @param hashMap
	 * @throws SnCommonException
	 */
	public static void trimHashMap(Map hashMap) throws SnCommonException {
		Set set=new HashSet();
		// 遍历集合
		for(Entry e:(Set<Entry>)hashMap.entrySet()){
			// 获取key
			Object k=e.getKey();
			// 获取value
			Object v=e.getValue();
			// 过滤信息
			if(v!=null&&v.getClass().getName().equals("java.lang.String")){
				if( !  ( ("brhCode".equals(k.toString()) )
						|| ("dealId".equals(k.toString()))
						|| ("draftId".equals(k.toString()))
						|| ("userId".equals(k.toString()))
						|| ("chgSeqNum".equals(k.toString()))
						|| ("buildSeqNum".equals(k.toString())))){
					// 增加到map中
					hashMap.put(k, ((String)v).trim());
				}
			}
		}
		//需要对busTm字段进行去空格，去&nbsp; &#160;
		String busTm = (String)hashMap.get("busTm");
		// 去除所有的空
		if(busTm!=null){
			// 空格替换
			busTm = busTm.replaceAll(" ", "");
			busTm = busTm.replaceAll("&nbsp;", "");
			busTm = busTm.replaceAll("&#160;", "");
			// 设置去除空的字段
			hashMap.put("busTm", busTm);
		}
	}
	/**
	 * 对参数传入的HashMap进行清理，取出value=null的键值对。修改为""
	 * @param hashMap
	 * @throws SnCommonException
	 */
	public static void transNullFromHashMapToSS(Map hashMap) throws SnCommonException {
		Set set=new HashSet();
		for(Entry e:(Set<Entry>)hashMap.entrySet()){
			// 获取key
			Object k=e.getKey();
			// 获取value
			Object v=e.getValue();
			if(v==null){
				set.add(k);
				continue;
			}
		}
		for(Object o:set){
			// 去除空信息
			hashMap.put(o, "");
		}
	}
	/**
	 * 反trim方法，对传入的hashMap进行遍历，将所有""的数据转化为" "
	 * 20120830用于解决变更处理单中空字符串插入数据库后为null的问题
	 * @param hashMap
	 * @throws SnCommonException
	 */
	public static void backTrimHashMap(Map hashMap) throws SnCommonException {
		Set set=new HashSet();
		// 循环遍历
		for(Entry e:(Set<Entry>)hashMap.entrySet()){
			// 获取key
			Object k=e.getKey();
			// 获取value
			Object v=e.getValue();
			// 如果不是机构编码、处理单号、草稿箱号、用户编号、序列号
			// 进行处理
			if(v!=null&&v.getClass().getName().equals("java.lang.String")){
				if( !  ( ("brhCode".equals(k.toString()) )
						|| ("dealId".equals(k.toString()))
						|| ("draftId".equals(k.toString()))
						|| ("userId".equals(k.toString()))
						|| ("chgSeqNum".equals(k.toString()))
						|| ("buildSeqNum".equals(k.toString())))){
					if(((String)v).equals("")){
						// 将空字符串转换为空格
						hashMap.put(k, " ");
					}
				}
			}
		}
	}
	/**
	 * 将一个对象中是数字的属性全部归0，主要用于处理Integer,BigDecimal,Short型的数据
	 * 在obj中寻找set(Number)方法，如果找到方法，则判断对应的get()方法返回值是否是null并且返回值类型为Number,如果是,则调用该对象的set(0)方法
	 *
	 * @param obj
	 * @throws Exception 
	 */
	public static void tranNullNumberToZero(Object obj) throws Exception{
		Class oc = obj.getClass();
		String valName = null;
		// get方法名
		String getMethodName = null;
		// set方法名
		String setMethdoName = null;
		// 属性集合
		Class[] paramters = null;
		// 属性名
		String paramName = null;
		Method getMethod = null;
		Object value = null;
		try {
			// 循环遍历所有的方法
			for (Method m : oc.getMethods()) {
				valName = m.getName();
				// 处理set方法
				if (valName.startsWith("set")) {
					// 获取属性类型
					paramters = m.getParameterTypes();
					if (paramters[0] != null) {
						// 获取属性名称
						paramName = paramters[0].getName();
						if (paramName.equals("java.lang.String")) {
							System.out.println("MapToObj.tranNullNumberToZero方法内被处理参数为字符串,不进行处理");
						}else if(paramName.equals("java.lang.Integer")){
							try {
								// 获取get方法
								getMethod = oc.getMethod("g" + valName.substring(1), null);
								if (getMethod != null) {
									// 执行get方法
									value = getMethod.invoke(obj, null);
									if(value==null&&getMethod.getReturnType().getName().equals("java.lang.Integer")){
										m.invoke(obj, 0);
									}
								}
								// 异常处理
							} catch (SecurityException e) {
								System.out.println("MapToObj.tranNullNumberToZero方法发生SecurityException异常，已人工处理");
							} catch (NoSuchMethodException e) {
								// 异常处理
								System.out.println("MapToObj.tranNullNumberToZero方法发生NoSuchMethodException异常，已人工处理");
							}
							// Short类型
						}else if(paramName.equals("java.lang.Short")){
							try {
								// 获取方法
								getMethod = oc.getMethod("g" + valName.substring(1), null);
								if (getMethod != null) {
									// 执行get方法
									value = getMethod.invoke(obj, null);
									if(value==null&&getMethod.getReturnType().getName().equals("java.lang.Short")){
										// 执行方法
										m.invoke(obj, new Short("0"));
									}
								}
							} catch (SecurityException e) {
								// 异常处理
								System.out.println("MapToObj.tranNullNumberToZero方法发生SecurityException异常，已人工处理");
							} catch (NoSuchMethodException e) {
								// 异常处理
								System.out.println("MapToObj.tranNullNumberToZero方法发生NoSuchMethodException异常，已人工处理");
							}
							// BigDecimal类型
						}else if(paramName.equals("java.math.BigDecimal")){
							try {
								getMethod = oc.getMethod("g" + valName.substring(1), null);
								if (getMethod != null) {
									// 执行get方法
									value = getMethod.invoke(obj, null);
									if(value==null&&getMethod.getReturnType().getName().equals("java.math.BigDecimal")){
										// 执行方法
										m.invoke(obj, new BigDecimal("0"));
									}
								}
							} catch (SecurityException e) {
								// 异常处理
								System.out.println("MapToObj.tranNullNumberToZero方法发生SecurityException异常，已人工处理");
							} catch (NoSuchMethodException e) {
								// 异常处理
								System.out.println("MapToObj.tranNullNumberToZero方法发生NoSuchMethodException异常，已人工处理");
							}
						}
					}
				}
			}
			// 异常处理
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalArgumentException异常"+ e.toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生IllegalAccessException异常"+ e.toString());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new Exception("BusinessUtil.classMapToObject内发生InvocationTargetException异常"+ e.toString());
		}
		
	}
}
