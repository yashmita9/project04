package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CollegeModel;
import in.co.rays.model.RoleModel;

public class TestCollege {

	public static void main(String[] args) throws Exception {
//		 testAdd();
//		 testUpdate();
//		testFindByPk();
//		testFindByName();
//		testDelete();
//		testList();
//		testSearch();
		
		  	PriorityQueue p = new PriorityQueue();
			p.add(1);
			p.add(2.3);
			p.add(6);
			p.add('A');
			System.out.println(p);
		
	}

	public static void testAdd() throws Exception {
		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean.setName("Swami Vivekanand College");
		bean.setAddress("abp road");
		bean.setState("MP");
		bean.setCity("indore");
		bean.setPhoneNo("4567898765");
		bean.setCreatedBy("yashmita@gmail.com");
		bean.setModifiedBy("yashmita@gmail.com");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	public static void testUpdate() throws Exception {
		CollegeModel model = new CollegeModel();
		CollegeBean bean = model.findByPk(4);
		bean.setName("abc");

		try {
			model.update(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByPk() throws Exception {

		CollegeModel model = new CollegeModel();
		CollegeBean bean = model.findByPk(4);

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
		CollegeBean bean = model.findByName("abc");

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
		
		model.delete(4);
	}
	
	public static void testSearch() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean.setName("oriental");
		bean.setCity("bhopal");
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
	
	public static void testList() throws Exception {
		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		List list = model.list();
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
