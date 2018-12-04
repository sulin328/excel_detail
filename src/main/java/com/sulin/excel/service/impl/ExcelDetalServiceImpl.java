package com.sulin.excel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sulin.excel.dao.ExcelDetailDao;
import com.sulin.excel.service.ExcelDetalService;

import lombok.Setter;

@Service
public class ExcelDetalServiceImpl implements ExcelDetalService {
	
	@Setter
	@Autowired
	private ExcelDetailDao excelDetailDao;

	@Override
	public List<Map<String, Object>> getAllTeam() {
		return excelDetailDao.getAllTeam();
		/*List<Map<String,Object>> teams = excelDetailDao.getAllTeam();
		List<Map<String,Object>> result = new ArrayList();
		if(null != teams && !teams.isEmpty()) {
			for (Map<String, Object> map : teams) {
				String teamId = map.get("teamId").toString();
				Map<String,Object> temp = null;
				for (Map<String, Object> map2 : result) {
					if(teamId.equals(map2.get("teamId").toString())) {
						temp = map2;
					}
				}
				if(null == temp) {
					temp = new HashMap<>();
					temp.put("teamId", teamId);
					temp.put("teamName", map.get("teamName"));
					temp.put("origin", new ArrayList());
					result.add(temp);
				}
				if(null !=temp.get("origin") && (temp.get("origin") instanceof List)){
					((List) temp.get("origin")).add(map);
				}
			}
		}
		return result;*/
	}

	@Override
	public List<Map<String, Object>> getOriginByTeamId(int teamId) {
		return excelDetailDao.getOriginByTeamId(teamId);
	}

	@Override
	public boolean updataOrAddSalesData(List<JSONObject> accounts) {
		int[] updataOrAddSalesData = excelDetailDao.updataOrAddSalesData(accounts);
		for (int i : updataOrAddSalesData) {
			System.out.println(i );
		}
		
		return false;
	}
}
