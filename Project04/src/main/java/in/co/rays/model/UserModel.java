package in.co.rays.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.RoleBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class UserModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from st_user");
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

	public void add(UserBean bean) throws ApplicationException, DuplicateRecordException {

		UserBean existBean = findByLogin(bean.getLogin());

		Connection conn = null;
		if (existBean != null) {
			throw new DuplicateRecordException("login id is already exist.....!!!");
		}
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

			conn.setAutoCommit(false);

			ptst.setLong(1, nextPk());
			ptst.setString(2, bean.getFirstName());
			ptst.setString(3, bean.getLastName());
			ptst.setString(4, bean.getLogin());
			ptst.setString(5, bean.getPassword());
			ptst.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			ptst.setString(7, bean.getMobileNo());
			ptst.setLong(8, bean.getRoleId());
			ptst.setString(9, bean.getGender());
			ptst.setString(10, bean.getCreatedBy());
			ptst.setString(11, bean.getModifiedBy());
			ptst.setTimestamp(12, bean.getCreateDateTime());
			ptst.setTimestamp(13, bean.getModifiedDateTime());

			int i = ptst.executeUpdate();

			conn.commit();

			System.out.println("data inserted" + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

	}

	public void update(UserBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		UserBean existBean = findByLogin(bean.getLogin());

		if (existBean != null && bean.getId() != existBean.getId()) {
			throw new DuplicateRecordException("login id is already exist.....!!!");
		}

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement(
					"update st_user set first_name = ?, last_name=?, login = ?, password=?, dob = ?, mobile_no = ?, role_id = ?, gender = ?,created_by = ? , modified_by = ? , created_datetime =?, modified_datetime = ? where id = ?");

			ptst.setString(1, bean.getFirstName());
			ptst.setString(2, bean.getLastName());
			ptst.setString(3, bean.getLogin());
			ptst.setString(4, bean.getPassword());
			ptst.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			ptst.setString(6, bean.getMobileNo());
			ptst.setLong(7, bean.getRoleId());
			ptst.setString(8, bean.getGender());
			ptst.setString(9, bean.getCreatedBy());
			ptst.setString(10, bean.getModifiedBy());
			ptst.setTimestamp(11, bean.getCreateDateTime());
			ptst.setTimestamp(12, bean.getModifiedDateTime());
			ptst.setLong(13, bean.getId());

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

	public UserBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		UserBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_user where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreateDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User by login " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public UserBean findByLogin(String login) throws ApplicationException {

		Connection conn = null;
		UserBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_user where login = ?");
			ptst.setString(1, login);

			ResultSet rs = ptst.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getInt(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreateDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));

			}
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting User by login " + e);

		} finally {

			JDBCDataSource.closeConnection(conn);

		}
		return bean;
	}

	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");

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
			throw new ApplicationException("Exception : Exception in delete User " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public List search(UserBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");

		if (bean != null) {
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" and first_name like '" + bean.getFirstName() + "%'");
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
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getTimestamp(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreateDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));

				list.add(bean);

			}
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search user " + e);

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
	
	public UserBean authenticate(String loginId, String password) throws ApplicationException {

		Connection conn = null;
		UserBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ? and password = ?");

			pstmt.setString(1, loginId);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreateDateTime(rs.getTimestamp(12));
				bean.setModifiedDateTime(rs.getTimestamp(13));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get roles " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

}
