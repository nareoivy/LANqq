package com.chat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author pccw
 *
 */
public class JsonUtil{
	
	
	/**
	 * 从一个java对象得到一个json字符串
	 * @param object
	 * @return
	 */
	public static String getJsonString(Object object){
	 JSONObject json = JSONObject.fromObject(object);
	 return json.toString();
	}
	
	
	
      /**     
       * 从一个JSON 对象字符格式中得到一个java对象     
       *      
       * @param jsonString     
       * @param pojoCalss     
       * @return     
       */       
      @SuppressWarnings("rawtypes")
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {        
          Object pojo;
//          System.out.println(jsonString);
          JSONObject jsonObject = JSONObject.fromObject(jsonString);        
          pojo = JSONObject.toBean(jsonObject, pojoCalss);        
          return pojo;        
      }        
         
         
      /**     
       * 从json HASH表达式中获取一个map，改map支持嵌套功能     
       *      
       * @param jsonString     
       * @return     
       */       
      @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMap4Json(String jsonString) {        
          JSONObject jsonObject = JSONObject.fromObject(jsonString);        
          Iterator keyIter = jsonObject.keys();        
          String key;        
          Object value;        
          Map valueMap = new HashMap();        
         
          while (keyIter.hasNext()) {        
              key = (String) keyIter.next();        
              value = jsonObject.get(key);        
              valueMap.put(key, value);        
          }        
         
          return valueMap;        
      }        
         
         
      /**     
       * 从json数组中得到相应java数组     
       *      
       * @param jsonString     
       * @return     
       */       
      public static Object[] getObjectArray4Json(String jsonString) {        
          JSONArray jsonArray = JSONArray.fromObject(jsonString);        
          return jsonArray.toArray();        
         
      }        
         
         
      /**     
       * 从json对象集合表达式中得到一个java对象列表     
       *      
       * @param jsonString     
       * @param pojoClass     
       * @return     
       */       
      @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getList4Json(String jsonString, Class pojoClass) {        
         
          JSONArray jsonArray = JSONArray.fromObject(jsonString);        
          JSONObject jsonObject;        
          Object pojoValue;        
         
          List list = new ArrayList();        
          for (int i = 0; i < jsonArray.size();i++) {        
         
              jsonObject = jsonArray.getJSONObject(i);        
              pojoValue = JSONObject.toBean(jsonObject, pojoClass);        
              list.add(pojoValue);        
         
          }        
          return list;        
          
       }        
          
          
       /**     
        * 从json数组中解析出java字符串数组     
        *      
        * @param jsonString     
        * @return     
        */       
       public static String[] getStringArray4Json(String jsonString) {        
          
           JSONArray jsonArray = JSONArray.fromObject(jsonString);        
           String[] stringArray = new String[jsonArray.size()];        
           for (int i = 0; i < jsonArray.size(); i++) {        
               stringArray[i] = jsonArray.getString(i);        
          
           }        
          
           return stringArray;        
       }        
          
          
       /**     
        * 从json数组中解析出javaLong型对象数组     
        *      
        * @param jsonString     
        * @return     
        */       
       public static Long[] getLongArray4Json(String jsonString) {        
          
           JSONArray jsonArray = JSONArray.fromObject(jsonString);        
           Long[] longArray = new Long[jsonArray.size()];        
           for (int i = 0; i < jsonArray.size(); i++) {        
               longArray[i] = jsonArray.getLong(i);        
          
           }        
           return longArray;        
       }        
          
          
       /**     
        * 从json数组中解析出java Integer型对象数组     
        *      
        * @param jsonString     
        * @return     
        */       
       public static Integer[] getIntegerArray4Json(String jsonString) {        
          
           JSONArray jsonArray = JSONArray.fromObject(jsonString);        
           Integer[] integerArray = new Integer[jsonArray.size()];        
           for (int i = 0; i < jsonArray.size(); i++) {        
               integerArray[i] = jsonArray.getInt(i);        
          
           }        
           return integerArray;        
       }        
       
       
       /**
        * List - JsonString
        */
       @SuppressWarnings("rawtypes")
	public static String getStringList4Json(List datas){
    	   JSONArray jsonArray = JSONArray.fromObject(datas);
    	   return jsonArray.toString();
       }
       
       
       /**     
        * 从一个JSON 对象字符格式中得到一个java对象     
        *      
        * @param jsonString     
        * @param pojoCalss     
        * @return     
        */       
       @SuppressWarnings({ "rawtypes", "deprecation" })
 	public static Object getList4JsonString(String jsonString, Class pojoCalss) {        
    	   JSONArray jsonArray = JSONArray.fromObject(jsonString);        
    	   List datas = JSONArray.toList(jsonArray, pojoCalss);
           return datas;        
       }  
}
