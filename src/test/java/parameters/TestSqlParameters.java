package parameters;

import org.testng.annotations.Test;
import utlities.SqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestSqlParameters {

    @Test
    public void testDatabaseValues() throws SQLException {
        ResultSet resultSet = SqlConnector.readData("select * from monthly_mortgage");
        try {
            while (resultSet.next()) {
                System.out.println("The Id # is: " + resultSet.getString("id"));
                System.out.println("The home price is: " + resultSet.getString("homeprice"));
                System.out.println("The down payment is: " + resultSet.getString("downpayment"));
                System.out.println("The interest rate is: " + resultSet.getString("interestrate"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
