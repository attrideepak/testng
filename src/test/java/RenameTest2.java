import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RenameTest2 implements ITest {
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @Override
    public String getTestName() {
        return testName.get();
    }

    @BeforeMethod
    public void updateTestName(ITestContext testContext, Method method) {
        if (!method.getDeclaredAnnotation(Test.class).dataProvider().isEmpty()) {
            int invocationCount = Arrays.stream(testContext.getAllTestMethods()).filter(
                    m -> m.getMethodName().equalsIgnoreCase(method.getName())
            ).findFirst().orElseThrow().getCurrentInvocationCount();
            String suffix = "";
            if (invocationCount == 0) {
                suffix = "alpha";
            } else if (invocationCount == 1) {
                suffix = "beta";
            }
            testName.set(method.getName() + "_" + suffix);
        } else {
            testName.set(method.getName());
        }
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        return new Object[][] {
                {"superman", "alpha"},
                {"batman", "beta"}
        };
    }

    @Test(dataProvider = "testData")
    public void usernamePassword(String username, String password){
        System.out.println("username: " + username + " and " + "password: " +password);
    }
}
