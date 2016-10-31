package com.tea.landlordapp.dto;

import java.util.ArrayList;
import java.util.List;

public class JqgridResponse<K> {
	private String page;
	private String total;
	private String records;
	private List<K> rows;
	
	
	
	public JqgridResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public JqgridResponse(String page, String total, String records,
			List<K> rows) {
		super();
		this.page = page;
		this.total = total;
		this.records = records;
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public List<K> getRows() {
		return rows;
	}
	public void setRows(List<K> rows) {
		this.rows = rows;
	}
	
	@Override
	public String toString() {
		return "JqgridResponse [page=" + page + ", total=" + total
				+ ", records=" + records + "]";
	}
}
