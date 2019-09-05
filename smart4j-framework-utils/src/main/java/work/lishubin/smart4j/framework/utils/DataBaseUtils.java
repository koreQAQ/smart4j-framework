package work.lishubin.smart4j.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import work.lishubin.smart4j.framework.commons.MyThreadLocal;
import work.lishubin.smart4j.framework.constant.ConfigConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author lishubin
 */
public class DataBaseUtils {


    //加载数据库的相关信息

    private static String JDBC_DRIVER;
    private static String JDBC_URL;
    private static String JDBC_USERNAME;
    private static String JDBC_PASSWORD;

    static {
        Properties configProp = PropUtils.getProperties(ConfigConstant.CONFIG_FILE);
        JDBC_DRIVER = PropUtils.getStringValue(configProp, ConfigConstant.SMART_FRAMEWORK_JDBC_DRIVER);
        JDBC_URL = PropUtils.getStringValue(configProp, ConfigConstant.SMART_FRAMEWORK_JDBC_URL);
        JDBC_USERNAME = PropUtils.getStringValue(configProp, ConfigConstant.SMART_FRAMEWORK_JDBC_USERNAME);
        JDBC_PASSWORD = PropUtils.getStringValue(configProp, ConfigConstant.SMART_FRAMEWORK_JDBC_PASSWORD);

    }


    private static final MyThreadLocal<Connection> CONNECTION_CONTAINER = new MyThreadLocal<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseUtils.class);


    public static Connection getConnection() {

        Connection connection = CONNECTION_CONTAINER.get();
        // 从DataManager获取Connection
        if (connection == null) {

            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(
                        JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD
                );
            } catch (ClassNotFoundException | SQLException e) {
                LOGGER.error("getConnection failure ", e);
            } finally {
                CONNECTION_CONTAINER.setValue(connection);
            }

        }
        return connection;
    }

    public static void closeConnection() {

        Connection connection = getConnection();
        if (connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close Connection failure ", e);
            } finally {
                CONNECTION_CONTAINER.remove();
            }
        }
    }

    public static void beginTransaction() throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("beginTransaction failure ", e);
                throw e;
            }
        }
    }

    public static void commitTransaction() throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error("commitTransaction failure ", e);
                throw e;
            }
        }
    }

    public static void rollbackTransaction() throws SQLException {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("rollbackTransaction failure ", e);
                throw e;
            }
        }
    }

}
