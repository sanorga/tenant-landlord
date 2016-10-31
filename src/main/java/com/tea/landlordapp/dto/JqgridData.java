/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.landlordapp.dto;

import java.util.List;

/**
 *
 * @author arnav
 */
public class JqgridData {
    
    private Integer page;
    private Integer total;
    private Long records; 
    private List<JqgridRow> rows;

    public JqgridData() {
	}

	public JqgridData(Integer page, Integer total, Long records,
			List<JqgridRow> rows) {
		this.page = page;
		this.total = total;
		this.records = records;
		this.rows = rows;
	}

	public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public List<JqgridRow> getRows() {
        return rows;
    }

    public void setRows(List<JqgridRow> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    
}
