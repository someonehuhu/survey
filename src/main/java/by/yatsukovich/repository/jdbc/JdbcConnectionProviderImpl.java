package by.yatsukovich.repository.jdbc;

import by.yatsukovich.configuration.DatabaseProperties;
import by.yatsukovich.repository.jdbc.JdbcConnectionProvider;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcConnectionProviderImpl implements JdbcConnectionProvider {
    private final DatabaseProperties databaseProperties;

    public JdbcConnectionProviderImpl(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        registerDriver();
    }
    private void registerDriver() {
        try {
            Class.forName(databaseProperties.getDriverName());
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Cannot be loaded!");
            throw new RuntimeException("JDBC Driver Cannot be loaded!");
        }
    }

    @Override
    public Connection getConnection() {
        String jdbcURL = StringUtils.join(databaseProperties.getUrl(), databaseProperties.getPort(), databaseProperties.getName());
        try {
            return DriverManager.getConnection(jdbcURL, databaseProperties.getLogin(), databaseProperties.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
