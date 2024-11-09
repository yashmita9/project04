package in.co.rays.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtility {

	public static final String APP_DATE_FORMAT = "yyyy-MM-dd";

	public static final String APP_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);

	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

	// getString method check value is null or not and value is not null so value ko
	// trim kar dengi......
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	// getStringData method is change any value to string format
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	// getInt method is change String into integer......
	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	public static long getLong(String val) {
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}
	
	public static Double getDouble(String val) {
		if (DataValidator.isDouble(val)) {
			return Double.parseDouble(val);
		} else {
			return (double) 0;
		}
	}

	// getDate method is change String value into Date format......
	public static Date getDate(String val) {
		Date date = null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return date;
	}

	// getDateDtring method is change Date format into String value.......
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
		}
		return "";
	}

//	public static Date getDate(Date date, int day) {
//		return null;
//	}

	public static Timestamp getTimeStamp(String val) {
		Timestamp timestamp = null;
		try {
			timestamp = new Timestamp((timeFormatter.parse(val).getTime()));
		} catch (Exception e) {
			return null;
		}

		return timestamp;
	}

	// getTimestamp method give Current Time Millis to Timestamp.....
	public static Timestamp getTimestamp(long l) {
		Timestamp timestamp = null;
		try {
			timestamp = new Timestamp(l);

		} catch (Exception e) {
			return null;
		}
		return timestamp;
	}

	// getCurrentTimestamp method give current time and date.......
	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
		}
		return timeStamp;

	}

	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	public static void main(String[] args) {

		// Test getString
		System.out.println("getString Test:");
		System.out.println("Original: '  Hello World  ' -> Trimmed: '" + getString("  Hello World  ") + "'");
		System.out.println("Null input: " + getString(null));

		// Test getStringData
		System.out.println("\ngetStringData Test:");
		System.out.println("Object to String: " + getStringData(1234));
		System.out.println("Null Object: '" + getStringData(null) + "'");

		// Test getInt
		System.out.println("\ngetInt Test:");
		System.out.println("Valid Integer String: '124' -> " + getInt("124"));
		System.out.println("Invalid Integer String: 'abc' -> " + getInt("abc"));
		System.out.println("Null String: -> " + getInt(null));

		// Test getLong
		System.out.println("\ngetLong Test:");
		System.out.println("Valid Long String: '123456789' -> " + getLong("123456789"));
		System.out.println("Invalid Long String: 'abc' -> " + getLong("abc"));

		// Test getDate
		System.out.println("\ngetDate Test:");
		String dateStr = "10/15/2024";
		Date date = getDate(dateStr);
		System.out.println("String to Date: '" + dateStr + "' -> " + date);

		// Test getDateString
		System.out.println("\ngetDateString Test:");
		System.out.println("Date to String: '" + getDateString(new Date()) + "'");

		// Test getTimestamp (String)
		System.out.println("\ngetTimestamp(String) Test:");
		String timestampStr = "10/15/2024 10:30:45";
		Timestamp timestamp = getTimeStamp(timestampStr);
		System.out.println("String to Timestamp: '" + timestampStr + "' -> " + timestamp);

		// Test getTimestamp (long)
		System.out.println("\ngetTimestamp(long) Test:");
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp ts = getTimestamp(currentTimeMillis);
		System.out.println("Current Time Millis to Timestamp: '" + currentTimeMillis + "' -> " + ts);

		// Test getCurrentTimestamp
		System.out.println("\ngetCurrentTimestamp Test:");
		Timestamp currentTimestamp = getCurrentTimestamp();
		System.out.println("Current Timestamp: " + currentTimestamp);

		// Test getTimestamp(Timestamp)
		System.out.println("\ngetTimestamp(Timestamp) Test:");
		System.out.println("Timestamp to long: " + getTimestamp(currentTimestamp));

	}
}
