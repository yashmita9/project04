package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.MarksheetBean;
import in.co.rays.model.MarksheetModel;

public class TestMarksheet {

	public static void main(String[] args) throws Exception{

//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testsearch();
//		testDelete();
		testFindByRoll();
	}

	public static void testAdd() throws Exception{
		
		MarksheetBean bean = new MarksheetBean();
		bean.setRollNo(2502);
		bean.setStudentId(3);
		bean.setPhysics(79);
		bean.setChemistry(66);
		bean.setMaths(54);
		bean.setCreatedBy("admin@gmail.com");
		bean.setModifiedBy("admin@gmail.com");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		MarksheetModel model = new MarksheetModel();
		model.add(bean);
	}

	public static void testUpdate() throws Exception{
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = model.findByPk(1);
		
		bean.setRollNo(2501);
		
		model.update(bean);
	}

	public static void testFindByPk() throws Exception {
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = model.findByPk(1);
		
		if(bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}
		
	}

	public static void testsearch() throws Exception {
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = new MarksheetBean();
		
		List list = model.search(bean, 1, 3);
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			bean = (MarksheetBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}
	}
	
	public static void testDelete() throws Exception {
		
		MarksheetModel model = new MarksheetModel();
		
		model.delete(3);
	}
	
	public static void testFindByRoll() throws Exception {
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = model.findByRoll(2501);
		
		if(bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}
	
	}
}
