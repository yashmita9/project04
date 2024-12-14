package in.co.rays.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.InitiativeBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class InitiativeModel {

	public static Integer nextPk() throws DatabaseException {
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from initiative");
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

	public static void add(InitiativeBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		InitiativeBean existbean = findByName(bean.getName());
		if (existbean != null) {
			throw new DuplicateRecordException(" Name is Already exist.....!!!");
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into initiative values(?,?,?,?,?)");
			conn.setAutoCommit(false);
			ptst.setLong(1, nextPk());
			ptst.setString(2, bean.getName());
			ptst.setString(3, bean.getType());
			ptst.setDate(4, new java.sql.Date(bean.getStartDate().getTime()));
			ptst.setDouble(5, bean.getVersionNo());

			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("Data Inserted :" + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add initiative " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	public static void update(InitiativeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		InitiativeBean existbean = findByName(bean.getName());
		if (existbean != null && existbean.getId() != bean.getId()) {
			throw new DuplicateRecordException(" Type is Already exist.....!!!");
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement(
					"update initiative set name = ?, type = ?, start_date = ?, version_number = ? where id =?");
			ptst.setString(1, bean.getName());
			ptst.setString(2, bean.getType());
			ptst.setDate(3, new java.sql.Date(bean.getStartDate().getTime()));
			ptst.setDouble(4, bean.getVersionNo());
			ptst.setLong(5, bean.getId());

			int i = ptst.executeUpdate();
			System.out.println("data updated " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update initiative" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public static InitiativeBean findByPk(long id) throws ApplicationException {
		Connection conn = null;
		InitiativeBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from initiative where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new InitiativeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setStartDate(rs.getDate(4));
				bean.setVersionNo(rs.getDouble(5));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting initiative by PK" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static InitiativeBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		InitiativeBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from initiative where name = ?");
			ptst.setString(1, name);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new InitiativeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setStartDate(rs.getDate(4));
				bean.setVersionNo(rs.getDouble(5));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Initiative by type" + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from initiative where id = ?");

			ptst.setLong(1, id);

			int i = ptst.executeUpdate();
			System.out.println("data delete " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(InitiativeBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from initiative where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getStartDate() != null) {
				sql.append(" and start_date like '" + new java.sql.Date(bean.getStartDate().getTime()) + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
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
				bean = new InitiativeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setStartDate(rs.getDate(4));
				bean.setVersionNo(rs.getDouble(5));

				list.add(bean);

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Initiative " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

}
