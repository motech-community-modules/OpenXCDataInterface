package org.motechproject.OpenXCDataInterface.repository.impl;


import org.motechproject.OpenXCDataInterface.bean.ApplicationSettings;
import org.motechproject.OpenXCDataInterface.domain.VehicleRegistrationRecord;
import org.motechproject.OpenXCDataInterface.domain.VehicleUploadRecord;
import org.motechproject.OpenXCDataInterface.repository.VehicleDetailsRecordDao;
import org.motechproject.OpenXCDataInterface.util.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * Class Name : VehicleDetailsRecordDaoImpl
 * Purpose    : DAO Implementation Class. Here only all database operations are performed.
 */

public class VehicleDetailsRecordDaoImpl implements VehicleDetailsRecordDao {
    private ApplicationSettings applicationSettings;

    JdbcTemplate jdbcTemplate;

    public VehicleDetailsRecordDaoImpl(ApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
        jdbcTemplate = DatabaseConnection.getJdbcTemplate(applicationSettings);
    }

    /*
	 * Method Name : registerVehicle
	 * Purpose     : DAO Method to insert a Vehicle's Registration Data
	 * Arguments   : VehicleRegistrationRecord
	 * Returns     : String
	 */

    public String registerVehicle(VehicleRegistrationRecord vehicleRegistrationRecord) throws OpenXCDataInterfaceException{

        String flag = Constants.STR_NO;
        Integer vehicleId = new Integer(Constants.STR_VEHICLE_ID_START_STRING);
        Integer vehicleId2 = new Integer(Constants.STR_VEHICLE_ID_START_STRING);
        Integer id = new Integer(Constants.STR_ZERO);
        String key = null;
        Object value = null;
        Map map = new HashMap();
        String query1 = null;
        String query2 = null;
        String query3 = null;
        int count = 0;

        try {

            /* Before Insertion, checking if Vehicle Registration No already Exists or Not */
            query1 = Constants.query_for_getVehicleId + vehicleRegistrationRecord.getVehRegnNo();
            query1 = query1 + "' ORDER BY id DESC";

            List<Map<String, Object>> query1List = jdbcTemplate.queryForList(query1);

            if (query1List != null && !query1List.isEmpty()) {

                map = query1List.get(0);
                for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Object> entry = it.next();
                    key = entry.getKey();
                    value = entry.getValue();
                    if (key.equalsIgnoreCase("vehicleId")) {
                        if (value != null) {
                            vehicleId = new Integer(value.toString());
                        }
                    }
                }

            }

            if (vehicleId.intValue() != vehicleId2.intValue()) {
                flag = Constants.STR_EXISTS;

            } else {

                /* Before Insertion, Fetching the maximum Vehicle Id and Id present in database */
                query2 = Constants.query2_for_registerVehicle;

                List<Map<String, Object>> query2List = jdbcTemplate.queryForList(query2);

                if (query2List != null && !query2List.isEmpty()) {

                    map = query2List.get(0);

                    for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext();) {
                        Map.Entry<String, Object> entry = it.next();
                        key = entry.getKey();
                        value = entry.getValue();
                        if (key.equalsIgnoreCase("vehicleId")) {
                            if (value != null) {
                                vehicleId = new Integer(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("id")) {
                            if (value != null) {
                                id = new Integer(value.toString());
                            }
                        }
                    }

                }

                if(vehicleId == null){
                    vehicleId = new Integer(Constants.STR_VEHICLE_ID_START_STRING);
                }

                if(id == null){
                    id = new Integer(Constants.STR_ZERO);
                }

                /* Inserting the Registration Details */
                query3 = Constants.query3_for_registerVehicle;
                vehicleId = vehicleId+1;
                count = jdbcTemplate.update(query3, new Object[]{id+1, vehicleId, vehicleRegistrationRecord.getVehRegnNo(), vehicleRegistrationRecord.getVehChasisNo(), vehicleRegistrationRecord.getVehMake(), vehicleRegistrationRecord.getVehModel(), vehicleRegistrationRecord.getContactNo(), vehicleRegistrationRecord.getEmailId()});

                if (count > 0) {
                    flag = Constants.STR_YES;
                }

            }

            return vehicleId.toString() + "-" + flag;
        }
        catch(Exception E)
        {
            throw new OpenXCDataInterfaceException(E.getMessage(), E);
        }

    }


    /*
	 * Method Name : uploadVehicleData
	 * Purpose     : DAO Method to insert a Vehicle's Registration Data
	 * Arguments   : VehicleUploadRecord
	 * Returns     : String
	 */

