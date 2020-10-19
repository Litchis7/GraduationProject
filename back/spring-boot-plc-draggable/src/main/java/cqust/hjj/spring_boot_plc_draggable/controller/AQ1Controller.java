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

import cqust.hjj.spring_boot_plc_draggable.biz.impl.AQ1BizImpl;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IAQ1Biz;

import cqust.hjj.spring_boot_plc_draggable.model.AQ1;

@CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/AQ1")
public class AQ1Controller {
	
	@RequestMapping(value = "/insert")
	public ResponseEntity<?> insertAQ1(String current,String sid) throws SQLException{
		IAQ1Biz iaq1Biz = new AQ1BizImpl();
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "保存成功");
		Double current2 = Double.valueOf(current.toString());
		Integer sid2 = Integer.valueOf(sid.toString());
		iaq1Biz.insert(current2, sid2);
		return ResponseEntity.ok(map);
	}
	
	
	@RequestMapping(value = "/query")
	public ResponseEntity<?> queryAQ1(String sid) throws SQLException{
		IAQ1Biz iaq1Biz = new AQ1BizImpl();
		Integer sid2 = Integer.valueOf(sid);
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		String [] arr = {"2s", "4s", "6s", "8s", "10s", "12s", "14s"};
		
		List<AQ1> list = iaq1Biz.queryall(sid2);
		map.put("xAxis", Arrays.asList(arr));
		ArrayList<Double> curList = new ArrayList<Double>();//动态数组
		for(AQ1 current : list) {
			curList.add(current.getCurrent());	
		}
		map.put("series", curList);
		return ResponseEntity.ok(map);
	}

}
