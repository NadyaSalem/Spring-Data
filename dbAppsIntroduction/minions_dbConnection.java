package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

enum minions_dbConnection {
    ;

    static Connection sqlConnection(Scanner scanner) throws SQLException {

        String user = scanner.nextLine();
        String password = scanner.nextLine();
        String url = "jdbc:mysql://localhost:3306/minions_db";

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        return DriverManager.getConnection(url, properties);
    }
}
