package in.co.rays.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.bean.RoleBean;
import in.co.rays.model.RoleModel;

public class TestRole {

	public static void main(String[] args) throws Exception {
		//testAdd();
		//testUpdate();
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
	
	
}
