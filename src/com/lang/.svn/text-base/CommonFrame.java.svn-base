package com.lang;

import javax.swing.JFrame;

public abstract class  CommonFrame extends JFrame implements LanguageChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  ResourceManager rm = ResourceManager.getInstance();     
	/**
	 * update UI message by I18n
	 */
	@Override
	public void updateResource() {
		
	}

	/**
	 *  get i18n value
	 */
	protected String getString(String key) {
		return rm.getString(key);
	}

	/**
	 * set i18n locale
	 */
	protected void setLanguage(String language) {
		rm.changeLang(language);
	}
	
}
