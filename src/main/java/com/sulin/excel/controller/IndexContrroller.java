package com.sulin.excel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sulin.excel.service.ExcelDetalService;
import com.sulin.excel.util.ConfUtil;

import lombok.Setter;

@Controller
public class IndexContrroller{
	@Setter
	@Autowired
	private ExcelDetalService  excelDetalService;
	
    private static Map<String,Object> config = ConfUtil.readJson("market.json");
    
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
	
	@RequestMapping("/")
	public ModelAndView redIndex(HttpServletRequest request){
		return index(request);
	}
}
