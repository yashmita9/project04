package in.co.rays.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.PatientBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class PatientModel {
	
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from patient");
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

	public void add(PatientBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into patient values(?,?,?,?,?)");

			conn.setAutoCommit(false);

			ptst.setInt(1, nextPk());
			ptst.setString(2, bean.getName());
			ptst.setDate(3, new java.sql.Date(bean.getVisitDate().getTime()));
			ptst.setString(4, bean.getMobile());
			ptst.setString(5, bean.getDecease());
	
			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("data inserted : " + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add patient " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		JDBCDataSource.closeConnection(conn);
	}

	public void update(PatientBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;


		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement(
					"update patient set name = ?, visit_date = ?, mobile = ?, decease=? where id =?");

			ptst.setString(1, bean.getName());
			ptst.setDate(2, new java.sql.Date(bean.getVisitDate().getTime()));
			ptst.setString(3, bean.getMobile());
			ptst.setString(4, bean.getDecease());
	
			ptst.setLong(5, bean.getId());

			int i = ptst.executeUpdate();
			System.out.println("Data updated " + i);
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating patient " + e);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public PatientBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		PatientBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from patient where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setVisitDate(rs.getDate(3));
				bean.setMobile(rs.getString(4));
				bean.setDecease(rs.getString(5));
			
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public PatientBean findByName(String name) throws ApplicationException {

		Connection conn = null;
		PatientBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from patient where name = ?");
			ptst.setString(1, name);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setVisitDate(rs.getDate(3));
				bean.setMobile(rs.getString(4));
				bean.setDecease(rs.getString(5));
			
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting patient by NAME");
		}
		return bean;
	}

	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from patient where id = ?");

			ptst.setLong(1, id);

			int i = ptst.executeUpdate();
			System.out.println("data delete " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete patient " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(PatientBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from patient where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
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
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setVisitDate(rs.getDate(3));
				bean.setMobile(rs.getString(4));
				bean.setDecease(rs.getString(5));
			
				list.add(bean);

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search patient " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}
}
