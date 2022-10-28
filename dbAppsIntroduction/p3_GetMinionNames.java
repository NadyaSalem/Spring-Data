package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p3_GetMinionNames {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Connection connection = sqlConnection(scanner);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.`name`, m.`age`, v.`name`" +
                " FROM `minions` AS m" +
                " JOIN `minions_villains` AS m_v ON m.`id` = m_v.`minion_id`" +
                " JOIN `villains` AS v ON m_v.`villain_id` = v.`id`" +
                " WHERE m_v.villain_id = ?;");

        int villainId = Integer.parseInt(scanner.nextLine());
        preparedStatement.setInt(1, villainId);

        ResultSet resultset = preparedStatement.executeQuery();

        if (resultset.next()) {

            for (int i = 1; resultset.next(); i++) {
                if (i == 1) {
                    System.out.println("Villain: " + resultset.getString("v.name"));
                }
                System.out.println(i + ". " + resultset.getString("m.name") + " " + resultset.getInt("m.age"));
            }
        } else {
            System.out.println("No villain with ID " + villainId + " exists in the database.");
        }

    }
}
