package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class CardDelivery {
    public String setDate (int date){
        return LocalDate.now().plusDays(date).format( DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void openUrl() {
    open("http://localhost:9999");
    }

    @Test
    void shouldSuccessOrderDelivery() {
        String date = setDate(3);

        $("[data-test-id='city'] .input__control").setValue("Волгоград");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(Keys.chord(BACK_SPACE,
                        date));
        $("[data-test-id='name'] .input__control").setValue("Петр Иванов");
        $("[data-test-id='phone'] .input__control").setValue("+77777777777");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована"))
                .shouldBe(Condition.text(date));
    }
}
