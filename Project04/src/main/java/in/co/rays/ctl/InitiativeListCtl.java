package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.InitiativeBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.InitiativeModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "InitiativeListCtl", urlPatterns = "/InitiativeListCtl")
public class InitiativeListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		InitiativeModel model = new InitiativeModel();
		try {
			List initiativeList = model.list();
			request.setAttribute("initiativeList", initiativeList);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		InitiativeBean bean = new InitiativeBean();

		bean.setId(DataUtility.getLong(request.getParameter("initiativeId")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setType(DataUtility.getString(request.getParameter("type")));
		bean.setStartDate(DataUtility.getDate(request.getParameter("startDate")));
		bean.setVersionNo(DataUtility.getDouble(request.getParameter("versionNo")));
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;

		int pageNo = 1;
		int pageSize = 10;

		InitiativeBean bean = (InitiativeBean) populateBean(request);
		InitiativeModel model = new InitiativeModel();

		try {
			list = model.search(bean, pageNo, pageSize);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("operation"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		InitiativeBean bean = (InitiativeBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		InitiativeModel model = new InitiativeModel();
		try {
			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.INITIATIVE_CTL, request, response);
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
				ServletUtility.redirect(ORSView.INITIATIVE_LIST_CTL, request, response);
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
		return ORSView.INITIATIVE_LIST_VIEW;
	}

}
