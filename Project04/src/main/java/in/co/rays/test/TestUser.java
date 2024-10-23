package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.UserModel;

public class TestUser {

	public static void main(String[] args) throws Exception {
		
//		testAdd();
		testAuthenticate();
//		testUpdate();
//		testSearch();
	}

	public static void testAdd() {
		
		UserModel model = new UserModel();
		UserBean bean = new UserBean();
		
		bean.setFirstName("rahul");
		bean.setLastName("sharma");
		bean.setLogin("rahul@gmail.com");
		bean.setPassword("8899");
		bean.setDob(new Date());
		bean.setMobileNo("9877472057");
		bean.setRoleId(2);
		bean.setGender("male");
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

		bean.setPassword("123");
		
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

	public static void testAuthenticate() throws Exception {
		
		UserModel model = new UserModel();
		UserBean bean = model.authenticate("yashmita@gmail.com", "123");
		
		if(bean != null) {
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
}
