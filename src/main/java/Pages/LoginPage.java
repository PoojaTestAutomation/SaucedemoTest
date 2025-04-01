package Pages;

import com.microsoft.playwright.Page;

import Utils.ConfigReader;

public class LoginPage {

    private Page page;
    private ConfigReader configReader;

    // Constructor for LoginPage class
    public LoginPage(Page page, ConfigReader configReader) {
        this.page = page;
        this.configReader = configReader;
    }

    // Method to perform login with username and password
    public void login(String username) {
        // Navigate to login page (base URL is fetched from configReader)
        page.navigate(configReader.getBaseUrl());
        
        // Input the username
        page.fill("#user-name", username);
        
        // Input the password (fetched from ConfigReader)
        page.fill("#password", username.equals("standard_user") ? configReader.getStandardUserPassword() : configReader.getVisualUserPassword());
        
        // Click the login button to submit the form
        page.click("#login-button");
    }
}
