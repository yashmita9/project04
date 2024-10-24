package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CollegeBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CollegeModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "CollegeListCtl", urlPatterns = "/CollegeListCtl")
public class CollegeListCtl extends BaseCtl{

	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CollegeBean bean = new CollegeBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("collegeId")));
		return bean;
	}
	
	@Override
	protected void preload(HttpServletRequest request) {
		CollegeModel collegeModel = new CollegeModel();
			try {
				List collegeList = collegeModel.list();
				request.setAttribute("collgegList", collegeList);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = 10;
		CollegeBean bean = null;
		CollegeModel model = new CollegeModel();
		
		try {
			List list = model.search(bean, pageNo, pageSize);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected String getView() {
		return ORSView.COLLEGE_LIST_VIEW;
	}

}
