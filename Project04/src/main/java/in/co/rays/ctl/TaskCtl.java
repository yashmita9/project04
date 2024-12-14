package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.TaskBean;
import in.co.rays.bean.TaskBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.TaskModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "TaskCtl", urlPatterns = "/TaskCtl")
public class TaskCtl extends BaseCtl{

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("creationDate"))) {
			request.setAttribute("creationDate", PropertyReader.getValue("error.require", "Date of Creation"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("creationDate"))) {

			request.setAttribute("creationDate", PropertyReader.getValue("error.require", "Date Of creation"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("taskTitle"))) {
			request.setAttribute("taskTitle", PropertyReader.getValue("error.require", "Task Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("details"))) {
			request.setAttribute("details", PropertyReader.getValue("error.require", "Details"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assign"))) { // check first name is null or not
			request.setAttribute("assign", PropertyReader.getValue("error.require", " Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("assign"))) { // if first name is not null so check
																				// name is correct or not
			request.setAttribute("assign", "Invalid Name");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("taskStatus"))) {
			request.setAttribute("taskStatus", PropertyReader.getValue("error.require", "Task Status"));
			pass = false;
		}

		
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		TaskBean bean = new TaskBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCreationDate(DataUtility.getDate(request.getParameter("creationDate")));
		bean.setTaskTitle(DataUtility.getString(request.getParameter("taskTitle")));
		bean.setDetails(DataUtility.getString(request.getParameter("details")));
		bean.setAssign(DataUtility.getString(request.getParameter("assign")));
		bean.setTaskStatus(DataUtility.getString(request.getParameter("taskStatus")));
	
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			TaskModel model = new TaskModel();

			try {
				TaskBean bean = model.findByPk(id);
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

		TaskModel model = new TaskModel();
		TaskBean bean = (TaskBean) populateBean(request);
		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				model.add(bean);
				ServletUtility.setSuccessMessage("Task Added Successfully..!!", request);
				ServletUtility.forward(getView(), request, response);
			}  catch (ApplicationException | DuplicateRecordException e) {
				e.printStackTrace();
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			try {
				model.update(bean);
				TaskBean TaskBean = model.findByPk(bean.getId()); // Retrieve the updated data
				ServletUtility.setBean(TaskBean, request);
				ServletUtility.setSuccessMessage("Task updated successfully...!!!", request);
				ServletUtility.forward(getView(), request, response);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Task name already exist", request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TASK_CTL, request, response);
			return;
		}else if (OP_CANCLE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TASK_LIST_CTL, request, response);
			return;
		}
	}

	@Override
	protected String getView() {
		return ORSView.TASK_VIEW;
	}

	
}
