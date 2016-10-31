package com.tea.landlordapp.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class JqgridFilterRule {
	private static final String REPORT_NUMBER = "a.id";
	private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private String field;
	private String op;
	private String data;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		if(op.equals("cn"))
			this.op="like";
  		else if(op.equals("eq"))
  			this.op="=";
  		else
  			this.op="like";
	}
	public String getData() {

	      // for all String criteria's
	      if (StringUtils.equals(op, "like")) return "%" + data + "%";

		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	 public String getValueAsString() throws ParseException {
	      if ((StringUtils.equals(field, REPORT_NUMBER)) ) {
	         return data;
	      }

	      if (StringUtils.containsIgnoreCase(field, "date")) {
	         Date date = df.parse(data);
	         return "'" + df.format(date).toString() + "'";
	      }

	      

	      // for all String criteria's
	      data = StringEscapeUtils.escapeSql(data);
	      if (StringUtils.equals(op, "like")) return "'%" + data.toUpperCase() + "%'";

	      return "'" + data.toUpperCase() + "'".toUpperCase();
	   }

	@Override
	public String toString() {
		return "JQGridFilterRule [field=" + field + ", op=" + op + ", data="
				+ data + "]";
	}
	
	public String queryClause(){
		String where = "";
		DateFormat uiFmt = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat sqlFmt = new SimpleDateFormat("yyyy-MM-dd");
		
		String fld = StringUtils.isNotBlank(field) ? field.trim() : "";
		String val = StringUtils.isNotBlank(data) ? data.trim() : "";
		boolean isBool = StringUtils.equalsIgnoreCase("true", val) || StringUtils.equalsIgnoreCase("false", val);
		
		if (!NumberUtils.isNumber(val) && !isBool){
			try {
				Date dt = uiFmt.parse(val);
				val = sqlFmt.format(dt);
				fld = "date(" + fld + ")";
			} catch (ParseException e) {
				// do nothing - not a date
			}
			
			val = "'" + val + "'";
		}

		String cleanVal = StringUtils.isNotBlank(data) ? data.trim() : "";
		switch (op){
			case "eq":
				where = fld + " = " + val;
				break;
			case "ne":
				where = fld + " <> " + val;
				break;
			case "lt":
				where = fld + " < " + val;
				break;
			case "le":
				where = fld + " <= " + val;
				break;
			case "gt":
				where = fld + " > " + val;
				break;
			case "ge":
				where = fld + " >= " + val;
				break;
			case "bw":
				where = fld + " like '" + cleanVal + "%'";
				break;
			case "bn":
				where = fld + " not like '" + cleanVal + "%'";
				break;
			case "in":
				where = fld + " in (" + cleanVal + ")";
				break;
			case "ni":
				where = fld + " not in (" + cleanVal + ")";
				break;
			case "ew":
				where = fld + " like '%" + cleanVal + "'";
				break;
			case "en":
				where = fld + " not like '%" + cleanVal + "'";
				break;
			case "cn":
				where = fld + " like '%" + cleanVal + "%'";
				break;
			case "nc":
				where = fld + " not like '%" + cleanVal + "%'";
				break;
			default:
				break;
		}
		return where;
	}
	
	
}
