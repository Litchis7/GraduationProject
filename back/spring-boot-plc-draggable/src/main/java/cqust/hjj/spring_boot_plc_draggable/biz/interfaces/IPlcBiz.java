package cqust.hjj.spring_boot_plc_draggable.biz.interfaces;

import java.sql.SQLException;
import java.util.List;

import cqust.hjj.spring_boot_plc_draggable.model.PLC;

public interface IPlcBiz {
	public void insert(String content,Integer sid) throws SQLException;
	public List<PLC> query(Integer sid) throws SQLException;
}
