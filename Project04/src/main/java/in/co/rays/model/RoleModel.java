package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.RoleBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class RoleModel {

	public static Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement("select max(id) from st_role");
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

	public void add(RoleBean bean) throws ApplicationException, DuplicateRecordException  {

		Connection conn = null;
		RoleBean exist = findByName(bean.getName());
		if (exist != null) {
			throw new DuplicateRecordException("role name is already exist.....!");
		}

		try {
			
			
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			conn.setAutoCommit(false);
			
			ptst.setInt(1, nextPk());
			ptst.setString(2, bean.getName());
			ptst.setString(3, bean.getDescription());
			ptst.setString(4, bean.getCreatedBy());
			ptst.setString(5, bean.getModifiedBy());
			// Convert java.security.Timestamp to java.sql.Timestamp
			ptst.setTimestamp(6, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
			ptst.setTimestamp(7, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("data inserted : " + i);
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add role " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException  {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement(
					"update st_role set name = ?, description = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");

			RoleBean exist = findByName(bean.getName());
			if (exist != null && exist.getId() != bean.getId()) {
				throw new DuplicateRecordException("role name is already exist.....!");
			}

			ptst.setString(1, bean.getName());
			ptst.setString(2, bean.getDescription());
			ptst.setString(3, bean.getCreatedBy());
			ptst.setString(4, bean.getModifiedBy());
			ptst.setTimestamp(5, bean.getCreateDateTime());
			ptst.setTimestamp(6, bean.getModifiedDateTime());
			ptst.setLong(7, bean.getId());

			int i = ptst.executeUpdate();
			System.out.println("Data updated " + i);
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating role " + e);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public RoleBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		RoleBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_role where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreateDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting role by PK " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public RoleBean findByName(String name) throws ApplicationException {

		Connection conn = null;
		RoleBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_role where name = ?");
			ptst.setString(1, name);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreateDateTime(rs.getTimestamp(6));
				bean.setModifiedDateTime(rs.getTimestamp(7));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting role by NAME " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");
		

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
		
		try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement(sql.toString());
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
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
		}catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search role " + e);

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("delete from st_role where id = ?");

		ptst.setLong(1, id);

		int i = ptst.executeUpdate();
		System.out.println("data delete " + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete role " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	
	public static List list() throws ApplicationException {
		return search(null, 0, 0);
		
	}
}
