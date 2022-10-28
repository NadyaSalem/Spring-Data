package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p7_PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = sqlConnection(scanner);

        PreparedStatement originalOrderMinionsNames = connection.prepareStatement("SELECT `name` FROM `minions`;");
        ResultSet result = originalOrderMinionsNames.executeQuery();

        ArrayList<String> minionsNames = new ArrayList<>();

        while (result.next()) {
            minionsNames.add(result.getString("name"));
        }

        ArrayList<String> outputMinionsNames = new ArrayList<>();

        for (int i = 0; i < minionsNames.size(); i++) {
            outputMinionsNames.add(minionsNames.get(i));
            outputMinionsNames.add(minionsNames.get(minionsNames.size() - 1 - i));
            System.out.println(outputMinionsNames.get(i));
        }

        connection.close();
    }
}
