package cqust.hjj.spring_boot_plc_draggable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IAQ0Dao;
import cqust.hjj.spring_boot_plc_draggable.dbconnpool.ConnectionPool;
import cqust.hjj.spring_boot_plc_draggable.model.AQ0;


@Repository
public class AQ0DaoImpl implements IAQ0Dao{

	private ConnectionPool connectionpool;
	private Connection conn;
	
	
	@Override
	public void insert(double current, Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		if (conn!=null) {
			String SQL = "insert into AQ0(current,sid) values (?,?)";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setDouble(1, current);
			pm.setInt(2, sid);
			pm.execute();
		}
		System.out.println("插入数据成功！");
	}

	@Override
	public List<AQ0> queryall(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		List<AQ0> list = new ArrayList<AQ0>();
		if (conn!=null) {
			String SQL = "select current from AQ0 where sid = ? order by id limit (select count(*) from AQ0 where sid = ?)-7,7";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, sid);
			pm.setInt(2, sid);
			ResultSet rs = pm.executeQuery();
			while (rs.next()) {
				AQ0 aq0 = new AQ0();
				aq0.setCurrent(rs.getDouble("current"));
				list.add(aq0);
			}
		}
		return list;
	}



}
