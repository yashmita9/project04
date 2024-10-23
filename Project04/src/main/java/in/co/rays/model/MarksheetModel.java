package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import in.co.rays.bean.MarksheetBean;
import in.co.rays.bean.StudentBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class MarksheetModel {

	public static Integer nextPk() throws Exception {

		int pk = 0;
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement("select max(id) from st_marksheet");
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConnection(conn);
		return pk + 1;

	}
	
	public MarksheetBean findByPk(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_marksheet where id = ?");
		ptst.setLong(1, id);

		MarksheetBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollNo(rs.getLong(2));
			bean.setStudentId(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedBy(rs.getString(8));
			bean.setModifiedBy(rs.getString(9));
			bean.setCreateDateTime(rs.getTimestamp(10));
			bean.setModifiedDateTime(rs.getTimestamp(11));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}
	
	public MarksheetBean findByRoll(long rollNo) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("select * from st_marksheet where roll_no = ?");
		ptst.setLong(1, rollNo);

		MarksheetBean bean = null;
		ResultSet rs = ptst.executeQuery();
		while (rs.next()) {
			bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollNo(rs.getLong(2));
			bean.setStudentId(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedBy(rs.getString(8));
			bean.setModifiedBy(rs.getString(9));
			bean.setCreateDateTime(rs.getTimestamp(10));
			bean.setModifiedDateTime(rs.getTimestamp(11));

		}
		JDBCDataSource.closeConnection(conn);
		return bean;
	}


	public void add(MarksheetBean bean) throws Exception {

		MarksheetBean exist = findByRoll((bean.getRollNo()));
		if (exist != null) {
			throw new DuplicateRecordException("roll no is already exist.....!!!");
		}
		
		StudentModel studentmodel = new StudentModel();
		StudentBean studentbean = studentmodel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName());

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");

		ptst.setLong(1, nextPk());
		ptst.setLong(2, bean.getRollNo());
		ptst.setLong(3, bean.getStudentId());
		ptst.setString(4, bean.getName());
		ptst.setInt(5, bean.getPhysics());
		ptst.setInt(6, bean.getChemistry());
		ptst.setInt(7, bean.getMaths());
		ptst.setString(8, bean.getCreatedBy());
		ptst.setString(9, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(10, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(11, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));

		int i = ptst.executeUpdate();
		System.out.println("data inserted : " + i);

		JDBCDataSource.closeConnection(conn);
	}
	
	public void update(MarksheetBean bean) throws Exception {

		MarksheetBean exist = findByRoll(bean.getRollNo());
		if (exist != null && exist.getId() != bean.getId()) {
			throw new DuplicateRecordException("roll no is already exist.....!!!");
		}
		
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ptst = conn.prepareStatement(
				"update st_marksheet set roll_no = ?, student_id = ?, name = ?,physics = ?, chemistry = ?, maths = ?, created_by =?, modified_by =?, created_datetime =?, modified_datetime =? where id =?");
		
		
		StudentModel studentmodel = new StudentModel();
		StudentBean studentbean = studentmodel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName());
		
		
		ptst.setLong(1, bean.getRollNo());
		ptst.setLong(2, bean.getStudentId());
		ptst.setString(3, bean.getName());
		ptst.setInt(4, bean.getPhysics());
		ptst.setInt(5, bean.getChemistry());
		ptst.setInt(6, bean.getMaths());
		ptst.setString(7, bean.getCreatedBy());
		ptst.setString(8, bean.getModifiedBy());
		// Convert java.security.Timestamp to java.sql.Timestamp
		ptst.setTimestamp(9, new java.sql.Timestamp(bean.getCreateDateTime().getTime()));
		ptst.setTimestamp(10, new java.sql.Timestamp(bean.getModifiedDateTime().getTime()));
		ptst.setLong(11, bean.getId());

		int i = ptst.executeUpdate();
		System.out.println("Data updated " + i);

		JDBCDataSource.closeConnection(conn);
	}
	
	public static void delete(long id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ptst = conn.prepareStatement("delete from st_marksheet where id = ?");

		ptst.setLong(1, id);

		int i = ptst.executeUpdate();
		System.out.println("data delete " + i);

	}
	
	public List search(MarksheetBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");
		PreparedStatement ptst = conn.prepareStatement(sql.toString());

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
		ResultSet rs = ptst.executeQuery();

		while (rs.next()) {
			bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollNo(rs.getLong(2));
			bean.setStudentId(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedBy(rs.getString(8));
			bean.setModifiedBy(rs.getString(9));
			bean.setCreateDateTime(rs.getTimestamp(10));
			bean.setModifiedDateTime(rs.getTimestamp(11));

			list.add(bean);

		}
		return list;

	}
}
