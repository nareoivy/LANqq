package com.chat.util;

import java.util.List;

public class StringUtil {
	/**
	 * 把String对应的位置替换成空字符串
	 */
	public static String replace(String msg,int ...pos){
		int[] i_pos = new int[pos.length];
		String res = "";
		for(int i = 0; i < pos.length; i++){
			i_pos[i] = pos[i];
		}
		res += msg.substring(0, i_pos[0]);
		for(int i = 0; i < i_pos.length; i++){
			if(i+1 == i_pos.length){
				res += msg.substring(i_pos[i]+1, msg.length());
			}else{
				res += msg.substring(i_pos[i]+1, i_pos[i+1]);
			}
		}
		
		return res;
	}
	
	/**
	 * 判断是否为空
	 * @param obj
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static boolean isNull(Object obj)
	{
		boolean isNull = false;
		if (obj == null)
		{
			return true;
		} else
		{
			if (obj instanceof String)
			{
				isNull = obj.toString().length() == 0;
			} else if (obj instanceof List)
			{
				isNull = ((List) obj).size() == 0;
			} else if (obj instanceof StringBuffer)
			{
				isNull = ((StringBuffer) obj).length() == 0;
			}else if(obj instanceof Object[]){
				isNull = !(obj != null && ((Object[])obj).length !=0);				
			}else if(obj instanceof Integer){
				isNull = !(obj != null && ((Integer)obj).intValue() !=0); 
			}

		}
		return isNull;
	}
	
	/**
	 * 查询中文文本长度
	 */
	public static int doLenStr(String str) {
		int len = 0;
		if (str != null && str.length() > 0) {
			try {
				// byte[] b = str.getBytes();
				byte[] b = str.getBytes("UTF-8");
				len = b.length;
			} catch (Exception e) {
			}
		}
		return len;
	}
}
