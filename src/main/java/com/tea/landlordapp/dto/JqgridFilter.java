package com.tea.landlordapp.dto;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JqgridFilter {

	private String groupOp;
	private List<JqgridFilterRule> rules;
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public List<JqgridFilterRule> getRules() {
		return rules;
	}
	public void setRules(List<JqgridFilterRule> rules) {
		this.rules = rules;
	}
	
	public static JqgridFilter getObject(String json){
		Gson gson = new Gson();
		Type collectionType = new TypeToken<JqgridFilter>(){}.getType();
		JqgridFilter filters = gson.fromJson(json, collectionType);
		
		return filters;
	}
	@Override
	public String toString() {
		return "JQGridFilter [groupOp=" + groupOp + ", rules=" + rules + "]";
	}
	
	public static void main(String args[]){
		String json = "{'groupOp':'AND','rules':[{'field':'user.username','op':'bw','data':'arnav'},{'field':'campaign.campaignName','op':'bw','data':'DB'}]}";
		Gson gson = new Gson();
		Type collectionType = new TypeToken<JqgridFilter>(){}.getType();
		JqgridFilter sections = gson.fromJson(json, collectionType);
		
		System.out.println(sections);
		
	}

}
