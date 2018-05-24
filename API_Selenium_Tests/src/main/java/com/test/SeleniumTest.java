package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;



public class SeleniumTest {

    private static WebDriver driver;

    public static void main(String[] args) throws Exception {

        setUp();
        //go to eBay site
        driver.navigate().to("http://www.eBay.com");
        try {
            //search an item and add it to the cart
            searchItemAndAdd("apple watch", "//*[@id='item213be2c4fc']/h3/a", "4", "18");
            //checking if the cart amount is more than $500
            if(checkTotalCart()){
                //if it true - go to paying screen
                goToPay();
                return;
            }
            //continue to shop
            click("contShoppingBtn");
            //search an item and add it to the cart
            searchItemAndAdd("gopro hero 5", "//*[@id='item3631bfd17f']/h3/a");
            //checking if the cart amount is more than $500
            if(checkTotalCart()){
                //if it true - go to paying screen
                goToPay();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        tearDown();
    }
    private static void setUp() {
        System.out.println("*******************");
        System.out.println("launching chrome browser");
        String driverPath = "C:\\work\\temp\\test\\API_Selenium_Tests\\path\\";
        System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void goToPay() {
        click("ptcBtnRight");
        click("gtChk");
        type("af-first-name","testerFirstName");
        type("af-last-name","testerLastName");
        type("af-address1","Histadrut");
        type("af-city","Holon");
        type("af-state","Israel");
        type("af-zip","587411");
        type("af-email","testerMail@gmail.com");
        type("af-email-confirm","testerMail@gmail.com");
        type("//*[@id='address-fields']/div[7]/span/input","972545556677");
        click("//*[@id='address-fields-ctr']/div[2]/div/button");
    }

    private static void searchItemAndAdd(String text, String locator3, String value, String value2) {
        clear("gh-ac");
        type("gh-ac", text);
        click("gh-btn");
        click(locator3);
        selectByValue("msku-sel-1", value);
        selectByValue("msku-sel-2", value2);
        click("isCartBtn_btn");
    }

    private static void searchItemAndAdd(String text, String locator3) {
        clear("gh-ac");
        type("gh-ac", text);
        click("gh-btn");
        click(locator3);
        click("isCartBtn_btn");
    }

    private static Boolean checkTotalCart() {
        String total = getText("syncTotal");
        total = total.replace("Total: US $", "");
        Double totalNo = Double.parseDouble(total);
        return  (totalNo > 500);
    }

    private static String getText (String locator) {
        WebElement element = findElementByLocator(locator);
        return element.getText();
    }

    private static void click(String locator) {
        WebElement element = findElementByLocator(locator);
        element.click();
    }

    private static void clear(String locator) {
        WebElement element = findElementByLocator(locator);
        element.clear();
    }

    private static void type(String locator, String text) {
        WebElement element = findElementByLocator(locator);
        element.sendKeys(text);
    }

    private static WebElement findElementByLocator(String locator) {
        return driver.findElement(buildByUsingLocator(locator));
    }

    private static By buildByUsingLocator(String locator) {
        By by;
        locator = locator.trim();
        if (locator.startsWith("id=")) {
            by = By.id(locator.replace("id=", ""));
        } else if (locator.startsWith("//") || locator.startsWith("xpath=")) {
            by = By.xpath(locator.replace("xpath=", ""));
        } else if (locator.startsWith("css=#") || locator.startsWith("img")) {
            by = By.cssSelector(locator.replace("css=", ""));
        } else if (locator.startsWith("css=") || locator.startsWith("img")) {
            by = By.cssSelector(locator.replace("css=", ""));
        } else if (locator.startsWith("link=")) {
            by = By.linkText(locator.replace("link=", ""));
        } else if (locator.startsWith("name=")) {
            by = By.name(locator.replace("name=", ""));
        } else if (locator.startsWith("class=")) {
            by = By.className(locator.replace("class=", ""));
        } else if (locator.startsWith("tagName=")) {
            by = By.tagName(locator.replace("tagName=", ""));
        } else {
            by = By.id(locator);
        }
        return by;
    }

    private static void selectByValue(String locator, String value) {
        final WebElement element = findElementByLocator(locator);
        if (isEditable(element)) {
            final Select select = new Select(element);
            select.selectByValue(value);
        }
    }

    private static boolean isEditable(WebElement element) {
        return element.isEnabled();
    }


    public static void tearDown() {
        if (driver != null) {
            System.out.println("Closing chrome browser");
            driver.quit();
        }
    }
}