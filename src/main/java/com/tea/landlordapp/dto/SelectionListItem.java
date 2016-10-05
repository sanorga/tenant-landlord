/**
 * 
 */
package com.tea.landlordapp.dto;

/**
 * @author jerry
 *
 */
public class SelectionListItem implements Comparable<SelectionListItem> {
	public SelectionListItem(int key, String label) {
		this.key = key;
		this.label = label;
	}
	private int key;
	private String label;
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int compareTo(SelectionListItem o) {
		final int BEFORE = -1;
//		final int EQUAL = 0;
		final int AFTER = 1;
		
		SelectionListItem testVal = o;
		if (this.key == testVal.key) return this.label.compareToIgnoreCase(testVal.label);
		if (this.key == 0) return BEFORE;
		if (testVal.key == 0) return AFTER;
		return this.label.compareToIgnoreCase(testVal.label);
	}
	
//	public static java.util.List<SelectionListItem> buildSelectionList(java.util.List.Iterator queryResults) {
//		
//	}
}
