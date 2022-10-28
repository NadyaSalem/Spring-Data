package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p8_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = sqlConnection(scanner);

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `minions`" +
                " SET `age` = `age` + 1, `name` = lower(`name`) WHERE `id` = ?;");

        int[] idInput = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int id : idInput) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }

        PreparedStatement statement = connection.prepareStatement("SELECT `name`, `age` FROM `minions`;");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }

        connection.close();
    }
}
