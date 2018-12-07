package com.sulin.excel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sulin.excel.service.ExcelDetalService;
import com.sulin.excel.util.DateUtils;

import lombok.Setter;

@Controller
public class IndexContrroller {
	@Setter
	@Autowired
	private ExcelDetalService excelDetalService;

	// private static Map<String,Object> config = ConfUtil.readJson("market.json");

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		String isMod = request.getParameter("modi_type");
		ModelAndView model = new ModelAndView();
		try {
			List<Map<String, Object>> data = excelDetalService.getAllTeam(isMod);

			// 后台用户角色list页面
			model.setViewName("/index");
			model.addObject("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping("/toFileManagerPage")
	public ModelAndView toFileManagerPage(HttpServletRequest request) {
		String type = request.getParameter("type");
		ModelAndView model = new ModelAndView();
		Map<String, String> submitTeam = excelDetalService.dataSubmitTeam();
		model.setViewName("/fileManager");
		model.addObject("info", submitTeam);
		model.addObject("type", type);
		return model;
	}

	@RequestMapping("/doUpload")
	@ResponseBody
	public Map<String, Object> springUpload(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		try {
			// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				// 将request变成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 获取multiRequest 中所有的文件名
				Iterator iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 一次遍历所有文件
					MultipartFile file = multiRequest.getFile(iter.next().toString());
					String originalFilename = file.getOriginalFilename();
					if (file != null) {
						String path = request.getRealPath("/") + "files" + File.separator + file.getOriginalFilename();
						// 上传之前先删除；
						File oldFile = new File(path);
						if (oldFile.exists()) {
							oldFile.delete();
						}
						// 上传
						file.transferTo(new File(path));
					}
				}
			}
			result.put("code", 200);
			result.put("msg", "模板上传成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "模板上传过程中遇到了严重的问题，没有上传到服务器，请联系管理员！");
		}
		return result;
	}
	
	@RequestMapping(value ="/donwloadfile", method = RequestMethod.GET)
    public ResponseEntity<byte[]> donwloadfile(HttpServletResponse response, HttpServletRequest request) throws Exception{
        String fileName = request.getParameter("fileName");
		try {
			fileName = new String(fileName.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String filePath = request.getRealPath("/") + "files";
		System.out.println(filePath + File.separator + fileName);
		response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;fileName="+java.net.URLEncoder.encode(fileName,"UTF-8"));
		File file = new File(filePath + File.separator + fileName);
		if(!file.exists()) {
			System.out.println("文件不存在；");
		}
		
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
            is.close();
        }
        return null;
    }
	
	@RequestMapping("/mark_origin")
	@ResponseBody
	public List<Map<String, Object>> teamMarket(HttpServletRequest request, @ModelAttribute("teamId") int teamId) {
		ModelAndView model = new ModelAndView();
		List<Map<String, Object>> data = new ArrayList<>();
		try {
			data = excelDetalService.getOriginByTeamId(teamId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	@RequestMapping("/sava_detail")
	@ResponseBody
	public Map<String, Object> savaDetail(HttpServletRequest request, @ModelAttribute("details") String data) {
		JSONArray parse = JSONArray.parseArray(data);
		Map<String, Object> result = null;
		try {
			List<JSONObject> accounts = new ArrayList<>();
			for (int i = 0; i < parse.size(); i++) {
				accounts.add(parse.getJSONObject(i));
			}
			result = excelDetalService.updataOrAddSalesData(accounts);
		} catch (Exception e) {
			result = new HashMap<>();
			result.put("status", false);
			result.put("msg", "数据保存失败，请检查数据是否填写规范正确，再次提交！");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/creat_report")
	@ResponseBody
	public Map<String, Object> creatReport(HttpServletRequest request) {
		Date exportDate = new Date();
		
		// 日报生成日期。如果后面要修改为根据日期导入，这个参数可以从前台传入
		Map map = new HashMap<>();
		String filePath = request.getRealPath("/") + "files" + File.separator;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(exportDate);
		calendar.add(Calendar.DATE, -1);
		Date dataDate = calendar.getTime();

		// 获取原文件路径
		String oldFileName = "";
		if (DateUtils.isFirstDayOfMonth(dataDate)) {
			oldFileName = filePath + DateUtils.getPreMonthOfDate(dataDate);
		} else {
			oldFileName = filePath + DateUtils.YYMM.format(dataDate);
		}

		oldFileName += "订单日报.xlsx";

		// 新生成的文件；
		String fileName = filePath + DateUtils.YYMM.format(new Date()) + "订单日报.xlsx";
		// 从数据库拿取数据；
		Map<String, Map<String, Map<String, Object>>> accounts = excelDetalService.getAllAccountsByDate(exportDate);
		if (accounts.size() == 0) {
			map.put("status", false);
			map.put("msg", "还没有团队提交数据，请和各团队负责人联系确认！");
			return map;
		}
		// 开始解析生成文件；
		try {
			map = exportDetailsToExcel(oldFileName, fileName, exportDate, accounts);
		} catch (Exception e) {
			map.put("status", false);
			map.put("msg", "生成报系统错误，请联系管理员进行处理！");
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("deprecation")
	private Map<String, Object> exportDetailsToExcel(String oldFileName, String newFileName, Date expDate,
			Map<String, Map<String, Map<String, Object>>> accounts) {
		Map<String, Object> result = new HashMap<>();
		XSSFWorkbook xssfWorkbook = null;
		// 寻找目录读取文件
		File oldFile = new File(oldFileName);
		if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
			result.put("status", false);
			result.put("msg", "日报模板不存在，请联系管理员上传！");
			return result;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(oldFile);
			xssfWorkbook = new XSSFWorkbook(is);
			xssfWorkbook.setForceFormulaRecalculation(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 关闭流，避免暂用新文件导出不成功；
		if (null != is) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (xssfWorkbook == null) {
			result.put("status", false);
			result.put("msg", "不存在日报模板文件，请联系管理员导入！");
			return result;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(expDate);

		calendar.add(Calendar.DATE, -1);
		Date yestordy = calendar.getTime();

		calendar.add(Calendar.DATE, -1);
		Date preYestordy = calendar.getTime();
		// 今天的日报，生成的sheet页要用昨天的日期，需用前天的日期获取sheet页；
		// 需要复制的sheet页名字
		String copySheetName = DateUtils.MD.format(preYestordy);
		String newSheetName = DateUtils.MD.format(yestordy);
		// 看是否已经有这个sheet,有就删除；
		int newSheetIndex = xssfWorkbook.getSheetIndex(newSheetName);
		if (0 <= newSheetIndex) {
			xssfWorkbook.removeSheetAt(newSheetIndex);
		}
		int copyAtIndex = xssfWorkbook.getSheetIndex(copySheetName);
		if (0 > copyAtIndex) {
			result.put("status", false);
			result.put("msg", "日报模板中的日期已经错乱，没有找到sheet名称为：" + copySheetName + " 的日报，请补全后再次上传模板！");
			return result;
		}
		// 复制出一份新的Sheet页
		XSSFSheet sheet = xssfWorkbook.cloneSheet(copyAtIndex, newSheetName);

		// 如果是月初，删除上个月除汇总以外的所有sheet
		if (DateUtils.isFirstDayOfMonth(yestordy)) {
			// 清空其他sheet页；
			while (xssfWorkbook.getNumberOfSheets() > 2) {
				xssfWorkbook.removeSheetAt(1);
			}
			// 清空汇总页的其他数据；
			XSSFSheet mountCountSheet = xssfWorkbook.getSheetAt(0);
			int lastRowNum = mountCountSheet.getLastRowNum();
			while (mountCountSheet.getLastRowNum() > 2) {
				lastRowNum = mountCountSheet.getLastRowNum();
				mountCountSheet.shiftRows(2, lastRowNum, -1);
			}
		}
		// 开始处理数据；
		// 以时间、合计做标志
		XSSFRow row = null;
		XSSFCell cell = null;
		String team = "";
		String origin = "";
		int countRowIndex = 0;
		int rowNumCount = sheet.getLastRowNum();
		for (int i = 0; i <= rowNumCount; i++) {
			row = sheet.getRow(i);
			cell = row.getCell(0);
			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && "时间".equals(row.getCell(0).getStringCellValue())) {
				sheet.getRow(i + 1).getCell(0).setCellValue(new Date());
				continue;
			}

			cell = row.getCell(1);

			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && ("合计".equals(cell.getStringCellValue())||"团队".equals(cell.getStringCellValue())))
				continue;

			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && !"".equals(cell.getStringCellValue()))
				team = cell.getStringCellValue();

			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING && "总计".equals(cell.getStringCellValue())) {
				countRowIndex = i;
				break;
			}

			// 拷贝上一天的订单到昨日订单；
			row.getCell(4).setCellValue(row.getCell(5).getNumericCellValue());

			origin = row.getCell(3).getStringCellValue();

			// 开始往表格中录入数据；
			Map<String, Object> map = null;
			if (accounts.get(team) == null || accounts.get(team).get(origin) == null) {
				map = new HashMap<>();
			} else {
				map = accounts.get(team).get(origin);
			}

			row.getCell(5).setCellValue(map.get("orderAmount") == null ? 0 : (int) map.get("orderAmount"));
			row.getCell(7).setCellValue(map.get("roiIndex") == null ? 0.00d : Double.parseDouble(map.get("roiIndex").toString()) );
			row.getCell(8).setCellValue(map.get("groundPro") == null ? 0 : (int) map.get("groundPro"));
			row.getCell(9).setCellValue(map.get("alivePro") == null ? 0 : (int) map.get("alivePro"));
			row.getCell(10).setCellValue(map.get("singleEarn") == null ? 0.00d : Double.parseDouble(map.get("singleEarn").toString()));
			row.getCell(11).setCellValue(map.get("salesAmount") == null ? 0.00d : Double.parseDouble(map.get("salesAmount").toString()));
			row.getCell(12).setCellValue(map.get("costAmount") == null ? 0.00d : Double.parseDouble(map.get("costAmount").toString()));
		}
		// 处理[汇总]sheet
		// 待公式计算完成后，把汇总数据插入到第一个汇总的sheet页。
		if (countRowIndex == 0) {
			result.put("status", true);
			result.put("msg", "日报导出完成，并生成sheet名称为：" + newSheetName + " 的日报，但[汇总sheet页的汇总失败]！");
		} else {
			row = null;
			int dataCountRowIndex = -1;
			String yesStr = DateUtils.YYYYMD.format(yestordy);
			String preDate = DateUtils.YYYYMD.format(preYestordy);
			XSSFSheet sheetCount = xssfWorkbook.getSheetAt(0);
			int lastRowNum = sheetCount.getLastRowNum();
			if (DateUtils.isFirstDayOfMonth(yestordy)) {
				for (int j = 0; j <= lastRowNum; j++) {
					cell = sheetCount.getRow(j).getCell(0);
					if ("m/d;@".equals(cell.getCellStyle().getDataFormatString())) {
						try {
							DateUtils.YYYYMD.format(cell.getDateCellValue());
							row = sheetCount.getRow(j);
							dataCountRowIndex = j + 1;
							break;
						} catch (Exception e) {
						}
					}
				}
			} else {
				int newIndex = -1;
				for (int j = 0; j <= lastRowNum; j++) {
					cell = sheetCount.getRow(j).getCell(0);
					if ("m/d;@".equals(cell.getCellStyle().getDataFormatString())) {
						try {
							String format = DateUtils.YYYYMD.format(cell.getDateCellValue());
							if (preDate.equals(format)) {
								newIndex = j;
								dataCountRowIndex = j + 2;
							}
							if (yesStr.equals(format)) {
								row = sheetCount.getRow(j);
								dataCountRowIndex = j + 1;
								break;
							}
						} catch (Exception e) {
						}
					}
				}
				if (null == row) {
					XSSFRow oldRow = sheetCount.getRow(newIndex);
					sheetCount.shiftRows(newIndex + 1, sheetCount.getLastRowNum(), 1, true, false);
					row = sheetCount.createRow(newIndex + 1);

					XSSFCell createCell = null;
					XSSFCell oldCell = null;
					for (int i = 0; i < 5; i++) {
						if(i==2)continue;
						createCell = row.createCell(i);
						oldCell = oldRow.getCell(i);
						copyCellStyle(oldCell.getCellStyle(), createCell.getCellStyle());
					}
					XSSFDataFormat format = xssfWorkbook.createDataFormat();
					row.getCell(0).getCellStyle().setDataFormat(format.getFormat("m/d;@"));
				}
			}

			row.getCell(0).setCellValue(yestordy);
			row.getCell(1).setCellValue(sheet.getRow(countRowIndex).getCell(5).getNumericCellValue());
			row.getCell(3).setCellValue(sheet.getRow(countRowIndex).getCell(11).getNumericCellValue());
			row.getCell(4).setCellValue(sheet.getRow(countRowIndex).getCell(12).getNumericCellValue());

			row = sheetCount.getRow(dataCountRowIndex);
			row.getCell(1).setCellFormula("SUM(B2:B" + dataCountRowIndex + ")");
			row.getCell(3).setCellFormula("SUM(D2:D" + dataCountRowIndex + ")");
			row.getCell(4).setCellFormula("SUM(E2:E" + dataCountRowIndex + ")");
		}

		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(new File(newFileName));

			xssfWorkbook.write(fileOut);
			result.put("status", true);
			result.put("msg", "日报导出完成，并生成sheet名称为：" + newSheetName + " 的日报，请到下载页面下载！");
		} catch (IOException e) {
			result.put("status", false);
			result.put("msg", "日报导出错误，获取因为文件被占用！");
			e.printStackTrace();
		}
		try {
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void copyCellStyle(XSSFCellStyle fromStyle, XSSFCellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignmentEnum());
		// 边框和边框颜色
		toStyle.setBorderBottom(fromStyle.getBorderBottomEnum());
		toStyle.setBorderLeft(fromStyle.getBorderLeftEnum());
		toStyle.setBorderRight(fromStyle.getBorderRightEnum());
		toStyle.setBorderTop(fromStyle.getBorderBottomEnum());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

		// 背景和前景
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPatternEnum());
		toStyle.setFont(fromStyle.getFont());
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());// 首行缩进
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());// 旋转
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignmentEnum());
		toStyle.setWrapText(fromStyle.getWrapText());
	}

	@RequestMapping("/")
	public ModelAndView redIndex(HttpServletRequest request) {
		return index(request);
	}
}
