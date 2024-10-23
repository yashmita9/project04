package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CourseBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class CourseModel {

	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement("select max(id) from st_course");
			ResultSet rs = ptst.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public CourseBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		CourseBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_course where id = ?");
			ptst.setLong(1, id);

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
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Course by PK " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public CourseBean findByName(String name) throws ApplicationException {

		Connection conn = null;
		CourseBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_course where name = ?");
			ptst.setString(1, name);

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
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Course by NAME " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public void add(CourseBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CourseBean exist = findByName(bean.getName());
		if (exist != null) {
			throw new DuplicateRecordException("Course is already exist.....!!!");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
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
			conn.commit();
			System.out.println("data inserted : " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add course " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		JDBCDataSource.closeConnection(conn);
	}

	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ptst = conn.prepareStatement(
					"update st_course set name = ?,duration = ?, description = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");
			CourseBean exist = findByName(bean.getName());
			if (exist != null && exist.getId() != bean.getId()) {
				throw new DuplicateRecordException("Course is already exist.....!!!");
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
			conn.commit();
			System.out.println("Data updated " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating course " + e);
		}

		JDBCDataSource.closeConnection(conn);

	}

	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from st_course where id = ?");

			ptst.setLong(1, id);

			int i = ptst.executeUpdate();
			System.out.println("data delete " + i);
			
		} catch (Exception e) {
			try {
				conn.rollback();
				
			} catch (Exception ex) {
				
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			
			}
			
			throw new ApplicationException("Exception : Exception in delete course " + e);
			
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
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


		System.out.println(sql);
		List list = new ArrayList();
		
		try {
		conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement(sql.toString());
		
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
		}catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search course " + e);

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

}
