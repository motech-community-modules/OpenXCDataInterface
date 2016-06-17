package org.motechproject.OpenXCDataInterface.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Class Name : DateTimeUtility
 * Purpose    : This class is used to convert any Long type value to SQL TimeStamp
 *
 */

public class DateTimeUtility {

	public static String convertUnixTimeStampToMySql(long timestamp)
	{
		Date date = new Date(timestamp*1000L); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate="";

	    try{
		    formattedDate = sdf.format(date);
	    }catch(Exception E)
	    {
		    E.printStackTrace();
	    }

		return formattedDate;
	}
	
}
