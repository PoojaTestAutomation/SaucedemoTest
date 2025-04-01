package APITest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

import Pages.EmployeeAPIPage;

public class EmployeeAPITest {

    private Playwright playwright;          // Playwright instance for browser automation
    private APIRequestContext apiContext;   // API context for making requests
    private EmployeeAPIPage employeeAPIPage; // Page Object for API validations

    /**
     * Setup method to initialize Playwright and API context before running tests.
     */
    @BeforeClass
    public void setup() {
        playwright = Playwright.create();  // Initialize Playwright
        apiContext = playwright.request().newContext(new APIRequest.NewContextOptions().setIgnoreHTTPSErrors(true));
        employeeAPIPage = new EmployeeAPIPage(apiContext);  // Initialize the API Page Object
        System.out.println("Setup completed.");
    }


    /**
     * The actual test case that validates employee API data.
     */
    @Test
    public void testEmployeeAPI() {
        String apiUrl = "https://hub.dummyapis.com/employee?noofRecords=10&idStarts=2";  // API URL
        String[] fields = {"imageUrl", "firstName", "lastName", "email", "contactNumber", "age", "dob", "salary", "address"};

        // Method chaining to send API request and validate fields
        employeeAPIPage
                .sendAPIRequest(apiUrl)        // Send the API request
                .validateEmployeeFields(fields); // Validate all specified fields
    }

  
    /**
     * Cleanup method to dispose of Playwright and API resources after all tests.
     */
    @AfterClass
    public void cleanup() {
        apiContext.dispose();  // Dispose API context
        playwright.close();    // Close Playwright instance
        System.out.println("\n"+" Cleanup done. All tests finished.");
    }
}
