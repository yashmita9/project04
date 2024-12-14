package in.co.rays.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.ProductBean;
import in.co.rays.bean.ShopBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DatabaseException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class ShopModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;

		int pk = 0;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select max(id) from shopping");
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

	public void add(ShopBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ProductModel productModel = new ProductModel();
		ProductBean productBean = productModel.findByPk(bean.getProductId());
		bean.setProductName(productBean.getName());

		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("insert into shopping values(?,?,?,?,?,?)");

			conn.setAutoCommit(false);

			ptst.setInt(1, nextPk());
			ptst.setString(2, bean.getName());
			ptst.setDate(3, new java.sql.Date(bean.getShopDate().getTime()));
			ptst.setLong(4, bean.getQuantity());
			ptst.setLong(5, bean.getProductId());
			ptst.setString(6, bean.getProductName());
			
			int i = ptst.executeUpdate();
			conn.commit();
			System.out.println("data inserted : " + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Shooping Cart " + e);
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		JDBCDataSource.closeConnection(conn);
	}

	public void update(ShopBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ptst = conn.prepareStatement(
					"update shopping set name = ?,shop_date = ?, quantity=? ,  product_id = ?, product_name = ? where id =?");

			ptst.setString(1, bean.getName());
			ptst.setDate(2,new java.sql.Date( bean.getShopDate().getTime()));
			ptst.setLong(3, bean.getQuantity());
			ptst.setLong(4, bean.getProductId());
			ptst.setString(5, bean.getProductName());
			ptst.setLong(6, bean.getId());

			int i = ptst.executeUpdate();
			System.out.println("Data updated " + i);
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating shopping cart " + e);

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public ShopBean findByPk(long id) throws ApplicationException {

		Connection conn = null;
		ShopBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("select * from shopping where id = ?");
			ptst.setLong(1, id);

			ResultSet rs = ptst.executeQuery();
			while (rs.next()) {
				bean = new ShopBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				
				bean.setShopDate(rs.getDate(3));
				bean.setQuantity(rs.getLong(4));
				bean.setProductId(rs.getLong(5));
				bean.setProductName(rs.getString(6));
				
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting shopping cart by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

		public static void delete(long id) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ptst = conn.prepareStatement("delete from shopping where id = ?");

			ptst.setLong(1, id);

			int i = ptst.executeUpdate();
			System.out.println("data delete " + i);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Shopping cart " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(ShopBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from shopping where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			
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
				bean = new ShopBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setShopDate(rs.getDate(3));
				bean.setQuantity(rs.getLong(4));
				bean.setProductId(rs.getLong(5));
				bean.setProductName(rs.getString(6));
				
				list.add(bean);

			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Shopping Cart " + e);
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	
}
