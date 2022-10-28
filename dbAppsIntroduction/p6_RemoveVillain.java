package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p6_RemoveVillain {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = sqlConnection(scanner);

        PreparedStatement findVillain = connection.prepareStatement("SELECT `name` FROM `villains` WHERE `id` = ?;");

        int id = Integer.parseInt(scanner.nextLine());
        findVillain.setInt(1, id);

        ResultSet resultSet = findVillain.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No such villain was found");
            connection.close();
            return;
        }

        connection.setAutoCommit(false);

        try {
            PreparedStatement deleteVillainIdFromMappingTable = connection.prepareStatement("DELETE FROM `minions_villains`" +
                    " WHERE `villain_id` = ?;");

            deleteVillainIdFromMappingTable.setInt(1, id);
            int countReleasedMinions = deleteVillainIdFromMappingTable.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement("DELETE FROM `villains`" +
                    " WHERE `id` = ?;");

            deleteVillain.setInt(1, id);
            deleteVillain.executeUpdate();

            connection.commit();

            System.out.println(resultSet.getString("name") + " was deleted");
            System.out.println(countReleasedMinions + " minions released");

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

        connection.close();
    }
}
