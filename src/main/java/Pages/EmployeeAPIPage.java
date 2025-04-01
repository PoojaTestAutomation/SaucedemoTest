package Pages;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.nio.charset.StandardCharsets; // For byte-to-string conversion

public class EmployeeAPIPage {

    private APIRequestContext apiContext; // Playwright API request context
    private ObjectMapper mapper;         // For parsing JSON responses
    private JsonNode employees;           // To store employee data from API

    // Constructor to initialize API context and JSON parser
    public EmployeeAPIPage(APIRequestContext apiContext) {
        this.apiContext = apiContext;
        this.mapper = new ObjectMapper(); // Jackson ObjectMapper for JSON parsing
    }

    /**
     * Sends an API request to the given URL and parses the response.
     * @param apiUrl The API endpoint URL.
     * @return The current instance of EmployeeAPIPage for method chaining.
     */
    public EmployeeAPIPage sendAPIRequest(String apiUrl) {
        APIResponse response = apiContext.get(apiUrl);  // Sending GET request to the API

        // Assert to ensure the status code is 200 (OK)
        Assert.assertEquals(response.status(), 200, "Then I should receive a 200 status code.");

        // Assert to check if the content-type is JSON
        Assert.assertTrue(response.headers().get("content-type").contains("application/json"),
                "Then I should receive a JSON response content-type.");

        // Convert byte[] response body to String before validating
        String responseBody = new String(response.body(), StandardCharsets.UTF_8);

        // Validate JSON response format
        validateJSONResponse(responseBody);

        try {
            // Parsing JSON response to a JsonNode object
            employees = mapper.readTree(responseBody);
            System.out.println(" API Response received successfully.");
        } catch (Exception e) {
            // If parsing fails, fail the test with an error message
            Assert.fail(" Failed to parse response body: " + e.getMessage());
        }
        return this;  // Returning current object for method chaining
    }

    /**
     * Validates if the response body contains valid JSON data.
     * @param responseBody The response body to validate.
     */
    private void validateJSONResponse(String responseBody) {
        try {
            // Attempt to parse the response body as JSON
            mapper.readTree(responseBody);
            System.out.println(" Response body contains valid JSON.");
        } catch (Exception e) {
            // If parsing fails, throw an assertion error
            Assert.fail(" Response body is not valid JSON: " + e.getMessage());
        }
    }

    /**
     * Validates that each field in the employee data is not null.
     * @param fields Array of field names to validate.
     * @return The current instance of EmployeeAPIPage for method chaining.
     */
    public EmployeeAPIPage validateEmployeeFields(String[] fields) {
        for (JsonNode employee : employees) {  // Loop through each employee in the response
            System.out.println("\n"+"Validating Employee: " + employee.toString());

            for (String field : fields) {
                validateEmployeeField(employee, field);  // Validate each field individually
            }
        }
        return this;  // Returning current object for method chaining
    }

    /**
     * Validates a specific field in the employee data to ensure it's not null or empty.
     * @param employee The employee data node.
     * @param fieldName The field name to validate.
     */
    private void validateEmployeeField(JsonNode employee, String fieldName) {
        try {
            if (employee.get(fieldName) == null || employee.get(fieldName).asText().isEmpty()) {
                System.err.println(" Field '" + fieldName + "' is null or empty.");  // Error message
                Assert.fail("Field '" + fieldName + "' is null or empty.");               // Fail the test
            } else {
                System.out.println(" Field '" + fieldName + "' is not null. Value: " + employee.get(fieldName).asText());
            }
        } catch (Exception e) {
            System.err.println(" Error validating field '" + fieldName + "': " + e.getMessage());
            Assert.fail("Error validating field '" + fieldName + "': " + e.getMessage());
        }
    }
}
