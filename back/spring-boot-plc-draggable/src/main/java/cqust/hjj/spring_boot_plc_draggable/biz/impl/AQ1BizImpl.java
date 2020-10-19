package cqust.hjj.spring_boot_plc_draggable.biz.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import cqust.hjj.spring_boot_plc_draggable.biz.interfaces.IAQ1Biz;
import cqust.hjj.spring_boot_plc_draggable.dao.impl.AQ1DaoImpl;
import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IAQ1Dao;
import cqust.hjj.spring_boot_plc_draggable.model.AQ1;
@Service
public class AQ1BizImpl implements IAQ1Biz{

	@Override
	public void insert(double current, Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAQ1Dao iaq1Dao = new AQ1DaoImpl();
		iaq1Dao.insert(current, sid);
	}

	@Override
	public List<AQ1> queryall(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		IAQ1Dao iaq1Dao = new AQ1DaoImpl();
		List<AQ1> list = iaq1Dao.queryall(sid);
		return list;
	}


}
