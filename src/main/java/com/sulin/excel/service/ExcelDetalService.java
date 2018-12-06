package com.sulin.excel.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface ExcelDetalService {
	List<Map<String, Object>> getAllTeam();

	List<Map<String, Object>> getOriginByTeamId(int teamId);
	
	Map<String,Object> updataOrAddSalesData(List<JSONObject> accounts);

	Map<String,Map<String,Map<String,Object>>> getAllAccountsByDate(Date expDate);
}