    public String uploadVehicleData(VehicleUploadRecord vehicleUploadRecord) throws OpenXCDataInterfaceException{

        String address = Constants.STR_BLANK_STRING;
        String flag = Constants.STR_NO;
        Integer id = new Integer(Constants.STR_ZERO);
        Integer vehicleId_count = new Integer(Constants.STR_ZERO);
        String key = null;
        Object value = null;
        Map map = new HashMap();
        int count1 = 0;
        int count2 = 0;
        String query1 = null;
        String query2 = null;
        String query3 = null;
        String query4 = null;
        String query5 = null;

        try {

            double doubleTimeStamp = Double.parseDouble(vehicleUploadRecord.getTimeStamp());
            long lTimestamp = (long) doubleTimeStamp;

            /*Converting the String Timestamp to MySQL Timestamp */
            String timeStamp = DateTimeUtility
                    .convertUnixTimeStampToMySql(lTimestamp);

            /*Fetching the address using Google API from Latitude and Longitude */
            if (vehicleUploadRecord.getLatitude() == null
                    || vehicleUploadRecord.getLongitude() == null) {
                address = "NA";
            } else if (vehicleUploadRecord.getLatitude().equalsIgnoreCase("")
                    || vehicleUploadRecord.getLongitude().equalsIgnoreCase("")) {

                address = "NA";
            } else {
                address = GeoCodingUtility.getAddress(vehicleUploadRecord.getLatitude(),
                        vehicleUploadRecord.getLongitude());
            }

            /*Before Upload, checking if the Vehicle Id exists in the database or not */
            query1 = Constants.query1_for_uploadVehicleData + vehicleUploadRecord.getVehicleId();

            List<Map<String, Object>> query1List = jdbcTemplate.queryForList(query1);

            if(query1List != null) {

                if (query1List.size() == 0) {
                    flag = Constants.STR_NOT_EXISTS;

                } else {

                    /*Before Upload, fetching the maximum Id present in the database */
                    query2 = Constants.query2_for_uploadVehicleData;

                    List<Map<String, Object>> query2List = jdbcTemplate.queryForList(query2);

                    if (query2List != null && !query2List.isEmpty()) {

                        map = query2List.get(0);

                        for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry<String, Object> entry = it.next();

                            key = entry.getKey();
                            value = entry.getValue();

                            if (key.equalsIgnoreCase("id")) {
                                if (value != null) {
                                    id = new Integer(value.toString());
                                }
                            }
                        }
                    }

                    if (id == null) {
                        id = new Integer(Constants.STR_ZERO);
                    }

                    /* Performing Insertion Operation */
                    query3 = Constants.query3_for_uploadVehicleData;
                    count1 = jdbcTemplate.update(query3, new Object[]{id + 1, address, vehicleUploadRecord.getLatitude(), vehicleUploadRecord.getLongitude(), timeStamp, vehicleUploadRecord.getVehSpeed(), vehicleUploadRecord.getVehicleId()});


                    /* After Insertion Operation, getting the number of record present in the database for that particular Vehicle Id */
                    query4 = Constants.query4_for_uploadVehicleData + vehicleUploadRecord.getVehicleId();

                    List<Map<String, Object>> query4List = jdbcTemplate.queryForList(query4);

                    if (query4List != null && !query4List.isEmpty()) {

                        map = query4List.get(0);

                        for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry<String, Object> entry = it.next();

                            key = entry.getKey();
                            value = entry.getValue();

                            if (key.equalsIgnoreCase("vehicleId_count")) {
                                if (value != null) {
                                    vehicleId_count = new Integer(value.toString());
                                }
                            }


                        }

                    }

                    if (vehicleId_count == null) {
                        vehicleId_count = new Integer(Constants.STR_ZERO);
                    }

                    /* After Insertion , if the number of record for that Vehicle Id exceeds 120, then deleting the older record */
                    if (vehicleId_count.intValue() > Constants.MAX_RECORD_FOR_A_VEHICLE) {

                        query5 = Constants.query5_for_uploadVehicleData + vehicleUploadRecord.getVehicleId();
                        query5 = query5 + " ORDER BY id LIMIT " + (vehicleId_count.intValue() - Constants.MAX_RECORD_FOR_A_VEHICLE);
                        count2 = jdbcTemplate.update(query5);

                    }

                    if (count1 > 0) {
                        flag = Constants.STR_YES;
                    }

                }
            }
            else{
                flag = Constants.STR_NOT_EXISTS;
            }

        }
        catch(Exception E){
            E.printStackTrace();
            flag = null;
        }

        return flag;

    }


    /*
	 * Method Name : getRegisteredVehicleList
	 * Purpose     : DAO Method to fetch the details of all Registered Vehicles
	 * Arguments   : None
	 * Returns     : List
	 */

    public List<VehicleRegistrationRecord> getRegisteredVehicleList() throws OpenXCDataInterfaceException {

        String query = Constants.query_for_getRegisteredVehicleList;
        List<VehicleRegistrationRecord> vehicleUploadRecordList = null;

        try {

            vehicleUploadRecordList = new ArrayList<VehicleRegistrationRecord>();

            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(query);

            if (queryList != null && !queryList.isEmpty()) {
                for (Map<String, Object> vehicleUploadMap : queryList) {
                    VehicleRegistrationRecord vehicleRegistrationRecord = new VehicleRegistrationRecord();

                    for (Iterator<Map.Entry<String, Object>> it = vehicleUploadMap.entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry<String, Object> entry = it.next();

                        String key = entry.getKey();
                        Object value = entry.getValue();

                        if (key.equalsIgnoreCase("vehicleId")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setVehicleId(new Integer(value.toString()));
                            }
                        }
                        if (key.equalsIgnoreCase("vehRegnNo")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setVehRegnNo(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("vehChasisNo")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setVehChasisNo(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("vehMake")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setVehMake(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("vehModel")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setVehModel(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("contactNo")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setContactNo(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("emailId")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setEmailId(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("registeredDate")) {
                            if (value != null) {
                                vehicleRegistrationRecord.setRegisteredOn(value.toString());
                            }
                        }

                    }
                    vehicleUploadRecordList.add(vehicleRegistrationRecord);
                }
            }
        }
        catch(Exception E){
            throw new OpenXCDataInterfaceException(E.getMessage(), E);
        }

        return vehicleUploadRecordList;

    }


    /*
	 * Method Name : getAllVehicleCurrentLocation
	 * Purpose     : DAO Method to fetch the current location of all the Registered Vehicles
	 * Arguments   : None
	 * Returns     : List
	 */

    public List<VehicleUploadRecord> getAllVehicleCurrentLocation() throws OpenXCDataInterfaceException {

        String query = null;
        List<VehicleUploadRecord> vehicleUploadRecordList = null;

        try {

            vehicleUploadRecordList = new ArrayList<VehicleUploadRecord>();
            query = Constants.query_for_getAllVehicleCurrentLocation;
            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(query);

            if (queryList != null && !queryList.isEmpty()) {
                for (Map<String, Object> vehicleUploadMap : queryList) {
                    VehicleUploadRecord vehicleUploadRecord = new VehicleUploadRecord();

                    for (Iterator<Map.Entry<String, Object>> it = vehicleUploadMap.entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry<String, Object> entry = it.next();

                        String key = entry.getKey();
                        Object value = entry.getValue();

                        if (key.equalsIgnoreCase("vehicleId")) {
                            if (value != null) {
                                vehicleUploadRecord.setVehicleId(new Integer(value.toString()));
                            }
                        }
                        if (key.equalsIgnoreCase("latitude")) {
                            if (value != null) {
                                vehicleUploadRecord.setLatitude(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("longitude")) {
                            if (value != null) {
                                vehicleUploadRecord.setLongitude(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("address")) {
                            if (value != null) {
                                vehicleUploadRecord.setAddress(value.toString());
                            }
                        }

                    }
                    vehicleUploadRecordList.add(vehicleUploadRecord);
                }
            }
        }
        catch(Exception E){
            throw new OpenXCDataInterfaceException(E.getMessage(), E);
        }

        return vehicleUploadRecordList;

    }


    /*
	 * Method Name : getVehicleCurrentLocation
	 * Purpose     : DAO Method to fetch a Vehicle's current location
	 * Arguments   : String - Vehicle Id
	 * Returns     : VehicleUploadRecord
	 */

    public VehicleUploadRecord getVehicleCurrentLocation(String vehicleId) throws OpenXCDataInterfaceException {

        String query = null;
        VehicleUploadRecord vehicleUploadRecord = null;

        try {

            vehicleUploadRecord = new VehicleUploadRecord();
            query = Constants.query_for_getVehicleCurrentLocation + vehicleId;
            query = query + " ORDER BY id DESC LIMIT 1";

            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(query);

            if (queryList != null && !queryList.isEmpty()) {
                for (Map<String, Object> vehicleUploadMap : queryList) {

                    for (Iterator<Map.Entry<String, Object>> it = vehicleUploadMap.entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry<String, Object> entry = it.next();

                        String key = entry.getKey();
                        Object value = entry.getValue();

                        if (key.equalsIgnoreCase("vehicleId")) {
                            if (value != null) {
                                vehicleUploadRecord.setVehicleId(new Integer(value.toString()));
                            }
                        }
                        if (key.equalsIgnoreCase("latitude")) {
                            if (value != null) {
                                vehicleUploadRecord.setLatitude(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("longitude")) {
                            if (value != null) {
                                vehicleUploadRecord.setLongitude(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("address")) {
                            if (value != null) {
                                vehicleUploadRecord.setAddress(value.toString());
                            }
                        }

                    }
                }
            }
        }
        catch(Exception E){
            throw new OpenXCDataInterfaceException(E.getMessage(), E);
        }
        return vehicleUploadRecord;

    }


    /*
	 * Method Name : showRoute
	 * Purpose     : DAO Method to fetch the route traversed by a Vehicle
	 * Arguments   : String - Vehicle Id
	 * Returns     : List
	 */

    public List<VehicleUploadRecord> showRoute(String vehicleId) throws OpenXCDataInterfaceException {

        String query = null;
        List<VehicleUploadRecord> vehicleUploadRecordList = null;

        try {

            vehicleUploadRecordList = new ArrayList<VehicleUploadRecord>();
            query = Constants.query_for_showRoute + vehicleId;
            query = query + " ORDER BY timeStamp desc";

            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(query);

            if (queryList != null && !queryList.isEmpty()) {
                for (Map<String, Object> vehicleUploadMap : queryList) {
                    VehicleUploadRecord vehicleUploadRecord = new VehicleUploadRecord();

                    for (Iterator<Map.Entry<String, Object>> it = vehicleUploadMap.entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry<String, Object> entry = it.next();

                        String key = entry.getKey();
                        Object value = entry.getValue();

                        if (key.equalsIgnoreCase("vehicleId")) {
                            if (value != null) {
                                vehicleUploadRecord.setVehicleId(new Integer(value.toString()));
                            }
                        }
                        if (key.equalsIgnoreCase("latitude")) {
                            if (value != null) {
                                vehicleUploadRecord.setLatitude(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("longitude")) {
                            if (value != null) {
                                vehicleUploadRecord.setLongitude(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("address")) {
                            if (value != null) {
                                vehicleUploadRecord.setAddress(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("timeStamp")) {
                            if (value != null) {
                                vehicleUploadRecord.setTimeStamp(value.toString());
                            }
                        }
                        if (key.equalsIgnoreCase("vehSpeed")) {
                            if (value != null) {
                                vehicleUploadRecord.setVehSpeed(value.toString());
                            }
                        }

                    }
                    vehicleUploadRecordList.add(vehicleUploadRecord);
                }
            }
        }
        catch(Exception E){
            throw new OpenXCDataInterfaceException(E.getMessage(), E);
        }
        return vehicleUploadRecordList;


    }


    /*
	 * Method Name : getVehicleId
	 * Purpose     : DAO Method to fetch the Vehicle Id on the basis of a Vehicle's Registration Data
	 * Arguments   : String - Vehicle Registration No
	 * Returns     : Integer - Vehicle Id
	 */

    public Integer getVehicleId(String vehRegnNo) throws OpenXCDataInterfaceException {

        Integer vehicleId = null;
        String query = null;
        Map map = new HashMap();
        Object value = null;

        try {

            query = Constants.query_for_getVehicleId + vehRegnNo;
            query = query + "' ORDER BY id DESC";

            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(query);

            if (queryList != null && !queryList.isEmpty()) {

                map = queryList.get(0);

                for (Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Object> entry = it.next();

                    String key = entry.getKey();
                    if (key.equalsIgnoreCase("vehicleId")) {
                        value = entry.getValue();
                    }
                }

            }
            if(value!=null && !value.toString().equalsIgnoreCase(Constants.STR_BLANK_STRING)) {
                vehicleId = new Integer(value.toString());
            }
            else{
                vehicleId = new Integer(Constants.STR_ZERO);
            }
        }
        catch(Exception E){
            throw new OpenXCDataInterfaceException(E.getMessage(), E);

        }

        return vehicleId;

    }


}
