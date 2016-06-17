package org.motechproject.OpenXCDataInterface.web;

import com.google.gson.Gson;
import org.motechproject.OpenXCDataInterface.domain.VehicleUploadRecord;
import org.motechproject.OpenXCDataInterface.service.VehicleDetailsRecordService;
import org.motechproject.OpenXCDataInterface.util.Constants;
import org.motechproject.OpenXCDataInterface.util.OpenXCDataInterfaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;


/**
 * Class Name : VehicleLocationWS
 * Purpose    : Controller Class for Vehicle Details Upload and fetching Vehicle Uploaded Details.
 */

@Controller
public class VehicleLocationWS {

    @Autowired
    private VehicleDetailsRecordService vehicleDetailsRecordService;


    /*
	 * Method Name : uploadmHealthVehicleData
	 * Purpose     : Method to upload a Vehicle's Data
	 */

    @RequestMapping(value = "/uploadmHealthVehicleData", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody String uploadmHealthVehicleData(InputStream incomingData) {

		String chkReturnResult = null;
		StringBuilder uploadData = new StringBuilder();
		String responseJson = null;
        Map map = new HashMap<String, String>();
        Gson gson = new Gson();
        String line = null;
        BufferedReader bufferedReader = null;
        VehicleUploadRecord vehicleUploadRecord = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(incomingData));
			while ((line = bufferedReader.readLine()) != null) {
				uploadData.append(line);
			}

            vehicleUploadRecord = gson.fromJson(uploadData.toString(), VehicleUploadRecord.class);

            if(vehicleUploadRecord.getVehicleId() != null && vehicleUploadRecord.getLatitude() != null  && vehicleUploadRecord.getLongitude() != null && vehicleUploadRecord.getTimeStamp() != null) {

                if(!vehicleUploadRecord.getLatitude().equalsIgnoreCase(Constants.STR_BLANK_STRING) && !vehicleUploadRecord.getLongitude().equalsIgnoreCase(Constants.STR_BLANK_STRING) && !vehicleUploadRecord.getTimeStamp().equalsIgnoreCase(Constants.STR_BLANK_STRING)) {

                    chkReturnResult = vehicleDetailsRecordService.uploadVehicleData(vehicleUploadRecord);

                    if (chkReturnResult != null && !chkReturnResult.equalsIgnoreCase(Constants.STR_NO)) {
                        map.put("responseMsg", chkReturnResult);
                        map.put("errorMsg", Constants.STR_BLANK_STRING);
                        responseJson = gson.toJson(map);
                    } else {
                        map.put("responseMsg", Constants.STR_NO);
                        map.put("errorMsg", Constants.STR_EXCEPTION_OCCURED);
                        responseJson = gson.toJson(map);
                    }
                }
                else{
                    map.put("responseMsg", Constants.STR_NO);
                    map.put("errorMsg", Constants.STR_INCORRECT_JSON);
                    responseJson =  gson.toJson(map);
                }

            }
            else{
                map.put("responseMsg", Constants.STR_NO);
                map.put("errorMsg", Constants.STR_INCORRECT_JSON);
                responseJson =  gson.toJson(map);
            }


		}
        catch (IOException E) {
            E.printStackTrace();
            map.put("responseMsg",Constants.STR_NO);
            map.put("errorMsg", Constants.STR_EXCEPTION_OCCURED);
            responseJson =  gson.toJson(map);
        }
		catch (OpenXCDataInterfaceException E) {
            E.printStackTrace();
            map.put("responseMsg",Constants.STR_NO);
            map.put("errorMsg", Constants.STR_EXCEPTION_OCCURED);
            responseJson =  gson.toJson(map);
		}

		return responseJson;
    }


    /*
	 * Method Name : getmHealthVehicleLocation
	 * Purpose     : Method to fetch a Vehicle's current location
	 */

    @RequestMapping(value = "/getmHealthVehicleLocation/{vehicleId}", produces = "application/json")
    public @ResponseBody String getmHealthVehicleLocation(@PathVariable String vehicleId) {

        Gson gson = new Gson();
        String responseJson = null;
        String msg = "";
        VehicleUploadRecord vehicleUploadRecord = null;

        try
        {

            if (vehicleDetailsRecordService != null && vehicleId != null && vehicleId.length() > 0) {
                vehicleUploadRecord = vehicleDetailsRecordService.getVehicleCurrentLocation(vehicleId);
            }

            if(vehicleUploadRecord!=null && (vehicleUploadRecord.getLatitude() != null || vehicleUploadRecord.getLongitude() != null))
            {
                responseJson = gson.toJson(vehicleUploadRecord);
            }
            else
            {
                msg = Constants.STR_VEHICLE_ID_NOT_FOUND;
                responseJson = gson.toJson(msg);
            }

        }
        catch (OpenXCDataInterfaceException E)
        {
            E.printStackTrace();
            msg = Constants.STR_EXCEPTION_MESSAGE;
            responseJson = gson.toJson(msg);

        }

        return responseJson;
    }


     /*
     * Method Name : getAllmHealthVehiclesLocation
     * Purpose     : Method to fetch the current location of all the Registered Vehicles
     */

    @RequestMapping(value = "/getAllmHealthVehiclesLocation", produces = "application/json")
    public @ResponseBody String getAllmHealthVehiclesLocation()
    {

        Gson gson = new Gson();
        String responseJson = null, msg = null;
        List<VehicleUploadRecord> lstUdb = null;

        try
        {
            if(vehicleDetailsRecordService != null)
            {
                lstUdb = (ArrayList<VehicleUploadRecord>)vehicleDetailsRecordService.getAllVehicleCurrentLocation();
            }

            if(lstUdb != null && lstUdb.size() > 0)
            {
                responseJson = gson.toJson(lstUdb);
            }
            else
            {
                msg = Constants.STR_NO_RECORD;
                responseJson = gson.toJson(msg);
            }
        }
        catch (OpenXCDataInterfaceException E)
        {
            E.printStackTrace();
            msg = Constants.STR_EXCEPTION_MESSAGE;
            responseJson = gson.toJson(msg);
        }

        return responseJson;

    }


    /*
	 * Method Name : getmHealthVehicleRoute
	 * Purpose     : Method to fetch the route traversed by a Vehicle
	 */

    @RequestMapping(value = "/getmHealthVehicleRoute/{vehicleId}", produces = "application/json")
	public @ResponseBody String getmHealthVehicleRoute(@PathVariable String vehicleId)
	{

		Gson gson = new Gson();
		String responseJson = null;
        String msg = null;
		List<VehicleUploadRecord> lstUdb = null;

		try
		{

			if(vehicleDetailsRecordService != null && vehicleId != null && vehicleId.length() > 0)
            {
                lstUdb = (ArrayList<VehicleUploadRecord>)vehicleDetailsRecordService.showRoute(vehicleId);
            }

			if(lstUdb != null && lstUdb.size() > 0)
			{
				responseJson = gson.toJson(lstUdb);
			}
			else
			{
                msg = Constants.STR_VEHICLE_ID_NOT_FOUND;
                responseJson = gson.toJson(msg);
			}

		}
		catch(OpenXCDataInterfaceException E)
		{
			E.printStackTrace();
			msg = Constants.STR_EXCEPTION_MESSAGE;
			responseJson = gson.toJson(msg);

		}

		return responseJson;
	}

}
