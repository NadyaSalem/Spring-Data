package dbAppsIntroduction;
import java.sql.*;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p2_GetVillainsNames {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Connection connection = sqlConnection(scanner);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT v.`name`, count(DISTINCT m_v.`minion_id`)" +
                " AS 'count_minions'" +
                " FROM `villains` AS v" +
                " JOIN `minions_villains` AS m_v ON v.`id` = m_v.`villain_id`" +
                " GROUP BY  v.`name`" +
                " HAVING `count_minions` > ?" +
                " ORDER BY `count_minions` DESC;");


        preparedStatement.setInt(1, 15);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            System.out.println(result.getString("v.name") + " " + result.getInt("count_minions"));
        }

        connection.close();
    }
}
