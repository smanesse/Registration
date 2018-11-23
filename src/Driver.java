import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> crns = new ArrayList<>();
        int input;
        System.out.println("Please log in to the USU registration link manually and ensure that there are no holds on your account or forms to sign.");
        System.out.println("Please enter in your crns. When done, enter 0.");
        while (true) {
            input = scanner.nextInt();
            if (input == 0) {
                break;
            }
            crns.add(input);
        }
        System.out.println("Please enter your A number:");
        String Anumber = scanner.next();
        System.out.println("Please enter your password:");
        String password = scanner.next();
        System.out.println("Please enter the semester you're registering for (i.e. Spring 2018):");
        String semester = scanner.next();
        System.out.println("If you need 20 seconds pause to use duo, enter y, otherwise n.");
        String duo = scanner.next();
        boolean wait = false;
        if (duo.contains("y")) {
            wait = true;
        }
        System.out.println("Launching browser. Please wait.");
        System.setProperty("webdriver.gecko.driver", System.getProperty("path"));
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");
        WebDriver b = new FirefoxDriver();
        b.get("https://my.usu.edu/");
        b.findElement(By.name("username")).sendKeys(Anumber);
        b.findElement(By.name("password")).sendKeys(password);
        b.findElement(By.xpath("//input[@value='LOGIN']")).click();
        if (wait) {
            wait(20000); //for duo
        }

        b.get("https://ss.banner.usu.edu/StudentRegistrationSsb/ssb/registration/registerPostSignIn?mode=registration");
        b.findElement(By.id("s2id_txt_term")).click();
        String xpath = "//body//*[text()[contains(.,'" + semester + "')]]";
        b.findElement(By.xpath(xpath)).click();

        refresh(b);

        b.findElement(By.id("enterCRNs-tab")).click();

        for (int i = 1; i <= crns.size(); i++) {
            b.findElement(By.id("txt_crn" + i)).sendKeys(crns.get(i - 1).toString());
            if (i == crns.size()) {
                break;
            }
            b.findElement(By.id("addAnotherCRN")).click();
        }
        b.findElement(By.id("addCRNbutton")).click();
        b.findElement(By.id("saveButton")).click();
    }

    public static void refresh(WebDriver b) {
        try {
            b.findElement(By.id("enterCRNs-tab")).click();
        } catch (WebDriverException f) {
            try {
                wait(250);
                b.findElement(By.xpath("//button[text()='Ok']")).click();
                wait(250);
            } catch (org.openqa.selenium.NoSuchElementException e) {
                //catch for first case
            }
            b.findElement(By.id("term-go")).click();
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

