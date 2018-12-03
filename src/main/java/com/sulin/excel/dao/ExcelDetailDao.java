package com.sulin.excel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.Setter;

@Repository
public class ExcelDetailDao {
	@Setter
	@Autowired
	JdbcTemplate template;

	public List<Map<String, Object>> getAllTeam() {
		/*String sql = "select t.id as teamId,t.team_name as teamName,o.id as originId,o.origin_name as originName from mark_team t "
				+ " inner join mark_team_origin tg on t.id = tg.team_id "
				+ " inner join mark_origin o on tg.origin_id = o.id";*/
		String sql = "select t.id as teamId,t.team_name teamName from mark_team t ";
		List<Map<String, Object>> list = null;
		try {
			list = template.queryForList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> getOriginByTeamId(int teamId) {
		String sql = "select t.id as originId,t.origin_name originName from mark_origin t "
				+ " inner join mark_team_origin tg on t.id = tg.origin_id where tg.team_id = ?";
		List<Map<String, Object>> list = null;
		try {
			list = template.queryForList(sql,teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
