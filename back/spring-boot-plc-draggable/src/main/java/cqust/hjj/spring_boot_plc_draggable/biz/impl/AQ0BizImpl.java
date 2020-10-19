package cqust.hjj.spring_boot_plc_draggable.biz.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IAQ0Biz;
import cqust.hjj.spring_boot_plc_draggable.dao.impl.AQ0DaoImpl;
import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IAQ0Dao;
import cqust.hjj.spring_boot_plc_draggable.model.AQ0;

@Service
public class AQ0BizImpl implements IAQ0Biz{

	@Override
	public void insert(double current, Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAQ0Dao iaq0Dao = new AQ0DaoImpl();
		iaq0Dao.insert(current, sid);
		
	}

	@Override
	public List<AQ0> queryall(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAQ0Dao iaq0Dao = new AQ0DaoImpl();
		List<AQ0> list = iaq0Dao.queryall(sid);
		return list;
	}

}
