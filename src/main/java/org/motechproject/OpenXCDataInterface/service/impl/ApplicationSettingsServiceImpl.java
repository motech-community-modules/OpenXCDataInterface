package org.motechproject.OpenXCDataInterface.service.impl;


import org.motechproject.OpenXCDataInterface.bean.ApplicationSettings;
import org.motechproject.OpenXCDataInterface.service.ApplicationSettingsService;
import org.motechproject.OpenXCDataInterface.util.DatabaseConnection;
import org.motechproject.server.config.SettingsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Implementation of the {@link org.motechproject.OpenXCDataInterface.service.ApplicationSettingsService} uses {@link org.motechproject.server.config.SettingsFacade}.
 */
@Service("applicationSettingsService")
public class ApplicationSettingsServiceImpl implements ApplicationSettingsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSettingsServiceImpl.class);
    private static final String CONFIGS_PROPERTIES_FILE_NAME = "OpenXCDataInterface-settings.properties";

    private SettingsFacade settingsFacade;

    private ApplicationSettings applicationSettings;

    @Autowired
    public ApplicationSettingsServiceImpl(SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
        loadSettings();
    }
    @Override
    public ApplicationSettings getApplicationSettings() {
        return applicationSettings;
    }

    @Override
    public String getSettingsValue(String key) {
        if (null == key) {
            return null;
        }
        return settingsFacade.getProperty(key);
    }

    @Override
    public void logInfoWithModuleSettings(String info) {

        LOGGER.info(info + " " +
                getSettingsValue("OpenXCDataInterface.dbUrl") + " " +
                getSettingsValue("OpenXCDataInterface.dbDriver") + " " +
                getSettingsValue("OpenXCDataInterface.dbUsername"));

    }

    /**
     *
     */
    private synchronized void loadSettings() {
        logInfoWithModuleSettings("Application Settings:: -- \n");
        applicationSettings = new ApplicationSettings(
                getSettingsValue("OpenXCDataInterface.dbUrl"),
                getSettingsValue("OpenXCDataInterface.dbDriver"),
                getSettingsValue("OpenXCDataInterface.dbUsername"),
                getSettingsValue("OpenXCDataInterface.dbPassword")
            );

    }

    /**
     * @param settings
     */
    @Override
    public void updateSettings(ApplicationSettings settings) {

        Properties props = new Properties();

        props.setProperty("OpenXCDataInterface.dbUrl", settings.getDbUrl());
        props.setProperty("OpenXCDataInterface.dbDriver", settings.getDbDriver());
        props.setProperty("OpenXCDataInterface.dbUsername", settings.getDbUsername());
        props.setProperty("OpenXCDataInterface.dbPassword", settings.getDbPassword());


        settingsFacade.saveConfigProperties(CONFIGS_PROPERTIES_FILE_NAME, props);

        loadSettings();

        DatabaseConnection.updateJDBCTemplate(applicationSettings);
    }

    @Override
    public boolean testDatabaseConnection(ApplicationSettings settings) {
        return DatabaseConnection.testDatabaseConnection(settings);
    }
}
