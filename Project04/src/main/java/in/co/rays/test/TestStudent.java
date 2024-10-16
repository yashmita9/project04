package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.StudentBean;
import in.co.rays.model.StudentModel;

public class TestStudent {

	public static void main(String[] args)throws Exception {
		//testAdd();
		//testUpdate();
		//testFindByPk();
		testFindByEmail();
		//testDelete();
		//estSearch();
	}
	
	public static void testAdd() throws Exception {

		StudentBean bean = new StudentBean();

		bean.setFirstName("ruhi");
		bean.setLastName("patel");
		bean.setDob(new Timestamp(new Date().getTime()));
		bean.setGender("Female");
		bean.setMobileNo("7144232017");
		bean.setEmail("ruu@gmail.com");
		bean.setCollegeId(3);
		bean.setCreatedBy("admin@gmail.com");
		bean.setModifiedBy("admin@gmail.com");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		StudentModel model = new StudentModel();

		model.add(bean);
	}
	
	public static void testUpdate() throws Exception {
		StudentModel model = new StudentModel();
		StudentBean bean = model.findByPk(1);
		
		bean.setFirstName("gourav");
		model.update(bean);
		
	}
	
	public static void testFindByPk() throws Exception {
		
		StudentModel model = new StudentModel();
		StudentBean bean = model.findByPk(1);
		
		if (bean != null) {
			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getEmail());
			System.out.print("\t" + bean.getCollegeId());
			System.out.print("\t" + bean.getCollegeName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreateDateTime());
			System.out.println("\t" + bean.getModifiedDateTime());
		}
	}
		
		public static void testFindByEmail() throws Exception {
			
			StudentModel model = new StudentModel();
			StudentBean bean = model.findByEmail("ruu@gmail.com");
			
			if (bean != null) {
				System.out.print("\t" + bean.getId());
				System.out.print("\t" + bean.getFirstName());
				System.out.print("\t" + bean.getLastName());
				System.out.print("\t" + bean.getDob());
				System.out.print("\t" + bean.getGender());
				System.out.print("\t" + bean.getMobileNo());
				System.out.print("\t" + bean.getEmail());
				System.out.print("\t" + bean.getCollegeId());
				System.out.print("\t" + bean.getCollegeName());
				System.out.print("\t" + bean.getCreatedBy());
				System.out.print("\t" + bean.getModifiedBy());
				System.out.print("\t" + bean.getCreateDateTime());
				System.out.println("\t" + bean.getModifiedDateTime());
			}
		
	}
		
	public static void testDelete() throws Exception{
		StudentModel model = new StudentModel();
		model.delete(1);
	}
	
	public static void testSearch() throws Exception{
		StudentModel model = new StudentModel();
		StudentBean bean = new StudentBean();
		 List list = model.search(bean, 1,5);
		 
		 Iterator it = list.iterator();
		 while(it.hasNext()) {
			 bean = (StudentBean) it.next();
			 System.out.print("\t" + bean.getId());
				System.out.print("\t" + bean.getFirstName());
				System.out.print("\t" + bean.getLastName());
				System.out.print("\t" + bean.getDob());
				System.out.print("\t" + bean.getGender());
				System.out.print("\t" + bean.getMobileNo());
				System.out.print("\t" + bean.getEmail());
				System.out.print("\t" + bean.getCollegeId());
				System.out.print("\t" + bean.getCollegeName());
				System.out.print("\t" + bean.getCreatedBy());
				System.out.print("\t" + bean.getModifiedBy());
				System.out.print("\t" + bean.getCreateDateTime());
				System.out.println("\t" + bean.getModifiedDateTime());
		 }
		
	}
	
}
