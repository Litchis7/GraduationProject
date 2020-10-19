package cqust.hjj.spring_boot_plc_draggable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cqust.hjj.spring_boot_plc_draggable.dao.interfaces.IAI1Dao;
import cqust.hjj.spring_boot_plc_draggable.dbconnpool.ConnectionPool;
import cqust.hjj.spring_boot_plc_draggable.model.AI1;


@Repository
public class AI1DaoImpl implements IAI1Dao{
	
	
	private ConnectionPool connectionpool;
	private Connection conn;

	@Override
	public void insert(double voltage, Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		if (conn!=null) {
			String SQL = "insert into AI1(voltage,sid) values (?,?)";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setDouble(1, voltage);
			pm.setInt(2, sid);
			pm.execute();
		}
		System.out.println("插入数据成功！");
	}

	@Override
	public List<AI1> queryall(Integer sid) throws SQLException {
		// TODO Auto-generated method stub
		conn = connectionpool.getConnection();
		List<AI1> list = new ArrayList<AI1>();
		if (conn!=null) {
			String SQL = "select voltage from AI1 where sid = ? order by id limit (select count(*) from AI1 where sid = ?)-7,7";
			PreparedStatement pm = conn.prepareStatement(SQL);
			pm.setInt(1, sid);
			pm.setInt(2, sid);
			ResultSet rs = pm.executeQuery();
			while (rs.next()) {
				AI1 ai1 = new AI1();
				ai1.setVoltage(rs.getDouble("voltage"));
				list.add(ai1);
			}
		}
		return list;
	}


	
	
	

}
