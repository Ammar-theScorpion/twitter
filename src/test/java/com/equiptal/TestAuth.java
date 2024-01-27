package com.equiptal;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

public class TestAuth {

    private static WebDriver driver;

    @BeforeClass
    public static void setConfiguration() {
        System.setProperty(
                "webdriver.edge.driver",
                "/mnt/c/Users/ammar/Downloads/edgedriver_win64/msedgedriver.exe");

        Configuration.browser = "edge";
        Configuration.holdBrowserOpen = true;

        driver = new EdgeDriver();
    }

    @Test
    public void testLoginPage() {
        open("http://localhost:8080/login");

        $(By.name("userName")).setValue("un_Ammar");
        $(By.name("pass")).setValue("un_1234").pressEnter();

        if (!$(By.id("error")).getText().isEmpty()) {
            // User authentication failed
            $(By.id("error"))
                    .shouldBe(Condition.visible)
                    .shouldHave(exactText("user name or password is worng"));

            // Click the link to go to the signup page
            $("a").shouldHave(exactText("Don't have an account?")).click();
            webdriver().shouldHave(url("http://localhost:8080/signup"));

        } else {
            webdriver().shouldHave(url("http://localhost:8080/"));
        }

    }

    @Test
    public void testSignUp() {
        open("http://localhost:8080/signup");

        $(By.id("auth_type")).shouldHave(text(" Sign Up"));

        $(By.name("userName")).setValue("un_Ammar");
        $(By.name("pass")).setValue("un_1234").pressEnter();

        if ($(By.id("error")).exists()) {
            // User already exists
            $(By.id("error")).shouldBe(Condition.visible).shouldHave(text("un_Ammar is already used try"));
            String errorMessage = $(By.id("error")).getText();

            // Extract the recommended username from the error message
            String recommendName = errorMessage.split(":")[1].trim();

            System.out.println("Recommended Username: " + recommendName);

            // Use the recommended username for the signup attempt
            $(By.name("userName")).setValue(recommendName);
            $(By.id("btn-submit")).pressEnter();

            // Check if redirected to the home page after using the recommended username
            webdriver().shouldHave(url("http://localhost:8080/"));
        } else {
            webdriver().shouldHave(url("http://localhost:8080/"));
        }
    }

}
