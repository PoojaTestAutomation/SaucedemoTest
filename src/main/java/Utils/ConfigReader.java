package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public ConfigReader() throws IOException {
        // Initialize Properties object to load the config file
        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
        properties.load(fileInputStream);  // Load properties from the file
    }

    // Method to get the URL
    public String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }

    // Method to get standard user password
    public String getStandardUserPassword() {
        return properties.getProperty("standard_user_password");
    }
    
    public String getStandardUserUsername() {
        return properties.getProperty("standard_user_username");
    }

    // Method to get visual user password
    public String getVisualUserPassword() {
        return properties.getProperty("visual_user_password");
    }
    
    // Method to get visual user password
    public String getVisualUserUsername() {
        return properties.getProperty("visual_user_username");
    }
}
