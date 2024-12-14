package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.ProductBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.bean.ShopBean;
import in.co.rays.bean.ShopBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.ProductModel;
import in.co.rays.model.RoleModel;
import in.co.rays.model.ShopModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "ShopCtl" , urlPatterns = "/ShopCtl")
public class ShopCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("productName"))) {
			request.setAttribute("productName", PropertyReader.getValue("error.require", "Product Name"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("shopDate"))) {
			request.setAttribute("shopDate", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("shopDate"))) {

			request.setAttribute("shopDate", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}


		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		}

		
		return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) {
	    ProductModel productModel = new ProductModel();
	    try {
	        List productList = productModel.list();
	        request.setAttribute("productList", productList);
	    } catch (ApplicationException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ShopBean bean = new ShopBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setShopDate(DataUtility.getDate(request.getParameter("shopDate")));
		bean.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
		bean.setProductId(DataUtility.getLong(request.getParameter("productId")));
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			ShopModel model = new ShopModel();

			try {
				ShopBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		ShopModel model = new ShopModel();
		ShopBean bean = (ShopBean) populateBean(request);
		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Shopping Cart Added Successfully..!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			try {
				model.update(bean);
				ShopBean ShopBean = model.findByPk(bean.getId()); // Retrieve the updated data
				ServletUtility.setBean(ShopBean, request);
				ServletUtility.setSuccessMessage("Shopping cart updated successfully...!!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SHOP_CTL, request, response);
			return;
		}else if (OP_CANCLE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SHOP_LIST_CTL, request, response);
			return;
		}
	}

	
	@Override
	protected String getView() {
		
		return ORSView.SHOP_VIEW;
	}

}
