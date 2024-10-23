package in.co.rays.util;

import java.util.ResourceBundle;

public class PropertyReader {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.bundle.system");
	
	public static String getValue(String key) {
		String val = null;
		try {
			val = rb.getString(key);
		}catch (Exception e) {
			val = key;
		}
		return val;
	}
	
	public static String getValue(String key, String param) {
		String msg = getValue(key); // {0} is required
		msg = msg.replace("{0}",param );// param is required
		return msg;
		
	}
	
	public static String getValue(String key , String[] param) {
		String msg = getValue(key);
		for (int i = 0; i < param.length; i++) {
			msg = msg.replace("{"+i+"}", param[i]);
		}
		return msg;
	}
	public static void main(String[] args) {
		
		System.out.println("Single key example:");
		System.out.println(PropertyReader.getValue("error.require"));
		
		System.out.println("\nSingle parameter replacement example:");
		System.out.println(PropertyReader.getValue("error.require", "loginId"));

		System.out.println("\nMultiple parameter replacement example:");
		String[] params = { "Roll No", "Student Name" };
		System.out.println(PropertyReader.getValue("error.multipleFields", params));
		
	}
	
}
