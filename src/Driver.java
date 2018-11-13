import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Driver {
    public static void main(String[] args) {
        String[] crns = {"asdf", "asdfdas", "asdf", "asdf", "asfd", "asdf", "sdaf"};
//        String semester = "Spring 2019";
        String semester = "Fall 2018";
        System.setProperty("webdriver.gecko.driver", System.getProperty("path"));
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
        WebDriver b = new FirefoxDriver();
        b.get("https://my.usu.edu/");
        b.findElement(By.name("username")).sendKeys(System.getProperty("username"));
        b.findElement(By.name("password")).sendKeys(System.getProperty("password"));
        b.findElement(By.xpath("//input[@value='LOGIN']")).click();
//        wait(10); //for duo
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Register for Classes")));
//        b.get(b.findElement(By.linkText("Register for Classes")).getAttribute("href"));

        b.get("https://ss.banner.usu.edu/StudentRegistrationSsb/ssb/registration/registerPostSignIn?mode=registration");
//        String xpath = "//body//*[text()[contains(.,'" + semester + "')]]";
//        WebDriverWait wait = new WebDriverWait(b, 3);
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
//        b.findElement(By.xpath(xpath)).click();
//        b.findElement(By.id("term-go"));
        b.findElement(By.id("s2id_txt_term")).click();
        String xpath = "//body//*[text()[contains(.,'" + semester + "')]]";
        b.findElement(By.xpath(xpath)).click();

//        b.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        refresh(b);

        b.findElement(By.id("enterCRNs-tab")).click();

        for (int i = 1; i <= crns.length; i++) {
            b.findElement(By.id("txt_crn" + i)).sendKeys(crns[i - 1]);
            if (i == crns.length) {
                break;
            }
            b.findElement(By.id("addAnotherCRN")).click();
        }
//        b.findElement(By.id("addCRNbutton")).click();

    }

    public static void refresh(WebDriver b) {
        try {
            b.findElement(By.id("enterCRNs-tab")).click();
        } catch (WebDriverException f) {
            try {
                b.findElement(By.xpath("//button[text()='Ok']")).click();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                //catch for first case
            }
            b.findElement(By.id("term-go")).click();
            wait(500);
            refresh(b);
        }
    }

    public static void wait(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

