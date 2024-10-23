package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.StudentBean;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class StudentModel {

	public Integer nextPk() throws Exception {
		int pk = 0;
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select max(id) from st_student");
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);
		return pk + 1;

	}

	public void add(StudentBean bean) throws Exception {

		StudentBean existBean = findByEmail(bean.getEmail());

		if (existBean != null) {
			throw new DuplicateRecordException("email is already exist.....!!!");
		}

		CollegeModel collegeModel = new CollegeModel();

		CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());

		bean.setCollegeName(collegeBean.getName());


		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn
				.prepareStatement("insert into st_student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		pstmt.setLong(1, nextPk());
		pstmt.setString(2, bean.getFirstName());
		pstmt.setString(3, bean.getLastName());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(5, bean.getGender());
		pstmt.setString(6, bean.getMobileNo());
		pstmt.setString(7, bean.getEmail());
		pstmt.setLong(8, bean.getCollegeId());
		pstmt.setString(9, bean.getCollegeName());
		pstmt.setString(10, bean.getCreatedBy());
		pstmt.setString(11, bean.getModifiedBy());
		pstmt.setTimestamp(12, bean.getCreateDateTime());
		pstmt.setTimestamp(13, bean.getModifiedDateTime());

		int i = pstmt.executeUpdate();
		
		System.out.println("data inserted " + i);
		JDBCDataSource.closeConnection(conn);
	}

	public StudentBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_student where id = ?");

		ptst.setLong(1, id);
		ResultSet rs = ptst.executeQuery();
		StudentBean bean = null;
		while (rs.next()) {
			bean = new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getTimestamp(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeId(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreateDateTime(rs.getTimestamp(12));
			bean.setModifiedDateTime(rs.getTimestamp(13));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}

	public void update(StudentBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement(
				"update st_student set first_name = ?, last_name = ?,dob = ?, gender = ? , mobile_no = ?, email = ?,college_id =? , college_name = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");

		StudentBean existBean = findByEmail(bean.getEmail());

		if (existBean != null && bean.getId() != existBean.getId()) {
			throw new DuplicateRecordException("email is already exist.....!!!");
		}

		CollegeModel collegeModel = new CollegeModel();

		CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());

		bean.setCollegeName(collegeBean.getName());

		ptst.setString(1, bean.getFirstName());
		ptst.setString(2, bean.getLastName());
		ptst.setTimestamp(3, bean.getDob());
		ptst.setString(4, bean.getGender());
		ptst.setString(5, bean.getMobileNo());
		ptst.setString(6, bean.getEmail());
		ptst.setLong(7, bean.getCollegeId());
		ptst.setString(8, bean.getCollegeName());
		ptst.setString(9, bean.getCreatedBy());
		ptst.setString(10, bean.getModifiedBy());
		ptst.setTimestamp(11, bean.getCreateDateTime());
		ptst.setTimestamp(12, bean.getModifiedDateTime());
		ptst.setLong(13, bean.getId());
		int i = ptst.executeUpdate();
		System.out.println("Data updated " + i);

		JDBCDataSource.closeConnection(conn);

	}

	public StudentBean findByEmail(String email) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_student where email = ?");

		ptst.setString(1, email);
		ResultSet rs = ptst.executeQuery();
		StudentBean bean = null;
		while (rs.next()) {
			bean = new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getTimestamp(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeId(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreateDateTime(rs.getTimestamp(12));
			bean.setModifiedDateTime(rs.getTimestamp(13));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}

	public void delete(long id) throws Exception {
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("delete from st_student where id = ?");
		ptst.setLong(1, id);

		int i = ptst.executeUpdate();
		JDBCDataSource.closeConnection(conn);

		System.out.println("data delete " + i);
	}
	
	public List search(StudentBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from st_student where 1=1");
		PreparedStatement ptst = conn.prepareStatement(sql.toString());

		if (bean != null) {
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" and name like '" + bean.getFirstName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				sql.append(" and dob like '" + new java.sql.Date(bean.getDob().getTime()) + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}
		List list = new ArrayList();
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			bean = new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getTimestamp(4));
			bean.setGender(rs.getString(5));
			bean.setMobileNo(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setCollegeId(rs.getLong(8));
			bean.setCollegeName(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreateDateTime(rs.getTimestamp(12));
			bean.setModifiedDateTime(rs.getTimestamp(13));
			list.add(bean);

		}
		return list;

	}

}
