package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.ProductBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class ProductModel {

	public static Integer nextPk() throws DatabaseException {
		
		int pk = 0;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from product");
			ResultSet rs = ptst.executeQuery();
			while(rs.next()) {
				pk = rs.getInt(1);
			}
		}
			catch (Exception e) {

				throw new DatabaseException("Exception : Exception in getting PK " + e);
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		
		
		return pk+1;
		
	}
	
	public void add(ProductBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		ProductBean existBean = findByName(bean.getName());
		if (existBean != null) {
			throw new DuplicateRecordException("product name is already exist.....!");
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into product values(?,?,?,?,?,?)");

			conn.setAutoCommit(false);

			ptst.setInt(1, nextPk());
			ptst.setString(2, bean.getName());
			ptst.setString(3, bean.getDescription());
			ptst.setDouble(4, bean.getPrice());
			ptst.setDate(5, new java.sql.Date(bean.getDop().getTime()));
			ptst.setString(6, bean.getCategory());
			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("data inserted : " + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add product " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		JDBCDataSource.closeConnection(conn);
	}

	public void update(ProductBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		
		ProductBean existBean = findByName(bean.getName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("product name is already exist.....!");
		}
		
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement(
					"update product set name = ?, description = ?, price = ?, dop=?, category = ? where id =?");

			ptst.setString(1, bean.getName());
			ptst.setString(2, bean.getDescription());
			ptst.setDouble(3, bean.getPrice());
			ptst.setDate(4, new java.sql.Date(bean.getDop().getTime()));;
			ptst.setString(5, bean.getCategory());
			ptst.setLong(6, bean.getId());

			int i = ptst.executeUpdate();
			System.out.println("Data updated " + i);
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating product " + e);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public ProductBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		ProductBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from product where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setPrice(rs.getDouble(4));;
				bean.setDop(rs.getDate(5));
				bean.setCategory(rs.getString(6));
			
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting product by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public ProductBean findByName(String name) throws ApplicationException {

		Connection conn = null;
		ProductBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from product where name = ?");
			ptst.setString(1, name);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setPrice(rs.getDouble(4));;
				bean.setDop(rs.getDate(5));
				bean.setCategory(rs.getString(6));
			
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting product by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from product where id = ?");

			ptst.setLong(1, id);

			int i = ptst.executeUpdate();
			System.out.println("data delete " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete product " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	
	public List search(ProductBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from product where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
//			if (bean.getCategory() != null && bean.getCategory().length() > 0) {
//				sql.append(" and category = " + bean.getCategory());
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
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setPrice(rs.getDouble(4));
				bean.setDop(rs.getDate(5));
				bean.setCategory(rs.getString(6));
				list.add(bean);

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search product " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

}
