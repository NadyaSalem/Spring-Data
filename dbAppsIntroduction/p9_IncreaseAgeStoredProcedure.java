package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p9_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = sqlConnection(scanner);

        PreparedStatement preparedStatement = connection.prepareStatement("CALL `usp_get_older`(?);");

        int idInput = Integer.parseInt(scanner.nextLine());

        preparedStatement.setInt(1, idInput);
        preparedStatement.executeUpdate();


        PreparedStatement statement = connection.prepareStatement("SELECT `name`, `age` FROM `minions` WHERE `id` = ?;");
        statement.setInt(1, idInput);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        } else {
            System.out.println("No minion with ID " + idInput + " exists in the database.");
        }

        connection.close();
    }
}
