package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.TimeTableBean;
import in.co.rays.model.TimeTableModel;

public class TestTimeTable {

	public static void main(String[] args) throws Exception {
//		testAdd();
//		testDelete();
//		testUpdate();
//		testSearch();
		testFindByPk();
	}

	public static void testAdd() throws Exception{
		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		
		bean.setSemester("1st");
		bean.setDescription("description");
		bean.setExamDate(new Timestamp(new Date().getTime()));
		bean.setExamTime("08:00 AM to 11:00 AM");
		bean.setCourseId(1);
		bean.setSubjectId(2);
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
	}

	public static void testUpdate() throws Exception {
		
		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = model.findByPk(1);
		bean.setExamTime("07:00 AM to 10:00 AM");
		
		model.update(bean);
	}
	
	public static void testSearch() throws Exception {
		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = new TimeTableBean();
		
		List list = model.search(bean, 1, 2);
		Iterator it = list.iterator();
		while(it.hasNext()) {
			bean = (TimeTableBean) it.next();
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getSemester());
			System.out.print("\t"+bean.getDescription());
			System.out.print("\t"+bean.getExamDate());
			System.out.print("\t"+bean.getExamTime());
			System.out.print("\t"+bean.getCourseId());
			System.out.print("\t"+bean.getCourseName());
			System.out.print("\t"+bean.getSubjectId());
			System.out.print("\t"+bean.getSubjectName());
			System.out.print("\t"+bean.getCreatedBy());
			System.out.print("\t"+bean.getModifiedBy());
			System.out.print("\t"+bean.getCreateDateTime());
			System.out.println("\t"+bean.getModifiedDateTime());
		}
	}
	
	public static void testFindByPk() throws Exception {
		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = model.findByPk(1);
		
		if(bean != null) {
			System.out.print(bean.getId());
			System.out.print("\t"+bean.getSemester());
			System.out.print("\t"+bean.getDescription());
			System.out.print("\t"+bean.getExamDate());
			System.out.print("\t"+bean.getExamTime());
			System.out.print("\t"+bean.getCourseId());
			System.out.print("\t"+bean.getCourseName());
			System.out.print("\t"+bean.getSubjectId());
			System.out.print("\t"+bean.getSubjectName());
			System.out.print("\t"+bean.getCreatedBy());
			System.out.print("\t"+bean.getModifiedBy());
			System.out.print("\t"+bean.getCreateDateTime());
			System.out.println("\t"+bean.getModifiedDateTime());
		}
	}
	
//	public static void testFindByName() throws Exception {
//
//	}
//	
	public static void testDelete() throws Exception {
		TimeTableModel model = new TimeTableModel();
		model.delete(2);
	}
}
