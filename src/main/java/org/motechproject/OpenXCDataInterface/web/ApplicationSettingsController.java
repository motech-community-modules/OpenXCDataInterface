package org.motechproject.OpenXCDataInterface.web;

import com.google.gson.Gson;

import org.motechproject.OpenXCDataInterface.bean.ApplicationSettings;
import org.motechproject.OpenXCDataInterface.service.ApplicationSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created on 15-07-2016.
 */
@Controller
public class ApplicationSettingsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSettingsController.class);
    ApplicationSettingsService applicationSettingsService;

    @Autowired
    public void setApplicationSettingsService(ApplicationSettingsService applicationSettingsService) {
        LOGGER.info("initializing  ApplicationSettingsService");
        this.applicationSettingsService = applicationSettingsService;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/applicationSettings", method = RequestMethod.GET, produces={"application/json"})
    public @ResponseBody String  getSettings() {
        LOGGER.debug("ApplicationSettingsController.getSettings Start");
        ApplicationSettings applicationSettings = applicationSettingsService.getApplicationSettings();

        Gson gson = new Gson();
        String settingsJson = gson.toJson(applicationSettings);
        LOGGER.debug("Setting Json:" + settingsJson);
        return settingsJson;

    }

    /**
     * Saves the modified properties of the application settings.
     * @param is the platform settings section to be saved
     *
     */
    @RequestMapping(value = "/applicationSettings/save", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody String saveApplicationSettings(InputStream is) {
        BufferedReader bufferedReader = null;
        ApplicationSettings applicationSettings = null;
        Gson gson = new Gson();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder settingsData = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                settingsData.append(line);
            }

            applicationSettings = gson.fromJson(settingsData.toString(), ApplicationSettings.class);
        }catch (Exception ex) {
            LOGGER.debug("Exception while reading input stream", ex);
        }
        applicationSettingsService.updateSettings(applicationSettings);

        LOGGER.info("Application Settings saved.");
        return "{\"success\":\"true\"}";
    }
    /**
     * Test the modified properties of the application settings.
     * @param is the database settings section to be tested
     *
     */
    @RequestMapping(value = "/applicationSettings/test", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody String testApplicationSettings(InputStream is) {
        BufferedReader bufferedReader = null;
        ApplicationSettings applicationSettings = null;
        Gson gson = new Gson();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder settingsData = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                settingsData.append(line);
            }

            applicationSettings = gson.fromJson(settingsData.toString(), ApplicationSettings.class);
        }catch (Exception ex) {
            LOGGER.debug("Exception while reading input stream", ex);
        }

        boolean isConnectionEstablished = applicationSettingsService.testDatabaseConnection(applicationSettings);

        LOGGER.info("Application Database Settings tested.");
        return "{\"success\":\"" + isConnectionEstablished + "\"}";
    }
}
