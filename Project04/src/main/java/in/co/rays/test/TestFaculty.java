package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.bean.FacultyBean;
import in.co.rays.model.FacultyModel;

public class TestFaculty {

	public static void main(String[] args)throws Exception {
//		testAdd();
		testUpdate();
//		testDelete();
	}

	public static void testAdd() throws Exception {
		
		FacultyBean bean = new FacultyBean();
		
		bean.setFirst_name("shruti");
		bean.setLast_name("yadav");
		bean.setDob(new Timestamp(new Date().getTime()));
		bean.setGender("female");
		bean.setMobileNo("9988776655");
		bean.setEmail("shryti@gmail.com");
		bean.setCollegeId(1);
		bean.setCourseId(1);
		bean.setSubjectId(3);
		bean.setCreatedBy("admin@gmail.com");
		bean.setModifiedBy("admin@gmail.com");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		FacultyModel model = new FacultyModel();
		model.add(bean);
		
	}

	public static void testUpdate() throws Exception {
		FacultyModel model = new FacultyModel();
		FacultyBean bean = model.findByPk(1);
		
		bean.setFirst_name("pihu");
		bean.setEmail("shryti@gmail.com");
		
		model.update(bean);
	}

	public static void testDelete() throws Exception {
		
		FacultyModel model = new FacultyModel();
		model.delete(1);
		
	}
}
