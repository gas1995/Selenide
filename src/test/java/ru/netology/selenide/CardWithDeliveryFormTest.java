package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardWithDeliveryFormTest {
    private WebDriver driver;


    private String createDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldConfirmCardDelivery() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        String currentDate = createDate(5, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Потемкин-Таврический Григорий Александрович");
        $("[data-test-id=phone] input").setValue("+01234567890");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно! Встреча успешно забронирована на " + currentDate));


    }

}