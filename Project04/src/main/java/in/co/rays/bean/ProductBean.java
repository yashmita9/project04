package in.co.rays.bean;

import java.util.Date;

public class ProductBean extends BaseBean {

	private String name;
	private String description;
	private double price;
	private Date dop;
	private String category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDop() {
		return dop;
	}

	public void setDop(Date dop) {
		this.dop = dop;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getKey() {
		
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return category;
	}

	@Override
	public String getvalue1() {
		// TODO Auto-generated method stub
		return null;
	}

}
