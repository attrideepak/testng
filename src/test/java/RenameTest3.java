import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RenameTest3 implements ITest {
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @Override
    public String getTestName() {
        return testName.get();
    }

    @BeforeMethod
    public void updateTestName(Method method, Object[] dataProvided) {
        if (!method.getDeclaredAnnotation(Test.class).dataProvider().isEmpty()) {
            String nameType = dataProvided[0].toString();
            testName.set(method.getName() + "_" + nameType);
        } else {
            testName.set(method.getName());
        }
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        return new Object[][] {
                {"data1","superman", "alpha"},
                {"data2","batman", "beta"}
        };
    }

    @Test(dataProvider = "testData")
    public void usernamePassword(String data, String username, String password){
        System.out.println("username: " + username + " and " + "password: " +password);
    }
}
