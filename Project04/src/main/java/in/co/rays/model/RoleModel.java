package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.RoleBean;
import in.co.rays.util.JDBCDataSource;

public class RoleModel {

	public static Integer nextPk() throws Exception {
		
		int pk = 0;
		Connection conn = JDBCDataSource.getConnection();
		
		PreparedStatement ptst = conn.prepareStatement("select max(id) from st_role");
		ResultSet rs = ptst.executeQuery();
		
		while(rs.next()) {
			pk = rs.getInt(1);
		}
		return pk+1;
		
	}
	
	public void add(RoleBean bean) throws Exception {
		
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
		
		ptst.setInt(1, nextPk());
		ptst.setString(2, bean.getName());
		ptst.setString(3, bean.getDescription());
		ptst.setString(4, bean.getCreatedBy());
		ptst.setString(5, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(6, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(7, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

		int i = ptst.executeUpdate();
		System.out.println("data inserted : " +i);
		
		JDBCDataSource.closeConnection(conn);
	}
	
	public void update(RoleBean bean) throws Exception {
		
		Connection conn = JDBCDataSource.getConnection();
		
		PreparedStatement ptst = conn.prepareStatement("update st_role set name = ?, description = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");
		
		RoleBean exist = findByPk(bean.getId());
		
		ptst.setString(1, bean.getName());
		ptst.setString(2, bean.getDescription());
		ptst.setString(3, bean.getCreatedBy());
		ptst.setString(4, bean.getModifiedBy());
		ptst.setTimestamp(5, bean.getCreateDateTime());
		ptst.setTimestamp(6, bean.getModifiedDateTime());
		ptst.setLong(7, bean.getId());
		
		int i = ptst.executeUpdate();
		System.out.println("Data updated " +i);
		
		JDBCDataSource.closeConnection(conn);
		
	}
	
	public RoleBean findByPk(long id) throws Exception {
		
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_role where id = ?");
		ptst.setLong(1, id);
		
	    RoleBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while(rs.next()) {
			bean = new RoleBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreateDateTime(rs.getTimestamp(6));
			bean.setModifiedDateTime(rs.getTimestamp(7));
			
		}
		return bean;
	}
	
	public List search(RoleBean bean , int pageNo , int pageSize) throws Exception {
		
		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");
		PreparedStatement ptst = conn.prepareStatement(sql.toString());
		
		if(bean!= null) {
			if(bean.getName()!=null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() +"%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}
		List list = new ArrayList();
		ResultSet rs = ptst.executeQuery();
		
		while(rs.next()) {
			bean = new RoleBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreateDateTime(rs.getTimestamp(6));
			bean.setModifiedDateTime(rs.getTimestamp(7));
			
			list.add(bean);
			
		}
		return list;
		
	}
}
