package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CourseBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.util.JDBCDataSource;

public class CourseModel {

	public static Integer nextPk() throws Exception {

		int pk = 0;
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement("select max(id) from st_course");
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);
		return pk + 1;

	}

	public CourseBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_course where id = ?");
		ptst.setLong(1, id);

		CourseBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new CourseBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDuration(rs.getString(3));
			bean.setDescription(rs.getString(4));
			bean.setCreatedBy(rs.getString(5));
			bean.setModifiedBy(rs.getString(6));
			bean.setCreateDateTime(rs.getTimestamp(7));
			bean.setModifiedDateTime(rs.getTimestamp(8));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}

	public CourseBean findByName(String name) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_course where name = ?");
		ptst.setString(1, name);

		CourseBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new CourseBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDuration(rs.getString(3));
			bean.setDescription(rs.getString(4));
			bean.setCreatedBy(rs.getString(5));
			bean.setModifiedBy(rs.getString(6));
			bean.setCreateDateTime(rs.getTimestamp(7));
			bean.setModifiedDateTime(rs.getTimestamp(8));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}

	public void add(CourseBean bean) throws Exception {

		CourseBean exist = findByName(bean.getName());
		if (exist != null) {
			throw new RuntimeException(" course is already exist");
		}

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("insert into st_course values(?,?,?,?,?,?,?,?)");

		ptst.setInt(1, nextPk());
		ptst.setString(2, bean.getName());
		ptst.setString(3, bean.getDuration());
		ptst.setString(4, bean.getDescription());
		ptst.setString(5, bean.getCreatedBy());
		ptst.setString(6, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(7, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(8, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

		int i = ptst.executeUpdate();
		System.out.println("data inserted : " + i);

		JDBCDataSource.closeConnection(conn);
	}

	public void update(CourseBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement(
				"update st_course set name = ?,duration = ?, description = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");
		CourseBean exist = findByName(bean.getName());
		if (exist != null && exist.getId() != bean.getId()) {
			throw new RuntimeException(" course is already exist");
		}

		ptst.setString(1, bean.getName());
		ptst.setString(2, bean.getDuration());
		ptst.setString(3, bean.getDescription());
		ptst.setString(4, bean.getCreatedBy());
		ptst.setString(5, bean.getModifiedBy());
		ptst.setTimestamp(6, bean.getCreateDateTime());
		ptst.setTimestamp(7, bean.getModifiedDateTime());
		ptst.setLong(8, bean.getId());

		int i = ptst.executeUpdate();
		System.out.println("Data updated " + i);

		JDBCDataSource.closeConnection(conn);

	}

	public static void delete(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("delete from st_course where id = ?");

		ptst.setLong(1, id);

		int i = ptst.executeUpdate();
		System.out.println("data delete " + i);

	}

	public List search(CourseBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from st_course where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		PreparedStatement ptst = conn.prepareStatement(sql.toString());

		System.out.println(sql);
		List list = new ArrayList();
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			bean = new CourseBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDuration(rs.getString(3));
			bean.setDescription(rs.getString(4));
			bean.setCreatedBy(rs.getString(5));
			bean.setModifiedBy(rs.getString(6));
			bean.setCreateDateTime(rs.getTimestamp(7));
			bean.setModifiedDateTime(rs.getTimestamp(8));

			list.add(bean);

		}
		return list;

	}

}
