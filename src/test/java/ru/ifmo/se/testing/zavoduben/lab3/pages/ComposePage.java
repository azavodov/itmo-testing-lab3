package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ComposePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    ComposePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public ComposePage sendExpectingError() {
        By byXPath = By.xpath(
                "//span[" +
                "   contains(concat(' ',normalize-space(@class),' '),' button2 ')" +
                "   and .//span[" +
                "       contains(concat(' ',normalize-space(@class),' ')," +
                "                ' button2__txt ') " +
                "       and contains(text(), \"Отправить\")" +
                "   ]" +
                "]");
        WebElement sendButton = driver.findElement(byXPath);
        wait.until(elementToBeClickable(sendButton));

        sendButton.click();
        return this;
    }

    public String getErrorMessage() {
        By byXPath = By.xpath("//div[contains(@class, 'compose-app__compose')]" +
                              "//div[contains(@class, 'rowError--')]");
        WebElement errorLabel = wait.until(visibilityOfElementLocated(byXPath));
        return errorLabel.getText();
    }

    public ComposePage saveDraft() {
        By byXPath = By.xpath(
                "//span[" +
                "   contains(concat(' ',normalize-space(@class),' '),' button2 ')" +
                "   and .//span[" +
                "       contains(concat(' ',normalize-space(@class),' ')," +
                "                ' button2__txt ') " +
                "       and contains(text(), \"Сохранить\")" +
                "   ]" +
                "]");
        WebElement saveButton = driver.findElement(byXPath);
        wait.until(elementToBeClickable(saveButton))
                .click();

        return this;
    }

    public FolderPage closeModal() {
        By byXPath = By.xpath("//button[@title='Закрыть']");
        WebElement closeButton = driver.findElement(byXPath);
        wait.until(elementToBeClickable(closeButton));
        closeButton.click();

        ExpectedCondition<Boolean> dimmerIsHidden =
                invisibilityOfElementLocated(By.xpath("//*[@id='dimmer']"));
        wait.until(dimmerIsHidden);

        return FolderPage.assumeOpen(driver);
    }

    public ComposePage typeSubject(String subject) {
        WebElement subjectField = driver.findElement(By.xpath("//input[@name='Subject']"));
        subjectField.clear();
        subjectField.sendKeys(subject);
        return this;
    }
}
