package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.ProductBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.ProductModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "ProductCtl" , urlPatterns = "/ProductCtl")
public class ProductCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass= false;
		}else if(!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass= false;
		} 	
		if(DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "Price"));
			pass= false;
		}
		if(DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "Category"));
			pass= false;
		}
		if (DataValidator.isNull(request.getParameter("dop"))) {
			request.setAttribute("dop", PropertyReader.getValue("error.require", "Date Of Purchase"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dop"))) {

			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Purchase"));
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ProductBean bean = new ProductBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setPrice(DataUtility.getDouble(request.getParameter("price")));
		bean.setDop(DataUtility.getDate(request.getParameter("dop")));
		bean.setCategory(DataUtility.getString(request.getParameter("category")));
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));
		
		if(id>0) {
			ProductModel model = new ProductModel();
			
			try {
				ProductBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ServletUtility.forward(getView(), request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		ProductModel model = new ProductModel();
		ProductBean bean = (ProductBean) populateBean(request);
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Product Added Successfully....!!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Product Name Is Duplicate...!!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if(OP_UPDATE.equalsIgnoreCase(op)) {
			try {
				model.update(bean);
				ProductBean productBean = model.findByPk(bean.getId()); 
				ServletUtility.setBean(productBean, request);
				ServletUtility.setSuccessMessage("Product updated Successfully....!!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Product Name Is Duplicate...!!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRODUCT_CTL, request, response);
			return;
		}else if (OP_CANCLE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		return ORSView.PRODUCT_VIEW;
	}

}
