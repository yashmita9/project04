package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.PatientBean;
import in.co.rays.model.PatientModel;

public class TestPatient {

	public static void main(String[] args) throws Exception {
//		 testAdd();
//		 testUpdate();
//		testFindByPk();
//		testFindByName();
//		testDelete();
//		testList();
		testSearch();
	}

	public static void testAdd() throws Exception {
		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();

		bean.setName("Rahul Parmar");
		bean.setVisitDate(new Date());
		bean.setMobile("9988589658");
		bean.setDecease("Asthma");
		
		model.add(bean);

	}

	public static void testUpdate() throws Exception {
		PatientModel model = new PatientModel();
		PatientBean bean = model.findByPk(1);
		bean.setName("shubham");

		try {
			model.update(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByPk() throws Exception {

		PatientModel model = new PatientModel();
		PatientBean bean = model.findByPk(1);
		
		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getVisitDate());
			System.out.println(bean.getMobile());
			System.out.println(bean.getDecease());
			
		}

	}

	public static void testFindByName() throws Exception {

		PatientModel model = new PatientModel();
		PatientBean bean = model.findByName("shubham");
		
		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getVisitDate());
			System.out.println(bean.getMobile());
			System.out.println(bean.getDecease());
			
		}
	}
	
	public static void testDelete() throws Exception {
		PatientModel model = new PatientModel();
		
		model.delete(1);
	}
	
	public static void testSearch() throws Exception {

		PatientModel model = new PatientModel();
		PatientBean bean = new PatientBean();
		
		bean.setName("Rahul Parmar");
		
		List list = model.search(bean, 1, 5);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (PatientBean) it.next();

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getVisitDate());
			System.out.println(bean.getMobile());
			System.out.println(bean.getDecease());
			
		}
	}
	
	public static void testList() throws Exception {
		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();

		List list = model.list();
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (PatientBean) it.next();

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getVisitDate());
			System.out.println(bean.getMobile());
			System.out.println(bean.getDecease());
			
		}
	}
	
}
