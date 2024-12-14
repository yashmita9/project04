package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.InitiativeBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.InitiativeModel;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "InitiativeCtl",urlPatterns = "/InitiativeCtl")
public class InitiativeCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) { 
			request.setAttribute("name", PropertyReader.getValue("error.require", "Initiative Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) { 
			request.setAttribute("name", "Invalid Initiative Name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "Type"));
			pass = false;
		}			
		if (DataValidator.isNull(request.getParameter("startDate"))) {
			request.setAttribute("startDate", PropertyReader.getValue("error.require", "Start Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("startDate"))) {

			request.setAttribute("dob", PropertyReader.getValue("error.require", "Start Date"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("versionNo"))) {
			request.setAttribute("versionNo", PropertyReader.getValue("error.require", "version Number"));
			pass = false;
		}
		return pass;
		
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		InitiativeBean bean = new InitiativeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setType(DataUtility.getString(request.getParameter("type")));
		bean.setStartDate(DataUtility.getDate(request.getParameter("startDate")));
		bean.setVersionNo(DataUtility.getDouble(request.getParameter("versionNo")));
		
		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		InitiativeModel model = new InitiativeModel();
		InitiativeBean bean = (InitiativeBean) populateBean(request);
		
		if (OP_SAVE.equalsIgnoreCase(op)) {
			
			try {
				model.add(bean);
			
				ServletUtility.setSuccessMessage("Initiative Added successfully..!!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("type already exist...!!!", request);
				ServletUtility.forward(getView(), request, response);
			}
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INITIATIVE_CTL, request, response);
			return;
		}

	}
	
	@Override
	protected String getView() {
		
		return ORSView.INITIATIVE_VIEW;
	}

}
