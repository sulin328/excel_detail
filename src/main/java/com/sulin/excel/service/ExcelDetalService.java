package com.sulin.excel.service;

import java.util.List;
import java.util.Map;

public interface ExcelDetalService {
	public List<Map<String, Object>> getAllTeam();

	public List<Map<String, Object>> getOriginByTeamId(int teamId);
}
