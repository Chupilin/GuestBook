package by.chupilin.web.guestbook.ui;

import by.chupilin.web.guestbook.models.validators.MessageValidator;
import by.chupilin.web.guestbook.utils.RandomStringUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class SendMessageUITest extends AbstractUITest {

    @Autowired
    private MessageValidator messageValidator;

    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();;
        baseUrl = "http://localhost:8080";
        driver.get(baseUrl + "/message");
        driver.findElement(By.id("inputText")).clear();
        driver.findElement(By.id("inputAuthor")).clear();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testSendMessage_successfulAuthorAndTextValidation() {
        String textCorrectly = RandomStringUtil.generate(messageValidator.getTextMinLength());
        driver.findElement(By.id("inputText")).sendKeys(textCorrectly);
        String authorCorrectly = RandomStringUtil.generate(messageValidator.getAuthorMinLength());
        driver.findElement(By.id("inputAuthor")).sendKeys(authorCorrectly);
        driver.findElement(By.id("btn-send")).click();
        assertTrue(driver.findElement(By.cssSelector("p.text-success")).isDisplayed());
    }

    @Test
    public void testSendMessage_authorEmptyLengthValidation() {
        String textCorrectly = RandomStringUtil.generate(messageValidator.getTextMinLength());
        driver.findElement(By.id("inputText")).sendKeys(textCorrectly);
        driver.findElement(By.id("btn-send")).click();
        assertTrue(driver.findElement(By.id("author.errors")).isDisplayed());
    }

    @Test
    public void testSendMessage_authorShortLengthValidation() {
        String textCorrectly = RandomStringUtil.generate(messageValidator.getTextMinLength());
        driver.findElement(By.id("inputText")).sendKeys(textCorrectly);
        String authorShort = RandomStringUtil.generate(messageValidator.getAuthorMinLength() - 1);
        driver.findElement(By.id("inputAuthor")).sendKeys(authorShort);
        driver.findElement(By.id("btn-send")).click();
        assertTrue(driver.findElement(By.id("author.errors")).isDisplayed());
    }

    @Test
    public void testSendMessage_authorLongLengthValidation() {
        String textCorrectly = RandomStringUtil.generate(messageValidator.getTextMinLength());
        driver.findElement(By.id("inputText")).sendKeys(textCorrectly);
        String authorLong = RandomStringUtil.generate(messageValidator.getAuthorMaxLength() + 1);
        driver.findElement(By.id("inputAuthor")).sendKeys(authorLong);
        driver.findElement(By.id("btn-send")).click();
        assertTrue(driver.findElement(By.id("author.errors")).isDisplayed());
    }

    @Test
    public void testSendMessage_textEmptyLengthValidation() {
        String authorCorrectly = RandomStringUtil.generate(messageValidator.getAuthorMinLength());
        driver.findElement(By.id("inputAuthor")).sendKeys(authorCorrectly);
        driver.findElement(By.id("btn-send")).click();
        assertTrue(driver.findElement(By.id("text.errors")).isDisplayed());
    }

    @Test
    public void testSendMessage_textShortLengthValidation() {
        String textShort = RandomStringUtil.generate(messageValidator.getTextMinLength() - 1);
        driver.findElement(By.id("inputText")).sendKeys(textShort);
        String authorCorrectly = RandomStringUtil.generate(messageValidator.getAuthorMinLength());
        driver.findElement(By.id("inputAuthor")).sendKeys(authorCorrectly);
        driver.findElement(By.id("btn-send")).click();
        assertTrue(driver.findElement(By.id("text.errors")).isDisplayed());
    }

    @Test
    public void testSendMessage_textLongLengthValidation() {
        String textLong = RandomStringUtil.generate(messageValidator.getTextMaxLength() + 1);
        driver.findElement(By.id("inputText")).sendKeys(textLong);
        String authorCorrectly = RandomStringUtil.generate(messageValidator.getAuthorMinLength());
        driver.findElement(By.id("inputAuthor")).sendKeys(authorCorrectly);
        driver.findElement(By.id("btn-send")).click();
        assertTrue(driver.findElement(By.id("text.errors")).isDisplayed());
    }

}