package in.co.rays.test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.ProductBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.ProductModel;

public class TestProduct {

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
//		testAdd();
//		testUpdate();
//		testFindByPk();
//		testFindByName();
//		testDelete();
		testSearch();
//		testList();
	}
	
	public static void testAdd() throws ApplicationException, DuplicateRecordException {
		ProductBean bean = new ProductBean();
		 
		bean.setName("Laptop");
		bean.setDescription("Waterproof smartwatch with health tracking features.");
		bean.setPrice(3000.00);
		bean.setDop(new Date());
		bean.setCategory("Wearables");
		
		ProductModel model = new ProductModel();
		model.add(bean);
	}
	
	public static void testUpdate() throws ApplicationException, DuplicateRecordException {
		ProductModel model = new ProductModel();
		ProductBean bean = model.findByPk(3);
		bean.setName("Laptop");
	
		model.update(bean);
	}
	
	public static void testFindByPk() throws ApplicationException {
		ProductModel model = new ProductModel();
		ProductBean bean = model.findByPk(1);
		
		if(bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getPrice());
			System.out.println(bean.getDop());
			System.out.println(bean.getCategory());
		}
	}
	
	public static void testFindByName() throws ApplicationException {
		ProductModel model = new ProductModel();
		ProductBean bean = model.findByName("Smartphone");
		
		if(bean != null) {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getPrice());
			System.out.println(bean.getDop());
			System.out.println(bean.getCategory());
		}
	}
	
	public static void testDelete() throws ApplicationException {
		ProductModel model = new ProductModel();
		model.delete(5);
	}
	
	public static void testSearch() throws ApplicationException {
		ProductBean bean = new ProductBean();
		ProductModel model = new ProductModel();
		bean.setId(2);
//		bean.setCategory("Wearables");
		List list = model.search(bean, 1, 3);
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			bean = (ProductBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getPrice());
			System.out.println(bean.getDop());
			System.out.println(bean.getCategory());
		}
	}
	
	public static void testList() throws ApplicationException {
		ProductBean bean = new ProductBean();
		ProductModel model = new ProductModel();
		bean.setId(2);
//		bean.setCategory("Wearables");
		List list = model.list();
		Iterator it = list.iterator();
		
		while(it.hasNext()) {
			bean = (ProductBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getPrice());
			System.out.println(bean.getDop());
			System.out.println(bean.getCategory());
		}
	}
	
}
