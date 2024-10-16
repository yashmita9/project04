package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.model.CollegeModel;
import in.co.rays.model.RoleModel;

public class TestCollege {

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testUpdate();
		//testFindByPk();
		//testFindByName();
		//testDelete();
		testSearch();
	}

	public static void testAdd() throws Exception {
		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean.setName("Prestig College");
		bean.setAddress("abp road");
		bean.setState("MP");
		bean.setCity("indore");
		bean.setPhoneNo("6986357467");
		bean.setCreatedBy("yashmita@gmail.com");
		bean.setModifiedBy("yashmita@gmail.com");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	public static void testUpdate() throws Exception {
		CollegeModel model = new CollegeModel();
		CollegeBean bean = model.findByPk(1);
		bean.setName("Oriental college");

		try {
			model.update(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByPk() throws Exception {

		CollegeModel model = new CollegeModel();
		CollegeBean bean = model.findByPk(2);

		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}

	}

	public static void testFindByName() throws Exception {

		CollegeModel model = new CollegeModel();
		CollegeBean bean = model.findByName("LNCT College");

		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}
	}
	
	public static void testDelete() throws Exception {
		CollegeModel model = new CollegeModel();
		
		model.delete(2);
	}
	
	public static void testSearch() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		List list = model.search(bean, 1, 5);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (CollegeBean) it.next();

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}
	}
}
