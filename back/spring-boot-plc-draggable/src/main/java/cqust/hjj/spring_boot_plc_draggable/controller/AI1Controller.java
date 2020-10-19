package cqust.hjj.spring_boot_plc_draggable.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cqust.hjj.spring_boot_plc_draggable.biz.impl.AI1BizImpl;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IAI1Biz;

import cqust.hjj.spring_boot_plc_draggable.model.AI1;


@CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/AI1")
public class AI1Controller {
	@RequestMapping(value = "/insert")
	public ResponseEntity<?> insertAI1(String voltage,String sid) throws SQLException{
		IAI1Biz iai1Biz = new AI1BizImpl();
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "保存成功");
		Double voltage2 = Double.valueOf(voltage.toString());
		Integer sid2 = Integer.valueOf(sid.toString());
		iai1Biz.insert(voltage2, sid2);
		return ResponseEntity.ok(map);
	}
	
	
	@RequestMapping(value = "/query")
	public ResponseEntity<?> queryAI1(String sid) throws SQLException{
		IAI1Biz iai1Biz = new AI1BizImpl();
		Integer sid2 = Integer.valueOf(sid);
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		String [] arr = {"2s", "4s", "6s", "8s", "10s", "12s", "14s"};
		
		List<AI1> list = iai1Biz.queryall(sid2);
		map.put("xAxis", Arrays.asList(arr));
		ArrayList<Double> curList = new ArrayList<Double>();//动态数组
		for(AI1 voltage : list) {
			curList.add(voltage.getVoltage());	
		}
		map.put("series", curList);
		return ResponseEntity.ok(map);
	}
}
