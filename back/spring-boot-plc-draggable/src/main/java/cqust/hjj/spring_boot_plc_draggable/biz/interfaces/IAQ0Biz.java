package cqust.hjj.spring_boot_plc_draggable.biz.interfaces;

import java.sql.SQLException;
import java.util.List;

import cqust.hjj.spring_boot_plc_draggable.model.AQ0;

public interface IAQ0Biz {
	public void insert(double current,Integer sid) throws SQLException;
	public List<AQ0> queryall(Integer sid) throws SQLException;
}
