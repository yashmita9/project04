package in.co.rays.test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.InitiativeBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;

import in.co.rays.model.InitiativeModel;

public class TestInitiative {

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testFindByType();
//		testDelete();
		testSearch();
	}
	
	public static void testAdd() throws ApplicationException, DuplicateRecordException {
		InitiativeBean bean = new InitiativeBean();
		InitiativeModel model = new InitiativeModel();

		bean.setName("Project Beta");
		bean.setType("Research");
		bean.setStartDate(new Date());
		bean.setVersionNo((1.1));
		
		model.add(bean);
	}
	
	public static void testUpdate() throws ApplicationException {
		InitiativeModel model = new InitiativeModel();
		InitiativeBean bean = model.findByPk(1);
		bean.setVersionNo(1.2);

		try {
			model.update(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() throws ApplicationException {
		InitiativeModel model = new InitiativeModel();
		InitiativeBean bean = model.findByPk(1);

		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getType());
			System.out.println(bean.getStartDate());
			System.out.println(bean.getVersionNo());
			
		}
	}
	
	public static void testFindByType() throws ApplicationException {
		InitiativeModel model = new InitiativeModel();
		InitiativeBean bean = model.findByName("Development");

		if (bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getType());
			System.out.println(bean.getStartDate());
			System.out.println(bean.getVersionNo());
			
		}

	}
	
	public static void testSearch() throws ApplicationException {
		InitiativeBean bean = new InitiativeBean();
		InitiativeModel model = new InitiativeModel();

//		bean.setName("Project Beta");
//		bean.setId(1);
		bean.setStartDate(new Date());
		List list = model.search(bean, 1, 5);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (InitiativeBean) it.next();

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getType());
			System.out.println(bean.getStartDate());
			System.out.println(bean.getVersionNo());
			}
	
	}
	
	public static void testDelete() throws ApplicationException {
		InitiativeModel model = new InitiativeModel();
		
		model.delete(0);
	}
}
