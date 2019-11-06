package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import sun.font.TrueTypeFont;

import java.util.List;

public class PageBase {
    WebDriver driver;
    public static final String LOGIN = "olga_mo_";
    public static final String PASSWORD = "Anna2019";

    public PageBase(WebDriver driver) {
        this.driver = driver;
    }

    public void tearDown() {
        driver.quit();
    }

    public void waitUntilElementIsVisible(By locator, int time){
        try{
            new WebDriverWait(driver, time)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void waitUntilAllElementsVisible(List<WebElement> listOptions, int time){
        try{
            new WebDriverWait(driver, time)
                    .until(ExpectedConditions.visibilityOfAllElements(listOptions));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void waitUntilElementIsClickable(By locator, int time){
        try{
            new WebDriverWait(driver, time)
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void waitUntilElementIsPresent(By locator, int time){
        try{
            new WebDriverWait(driver, time)
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

   public void waitUntilPageIsLoaded(By locator, int time){
       waitUntilElementIsClickable(locator, time);
   }

    public Boolean correctPageIsLoaded(By locator, String loc){
        WebElement listEvent
                = driver.findElement(locator);
        return driver.findElement(locator).getText().equalsIgnoreCase(loc);
    }
    public boolean getAllHolidaysValuesForAllEventsChosenByFilter(By locator, String name) {

        List<WebElement> listHolidays = driver.findElements(locator);

        // --- verify that all holidays values are "name" ----
        int counter = 0;
        for (int i=0; i < listHolidays.size(); i++){
            System.out.println("Filter: " + listHolidays.get(i).getText());
            if (listHolidays.get(i).getText().contains(name)) counter++;
        }
        Boolean allHolidaysChosenByFilter = true;
        allHolidaysChosenByFilter = counter == listHolidays.size();
        return allHolidaysChosenByFilter;
    }

 /*   public void getAllHolidaysValuesForAllEventsChosenByFilter(By locator, String name) {

        List<WebElement> listHolidays = driver.findElements(locator);

        // --- verify that all holidays values are "name" ----
        int counter = 0;
        for (int i=0; i < listHolidays.size(); i++){
            System.out.println("Filter: " + listHolidays.get(i).getText());
            if (listHolidays.get(i).getText().contains(name)) counter++;
        }
        Assert.assertEquals(counter, listHolidays.size());
    }*/

    public void waitThatFilterAndAllOptionsAreLoaded(By locator1, By locator2) throws InterruptedException {

        Thread.sleep(5000);
        waitUntilElementIsClickable(locator1,100);
        waitUntilAllElementsVisible(driver
                .findElements(locator2),90);
    }

    public void waitThatFilterIsChosenAndAllEventsByFiterAreLoaded(By locator) {
        waitUntilElementIsClickable(By.cssSelector("#idbtnclearfilter"),20);
        waitUntilElementIsPresent(locator,20);
        // ------ wait that all events by fiter "shabbat" are loaded ----
        waitUntilAllElementsVisible(driver.findElements(By
                .xpath("//div[@class = 'itemEventInsert']")),40);
    }

    public void getSelectElementFilterBy(By locator1, String name, By locator2) throws InterruptedException {
        waitUntilElementIsClickable(locator1, 90);

        WebElement holidaysFilter = driver
                .findElement(locator1);

       // waitUntilElementIsClickable(locator1, 90);

        Select selector;
        try{
            selector = new Select(holidaysFilter);
            selector.selectByValue(name);
        }catch(Exception e){
            try {
                Thread.sleep(20000);
                System.out.println("Exception: " + e);
                selector = new Select(driver
                        .findElement(locator1));
                selector.selectByValue(name);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
      /*  Select selector = new Select(holidaysFilter);*/
        waitThatFilterAndAllOptionsAreLoaded(locator1, locator2);
       /* selector.selectByValue(name);*/
    }
}
