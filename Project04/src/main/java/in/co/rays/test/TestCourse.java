package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.CourseBean;
import in.co.rays.model.CourseModel;

public class TestCourse {

	public static void main(String[] args) throws Exception {
//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testFindByName();
//		testSearch();
		testDelete();
	}

	public static void testAdd() throws Exception {
		CourseBean bean = new CourseBean();

		bean.setName("CA");
		bean.setDuration("3 year");
		bean.setDescription("Chartered Accountancy");
		bean.setCreatedBy("admin@gmail.com");
		bean.setModifiedBy("admin@gmail.com");
		bean.setCreateDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));

		CourseModel model = new CourseModel();
		model.add(bean);
	}

	public static void testUpdate() throws Exception {
		CourseModel model = new CourseModel();
		CourseBean bean = model.findByPk(3);
		bean.setDuration("2 year");

		model.update(bean);
	}

	public static void testFindByPk() throws Exception {

		CourseModel model = new CourseModel();
		CourseBean bean = model.findByPk(2);

		if (bean != null) {
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreateDateTime());
			System.out.println("\t" + bean.getModifiedDateTime());
		}
	}

	public static void testFindByName() throws Exception {
		CourseModel model = new CourseModel();
		CourseBean bean = model.findByName("MCA");

		if (bean != null) {
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreateDateTime());
			System.out.println("\t" + bean.getModifiedDateTime());
		}
	}

	public static void testSearch() throws Exception {
		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();

		List list = model.search(bean, 1, 2);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (CourseBean) it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreateDateTime());
			System.out.println("\t" + bean.getModifiedDateTime());
		}
	}

	public static void testDelete() throws Exception {
		CourseModel model = new CourseModel();
		
		model.delete(4);
	}

}
