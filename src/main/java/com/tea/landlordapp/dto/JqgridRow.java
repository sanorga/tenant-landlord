/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.landlordapp.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arnav
 */
public class JqgridRow {
    
    private Long id;
    private List<String> cell = new ArrayList<String>();

    public List<String> getCell() {
        return cell;
    }

    public void setCell(List<String> cell) {
        this.cell = cell;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}