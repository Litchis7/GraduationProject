package cqust.hjj.spring_boot_plc_draggable.biz.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IAI1Biz;
import cqust.hjj.spring_boot_plc_draggable.dao.impl.AI1DaoImpl;
import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IAI1Dao;
import cqust.hjj.spring_boot_plc_draggable.model.AI1;


@Service
public class AI1BizImpl implements IAI1Biz{

	@Override
	public void insert(double voltage, Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAI1Dao iai1Dao = new AI1DaoImpl();
		iai1Dao.insert(voltage, sid);
	}

	@Override
	public List<AI1> queryall(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAI1Dao iai1Dao = new AI1DaoImpl();
		List<AI1> list = iai1Dao.queryall(sid);
		return list;
	}

}
