package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class CollegeModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from st_college");
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

	public void add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CollegeBean existBean = findByName(bean.getName());
		if (existBean != null) {
			throw new DuplicateRecordException("college name is already exist.....!");
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");

			conn.setAutoCommit(false);

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
			conn.commit();
			System.out.println("data inserted : " + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add college " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		JDBCDataSource.closeConnection(conn);
	}

	public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CollegeBean existBean = findByName(bean.getName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("college name is already exist.....!");
		}

		try {
			conn = JDBCDataSource.getConnection();

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
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating college " + e);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public CollegeBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		CollegeBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_college where id = ?");
			ptst.setLong(1, id);

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
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public CollegeBean findByName(String name) throws ApplicationException {

		Connection conn = null;
		CollegeBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_college where name = ?");
			ptst.setString(1, name);

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
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by NAME");
		}
		return bean;
	}

	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from st_college where id = ?");

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

	public List search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_college where 1=1");

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
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search college " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
}
