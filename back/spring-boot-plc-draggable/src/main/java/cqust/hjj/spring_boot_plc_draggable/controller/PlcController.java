package cqust.hjj.spring_boot_plc_draggable.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cqust.hjj.spring_boot_plc_draggable.biz.impl.PlcBizImpl;
import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IPlcBiz;
import cqust.hjj.spring_boot_plc_draggable.model.PLC;

@CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/PLC")
public class PlcController {

	@RequestMapping(value = "/insert")
	public ResponseEntity<?> index(String content,String sid) throws SQLException{
		
		IPlcBiz iPlcBiz = new PlcBizImpl();
		System.out.println(content);
		System.out.println(sid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "保存成功");
		Integer sid2 = Integer.valueOf(sid.toString());
		iPlcBiz.insert(content,sid2);
		return ResponseEntity.ok(map);
		
	}
	
	@RequestMapping(value = "/query")
	public ResponseEntity<?> querycon(String sid) throws SQLException{
		
		System.out.println("进入查询");
		Integer sid2 = Integer.valueOf(sid.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		IPlcBiz iPlcBiz = new PlcBizImpl();
		List<PLC> list = iPlcBiz.query(sid2);
		ArrayList<String> conList = new ArrayList<String>();
		for(PLC content : list) {
			conList.add(content.getContent());
		}
		
		map.put("content", conList);
		System.out.println(conList);
		return ResponseEntity.ok(map);
		
		
	}
}
