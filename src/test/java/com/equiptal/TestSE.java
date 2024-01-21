package com.equiptal;

import static com.codeborne.selenide.Selenide.*;

import org.junit.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

public class TestSE {

    @Test
    public void searchBaeldung() {
        open("http://localhost:8080/login");

        SelenideElement searchbox = $(By.id("floatingInput"));
        searchbox.click();
        searchbox.sendKeys("1234");

        SelenideElement searchboxpass = $(By.id("floatingPassword"));
        searchboxpass.click();
        searchboxpass.sendKeys("1");

        $(By.id("btn-submit")).click();
    }
}
