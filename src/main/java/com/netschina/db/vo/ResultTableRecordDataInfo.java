package com.netschina.db.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//对应记录对象
public class ResultTableRecordDataInfo implements Serializable{
	
	private Map<String, String> mapField;
	private List<ResultProperty> resultPropertys;
	
	public Map<String, String> getMapField() {
		return mapField;
	}
	public void setMapField(Map<String, String> mapField) {
		this.mapField = mapField;
	}
	public List<ResultProperty> getResultPropertys() {
		return resultPropertys;
	}
	public void setResultPropertys(List<ResultProperty> resultPropertys) {
		this.resultPropertys = resultPropertys;
	}
	
}
