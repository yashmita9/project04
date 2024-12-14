package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.DoctorBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class DoctorModel {

	public static Integer nextPk() throws DatabaseException {
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from doctor");
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

	public static void add(DoctorBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		DoctorBean existBean = findByName(bean.getName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Doctor name is already exist.....!");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into doctor values(?,?,?,?,?)");
			conn.setAutoCommit(false);
			ptst.setInt(1, nextPk());
			ptst.setString(2, bean.getName());
			ptst.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			ptst.setString(4, bean.getMobile());
			ptst.setString(5, bean.getExpertise());

			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("inserted data -->>" + i);
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Exception e) {
				throw new ApplicationException("Exception : add rollback exception " + e.getMessage());

			}
			throw new ApplicationException("Exception : Exception in add Doctor" + ex);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void update(DoctorBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		DoctorBean existBean = findByName(bean.getName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Doctor name is already exist.....!");
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn
					.prepareStatement("update doctor set name=?,dob=?,mobile=?,expertise=? where id = ?");
			ptst.setString(1, bean.getName());
			ptst.setDate(2, new java.sql.Date(bean.getDob().getTime()));
			ptst.setString(3, bean.getMobile());
			ptst.setString(4, bean.getExpertise());
			ptst.setLong(5, bean.getId());
			int i = ptst.executeUpdate();
			System.out.println("data updated -->>" + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating doctor " + e);
		}
	}

	public DoctorBean findByPk(long id) throws ApplicationException{
		DoctorBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from doctor where id = ?");
			ptst.setLong(1, id);
			ResultSet rs = ptst.executeQuery();
			while(rs.next()) {
				bean = new DoctorBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobile(rs.getString(4));
				bean.setExpertise(rs.getString(5));
			}
			
		}catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Doctor by PK");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
		
	}
	
	public static DoctorBean findByName(String name) throws ApplicationException{
		DoctorBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from doctor where name = ?");
			ptst.setString(1, name);
			ResultSet rs = ptst.executeQuery();
			while(rs.next()) {
				bean = new DoctorBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobile(rs.getString(4));
				bean.setExpertise(rs.getString(5));
			}
		
		}catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Doctor by Name");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
		
	}
	
	public void delete(long id) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from doctor where id = ?");
			ptst.setLong(1, id);
			int i = ptst.executeUpdate();
			
			System.out.println("data deleted "+i);
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete doctor " +e);
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public List search(DoctorBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from doctor where 1=1");

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
				bean = new DoctorBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobile(rs.getString(4));
				bean.setExpertise(rs.getString(5));
				
				list.add(bean);

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search doctor " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}
}
