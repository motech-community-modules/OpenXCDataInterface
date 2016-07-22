package org.motechproject.OpenXCDataInterface.bean;


/**
 * Created on 18-07-2016.
 */
public class ApplicationSettings {
    private String dbUrl;
    private String dbDriver;
    private String dbUsername;
    private String dbPassword;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public ApplicationSettings(String dbUrl, String dbDriver, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbDriver = dbDriver;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public ApplicationSettings() {
    }
}
