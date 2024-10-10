package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.UserBean;
import in.co.rays.model.UserModel;

public class TestUser {

	public static void main(String[] args) throws Exception {
		
		//testAdd();
		//testUpdate();
		testSearch();
	}

	public static void testAdd() {
		
		UserModel model = new UserModel();
		UserBean bean = new UserBean();
		
		bean.setFirstName("yashmita");
		bean.setLastName("Rathore");
		bean.setLogin("yashmita@gmail.com");
		bean.setPassword("1234");
		bean.setDob(new Timestamp(new Date().getTime()));
		bean.setMobileNo("9988581499");
		bean.setRoleId(1);
		bean.setGender("female");
		bean.setCreatedBy("yashmita@gmail.com");
		bean.setModifiedBy("yashmita@gmail.com");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		try {
			model.add(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void testUpdate() throws Exception {
		
		UserModel model = new UserModel();
		UserBean bean = model.findByPk(1);

		bean.setPassword("yashi");
		
		model.update(bean);
		
		
	}
	
	public static void testSearch() throws Exception{
		
		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		
		bean.setFirstName("yash");
		
		List list = model.search(bean, 1, 5);
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			bean = (UserBean) it.next();
			
			System.out.println(bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getLogin());
			System.out.print("\t" + bean.getPassword());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getRoleId());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreateDateTime());
			System.out.println("\t" + bean.getModifiedDateTime());
		}
	}

	public static void testdelete() {

	}
}
