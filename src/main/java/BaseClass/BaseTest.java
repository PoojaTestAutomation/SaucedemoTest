package BaseClass;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Utils.ConfigReader;

public class BaseTest {

    protected Page page;
    protected Browser browser;
    protected BrowserContext context;
    protected ConfigReader configReader;

    // Setup method to initialize browser and page objects before tests
    @BeforeClass
    @Parameters("browser")
    public void setup(@Optional("chromium") String browserType) throws IOException {
    	System.out.println("in setup");
        // Initialize ConfigReader to fetch base URL and credentials
        configReader = new ConfigReader();

        // Launch the browser based on the input parameter (chromium, chrome, firefox, webkit)
        Playwright playwright = Playwright.create();
        browser = createBrowser(playwright, browserType); // Create the appropriate browser
        context = browser.newContext(); // Create a new browser context
        page = context.newPage(); // Open a new page
    }

    // AfterClass to close the browser after tests
    @AfterClass
    public void tearDown() {
    	System.out.println("in tearDown");
        page.close(); // Close the page
        browser.close(); // Close the browser
    }

    // Helper method to launch the specified browser
    private Browser createBrowser(Playwright playwright, String browserType) {
        switch (browserType.toLowerCase()) {
            case "chromium":
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            case "chrome":
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome"));
            case "firefox":
                return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
            case "webkit":
                return playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
            default:
                throw new IllegalArgumentException("Invalid browser type: " + browserType);
        }
    }
    
   

}
