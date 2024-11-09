package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.ProductBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CollegeModel;
import in.co.rays.model.ProductModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "ProductListCtl", urlPatterns = "/ProductListCtl")
public class ProductListCtl extends BaseCtl {

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ProductBean bean = new ProductBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("productId")));
		return bean;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List next = null;

		int pageNo = 1;
		int pageSize = 10;

		ProductBean bean = (ProductBean) populateBean(request);
		ProductModel model = new ProductModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextListSize", next.size());
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   List list = null;
		    List next = null;

		    int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		    int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		    pageNo = (pageNo == 0) ? 1 : pageNo;
		    pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		    ProductBean bean = (ProductBean) populateBean(request);
		    String op = DataUtility.getString(request.getParameter("operation"));
		    String[] ids = request.getParameterValues("ids");

		    ProductModel model = new ProductModel();
		    
			try {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.PRODUCT_CTL, request, response);
					return;
				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids != null && ids.length > 0) {
						for (String id : ids) {
							model.delete(DataUtility.getLong(id));
						}
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
					} else {
						ServletUtility.setErrorMessage("Select at least one record", request);
					}
				} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
					return;
				}

				ServletUtility.setBean(bean, request);
				
				list = model.search(bean, pageNo, pageSize);
				next = model.search(bean, pageNo + 1, pageSize);

				if (!OP_DELETE.equalsIgnoreCase(op) && (list == null || list.size() == 0)) {
					ServletUtility.setErrorMessage("No record found", request);
				}

				request.setAttribute("nextListSize", next.size());
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {
				e.printStackTrace();
			}
	}



	@Override
	protected String getView() {

		return ORSView.PRODUCT_LIST_VIEW;
	}

}