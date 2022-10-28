package dbAppsIntroduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static dbAppsIntroduction.minions_dbConnection.sqlConnection;

public class p4_AddMinion {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        Connection connection = sqlConnection(scanner);

        String[] minionInfo = scanner.nextLine().split(" ");

        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];

        PreparedStatement findTown = connection.prepareStatement("SELECT `name` FROM `towns` WHERE `name` = ?;");
        findTown.setString(1, minionTown);

        ResultSet resultOfFindTown = findTown.executeQuery();

        if (!resultOfFindTown.next()) {
            PreparedStatement addTown = connection.prepareStatement("INSERT INTO `towns` (`name`)" + " VALUES (?);");
            addTown.setString(1, minionTown);

            addTown.executeUpdate();

            System.out.println("Town " +  minionTown + " was added to the database.");
        }

        String[] villainInfo = scanner.nextLine().split(" ");

        String villainName = villainInfo[1];

        PreparedStatement findVillain = connection.prepareStatement("SELECT `name` FROM villains WHERE `name` = ?;");
        findVillain.setString(1, villainName);

        ResultSet resultOfFindVillain = findVillain.executeQuery();

        if (!resultOfFindVillain.next()) {
            PreparedStatement addVillain = connection.prepareStatement("INSERT INTO `villains` (`name`, `evilness_factor`)" +
                    " VALUES (?, ?);");
            addVillain.setString(1, villainName);
            String evilness_factor = "evil";
            addVillain.setString(2, evilness_factor);

            addVillain.executeUpdate();

            System.out.println("Villain " + villainName + " was added to the database.");
        }

        PreparedStatement addMinion = connection.prepareStatement("INSERT INTO `minions` (`name`, `age`, `town_id`)" +
                " VALUES (?, ?, (SELECT t.`id` FROM `towns` AS t WHERE t.`name` = ?));");

        addMinion.setString(1, minionName);
        addMinion.setInt(2, minionAge);
        addMinion.setString(3, minionTown);

        addMinion.executeUpdate();

        PreparedStatement connectMinionToVillain = connection.prepareStatement("INSERT INTO `minions_villains` (minion_id, villain_id)" +
        " VALUES ((SELECT m.`id` FROM `minions` AS m WHERE m.`name` = ?)," +
        " (SELECT v.`id` FROM `villains` AS v WHERE v.`name` = ?));");

        connectMinionToVillain.setString(1, minionName);
        connectMinionToVillain.setString(2, villainName);

        System.out.println("Successfully added " + minionName + " to be minion of " + villainName + ".");

        connection.close();
    }
}
