package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import helper.ApkHelper;

public class Main {

    WebDriver driver;

    @BeforeClass
    public void before() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability("", true);
        capabilities.setCapability("deviceName", "PIXEL_3A");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("browserName", "");

        String app = "src/test/resources/apk/app.apk";
        ApkHelper apkHelper = new ApkHelper(app);
        capabilities.setCapability("appPackage", apkHelper.getAppPackageFromApk());
        capabilities.setCapability("appActivity", apkHelper.getAppMainActivity());
        capabilities.setCapability("app", new File(app).getAbsolutePath());

        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test
    public void firstTest() throws Exception {
        WebElement one = driver.findElement(By.name("2"));
        System.out.println(one.getTagName());
    }
    
}
