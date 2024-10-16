package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.util.JDBCDataSource;

public class CollegeModel {

	public static Integer nextPk() throws Exception {

		int pk = 0;
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement("select max(id) from st_college");
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		return pk + 1;

	}

	public void add(CollegeBean bean) throws Exception {

		CollegeBean existBean = findByName(bean.getName());
		if (existBean != null) {
			throw new RuntimeException("College name already exist");
		}

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");

		ptst.setInt(1, nextPk());
		ptst.setString(2, bean.getName());
		ptst.setString(3, bean.getAddress());
		ptst.setString(4, bean.getState());
		ptst.setString(5, bean.getCity());
		ptst.setString(6, bean.getPhoneNo());
		ptst.setString(7, bean.getCreatedBy());
		ptst.setString(8, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(9, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(10, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

		int i = ptst.executeUpdate();
		System.out.println("data inserted : " + i);

		JDBCDataSource.closeConnection(conn);
	}

	public void update(CollegeBean bean) throws Exception {

		CollegeBean existBean = findByName(bean.getName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new RuntimeException("college already exist");
		}
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement(
				"update st_college set name = ?, address = ?, state = ?, city=?, phone_no = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");

		ptst.setString(1, bean.getName());
		ptst.setString(2, bean.getAddress());
		ptst.setString(3, bean.getState());
		ptst.setString(4, bean.getCity());
		ptst.setString(5, bean.getPhoneNo());
		ptst.setString(6, bean.getCreatedBy());
		ptst.setString(7, bean.getModifiedBy());
		ptst.setTimestamp(8, bean.getCreateDateTime());
		ptst.setTimestamp(9, bean.getModifiedDateTime());
		ptst.setLong(10, bean.getId());

		int i = ptst.executeUpdate();
		System.out.println("Data updated " + i);

		JDBCDataSource.closeConnection(conn);

	}

	public CollegeBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_college where id = ?");
		ptst.setLong(1, id);

		CollegeBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new CollegeBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setState(rs.getString(4));
			bean.setCity(rs.getString(5));
			bean.setPhoneNo(rs.getString(6));
			bean.setCreatedBy(rs.getString(7));
			bean.setModifiedBy(rs.getString(8));
			bean.setCreateDateTime(rs.getTimestamp(9));
			bean.setModifiedDateTime(rs.getTimestamp(10));

		}
		return bean;
	}

	public CollegeBean findByName(String name) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_college where name = ?");
		ptst.setString(1, name);

		CollegeBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new CollegeBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setState(rs.getString(4));
			bean.setCity(rs.getString(5));
			bean.setPhoneNo(rs.getString(6));
			bean.setCreatedBy(rs.getString(7));
			bean.setModifiedBy(rs.getString(8));
			bean.setCreateDateTime(rs.getTimestamp(9));
			bean.setModifiedDateTime(rs.getTimestamp(10));

		}
		return bean;
	}

	public static void delete(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("delete from st_college where id = ?");

		ptst.setLong(1, id);

		int i = ptst.executeUpdate();
		System.out.println("data delete " + i);

	}
	
	public List search(CollegeBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from st_college where 1=1");
		PreparedStatement ptst = conn.prepareStatement(sql.toString());

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}
		List list = new ArrayList();
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			bean = new CollegeBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setState(rs.getString(4));
			bean.setCity(rs.getString(5));
			bean.setPhoneNo(rs.getString(6));
			bean.setCreatedBy(rs.getString(7));
			bean.setModifiedBy(rs.getString(8));
			bean.setCreateDateTime(rs.getTimestamp(9));
			bean.setModifiedDateTime(rs.getTimestamp(10));

			list.add(bean);

		}
		return list;

	}
}
