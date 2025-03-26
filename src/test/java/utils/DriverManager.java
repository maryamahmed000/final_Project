package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
    private static final AtomicReference<WebDriver> driverRef = new AtomicReference<>();
    private static final Logger logger = Logger.getLogger(DriverManager.class.getName());

    public static WebDriver getDriver() {
        if (driverRef.get() == null) {
            synchronized (DriverManager.class) {
                if (driverRef.get() == null) {
                    try {
                        WebDriverManager.firefoxdriver().setup(); // Auto-downloads GeckoDriver (Firefox)

                        FirefoxOptions options = new FirefoxOptions();
                        options.addArguments("--start-maximized"); // Open Firefox in maximized mode
                        options.addArguments("--disable-extensions"); // Disable browser extensions

                        WebDriver newDriver = new FirefoxDriver(options);
                        newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                        driverRef.set(newDriver);

                        logger.info("Firefox WebDriver initialized successfully.");
                    } catch (Exception e) {
                        logger.severe("Failed to initialize WebDriver: " + e.getMessage());
                        throw new RuntimeException("WebDriver initialization failed.", e);
                    }
                }
            }
        }
        return driverRef.get();
    }

    public static void quitDriver() {
        if (Objects.nonNull(driverRef.get())) {
            synchronized (DriverManager.class) {
                if (Objects.nonNull(driverRef.get())) {
                    try {
                        driverRef.get().quit();
                        driverRef.set(null);
                        logger.info("Firefox WebDriver quit successfully.");
                    } catch (Exception e) {
                        logger.severe("Error while quitting WebDriver: " + e.getMessage());
                    }
                }
            }
        }
    }
}
