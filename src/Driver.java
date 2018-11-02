import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {
    public static WebDriver b;
    public static final long OPENS_AT = 1542250800;
    public static final String[] crns = {"10816", "13803", "10172", "11402", "12663", "10166", "13128"};
    public static final String semester = "Spring 2019";

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", System.getProperty("path"));
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
        b = new FirefoxDriver();
        b.get("https://ss.banner.usu.edu/StudentRegistrationSsb/ssb/term/termSelection?mode=registration");
        b.findElement(By.id("registerLink")).click();
        b.findElement(By.name("username")).sendKeys(System.getProperty("username"));
        b.findElement(By.name("password")).sendKeys(System.getProperty("password"));
        b.findElement(By.name("submit")).click();

        //click the select semester
        b.findElement(By.className("select2-choice")).click();

        //wait until spring is available
        while (true) {
            try {
                b.findElement(By.xpath("//div[text()[contains(.,'" + semester + "')]]")).click();
                break;
            } catch (WebDriverException e) {
                System.out.println("caught");
                long seconds = OPENS_AT - (System.currentTimeMillis() / 1000);
                System.out.println(seconds);
                if (seconds < 15) {
                    //don't wait
                } else if (seconds < 60) { // if less than 1 minute, try every second
                    wait(1000);
                } else if (seconds < 120) { //if less than 2 minutes, try every 5 seconds
                    wait(5000);
                } else if (seconds < 300) { //if less than 5 minutes, try every 10 seconds
                    wait(10000);
                } else if (seconds < 1200) { //if less than 10 minutes, try every 15 seconds
                    wait(15000);
                } else if (seconds < 900) { //if less than 20 minutes, try every 30 seconds
                    wait(30000);
                } else if (seconds < 1800) {//if less than 30 minutes, try every 45 seconds
                    wait(45000);
                } else {
                    wait(60000); //otherwise wait a minute
                }
                b.get(b.getCurrentUrl());
            }
        }

        b.findElement(By.id("term-go")).click();
        b.findElement(By.id("enterCRNs-tab")).click();
//        b.findElement(By.id("txt_crn1")).sendKeys(crns[0]);
        for (int i = 1; i < crns.length; i++) {
            b.findElement(By.id("addAnotherCRN")).click();
            b.findElement(By.id("txt_crn" + (i + 1))).sendKeys(crns[i]);
        }
//        b.findElement(By.id("addCRNbutton")).click();
//        b.findElement(By.id("saveButton")).click();


    }

    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

