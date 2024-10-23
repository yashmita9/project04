package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.CourseBean;
import in.co.rays.bean.FacultyBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class FacultyModel {

	public static Integer nextPk() throws Exception {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement("select max(id) from st_faculty");
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

	public FacultyBean findByPk(long id) throws ApplicationException {

		Connection conn = null;

		FacultyBean bean = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_faculty where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirst_name(rs.getString(2));
				bean.setLast_name(rs.getString(3));
				bean.setDob(rs.getTimestamp(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreateDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting faculty by PK " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public FacultyBean findByEmail(String email) throws ApplicationException {

		Connection conn = null;

		FacultyBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from st_faculty where email = ?");
			ptst.setString(1, email);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirst_name(rs.getString(2));
				bean.setLast_name(rs.getString(3));
				bean.setDob(rs.getTimestamp(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreateDateTime(rs.getTimestamp(16));
				bean.setModifiedDateTime(rs.getTimestamp(17));

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting faculty by NAME " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public void add(FacultyBean bean) throws ApplicationException, DuplicateRecordException, DatabaseException {

		Connection conn = null;

		SubjectModel subjectmodel = new SubjectModel();
		SubjectBean subjectbean = null;
		try {
			subjectbean = subjectmodel.findByPk(bean.getSubjectId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bean.setSubjectName(subjectbean.getName());

		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(bean.getCourseId());
		bean.setCourseName(coursebean.getName());

		CollegeModel collegemodel = new CollegeModel();
		CollegeBean collegebean = collegemodel.findByPk(bean.getCollegeId());
		bean.setCollegeName(collegebean.getName());

		FacultyBean existBean = findByEmail(bean.getEmail());

		if (existBean != null) {
			throw new DuplicateRecordException("email is already exist.....!!!");
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn
					.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			conn.setAutoCommit(false);

			ptst.setLong(1, nextPk());
			ptst.setString(2, bean.getFirst_name());
			ptst.setString(3, bean.getLast_name());
			ptst.setTimestamp(4, bean.getDob());
			ptst.setString(5, bean.getGender());
			ptst.setString(6, bean.getMobileNo());
			ptst.setString(7, bean.getEmail());
			ptst.setLong(8, bean.getCollegeId());
			ptst.setString(9, bean.getCollegeName());
			ptst.setLong(10, bean.getCourseId());
			ptst.setString(11, bean.getCourseName());
			ptst.setLong(12, bean.getSubjectId());
			ptst.setString(13, bean.getSubjectName());
			ptst.setString(14, bean.getCreatedBy());
			ptst.setString(15, bean.getModifiedBy());
			// Convert java.security.Timestamp to java.sql.Timestamp
			ptst.setTimestamp(16, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
			ptst.setTimestamp(17, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("data inserted : " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add faculty " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

	}

	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		SubjectModel subjectmodel = new SubjectModel();
		SubjectBean subjectbean = null;
		try {
			subjectbean = subjectmodel.findByPk(bean.getSubjectId());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bean.setSubjectName(subjectbean.getName());

		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findByPk(bean.getCourseId());
		bean.setCourseName(coursebean.getName());

		CollegeModel collegemodel = new CollegeModel();
		CollegeBean collegebean = collegemodel.findByPk(bean.getCollegeId());
		bean.setCollegeName(collegebean.getName());

		FacultyBean existBean = findByEmail(bean.getEmail());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("email is already exist.....!!!");
		}
		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement(
					"update st_faculty set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?,college_id = ?, college_name = ?, course_id = ?, course_name = ?, subject_id=? , subject_name = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");

			conn.setAutoCommit(false);
			ptst.setString(1, bean.getFirst_name());
			ptst.setString(2, bean.getLast_name());
			ptst.setTimestamp(3, bean.getDob());
			ptst.setString(4, bean.getGender());
			ptst.setString(5, bean.getMobileNo());
			ptst.setString(6, bean.getEmail());
			ptst.setLong(7, bean.getCollegeId());
			ptst.setString(8, bean.getCollegeName());
			ptst.setLong(9, bean.getCourseId());
			ptst.setString(10, bean.getCourseName());
			ptst.setLong(11, bean.getSubjectId());
			ptst.setString(12, bean.getSubjectName());
			ptst.setString(13, bean.getCreatedBy());
			ptst.setString(14, bean.getModifiedBy());
			// Convert java.security.Timestamp to java.sql.Timestamp
			ptst.setTimestamp(15, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
			ptst.setTimestamp(16, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));
			ptst.setLong(17, bean.getId());

			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("Data updated " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating faculty " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from st_faculty where id = ?");

			ptst.setLong(1, id);

			int i = ptst.executeUpdate();
			System.out.println("data delete " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete faculty " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);

		}

	}

	public List search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");


		if (bean != null) {
			if (bean.getFirst_name() != null && bean.getFirst_name().length() > 0) {
				sql.append(" and name like '" + bean.getFirst_name() + "%'");
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
			bean = new FacultyBean();
			bean.setId(rs.getLong(1));
			bean.setFirst_name(rs.getString(2));
			bean.setLast_name(rs.getString(3));
			bean.setDob(rs.getTimestamp(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeId(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCourseId(rs.getLong(10));
			bean.setCourseName(rs.getString(11));
			bean.setSubjectId(rs.getLong(12));
			bean.setSubjectName(rs.getString(13));
			bean.setCreatedBy(rs.getString(114));
			bean.setModifiedBy(rs.getString(15));
			bean.setCreateDateTime(rs.getTimestamp(16));
			bean.setModifiedDateTime(rs.getTimestamp(17));

			list.add(bean);

		}
		}catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search faculty " + e);
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}
}
