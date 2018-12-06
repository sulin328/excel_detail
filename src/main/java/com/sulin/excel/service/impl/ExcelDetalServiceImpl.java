package com.sulin.excel.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
	}

	@Override
	public List<Map<String, Object>> getOriginByTeamId(int teamId) {
		return excelDetailDao.getOriginByTeamId(teamId);
	}

	@Override
	public Map<String,Object> updataOrAddSalesData(List<JSONObject> accounts) {
		Map<String,Object> map = new HashMap<>();
		int[] updataOrAddSalesData = excelDetailDao.updataOrAddSalesData(accounts);
		if(null == updataOrAddSalesData) {
			map.put("status", false);
			map.put("msg", "数据保存失败，请检查数据是否填写规范正确，再次提交！");
		}else {
			map.put("status", true);
			map.put("msg", "全部提交成功！");
		}
		return map;
	}

	@Override
	public Map<String,Map<String,Map<String,Object>>> getAllAccountsByDate(Date expDate) {
		Map<String,Map<String,Map<String,Object>>> result = new HashMap<>();
		List<Map<String,Object>> accounts = excelDetailDao.getAllAccountsByDate(expDate);
		if(null != accounts&&accounts.size()>0) {
			String str = "";
			for (Map<String, Object> map : accounts) {
				str = map.get("teamName").toString();
				Map<String, Map<String, Object>> team = result.get(str);
				if(null == team) {
					team = new HashMap<>();
					result.put(str, team);
				}
				str = map.get("originName").toString();
				team.put(str, map);
			}
		}
		return result;
	}
}
