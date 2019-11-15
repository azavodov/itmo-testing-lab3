package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class FolderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    FolderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 10);
    }

    public static FolderPage open(Folder folder, WebDriver driver) {
        driver.get(folder.getUrl());
        return new FolderPage(driver);
    }

    public static FolderPage openInbox(WebDriver driver) {
        return open(Folder.INBOX, driver);
    }

    public static FolderPage openLoggedInAs(User user, WebDriver driver) {
        return LoginPage.open(driver).loginAs(user);
    }

    public String getCurrentUserEmail() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//*[@id='PH_user-email']");
        WebElement usernameLabel = driver.findElement(byXPath);

        ExpectedCondition<Boolean> emailIsDisplayed =
                textToBePresentInElement(usernameLabel, "@");
        wait.until(emailIsDisplayed);

        return usernameLabel.getText();
    }

    public LoginPage logout() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//*[@id='PH_logoutLink']");
        WebElement logoutBtn = driver.findElement(byXPath);
        wait.until(elementToBeClickable(logoutBtn));

        logoutBtn.click();

        return new LoginPage(driver);
    }

    public FolderPage removeAllMessages() {
        // TODO: Implement selecting and removing all messages
        return this;
    }

    public ComposePage compose() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//span[contains(" +
                              "concat(\" \",normalize-space(@class),\" \")," +
                              "\" compose-button \")" +
                              "]");

        WebElement composeButton = driver.findElement(byXPath);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='app-loader']")));
        wait.until(elementToBeClickable(composeButton));

        composeButton.click();

        return new ComposePage(driver);
    }

    public FolderPage goToFolder(Folder folder) {
        return folder.open(driver); // TODO: Click on nav menu, not open URL
    }
}
