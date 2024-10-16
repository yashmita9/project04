package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CourseBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.bean.TimeTableBean;
import in.co.rays.util.JDBCDataSource;

public class TimeTableModel {

	public static Integer nextPk() throws Exception {

		int pk = 0;
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement("select max(id) from st_timetable");
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);
		return pk + 1;

	}
	
	public TimeTableBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_timetable where id = ?");
		ptst.setLong(1, id);

		TimeTableBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new TimeTableBean();
			bean.setId(rs.getLong(1));
			bean.setSemester(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setExamDate(rs.getTimestamp(4));
			bean.setExamTime(rs.getString(5));
			bean.setCourseId(rs.getLong(6));
			bean.setCourseName(rs.getString(7));
			bean.setSubjectId(rs.getLong(8));
			bean.setSubjectName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreateDateTime(rs.getTimestamp(12));
			bean.setModifiedDateTime(rs.getTimestamp(13));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}
	
//	public TimeTableBean findByName(String name) throws Exception {
//
//		Connection conn = JDBCDataSource.getConnection();
//		PreparedStatement ptst = conn.prepareStatement("select * from st_timetable where name = ?");
//		ptst.setString(1, name);
//
//		TimeTableBean bean = null;
//		ResultSet rs = ptst.executeQuery();
//		while (rs.next()) {
//			bean = new TimeTableBean();
//			bean.setId(rs.getLong(1));
//			bean.setSemester(rs.getString(2));
//			bean.setDescription(rs.getString(3));
//			bean.setExamDate(rs.getTimestamp(4));
//			bean.setExamTime(rs.getString(5));
//			bean.setCourseId(rs.getLong(6));
//			bean.setCourseName(rs.getString(7));
//			bean.setSubjectId(rs.getLong(8));
//			bean.setSubjectName(rs.getString(9));
//			bean.setCreatedBy(rs.getString(10));
//			bean.setModifiedBy(rs.getString(11));
//			bean.setCreateDateTime(rs.getTimestamp(12));
//			bean.setModifiedDateTime(rs.getTimestamp(13));
//
//		}
//		JDBCDataSource.closeConnection(conn);
//		return bean;
//	}
	
	public void add(TimeTableBean bean) throws Exception {
		
		SubjectModel subjectmodel = new SubjectModel();
		SubjectBean subjectbean = subjectmodel.findByPk(bean.getSubjectId());
		bean.setSubjectName(subjectbean.getName());
		
		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(bean.getCourseId());
		bean.setCourseName(coursebean.getName());

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

		ptst.setLong(1, nextPk());
		ptst.setString(2, bean.getSemester());
		ptst.setString(3, bean.getDescription());;
		ptst.setTimestamp(4, bean.getExamDate());
		ptst.setString(5, bean.getExamTime());
		ptst.setLong(6, bean.getCourseId());
		ptst.setString(7, bean.getCourseName());
		ptst.setLong(8, bean.getSubjectId());
		ptst.setString(9, bean.getSubjectName());
		ptst.setString(10, bean.getCreatedBy());
		ptst.setString(11, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(12, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(13, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

		int i = ptst.executeUpdate();
		System.out.println("data inserted : " + i);

		JDBCDataSource.closeConnection(conn);
	}
	
	public void update(TimeTableBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement(
				"update st_timetable set semester = ?, description = ?, exam_date = ?, exam_time = ?, course_id = ?, course_name = ?, subject_id=? , subject_name = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");
		
		SubjectModel subjectmodel = new SubjectModel();
		SubjectBean subjectbean = subjectmodel.findByPk(bean.getSubjectId());
		bean.setSubjectName(subjectbean.getName());
		
		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(bean.getCourseId());
		bean.setCourseName(coursebean.getName());
		
		
		ptst.setString(1, bean.getSemester());
		ptst.setString(2, bean.getDescription());;
		ptst.setTimestamp(3, bean.getExamDate());
		ptst.setString(4, bean.getExamTime());
		ptst.setLong(5, bean.getCourseId());
		ptst.setString(6, bean.getCourseName());
		ptst.setLong(7, bean.getSubjectId());
		ptst.setString(8, bean.getSubjectName());
		ptst.setString(9, bean.getCreatedBy());
		ptst.setString(10, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(11, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(12, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

		ptst.setLong(13, bean.getId());


		int i = ptst.executeUpdate();
		System.out.println("Data updated " + i);

		JDBCDataSource.closeConnection(conn);
	}
	
	public static void delete(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("delete from st_timetable where id = ?");

		ptst.setLong(1, id);

		int i = ptst.executeUpdate();
		System.out.println("data delete " + i);

	}
	
	public List search(TimeTableBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");
		PreparedStatement ptst = conn.prepareStatement(sql.toString());

		if (bean != null) {
			if (bean.getSemester() != null && bean.getSemester().length() > 0) {
				sql.append(" and name like '" + bean.getSemester() + "%'");
			}
			
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}
		List list = new ArrayList();
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			bean = new TimeTableBean();
			bean.setId(rs.getLong(1));
			bean.setSemester(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setExamDate(rs.getTimestamp(4));
			bean.setExamTime(rs.getString(5));
			bean.setCourseId(rs.getLong(6));
			bean.setCourseName(rs.getString(7));
			bean.setSubjectId(rs.getLong(8));
			bean.setSubjectName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreateDateTime(rs.getTimestamp(12));
			bean.setModifiedDateTime(rs.getTimestamp(13));

			list.add(bean);

		}
		return list;

	}
}
