package in.co.rays.bean;

import java.util.Date;

public class PatientBean extends BaseBean {

	private String name;
	private Date visitDate;
	private String mobile;
	private String decease;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDecease() {
		return decease;
	}

	public void setDecease(String decease) {
		this.decease = decease;
	}

	@Override
	public String getKey() {

		return id + "";
	}

	@Override
	public String getValue() {
		return decease;
	}

	@Override
	public String getvalue1() {
		// TODO Auto-generated method stub
		return null;
	}

}
