package in.co.rays.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.TaskBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class TaskModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from task");
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

	public void add(TaskBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

//		TaskBean existBean = findByName(bean.getName());
//		if (existBean != null) {
//			throw new DuplicateRecordException("college name is already exist.....!");
//		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into task values(?,?,?,?,?,?)");

			conn.setAutoCommit(false);

			ptst.setInt(1, nextPk());
			ptst.setDate(2, new java.sql.Date(bean.getCreationDate().getTime()) );
			ptst.setString(3, bean.getTaskTitle());
			ptst.setString(4, bean.getDetails());
			ptst.setString(5, bean.getAssign());
			ptst.setString(6, bean.getTaskStatus());
			
			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("data inserted : " + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Task " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		JDBCDataSource.closeConnection(conn);
	}

	public void update(TaskBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

//		TaskBean existBean = findByName(bean.getName());
//		if (existBean != null && existBean.getId() != bean.getId()) {
//			throw new DuplicateRecordException("college name is already exist.....!");
//		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ptst = conn.prepareStatement(
					"update task set creation_date = ?, task_title = ?, details = ?, assign=?, task_status = ? where id =?");

			ptst.setDate(1, new java.sql.Date(bean.getCreationDate().getTime()) );
			ptst.setString(2, bean.getTaskTitle());
			ptst.setString(3, bean.getDetails());
			ptst.setString(4, bean.getAssign());
			ptst.setString(5, bean.getTaskStatus());
			ptst.setLong(6, bean.getId());

			int i = ptst.executeUpdate();
			System.out.println("Data updated " + i);
			conn.commit();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating task " + e);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public TaskBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		TaskBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from task where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new TaskBean();
				bean.setId(rs.getLong(1));
				bean.setCreationDate(rs.getDate(2));
				bean.setTaskTitle(rs.getString(3));
				bean.setDetails(rs.getString(4));
				bean.setAssign(rs.getString(5));
				bean.setTaskStatus(rs.getString(6));
				
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Task by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	
	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from task where id = ?");

			ptst.setLong(1, id);

			int i = ptst.executeUpdate();
			System.out.println("data delete " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Task " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(TaskBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from task where 1=1");

		if (bean != null) {
//			if (bean.getTaskTitle() != null && bean.getTaskTitle().length() > 0) {
//				sql.append(" and name like '" + bean.getTaskTitle() + "%'");
//			}
//			if (bean.getCity() != null && bean.getCity().length() > 0) {
//				sql.append(" and city like '" + bean.getCity() + "%'");
//			}
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
				bean = new TaskBean();
				bean.setId(rs.getLong(1));
				bean.setCreationDate(rs.getDate(2));
				bean.setTaskTitle(rs.getString(3));
				bean.setDetails(rs.getString(4));
				bean.setAssign(rs.getString(5));
				bean.setTaskStatus(rs.getString(6));
				
				list.add(bean);

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Task " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}
	
}
