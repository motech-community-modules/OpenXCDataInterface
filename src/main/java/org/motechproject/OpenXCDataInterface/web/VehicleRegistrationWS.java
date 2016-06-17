package org.motechproject.OpenXCDataInterface.web;

import com.google.gson.Gson;
import org.motechproject.OpenXCDataInterface.domain.VehicleRegistrationRecord;
import org.motechproject.OpenXCDataInterface.service.VehicleDetailsRecordService;
import org.motechproject.OpenXCDataInterface.util.Constants;
import org.motechproject.OpenXCDataInterface.util.OpenXCDataInterfaceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Class Name : VehicleRegistrationWS
 * Purpose    : Controller Class for Vehicle Registration and fetching Vehicle Registration Details.
 */

@Controller
public class VehicleRegistrationWS {

    @Autowired
    private VehicleDetailsRecordService vehicleDetailsRecordService;


    /*
	 * Method Name : registermHealthVehicle
	 * Purpose     : Method to register a new vehicle
	 */

    @RequestMapping(value = "/registermHealthVehicle", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody String registermHealthVehicle(InputStream incomingData) {

		String chkReturnResult = null;
		StringBuilder registrationData = new StringBuilder();
        Map map = new HashMap<String, String>();
        Gson gson = new Gson();
        String responseJson = null;
        String line = null;
        BufferedReader bufferedReader = null;
        VehicleRegistrationRecord vehicleRegistrationRecord = null;

		try {

            bufferedReader = new BufferedReader(new InputStreamReader(incomingData));

            while ((line = bufferedReader.readLine()) != null) {
                registrationData.append(line);
            }

            vehicleRegistrationRecord = gson.fromJson(registrationData.toString(), VehicleRegistrationRecord.class);

            if(vehicleRegistrationRecord.getVehRegnNo() != null && vehicleRegistrationRecord.getVehChasisNo() != null && vehicleRegistrationRecord.getVehModel() != null && vehicleRegistrationRecord.getVehMake() != null) {

                if(!vehicleRegistrationRecord.getVehRegnNo().equalsIgnoreCase(Constants.STR_BLANK_STRING) && !vehicleRegistrationRecord.getVehChasisNo().equalsIgnoreCase(Constants.STR_BLANK_STRING) && !vehicleRegistrationRecord.getVehModel().equalsIgnoreCase(Constants.STR_BLANK_STRING) && !vehicleRegistrationRecord.getVehMake().equalsIgnoreCase(Constants.STR_BLANK_STRING)) {
                    chkReturnResult = vehicleDetailsRecordService.registerVehicle(vehicleRegistrationRecord);

                    if(chkReturnResult!=null)
                    {
                        if(chkReturnResult.contains("-"))
                        {
                            String arr[] = chkReturnResult.split("-");
                            map.put("vehicleId", arr[0]);
                            map.put("responseMsg",arr[1]);
                            map.put("errorMsg", "");
                            responseJson =  gson.toJson(map);
                        }
                    }
                    else
                    {
                        map.put("vehicleId", "");
                        map.put("responseMsg", Constants.STR_NO);
                        map.put("errorMsg", Constants.STR_EXCEPTION_OCCURED);
                        responseJson =  gson.toJson(map);
                    }

                }
                else{
                    map.put("vehicleId", "");
                    map.put("responseMsg", Constants.STR_NO);
                    map.put("errorMsg", Constants.STR_INCORRECT_JSON);
                    responseJson =  gson.toJson(map);
                }


            }
            else{
                map.put("vehicleId", "");
                map.put("responseMsg", Constants.STR_NO);
                map.put("errorMsg", Constants.STR_INCORRECT_JSON);
                responseJson =  gson.toJson(map);
            }

		}
        catch (IOException E)
        {
            E.printStackTrace();
            map.put("vehicleId", "");
            map.put("responseMsg",Constants.STR_NO);
            map.put("errorMsg", Constants.STR_EXCEPTION_OCCURED);
            responseJson =  gson.toJson(map);

        }
		catch (OpenXCDataInterfaceException E)
        {
            E.printStackTrace();
            map.put("vehicleId", "");
            map.put("responseMsg",Constants.STR_NO);
            map.put("errorMsg", Constants.STR_EXCEPTION_OCCURED);
            responseJson =  gson.toJson(map);

        }

		return responseJson;
	}


    /*
	 * Method Name : getRegisteredmHealthVehicleList
	 * Purpose     : Method to fetch the details of all Registered Vehicles
	 */

    @RequestMapping(value = "/getRegisteredmHealthVehicleList", produces = "application/json")
	public @ResponseBody String getRegisteredmHealthVehicleList() {

		Gson gson = new Gson();
		String msg = null;
		String responseJson = null;
		List<VehicleRegistrationRecord> lstVehReg = null;

		try
		{
			if(vehicleDetailsRecordService != null)
            {
				lstVehReg = (ArrayList<VehicleRegistrationRecord>)vehicleDetailsRecordService.getRegisteredVehicleList();
            }

			if(lstVehReg != null && lstVehReg.size()>0)
			{
				responseJson = gson.toJson(lstVehReg);
			}
			else
			{
                msg = Constants.STR_NO_RECORD;
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


    /*
	 * Method Name : getmHealthVehicleId
	 * Purpose     : Method to fetch the vehicle ID of a Vehicle on basis of Vehicle Registration No.
	 */

    @RequestMapping(value = "/getmHealthVehicleId/{vehRegnNo}", produces = "application/json")
	public @ResponseBody String getmHealthVehicleId(@PathVariable String vehRegnNo) {

        Gson gson = new Gson();
        Map map = new HashMap<String,String>();
        String responseJson = null;
        Integer vehicleId = null;
        String msg = null;

		try
		{
            if (vehicleDetailsRecordService != null && vehRegnNo != null && vehRegnNo.length() > 0) {
                 vehicleId = vehicleDetailsRecordService.getVehicleId(vehRegnNo);

            }

			if(vehicleId!=null && vehicleId.intValue()!=0)
			{
                map.put("vehicleId",vehicleId);
                responseJson =  gson.toJson(map);
			}
            else if(vehicleId!=null && vehicleId.intValue()==0)
            {
                msg = Constants.STR_REGSTRTN_NOT_FOUND;
                responseJson =  gson.toJson(msg);
            }
			else
			{
                msg = Constants.STR_EXCEPTION_OCCURED;
                responseJson =  gson.toJson(msg);
			}
		}
		catch (OpenXCDataInterfaceException E)
		{
            E.printStackTrace();
            msg = Constants.STR_EXCEPTION_OCCURED;
            responseJson =  gson.toJson(msg);

		}

		return responseJson;
	}


}
