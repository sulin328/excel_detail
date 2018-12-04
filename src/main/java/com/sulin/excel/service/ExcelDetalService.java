package com.sulin.excel.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface ExcelDetalService {
	public List<Map<String, Object>> getAllTeam();

	public List<Map<String, Object>> getOriginByTeamId(int teamId);
	
	boolean updataOrAddSalesData(List<JSONObject> accounts);
}
