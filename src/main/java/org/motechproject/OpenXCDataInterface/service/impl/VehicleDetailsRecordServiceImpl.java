package org.motechproject.OpenXCDataInterface.service.impl;

import org.motechproject.OpenXCDataInterface.domain.VehicleRegistrationRecord;
import org.motechproject.OpenXCDataInterface.domain.VehicleUploadRecord;
import org.motechproject.OpenXCDataInterface.repository.impl.VehicleDetailsRecordDaoImpl;
import org.motechproject.OpenXCDataInterface.service.ApplicationSettingsService;
import org.motechproject.OpenXCDataInterface.service.VehicleDetailsRecordService;
import org.motechproject.OpenXCDataInterface.repository.VehicleDetailsRecordDao;
import org.motechproject.OpenXCDataInterface.util.OpenXCDataInterfaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Class Name : VehicleDetailsRecordServiceImpl
 * Purpose    : Service Implementation Class. All DAO classes are called from here.
 */

@Service("vehicleDetailsRecordService")
public class VehicleDetailsRecordServiceImpl implements VehicleDetailsRecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDetailsRecordServiceImpl.class);
    ApplicationSettingsService applicationSettingsService;

    @Autowired
    public void setApplicationSettingsService(ApplicationSettingsService applicationSettingsService) {
        LOGGER.info("Initializing  ApplicationSettingsService for VehicleDetailsRecordService");
        this.applicationSettingsService = applicationSettingsService;
    }
    /*
	 * Method Name : getAllVehicleCurrentLocation
	 * Purpose     : Service Layer Method to fetch the current location of all the Registered Vehicles from DAO Layer
	 * Arguments   : None
	 * Returns     : List
	 */

    public List<VehicleUploadRecord> getAllVehicleCurrentLocation() throws OpenXCDataInterfaceException{

        List<VehicleUploadRecord> vehicleUploadRecordList = null;
        VehicleDetailsRecordDao vehicleDetailsRecordDao = new VehicleDetailsRecordDaoImpl(applicationSettingsService.getApplicationSettings());
        vehicleUploadRecordList = vehicleDetailsRecordDao.getAllVehicleCurrentLocation();

        return vehicleUploadRecordList;

    }


    /*
	 * Method Name : getRegisteredVehicleList
	 * Purpose     : Service Layer Method to fetch the details of all Registered Vehicles from DAO Layer
	 * Arguments   : None
	 * Returns     : List
	 */

    public List<VehicleRegistrationRecord> getRegisteredVehicleList() throws OpenXCDataInterfaceException{

        List<VehicleRegistrationRecord> vehicleRegistrationRecordList = null;
        VehicleDetailsRecordDao vehicleDetailsRecordDao = new VehicleDetailsRecordDaoImpl(applicationSettingsService.getApplicationSettings());
        vehicleRegistrationRecordList = vehicleDetailsRecordDao.getRegisteredVehicleList();

        return vehicleRegistrationRecordList;

    }


    /*
	 * Method Name : getVehicleCurrentLocation
	 * Purpose     : Service Layer Method to fetch a Vehicle's current location from DAO Layer
	 * Arguments   : String - Vehicle Id
	 * Returns     : VehicleUploadRecord
	 */

    public VehicleUploadRecord getVehicleCurrentLocation(String vehicleId) throws OpenXCDataInterfaceException{

        VehicleUploadRecord vehicleUploadRecord = null;
        VehicleDetailsRecordDao vehicleDetailsRecordDao = new VehicleDetailsRecordDaoImpl(applicationSettingsService.getApplicationSettings());
        vehicleUploadRecord = vehicleDetailsRecordDao.getVehicleCurrentLocation(vehicleId);

        return vehicleUploadRecord;

    }


    /*
	 * Method Name : showRoute
	 * Purpose     : Service Layer Method to fetch the route traversed by a Vehicle from DAO Layer
	 * Arguments   : String - Vehicle Id
	 * Returns     : List
	 */

    public List<VehicleUploadRecord> showRoute(String vehicleId) throws OpenXCDataInterfaceException{

        List<VehicleUploadRecord> vehicleUploadRecordList = null;
        VehicleDetailsRecordDao vehicleDetailsRecordDao = new VehicleDetailsRecordDaoImpl(applicationSettingsService.getApplicationSettings());
        vehicleUploadRecordList = vehicleDetailsRecordDao.showRoute(vehicleId);

        return vehicleUploadRecordList;

    }


    /*
	 * Method Name : getVehicleId
	 * Purpose     : Service Layer Method to fetch the Vehicle Id on the basis of a Vehicle's Registration Data from DAO Layer
	 * Arguments   : String - Vehicle Registration No
	 * Returns     : Integer - Vehicle Id
	 */

    public Integer getVehicleId(String vehRegnNo) throws OpenXCDataInterfaceException{

        Integer vehicleId = null;
        VehicleDetailsRecordDao vehicleDetailsRecordDao = new VehicleDetailsRecordDaoImpl(applicationSettingsService.getApplicationSettings());
        vehicleId = vehicleDetailsRecordDao.getVehicleId(vehRegnNo);

        return vehicleId;

    }


    /*
	 * Method Name : registerVehicle
	 * Purpose     : Service Layer Method to pass Vehicle's Registration Data to DAO Layer
	 * Arguments   : VehicleRegistrationRecord
	 * Returns     : String
	 */

    public String registerVehicle(VehicleRegistrationRecord vehicleRegistrationRecord) throws OpenXCDataInterfaceException{

        String chkReturnResult = null;
        VehicleDetailsRecordDao vehicleDetailsRecordDao = new VehicleDetailsRecordDaoImpl(applicationSettingsService.getApplicationSettings());
        chkReturnResult = vehicleDetailsRecordDao.registerVehicle(vehicleRegistrationRecord);

        return chkReturnResult;

    }


    /*
	 * Method Name : uploadVehicleData
	 * Purpose     : Service Layer Method to pass a Vehicle's upload Data to DAO Layer
	 * Arguments   : VehicleUploadRecord
	 * Returns     : String
	 */

    public String uploadVehicleData(VehicleUploadRecord vehicleUploadRecord) throws OpenXCDataInterfaceException{

        String chkReturnResult = null;
        VehicleDetailsRecordDao vehicleDetailsRecordDao = new VehicleDetailsRecordDaoImpl(applicationSettingsService.getApplicationSettings());
        chkReturnResult = vehicleDetailsRecordDao.uploadVehicleData(vehicleUploadRecord);


        return chkReturnResult;

    }


}
