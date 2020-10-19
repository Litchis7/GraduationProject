package cqust.hjj.spring_boot_plc_draggable.biz.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IAI0Biz;
import cqust.hjj.spring_boot_plc_draggable.dao.impl.AI0DaoImpl;

import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IAI0Dao;

import cqust.hjj.spring_boot_plc_draggable.model.AI0;


@Service
public class AI0BizImpl implements IAI0Biz{

	@Override
	public void insert(double voltage, Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAI0Dao iai0Dao = new AI0DaoImpl();
		iai0Dao.insert(voltage, sid);
	}

	@Override
	public List<AI0> queryall(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAI0Dao iai0Dao = new AI0DaoImpl();
		List<AI0> list = iai0Dao.queryall(sid);
		return list;
	}

}
