package com.equiptal;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

public class TestPosts {

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

    @Before
    public void login() {
        open("http://localhost:8080/login");
        $(By.name("userName")).setValue("un_Ammar");
        $(By.name("pass")).setValue("un_1234").pressEnter();
    }

    @Test
    public void shouldIncrementLikesOnLikeGiven() {
        int preLike = Integer.parseInt($((".like span")).getText());

        $((".like")).click();
        $((".like span")).shouldHave(exactText(String.valueOf(preLike + 1)));

    }

    @Test
    public void shouldDecrementLikesOnLikeTaken() {
        int preLike = Integer.parseInt($((".like span")).getText());

        $((".like")).click();
        $((".like span")).shouldHave(exactText(String.valueOf(preLike - 1)));

    }

    @Test
    public void shouldTweetOnTweet() {

        $(("#tweet")).click();
        $(("#make-post")).shouldBe(Condition.visible);
        $(By.id("comment")).setValue("new Test tweet");
        $(("#submit_tweet")).click();
        $(("#make-post")).shouldBe(Condition.hidden);

        $(By.cssSelector(".container p"))
                .shouldBe(exactTextCaseSensitive("new Test tweet"));
        $((".like span")).shouldHave(exactText("0"));
    }

    @Test
    public void shouldPostRetweetOnRetweet() {
        $((".retweet")).click();

        int beforeReTweetLikes = Integer.parseInt($((".like span")).getText());

        // check if posted
        $(By.cssSelector(".container h6"))
                .shouldBe(exactTextCaseSensitive("retweeted"));

        $(By.cssSelector(".container a.text-decoration-none"))
                .shouldBe(exactTextCaseSensitive("un_Ammar"));

        // likes count shold be the same
        $((".like span")).shouldHave(exactText(String.valueOf(beforeReTweetLikes)));

    }

    @Test
    public void shouldSearchAndDisplayProfileOnClickOnImage() {
        $(By.name("name")).click();
        $(By.name("name")).setValue("un_Ammar1");
        $(By.id("pop-card")).shouldBe(Condition.visible);
        $(By.cssSelector("#pop-card .card-body a")).shouldBe(Condition.visible).click();
        sleep(2000);
        webdriver().shouldHave(url("http://localhost:8080/profile/?profile=un_Ammar1"));

    }
}
