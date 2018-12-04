package com.sulin.excel.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
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
	
	public int[] updataOrAddSalesData(final List<JSONObject> accounts) {
		String sql = "insert into mark_sales_amount(TEAM_ID,ORIGIN_ID,ORDER_AMOUNT,ROI_INDEX,"
				+ "GROUND_PRO,ALIVE_PRO,SINGLE_EARN,SALES_AMOUNT,COST_AMOUNT,CREATE_DATE) "
				+ " values (?, ?, ?, ?, ?, ?, ?, ?,?,current_timestamp()) ON DUPLICATE KEY "
				+ "UPDATE ORDER_AMOUNT=?,ROI_INDEX=?,GROUND_PRO=?,ALIVE_PRO=?,SINGLE_EARN=?,SALES_AMOUNT=?,COST_AMOUNT=?,CREATE_DATE=current_timestamp()";
		int[] updateNum = null;
        try {
            updateNum = template.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, accounts.get(i).getIntValue("teamId"));
                    ps.setInt(2,accounts.get(i).getIntValue("originId"));
                    ps.setInt(3,accounts.get(i).getIntValue("order_amount"));
                    ps.setDouble(4,accounts.get(i).getDoubleValue("roi_index"));
                    ps.setInt(5,accounts.get(i).getIntValue("ground_pro"));
                    
                    ps.setInt(6,accounts.get(i).getIntValue("alive_pro"));
                    ps.setDouble(7,accounts.get(i).getDoubleValue("single_earn"));
                    ps.setDouble(8,accounts.get(i).getDoubleValue("sales_amount"));
                    ps.setDouble(9,accounts.get(i).getIntValue("cost_amount"));
                    //ps.setString(10,"current_timestamp()");
                    
                    ps.setInt(10,accounts.get(i).getIntValue("order_amount"));
                    ps.setDouble(11,accounts.get(i).getDoubleValue("roi_index"));
                    ps.setInt(12,accounts.get(i).getIntValue("ground_pro"));
                    
                    ps.setInt(13,accounts.get(i).getIntValue("alive_pro"));
                    ps.setDouble(14,accounts.get(i).getDoubleValue("single_earn"));
                    ps.setDouble(15,accounts.get(i).getDoubleValue("sales_amount"));
                    ps.setDouble(16,accounts.get(i).getIntValue("cost_amount"));
                    //ps.setString(18, "current_timestamp()");
                }
                
                @Override
                public int getBatchSize() {
                    return accounts.size();
                }
            });
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return updateNum;
	}
}
