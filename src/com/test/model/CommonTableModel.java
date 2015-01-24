package com.test.model;


import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class CommonTableModel implements TableModel {
   public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

private String[] header;
   private List<Map<String,Object>> datas;
	
	public CommonTableModel(String[] header,List<Map<String,Object>> datas){
		this.datas = datas;
		this.header = header;
	}
   
	@Override
	public int getRowCount() {
		return (null == datas ||  0 == datas.size() ) ? 0:datas.size();
	}

	@Override
	public int getColumnCount() {
		return (null == header ||  0 == header.length) ? 0:header.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

}
