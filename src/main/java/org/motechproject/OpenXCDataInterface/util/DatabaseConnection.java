package org.motechproject.OpenXCDataInterface.util;


import org.motechproject.OpenXCDataInterface.bean.ApplicationSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class Name : DatabaseConnection
 * Purpose    : Custom class for making Database Connection and returning a JdbcTemplate.
 */

public class DatabaseConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
    private static DriverManagerDataSource managerDataSource = null;

    private static JdbcTemplate jdbcTemplate = null;


    public static DriverManagerDataSource getDriverManagerDataSource(ApplicationSettings applicationSettings) {
        if (managerDataSource == null) {
            managerDataSource = new DriverManagerDataSource();
        }

        managerDataSource.setDriverClassName(applicationSettings.getDbDriver());
        managerDataSource.setUrl(applicationSettings.getDbUrl());
        managerDataSource.setUsername(applicationSettings.getDbUsername());
        managerDataSource.setPassword(applicationSettings.getDbPassword());

        return managerDataSource;
    }


    public static JdbcTemplate getJdbcTemplate(ApplicationSettings applicationSettings) {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(getDriverManagerDataSource(applicationSettings));
        }
        return jdbcTemplate;
    }

    /**
     * Nullifying the managerDataSource and jdbcTemplate so that @getJdbcTemplate method will create
     * new managerDataSource and jdbcTemplate if there is any changes in Database connection properties.
     *
     * @param applicationSettings
     */
    public static void updateJDBCTemplate(ApplicationSettings applicationSettings) {

        managerDataSource = null;
        jdbcTemplate = null;
    }

    /**
     *
     * @param testApplicationSettings
     * @return
     */
    public static boolean testDatabaseConnection(ApplicationSettings testApplicationSettings) {
        DriverManagerDataSource testDriverManagerDataSource = new DriverManagerDataSource();
        testDriverManagerDataSource.setDriverClassName(testApplicationSettings.getDbDriver());
        testDriverManagerDataSource.setUrl(testApplicationSettings.getDbUrl());
        testDriverManagerDataSource.setUsername(testApplicationSettings.getDbUsername());
        testDriverManagerDataSource.setPassword(testApplicationSettings.getDbPassword());

        try {
            Connection connection = testDriverManagerDataSource.getConnection();
            return true;
        } catch (SQLException e) {
            LOGGER.debug("Error while creating the database connection with given connection properties.", e);
        }

        return false;

    }
}

