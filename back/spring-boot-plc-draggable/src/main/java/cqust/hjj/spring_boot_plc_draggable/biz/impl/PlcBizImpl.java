package cqust.hjj.spring_boot_plc_draggable.biz.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IPlcBiz;
import cqust.hjj.spring_boot_plc_draggable.dao.impl.PlcDaoImpl;
import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IPlcDao;
import cqust.hjj.spring_boot_plc_draggable.model.PLC;

@Service
public class PlcBizImpl implements IPlcBiz{

	@Override
	public void insert(String content,Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IPlcDao iPlcDao = new PlcDaoImpl();
		iPlcDao.insert(content,sid);
	}

	@Override
	public List<PLC> query(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IPlcDao iPlcDao = new PlcDaoImpl();
		List<PLC> list = iPlcDao.query(sid);
		return list;
	}

}
