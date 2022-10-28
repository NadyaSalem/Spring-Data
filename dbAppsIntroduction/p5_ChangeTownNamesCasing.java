package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p5_ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = sqlConnection(scanner);

        PreparedStatement changeTownsToUppercase = connection.prepareStatement("UPDATE `towns`" +
                " SET `name` = upper(`name`)" + " WHERE `country`  = ?;");

        String country = scanner.nextLine();
        changeTownsToUppercase.setString(1, country);

        int numberOfTowns = changeTownsToUppercase.executeUpdate();

        if (numberOfTowns == 0) {
            System.out.println("No town names were affected.");
            connection.close();
            return;
        }

        System.out.println(numberOfTowns + " town names were affected.");

        PreparedStatement getTownsByCountry = connection.prepareStatement("SELECT `name` from `towns`" +
                " WHERE `country`  = ?;");

        getTownsByCountry.setString(1, country);
        ResultSet resultSet = getTownsByCountry.executeQuery();

        ArrayList<String> towns = new ArrayList<>();

        while (resultSet.next()) {
            towns.add(resultSet.getString("name"));
        }

        System.out.println(towns);

        connection.close();
    }
}
