package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardsTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestDeliveryCard() {
        long daysGap = 4;
        String newDatePattern = "dd.MM.yyyy";

        $x("//*[@data-test-id='city']//input").setValue("Краснодар");
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.CONTROL + "a");
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").sendKeys(generateDate(daysGap, newDatePattern));
        $x("//*[@data-test-id='name']//input").setValue("Александр Бесстужев-Никольский");
        $x("//*[@data-test-id='phone']//input").setValue("+79624569137");
        $x("//*[@data-test-id='agreement']").click();
        $x("//div[contains (@class, 'grid-row')]//button").click();
        $x("//div[contains (text(), 'Успешно!')]").shouldBe(Condition.visible, Duration.ofMillis(15000));
        $x("//*[@class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(daysGap, newDatePattern)), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldTestComplexDeliveryCard() {
        long daysGap = 7;
        String newDatePattern = "dd.MM.yyyy";

        $x("//*[@data-test-id='city']//input").setValue("См");
        $x("//*[contains(text(),'Смоленск')]").click();
        $x("//*[@data-test-id='date']//button").click();
        if (LocalDate.now().getMonthValue() != LocalDate.now().plusDays(daysGap).getMonthValue()) {
            $x("//*[(@data-step='1')]").click();
        }
        $x("//*[@data-day='" + generateMilliseconds(daysGap) + "']").click();
        $x("//*[@data-test-id='name']//input").setValue("Иван Петров-Разумовский");
        $x("//*[@data-test-id='phone']//input").setValue("+79628429586");
        $x("//*[@data-test-id='agreement']").click();
        $x("//div[contains (@class, 'grid-row')]//button").click();
        $x("//div[contains (text(), 'Успешно!')]").shouldBe(Condition.visible, Duration.ofMillis(15000));
        $x("//*[@class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(daysGap, newDatePattern)), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    public long generateMilliseconds(long addDays) {
        return LocalDate.now().plusDays(addDays).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }
}