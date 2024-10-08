package parameters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderClass {
    @DataProvider (name = "SingleValue")
    public Object[][] storeSingleValue() {
        return new  Object[][] {
                {"Rifat"},
                {"Mohammad"},
                {"Abu"},
                {"Sally"}};
        }

    @DataProvider (name = "MultipleValues")
    public Object[][] storeMultipleValues() {
        return new Object[][] {
                {"Rifat", "Florida", 33018},
                {"Mohammad", "New Jersey", 12321},
                {"Abu", "New York", 11212},
                {"Sally", "Georgia", 56432}
        };
    }

    @DataProvider (name = "AmortizedLoanPgObjDataProvider")
    public Object[][] storeAmortizedLoanData() {
        return new Object[][] {
                {"350000","30","1","7.5","Quarterly","Every Quarter","$7348.44"}
        };
    }

    @DataProvider (name = "MortgageCalculatorDataProvider")
    public Object[][] storeMortgageCalculatorDpData() {
        return new Object[][] {
            {"350000","$","70000","30","9","Jun","2024","%","7","15000","%","3","$2,252.94","$2,252.94"}
        };
    }


    @Test (dataProvider = "SingleValue")
    public void readSingleValue(String name) {
        System.out.println("[Single value] Name is: " + name);
    }

    @Test(dataProvider = "MultipleValues")
    public void readMultipleValues(String name, String state, int zipCode) {
        System.out.println("[Multiple Column Value] Name is: " + name);
        System.out.println("[Multiple Column Value] State is: " + state);
        System.out.println("[Multiple Column Value] Zip Code is: " + zipCode);
    }
}
