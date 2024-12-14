package in.co.rays.bean;

import java.util.Date;

public class TaskBean extends BaseBean {

	private Date creationDate;
	private String taskTitle;
	private String details;
	private String assign;
	private String taskStatus;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public String getKey() {
		
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return assign;
	}

	@Override
	public String getvalue1() {

		return taskStatus;
	}

}
