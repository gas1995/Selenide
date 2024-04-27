package ru.netology.selenide;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldFailInvalidName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Federico Fellini");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+12345678910");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());


    }

    @Test
    public void shouldFailEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+12345678910");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());

    }

    @Test
    public void shouldFailEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Федерико Феллини");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldFailInvalidPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Федерико Феллини");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("2778945");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }


    @Test
    public void shouldFailEmptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Федерико Феллини");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+01234567891");
        // driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }


}