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

import cqust.hjj.spring_boot_plc_draggable.biz.impl.AI0BizImpl;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IAI0Biz;

import cqust.hjj.spring_boot_plc_draggable.model.AI0;


@CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/AI0")
public class AI0Controller {
	@RequestMapping(value = "/insert")
	public ResponseEntity<?> insertAI0(String voltage,String sid) throws SQLException{
		IAI0Biz iai0Biz = new AI0BizImpl();
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "保存成功");
		Double voltage2 = Double.valueOf(voltage.toString());
		Integer sid2 = Integer.valueOf(sid.toString());
		iai0Biz.insert(voltage2, sid2);
		return ResponseEntity.ok(map);
	}
	
	
	@RequestMapping(value = "/query")
	public ResponseEntity<?> queryAI0(String sid) throws SQLException{
		IAI0Biz iai0Biz = new AI0BizImpl();
		Integer sid2 = Integer.valueOf(sid);
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		String [] arr = {"2s", "4s", "6s", "8s", "10s", "12s", "14s"};
		List<AI0> list = iai0Biz.queryall(sid2);
		map.put("xAxis", Arrays.asList(arr));
		ArrayList<Double> curList = new ArrayList<Double>();//动态数组
		for(AI0 voltage : list) {
			curList.add(voltage.getVoltage());	
		}
		map.put("series", curList);
		return ResponseEntity.ok(map);
	}
}
