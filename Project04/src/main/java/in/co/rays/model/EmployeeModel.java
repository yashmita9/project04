package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.EmployeeBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class EmployeeModel {

	public Integer nextPk() throws DatabaseException {
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from employee");
			ResultSet rs = ptst.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
		}catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK " + e);
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
		
	}
	
	public void add(EmployeeBean bean) throws ApplicationException, DuplicateRecordException {

		EmployeeBean existBean = findByLogin(bean.getUserName());

		Connection conn = null;
		if (existBean != null) {
			throw new DuplicateRecordException("login id is already exist.....!!!");
		}
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into employee values(?,?,?,?,?,?)");

			conn.setAutoCommit(false);

			ptst.setLong(1, nextPk());
			ptst.setString(2, bean.getName());
			ptst.setString(3, bean.getUserName());
			ptst.setString(4, bean.getPassword());
			ptst.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			ptst.setString(6, bean.getMobileNo());
			
			int i = ptst.executeUpdate();

			conn.commit();

			System.out.println("data inserted" + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Employee " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

	}

	public void update(EmployeeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		EmployeeBean existBean = findByLogin(bean.getUserName());

		if (existBean != null && bean.getId() != existBean.getId()) {
			throw new DuplicateRecordException("login id is already exist.....!!!");
		}

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement(
					"update employee set name = ?, user_name = ?, password=?, dob = ?, mobile_no = ? where id = ?");

			ptst.setString(1, bean.getName());
			ptst.setString(2, bean.getUserName());
			ptst.setString(3, bean.getPassword());
			ptst.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			ptst.setString(5, bean.getMobileNo());
			
			ptst.setLong(6, bean.getId());

			int i = ptst.executeUpdate();


			System.out.println("data updated " + i);

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User " + e);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public EmployeeBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		EmployeeBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from employee where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();

			while (rs.next()) {
				bean = new EmployeeBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setMobileNo(rs.getString(6));
				
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting employee by login " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public EmployeeBean findByLogin(String userName) throws ApplicationException {

		Connection conn = null;
		EmployeeBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from employee where user_name = ?");
			ptst.setString(1, userName);

			ResultSet rs = ptst.executeQuery();

			while (rs.next()) {
				
					bean = new EmployeeBean();
					bean.setId(rs.getInt(1));
					bean.setName(rs.getString(2));
					bean.setUserName(rs.getString(3));
					bean.setPassword(rs.getString(4));
					bean.setDob(rs.getDate(5));
					bean.setMobileNo(rs.getString(6));
					
			}
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting Employee by login " + e);

		} finally {

			JDBCDataSource.closeConnection(conn);

		}
		return bean;
	}

	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("delete from employee where id = ?");

			pstmt.setLong(1, id);

			int i = pstmt.executeUpdate();
			conn.commit();

			System.out.println("data deleted => " + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Employee " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public List search(EmployeeBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from employee where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and first_name like '" + bean.getName() + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
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

			System.out.println(sql);
			while (rs.next()) {
				bean = new EmployeeBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setMobileNo(rs.getString(6));
				
				list.add(bean);

			}
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search Employee " + e);

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
	
public EmployeeBean authenticate(String userName, String password) throws ApplicationException {
	Connection conn = null;
	EmployeeBean bean = null;
	
	try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from employee where user_name = ?, password=?");
		ptst.setString(1, userName);
		ptst.setString(2, password);

		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new EmployeeBean();
			bean.setId(rs.getInt(1));
			bean.setName(rs.getString(2));
			bean.setUserName(rs.getString(3));
			bean.setPassword(rs.getString(4));
			bean.setDob(rs.getDate(5));
			bean.setMobileNo(rs.getString(6));
		}
				
	} catch (Exception e) {
		throw new ApplicationException("Exception : Exception in get roles " + e);
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	return bean;

}
public List list() throws ApplicationException {
	return search(null, 0, 0);
}
}
