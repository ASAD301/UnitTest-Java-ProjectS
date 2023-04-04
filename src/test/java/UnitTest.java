
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UnitTest {
    WebDriver driver ;
    @Before
    public void setup(){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    public void getTitle(){
        driver.get("https://demoqa.com");
        String title = driver.getTitle();
        System.out.println(title);
        Assert.assertTrue(title.contains("DEMOQA"));
    }
    @After
    public void finishedTest(){
        //close use for close all the window
        //Quite use for only particular window
        driver.close();
    }

    //Check Banner image Exist
    @Test
    public void elementExist() throws InterruptedException {
        driver.get("https://demoqa.com");
        Thread.sleep(300);
        Boolean status = driver.findElement(By.className("banner-image")).isDisplayed();
        Assert.assertTrue(status);
    }
    @Test
    //text box
    public void textboxWrite(){
        driver.get("https://demoqa.com/text-box");
        //use from-control  for taking all index text box
        List<WebElement> textBox = driver.findElements(By.className("form-control"));

        // Write text to the text box
        // get the text box by index
        textBox.get(0).sendKeys("Full Name");
        textBox.get(1).sendKeys("email@gmail.com");
        textBox.get(2).sendKeys("Current Address");
        textBox.get(3).sendKeys("Permanent Address");

        //scroll the page for finding the submit button
        Utils.scroll(driver);

        //Submit button click
        driver.findElement(By.id("submit")).click();

        // get-text after click
        List<WebElement> confirmetionText = driver.findElements(By.className("mb-1"));
        String name = confirmetionText.get(0).getText();
        String email = confirmetionText.get(1).getText();
        String addressPresent = confirmetionText.get(2).getText();
        String addresspurmanent = confirmetionText.get(3).getText();

        // print get text
        System.out.println(name);
        System.out.println(email);
        System.out.println(addressPresent);
        System.out.println(addresspurmanent);

        //Assertion for get-text
        Assert.assertTrue(name.contains("Full Name"));
        Assert.assertTrue(email.contains("email@gmail.com"));
        Assert.assertTrue(addressPresent.contains("Current Address"));
        Assert.assertTrue(addresspurmanent.contains("Permanent Address"));
    }
    @Test
    public void clickButtonMultipleElements(){
        driver.get("https://demoqa.com/buttons");
        // use Action method for multipleClick or contexClick on button
        Actions action = new Actions(driver);
        List<WebElement> buttons = driver.findElements(By.tagName("button"));

        //double click
        action.doubleClick(buttons.get(1)).perform();
        String text1 = driver.findElement(By.id("doubleClickMessage")).getText();
        System.out.println(text1);
        Assert.assertTrue(text1.contains("You have done a double click"));

        //context click / right click
        action.contextClick(buttons.get(2)).perform();
        String text2 =  driver.findElement(By.id("rightClickMessage")).getText();
        System.out.println(text2);
        Assert.assertTrue(text2.contains("You have done a right click"));

        // just click button as normal click
        buttons.get(3).click();
        String text3 = driver.findElement(By.id("dynamicClickMessage")).getText();
        System.out.println(text3);
        Assert.assertTrue(text3.contains("You have done a dynamic click"));
    }
    @Test
    public void handelAlarts() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        //use switchTo for switch the driver to the javascript alart
        driver.switchTo().alert().accept();

        driver.findElement(By.id("timerAlertButton")).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();

        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().dismiss();
        String successTest = driver.findElement(By.className("text-success")).getText();
        System.out.println(successTest);
        Assert.assertTrue(successTest.contains("You selected Cancel"));

    }
    @Test
    public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).click();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.CONTROL+"a");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("15/03/2023");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
    }
    @Test
    public void dateTime(){
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("dateAndTimePickerInput")).click();
        driver.findElement(By.id("dateAndTimePickerInput")).sendKeys(Keys.CONTROL+"a");
        driver.findElement(By.id("dateAndTimePickerInput")).sendKeys(Keys.BACK_SPACE);
        driver.findElement(By.id("dateAndTimePickerInput")).sendKeys("March 15, 2023 8:36 PM");
        driver.findElement(By.id("dateAndTimePickerInput")).sendKeys(Keys.ENTER);
    }
    @Test
    public void dropDown(){
        driver.get("https://demoqa.com/select-menu");

        //old select
        Select option = new Select(driver.findElement(By.id("oldSelectMenu")));
        option.selectByValue("1");
    }

    @Test
    public void multiDropdown(){

        // use action for multiple cause it did't markup with Select
        driver.get("https://demoqa.com/select-menu");
        driver.findElement(By.xpath("//div[contains(text(),'Select...')]")).click();
        Actions action = new Actions(driver);

        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ENTER);

        action.sendKeys(Keys.ARROW_DOWN);
        action.sendKeys(Keys.ENTER);
        //for perfoming the action
        action.perform();

    }
    @Test
    public void mouseHover(){
        driver.get("https://daffodilvarsity.edu.bd/");
        WebElement menue = driver.findElement(By.xpath("//a[contains(text(),'Admissions')]"));
        Actions action = new Actions(driver);
        action.moveToElement(menue).perform();
    }

    @Test
    public void modalHandel(){
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showSmallModal")).click();
        String modalText = driver.findElement(By.className("modal-body")).getText();
        System.out.println(modalText);
        driver.findElement(By.id("closeSmallModal")).click();
    }

    @Test
    public void uploadImage() throws InterruptedException {
        driver.get("chttps://demoqa.com/upload-download");
        driver.findElement(By.id("downloadButton")).click();
        Thread.sleep(300);
        driver.findElement(By.id("uploadFile")).click();
        driver.findElement(By.id("uploadFile")).sendKeys("C:\\Downloads\\sampleFile.jpeg");

        String text = driver.findElement(By.id("uploadedFilePath")).getText();
        Assert.assertTrue(text.contains("sampleFile.jpeg"));
    }

    public void tabHandle() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(300);
        // handel window by String Arraylist
        ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
        // switch to the new tabs
        driver.switchTo().window(tabs.get(1));

        String title = driver.findElement(By.id("sampleHeading")).getText();
        System.out.println(title);
        Assert.assertTrue(title.contains("This is a sample page"));
        //For closing the driver
        driver.close();
        // back to the mother window
        driver.switchTo().window(tabs.get(0));
    }
    @Test
    public void windowHandle(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id(("windowButton"))).click();
        // mainwindow handle
        String mainWindowHandle = driver.getWindowHandle();
        // all window store in set
        Set<String> allWindowHandles = driver.getWindowHandles();
        // count the all window
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            // without main window conditiion
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow))
            {        driver.switchTo().window(ChildWindow);
                String text= driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
            }}
        driver.close();
        driver.switchTo().window(mainWindowHandle);
    }
    @Test
    public void tableHandle() {
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i = 0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num[" + i + "] "+cell.getText());
            }

        }
    }
    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }







}
