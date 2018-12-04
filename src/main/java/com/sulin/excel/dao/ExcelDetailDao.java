package com.sulin.excel.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sulin.excel.util.DateUtils;

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
		String sql = "SELECT\r\n" + 
				"	t.id AS originId,\r\n" + 
				"	t.origin_name originName,\r\n" + 
				"	p.ORDER_AMOUNT orderAmount,\r\n" + 
				"	p.roi_index roiIndex,\r\n" + 
				"	p.ground_pro groundPro,\r\n" + 
				"	p.alive_pro alivePro,\r\n" + 
				"	p.single_earn singleEarn,\r\n" + 
				"	p.sales_amount salesAmount,\r\n" + 
				"	p.cost_amount costAmount \r\n" + 
				"FROM\r\n" + 
				"	mark_origin t\r\n" + 
				"	INNER JOIN mark_team_origin tg ON t.id = tg.origin_id\r\n" + 
				"	LEFT JOIN (\r\n" + 
				"SELECT\r\n" + 
				"	* \r\n" + 
				"FROM\r\n" + 
				"	mark_sales_amount s \r\n" + 
				"WHERE\r\n" + 
				"	s.CREATE_DATE > DATE_FORMAT(?, \"%Y-%m-%d %H:%i:%s\" ) \r\n" + 
				"	AND s.CREATE_DATE < DATE_FORMAT(?, \"%Y-%m-%d %H:%i:%s\" ) \r\n" + 
				"	) p ON tg.TEAM_ID = p.TEAM_ID \r\n" + 
				"	AND tg.ORIGIN_ID = p.ORIGIN_ID \r\n" + 
				"WHERE\r\n" + 
				"	tg.team_id = ?";
		List<Map<String, Object>> list = null;
		try {
			list = template.queryForList(sql,DateUtils.YYYYMMDD.format(new Date())+" 00:00:00",
					DateUtils.YYYYMMDD.format(new Date())+" 23:59:59",teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
