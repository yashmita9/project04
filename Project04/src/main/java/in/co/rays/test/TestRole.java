package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.RoleBean;
import in.co.rays.bean.UserBean;
import in.co.rays.model.RoleModel;
import in.co.rays.model.UserModel;

public class TestRole {

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testUpdate();
		// testFindByName();
		//testDelete();
		testSearch();
	}

	public static void testAdd() {
		RoleModel model = new RoleModel();
		RoleBean bean = new RoleBean();
		bean.setName("yashmita");
		bean.setDescription("Faculty");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
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
		RoleModel model = new RoleModel();
		RoleBean bean = model.findByPk(1);
		bean.setName("sweta");

		try {
			model.update(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByName() throws Exception {
		RoleModel model = new RoleModel();
		RoleBean bean = model.findByName("admin");
		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}

	}

	public static void testDelete() throws Exception {

		RoleModel model = new RoleModel();

		model.delete(3);

	}

	public static void testSearch() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		List list = model.search(bean, 1, 5);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (RoleBean) it.next();

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreateDateTime());
			System.out.println(bean.getModifiedDateTime());
		}
	}
}
