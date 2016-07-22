package org.motechproject.OpenXCDataInterface.util;


/**
 * Class Name : Constants
 * Purpose    : Custom class for all the constants used.
 */

public class Constants {
    /*
        Keys for getting database connection properties.
     */
    public static final String DATABASE_URL_KEY = "OpenXCDataInterface.dbUrl";
    public static final String DATABASE_DRIVER_KEY = "OpenXCDataInterface.dbDriver";
    public static final String DATABASE_USERNAME_KEY = "OpenXCDataInterface.dbUsername";
    public static final String DATABASE_PASSWORD_KEY = "OpenXCDataInterface.dbPassword";

//
//    /*Constants for getting database connection */
//    public static final String dbUrl = "jdbc:mysql://localhost:3306/motechdata";
//    public static final String dbDriver = "com.mysql.jdbc.Driver";
//    public static final String dbUsername = "root";
//    public static final String dbPassword = "hcl123";


    /*All the queries used for fetching or inserting record in the database */
    public static final String query_for_getAllVehicleCurrentLocation = "SELECT vehicleId, latitude, longitude, address, id FROM OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD\n" +
            "WHERE id IN (SELECT MAX( id ) FROM OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD GROUP BY vehicleId)";

    public static final String query_for_getRegisteredVehicleList = "SELECT vehicleId, vehRegnNo, vehChasisNo, vehMake, vehModel, contactNo, emailId, DATE_FORMAT( registeredOn, '%d/%m/%Y' ) as registeredDate FROM OPENXCDATAINTERFACE_VEHICLEREGISTRATIONRECORD";

    public static final String query_for_getVehicleCurrentLocation = "SELECT latitude,longitude,address FROM OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD WHERE vehicleId = ";

    public static final String query_for_getVehicleId = "SELECT vehicleId FROM OPENXCDATAINTERFACE_VEHICLEREGISTRATIONRECORD WHERE vehRegnNo = '";

    public static final String query_for_showRoute = "SELECT vehicleId,latitude,longitude,address,timeStamp,vehSpeed FROM OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD WHERE vehicleId = ";

    public static final String query2_for_registerVehicle = "SELECT max( id ) AS id, max( vehicleId ) AS vehicleId FROM OPENXCDATAINTERFACE_VEHICLEREGISTRATIONRECORD";

    public static final String query3_for_registerVehicle = "insert into OPENXCDATAINTERFACE_VEHICLEREGISTRATIONRECORD(id, creationDate, creator, modificationDate, modifiedBy, owner, vehicleId, vehRegnNo,vehChasisNo,vehMake,vehModel,contactNo,emailId,registeredOn) values(?,now(),'admin',now(),'admin','admin',?,?,?,?,?,?,?,now())";

    public static final String query1_for_uploadVehicleData = "select vehicleId from OPENXCDATAINTERFACE_VEHICLEREGISTRATIONRECORD where vehicleId = ";

    public static final String query2_for_uploadVehicleData = "SELECT max( id ) AS id FROM OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD";

    public static final String query3_for_uploadVehicleData = "INSERT INTO OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD(id, address, creationDate, creator, latitude, longitude, modificationDate, modifiedBy, owner, timeStamp, vehSpeed, vehicleId) VALUES (?,?,now(),'admin',?,?,now(),'admin','admin',?,?,?)";

    public static final String query4_for_uploadVehicleData =  "SELECT COUNT( vehicleId ) AS vehicleId_count FROM OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD WHERE vehicleId = ";

    public static final String query5_for_uploadVehicleData =  "DELETE FROM OPENXCDATAINTERFACE_VEHICLEUPLOADRECORD WHERE vehicleId = ";


    public static final int MAX_RECORD_FOR_A_VEHICLE = 120;

    /* All the String constants used all over the codes */
    public static final String STR_BLANK_STRING = "";
    public static final String STR_ZERO = "0";
    public static final String STR_YES = "Y";
    public static final String STR_NO = "N";
    public static final String STR_EXISTS = "E";
    public static final String STR_NOT_EXISTS = "NE";
    public static final String STR_EXCEPTION_OCCURED = "Exception Occurred";
    public static final String STR_NO_RECORD = "No Records Found";
    public static final String STR_EXCEPTION_MESSAGE = "Exception Occurred while fetching the Records.";
    public static final String STR_REGSTRTN_NOT_FOUND = "Vehicle Registration Number not Found";
    public static final String STR_VEHICLE_ID_NOT_FOUND = "Vehicle Id not Found";
    public static final String STR_VEHICLE_ID_START_STRING = "10000";
    public static final String STR_INCORRECT_JSON = "Input JSON is Incorrect";


}
