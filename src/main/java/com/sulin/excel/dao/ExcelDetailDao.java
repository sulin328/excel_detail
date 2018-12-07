package com.sulin.excel.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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

	public List<Map<String, Object>> getAllTeam(String isMod) {
		/*String sql = "select t.id as teamId,t.team_name as teamName,o.id as originId,o.origin_name as originName from mark_team t "
				+ " inner join mark_team_origin tg on t.id = tg.team_id "
				+ " inner join mark_origin o on tg.origin_id = o.id";*/
		String sql = "select t.id as teamId,t.team_name teamName from mark_team t " ;
		List<Object> args = new ArrayList<>();
		if(!"needMod".equals(isMod)) {
			sql += " where t.id not in (SELECT DISTINCT a.TEAM_ID from  mark_sales_amount a where a.CREATE_DATE > DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s' ) and a.CREATE_DATE < DATE_FORMAT(?, '%Y-%m-%d %H:%i:%s'))";
			args.add(DateUtils.YYYYMMDD.format(new Date())+" 00:00:00");
			args.add(DateUtils.YYYYMMDD.format(new Date())+" 23:59:59");
		}
		List<Map<String, Object>> list = null;
		try {
			list = template.queryForList(sql,args.toArray());
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
                    ps.setBigDecimal(4,new BigDecimal(accounts.get(i).getDoubleValue("roi_index")));
                    ps.setInt(5,accounts.get(i).getIntValue("ground_pro"));
                    
                    ps.setInt(6,accounts.get(i).getIntValue("alive_pro"));
                    ps.setBigDecimal(7,new BigDecimal(accounts.get(i).getDoubleValue("single_earn")));
                    ps.setBigDecimal(8,new BigDecimal(accounts.get(i).getDoubleValue("sales_amount")));
                    ps.setBigDecimal(9,new BigDecimal(accounts.get(i).getDoubleValue("cost_amount")));
                    //ps.setString(10,"current_timestamp()");
                    
                    ps.setInt(10,accounts.get(i).getIntValue("order_amount"));
                    ps.setBigDecimal(11,new BigDecimal(accounts.get(i).getDoubleValue("roi_index")));
                    ps.setInt(12,accounts.get(i).getIntValue("ground_pro"));
                    
                    ps.setInt(13,accounts.get(i).getIntValue("alive_pro"));
                    ps.setBigDecimal(14,new BigDecimal(accounts.get(i).getDoubleValue("single_earn")));
                    ps.setBigDecimal(15,new BigDecimal(accounts.get(i).getDoubleValue("sales_amount")));
                    ps.setBigDecimal(16,new BigDecimal(accounts.get(i).getDoubleValue("cost_amount")));
                    //ps.setString(18, "current_timestamp()");
                }
                
                @Override
                public int getBatchSize() {
                    return accounts.size();
                }
            });
        } catch (Exception ex) {
           ex.printStackTrace();
           return null;
        }
        return updateNum;
	}

	public List<Map<String, Object>> getAllAccountsByDate(Date expDate) {
		String sql = "select m.TEAM_NAME teamName,o.ORIGIN_NAME originName,a.ORDER_AMOUNT orderAmount,a.roi_index roiIndex, "
				+ " a.ground_pro groundPro,a.alive_pro alivePro,a.single_earn singleEarn,a.sales_amount salesAmount,a.cost_amount costAmount "
				+ " from mark_team m INNER join mark_team_origin t on m.ID = t.TEAM_ID "
				+ " INNER JOIN mark_origin o on t.ORIGIN_ID =o.ID LEFT JOIN "
				+ " (select * from mark_sales_amount where CREATE_DATE >= STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') and CREATE_DATE <= STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s')) a "
				+ " on m.ID = a.TEAM_ID and o.ID =a.ORIGIN_ID " ;
		List<Map<String, Object>> list = null;
		try {
			list = template.queryForList(sql,DateUtils.YYYYMMDD.format(expDate)+" 00:00:00",
					DateUtils.YYYYMMDD.format(expDate)+" 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getSubmitedTeam() {
		String sql = "SELECT t.TEAM_NAME from mark_team t INNER join mark_sales_amount a on t.ID = a.TEAM_ID "
				+ " where a.CREATE_DATE > DATE_FORMAT(?, '%Y-%m-%d %H:%i:%s' )";
		List<String> list = null;
		try {
			list = template.queryForList(sql, String.class, DateUtils.YYYYMMDD.format(new Date())+" 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
