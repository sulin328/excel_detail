package com.sulin.excel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.sulin.excel.service.ExcelDetalService;
import com.sulin.excel.util.DateUtils;

import lombok.Setter;

@Controller
public class IndexContrroller{
	@Setter
	@Autowired
	private ExcelDetalService  excelDetalService;
	
    //private static Map<String,Object> config = ConfUtil.readJson("market.json");
    
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		try{
			List<Map<String,Object>> data = excelDetalService.getAllTeam();
			
			//后台用户角色list页面
			model.setViewName("/index");
			model.addObject("data", data);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	@RequestMapping("/mark_origin" )
	@ResponseBody
	public List<Map<String,Object>> teamMarket(HttpServletRequest request,@ModelAttribute("teamId") int teamId){
		ModelAndView model = new ModelAndView();
		List<Map<String,Object>> data = new ArrayList<>();
		try{
			data = excelDetalService.getOriginByTeamId(teamId);
			/*//后台用户角色list页面
			 
			model.setViewName("/index");
			model.addObject("origin",data);*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	@RequestMapping("/sava_detail" )
	@ResponseBody
	public String savaDetail(HttpServletRequest request,@ModelAttribute("details") String data){
		JSONArray parse = JSONArray.parseArray(data);
		String contextPath = request.getRealPath("/");
		System.out.println(contextPath);
		try{
			//data = excelDetalService.getOriginByTeamId(teamId);
			/*//后台用户角色list页面
			 
			model.setViewName("/index");
			model.addObject("origin",data);*/
			/*for (Object object : data) {
				System.out.println(JSONObject.toJSONString(object));
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@RequestMapping("/creat_report" )
	@ResponseBody
	public String creatReport(HttpServletRequest request){
		String filePath = request.getRealPath("/")+"files"+File.separator;
		//获取原文件路径
		String oldFileName = "";
		//判断今天是否是月的第一天；
		if(DateUtils.isFirstDayOfMonth(new Date())) {
			oldFileName = filePath + DateUtils.getPreMonthOfDate(new Date()); 
		}else {
			oldFileName = filePath + DateUtils.YYMM.format(new Date()); 
		}
		oldFileName += "订单日报.xlsx";
		
		//新生成的文件；
		String fileName = filePath  + DateUtils.YYMM.format(new Date()) + "订单日报.xlsx";;
		//开始解析生成文件；
		try{
			long st = System.currentTimeMillis();
			exportDetailsToExcel(oldFileName,fileName);
			System.out.println((System.currentTimeMillis()-st)+"毫秒");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@SuppressWarnings("deprecation")
	private boolean exportDetailsToExcel(String oldFileName,String newFileName) {
		 XSSFWorkbook xssfWorkbook = null;
	     //寻找目录读取文件
	     File oldFile = new File(oldFileName); 
	     InputStream is = null;
		try {
			is = new FileInputStream(oldFile);
			xssfWorkbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//关闭流，避免暂用新文件导出不成功；
	    if(null != is) {
	    	try {
	    		is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    if(xssfWorkbook==null)
        	return false;
	    
	    //获取最后一个有效的sheet页
	    Integer lastSheet = getEffectiveSheet(xssfWorkbook,xssfWorkbook.getNumberOfSheets()-1);
	    if(null == lastSheet)
	    	return false;
	    
	    //删除与新sheet同名的sheet（用在同一天多次生成excel时）
	    String newSheetName = DateUtils.MD.format(new Date());
	    if(newSheetName.equals(xssfWorkbook.getSheetAt(lastSheet).getSheetName())) {
	    	xssfWorkbook.removeSheetAt(lastSheet);
	    	lastSheet = getEffectiveSheet(xssfWorkbook,xssfWorkbook.getNumberOfSheets()-1);
	    }
	    //复制出一份新的Sheet页
	    XSSFSheet sheet = xssfWorkbook.cloneSheet(lastSheet, DateUtils.MD.format(new Date()));
	    
	    //如果是月初，删除上个月除汇总以外的所有sheet
	    if(DateUtils.isFirstDayOfMonth(new Date())) {
	    	for(int i=1;i< xssfWorkbook.getNumberOfSheets()-1;i++)
	    		xssfWorkbook.removeSheetAt(i);
	    }
	    //开始处理数据；
	    //拷贝数据；
	    //以时间、合计做标志
	    XSSFRow row = null;
	    XSSFCell cell = null;
	    String team = "";
	    String origin = "";
	    for(int i = 0;i<sheet.getLastRowNum();i++) {
	    	row = sheet.getRow(i);
	    	cell = row.getCell(0);
	    	if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING && "时间".equals(row.getCell(0).getStringCellValue())) {
	    		sheet.getRow(i+1).getCell(0).setCellValue(new Date());
	    		continue;
	    	}
	    	
	    	cell = row.getCell(1);
	    	
	    	if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING && "合计".equals(cell.getStringCellValue()))
	    		continue;
	    	
	    	if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING && !"".equals(cell.getStringCellValue()))
	    		team = cell.getStringCellValue();
	    	
	    	
	    	
	    	if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING 
	    			&& "总计".equals(cell.getStringCellValue()))
	    		break;
	    	//拷贝上一天的订单到昨日订单；
	    	row.getCell(4).setCellValue(row.getCell(5).getNumericCellValue());
	    	
	    	origin = row.getCell(3).getStringCellValue();
	    	row.getCell(5).setCellValue(2);
	    	row.getCell(7).setCellValue(2.22);
	    	row.getCell(8).setCellValue(2.22);
	    	row.getCell(9).setCellValue(2.22);
	    	row.getCell(10).setCellValue(2.22);
	    	row.getCell(11).setCellValue(22.2);
	    	row.getCell(12).setCellValue(2.22);
	    }
	    
	    FileOutputStream fileOut = null;
	    try {
	    	fileOut = new FileOutputStream(new File(newFileName));
	    	xssfWorkbook.setForceFormulaRecalculation(true);
			xssfWorkbook.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}   
        try {
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	    return true;
	}

	private Integer getEffectiveSheet(XSSFWorkbook work,int index){
		Integer indexAt = null;
		if(index>=0) {
			XSSFSheet sheetAt = work.getSheetAt(index);
			Pattern compile = Pattern.compile("[0-9]{1,2}[.-][0-9]{1,2}");
			String sheetName = sheetAt.getSheetName();//对sheet名称做简单的校验
			Matcher matcher = compile.matcher(sheetName);
			if(!matcher.matches()) {
				indexAt = getEffectiveSheet(work,index-1);
			} else {
				indexAt = index;
			}
		}
		return indexAt;
	}
	
	@RequestMapping("/")
	public ModelAndView redIndex(HttpServletRequest request){
		return index(request);
	}
}
