package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.SubjectBean;
import in.co.rays.model.SubjectModel;

public class TestSubject {

	public static void main(String[] args) throws Exception{
//		testAdd();
//		testUpdate();
//		testDelete();
//		testSearch();
//		testFindByName();
		testFindByPk();
		
	}
	
	public static void testAdd() throws Exception {
		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();
		
		bean.setName("Core Java");
		bean.setCourseId(2);
		bean.setDescription("Core Java");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
	}
	
	public static void testUpdate() throws Exception {
		SubjectModel model = new SubjectModel();
		
		SubjectBean bean = model.findByPk(3);
		bean.setName("MIS");
		bean.setDescription("MIS");
		model.update(bean);
	}
	
	public static void testDelete() throws Exception {
		SubjectModel model = new SubjectModel();
		model.delete(1);
	}
	
	public static void testSearch() throws Exception {
		
		SubjectModel model = new SubjectModel();
		SubjectBean bean = new SubjectBean();
//		bean.setName("Py");
		List list = model.search(bean, 1, 3);
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			bean = (SubjectBean) it.next();
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getName());
			System.out.print("\t"+bean.getCourseId());
			System.out.print("\t"+bean.getCourseName());
			System.out.print("\t"+bean.getDescription());
			System.out.print("\t"+bean.getCreatedBy());
			System.out.print("\t"+bean.getModifiedBy());
			System.out.print("\t"+bean.getCreateDateTime());
			System.out.println("\t"+bean.getModifiedDateTime());
		}
	}
	
	public static void testFindByName() throws Exception {
		
		SubjectModel model = new SubjectModel();
		SubjectBean bean = model.findByName("python");
		
		if(bean != null) {
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getName());
			System.out.print("\t"+bean.getCourseId());
			System.out.print("\t"+bean.getCourseName());
			System.out.print("\t"+bean.getDescription());
			System.out.print("\t"+bean.getCreatedBy());
			System.out.print("\t"+bean.getModifiedBy());
			System.out.print("\t"+bean.getCreateDateTime());
			System.out.println("\t"+bean.getModifiedDateTime());
		}
	}
	
	public static void testFindByPk() throws Exception {
		SubjectModel model = new SubjectModel();
		SubjectBean bean = model.findByPk(2);
		
		if(bean != null) {
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getName());
			System.out.print("\t"+bean.getCourseId());
			System.out.print("\t"+bean.getCourseName());
			System.out.print("\t"+bean.getDescription());
			System.out.print("\t"+bean.getCreatedBy());
			System.out.print("\t"+bean.getModifiedBy());
			System.out.print("\t"+bean.getCreateDateTime());
			System.out.println("\t"+bean.getModifiedDateTime());
		}
	}
	
}
