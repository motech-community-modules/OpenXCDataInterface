package org.motechproject.OpenXCDataInterface.service;


import org.motechproject.OpenXCDataInterface.bean.ApplicationSettings;

/**
 *
 * Service interface for getting and logging module settings.
 *
 */
public interface ApplicationSettingsService {
    public String getSettingsValue(String key);
    public ApplicationSettings getApplicationSettings();
    public void logInfoWithModuleSettings(String info);
    public void updateSettings(ApplicationSettings settings);
    public boolean testDatabaseConnection(ApplicationSettings settings);
}
