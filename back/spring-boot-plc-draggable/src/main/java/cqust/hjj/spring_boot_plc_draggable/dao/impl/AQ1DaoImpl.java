package cqust.hjj.spring_boot_plc_draggable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IAQ1Dao;
import cqust.hjj.spring_boot_plc_draggable.dbconnpool.ConnectionPool;
import cqust.hjj.spring_boot_plc_draggable.model.AQ1;

@Repository
public class AQ1DaoImpl implements IAQ1Dao{

	private ConnectionPool connectionpool;
	private Connection conn;
	
	@Override
	public void insert(double current, Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		if (conn!=null) {
			String SQL = "insert into AQ1(current,sid) values (?,?)";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setDouble(1, current);
			pm.setInt(2, sid);
			pm.execute();
		}
		System.out.println("插入数据成功！");
	}

	@Override
	public List<AQ1> queryall(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		List<AQ1> list = new ArrayList<AQ1>();
		if (conn!=null) {
			String SQL = "select current from AQ1 where sid = ? order by id limit (select count(*) from AQ1 where sid = ?)-7,7";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, sid);
			pm.setInt(2, sid);
			ResultSet rs = pm.executeQuery();
			while (rs.next()) {
				AQ1 aq1 = new AQ1();
				aq1.setCurrent(rs.getDouble("current"));
				list.add(aq1);
			}
		}
		return list;
	}

}
