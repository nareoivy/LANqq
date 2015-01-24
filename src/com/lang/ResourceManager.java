package com.lang;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
	// 监听器列表
	@SuppressWarnings("rawtypes")
	LinkedList listeners = new LinkedList();
	ResourceBundle bundle;
	Locale locale;

	// ResourceBundle文件
	private static final String RESOURCE_NAME = "com/lang/message";
	// 使用Singleton使得ResourceManager在程序里只有一个实例
	private static ResourceManager _instance = new ResourceManager();
	public static ResourceManager getInstance() {
		return _instance;
	}

	// 更换语言显示
	@SuppressWarnings("rawtypes")
	public void changeLang(String lang) {
		// 1 换bundle
		if (lang.equalsIgnoreCase("zh_CN")) {
			locale = Locale.CHINA;
		} else {
			locale = Locale.US;
		}
		// 2 通知listeners
		Iterator ite = listeners.iterator();
		while (ite.hasNext()) {
			((LanguageChangeListener) ite.next()).updateResource();
		}
	}

	// 取得显示字符串
	public String getString(String key) {
		return getBundle().getString(key);
	}

	/**
	 * 取得设置后的语言版本，如果没有设置，则使用默认值Local.US
	 * @return
	 */
	public ResourceBundle getBundle() {

		if (locale == null)
			locale = Locale.CHINA;

		ResourceBundle bundle = null;

		if (bundle == null) {
			//bundle = ResourceBundle.getBundle("message");
			bundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
		}
		return bundle;
	}

	// 增加监听器
	@SuppressWarnings("unchecked")
	public void addListener(LanguageChangeListener lsn) {
		listeners.add(lsn);
	}
}
