package in.co.rays.bean;

import java.util.Date;

public class InitiativeBean extends BaseBean{

	private String name;
	private String type;
	private Date startDate;
	private double versionNo;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Double getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Double versionNo) {
		this.versionNo = versionNo;
	}

	@Override
	public String getKey() {
		
		return id+"";
	}

	@Override
	public String getValue() {
		return type;
	}

	@Override
	public String getvalue1() {
		// TODO Auto-generated method stub
		return null;
	}

}
