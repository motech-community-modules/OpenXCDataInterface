package org.motechproject.OpenXCDataInterface.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Class Name : DatabaseConnection
 * Purpose    : Custom class for making Database Connection and returning a JdbcTemplate.
 */

public class DatabaseConnection {

    private static DriverManagerDataSource managerDataSource = new DriverManagerDataSource();

    private static JdbcTemplate jdbcTemplate = new JdbcTemplate(getDriverManagerDataSource());


    public static DriverManagerDataSource getDriverManagerDataSource(){

        managerDataSource.setDriverClassName(Constants.dbDriver);
        managerDataSource.setUrl(Constants.dbUrl);
        managerDataSource.setUsername(Constants.dbUsername);
        managerDataSource.setPassword(Constants.dbPassword);

        return managerDataSource;
    }


    public static JdbcTemplate getJdbcTemplate(){

        return jdbcTemplate;
    }


}
