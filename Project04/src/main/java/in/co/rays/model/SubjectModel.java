package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CourseBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class SubjectModel {

	public static Integer nextPk() throws Exception {

		int pk = 0;
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement("select max(id) from st_subject");
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);
		return pk + 1;

	}
	
	public SubjectBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_subject where id = ?");
		ptst.setLong(1, id);

		SubjectBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new SubjectBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCourseId(rs.getLong(3));
			bean.setCourseName(rs.getString(4));
			bean.setDescription(rs.getString(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreateDateTime(rs.getTimestamp(8));
			bean.setModifiedDateTime(rs.getTimestamp(9));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}

	public SubjectBean findByName(String name) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_subject where name = ?");
		ptst.setString(1, name);

		SubjectBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new SubjectBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCourseId(rs.getLong(3));
			bean.setCourseName(rs.getString(4));
			bean.setDescription(rs.getString(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreateDateTime(rs.getTimestamp(8));
			bean.setModifiedDateTime(rs.getTimestamp(9));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}
	
	public void add(SubjectBean bean) throws Exception {

		SubjectBean exist = findByName(bean.getName());
		if (exist != null) {
			throw new DuplicateRecordException("subject is already exist.....!!!");
		}
		
		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(bean.getCourseId());
		bean.setCourseName(coursebean.getName());

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");

		ptst.setLong(1, nextPk());
		ptst.setString(2, bean.getName());
		ptst.setLong(3, bean.getCourseId());
		ptst.setString(4, bean.getCourseName());
		ptst.setString(5, bean.getDescription());
		ptst.setString(6, bean.getCreatedBy());
		ptst.setString(7, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(8, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(9, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

		int i = ptst.executeUpdate();
		System.out.println("data inserted : " + i);

		JDBCDataSource.closeConnection(conn);
	}

	public void update(SubjectBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement(
				"update st_subject set name = ?,course_id = ?,course_name = ?, description = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");
		SubjectBean exist = findByName(bean.getName());
		if (exist != null && exist.getId() != bean.getId()) {
			throw new DuplicateRecordException("subject is already exist.....!!!");
		}

		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(bean.getCourseId());
		bean.setCourseName(coursebean.getName());
		
		ptst.setString(1, bean.getName());
		ptst.setLong(2, bean.getCourseId());
		ptst.setString(3, bean.getCourseName());
		ptst.setString(4, bean.getDescription());
		ptst.setString(5, bean.getCreatedBy());
		ptst.setString(6, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(7, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(8, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));
		ptst.setLong(9, bean.getId());

		int i = ptst.executeUpdate();
		System.out.println("Data updated " + i);

		JDBCDataSource.closeConnection(conn);

	}
	
	public static void delete(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("delete from st_subject where id = ?");

		ptst.setLong(1, id);

		int i = ptst.executeUpdate();
		System.out.println("data delete " + i);

	}
	
	public List search(SubjectBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");
		

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
		List list = new ArrayList();
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			bean = new SubjectBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCourseId(rs.getLong(3));
			bean.setCourseName(rs.getString(4));
			bean.setDescription(rs.getString(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreateDateTime(rs.getTimestamp(8));
			bean.setModifiedDateTime(rs.getTimestamp(9));


			list.add(bean);

		}
		return list;

	}

}
