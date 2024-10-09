import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Variant3Test {

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 5000;         // Устанавливаем таймаут ожидания элементов
        open("https://qa-scooter.praktikum-services.ru/");
    }

    @AfterEach
    public void tearDown() {
        // Закрытие браузера после каждого теста
        closeWebDriver();
    }

    private static Stream<Arguments> data() {
        return Stream.of(
                arguments("Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                arguments("Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                arguments("Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру."),
                arguments("Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                arguments("Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                arguments("Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня."),
                arguments("Можно ли отменить заказ?", "Да, пока самокат не привезли."),
                arguments("Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void allQuestionsHaveAnswerText(String question, String answer) {
        // Закрываем окно с куки
        $(".App_CookieButton__3cvqF").shouldBe(visible).click();

        // Поиск нужного вопроса
        SelenideElement questionElement = $$("div.accordion__button")
                .findBy(text(question))
                .shouldBe(visible);

        questionElement.click(); // Кликаем на вопрос

        // Поиск и проверка текста ответа
        SelenideElement answerElement = $$("div.accordion__panel p")
                .findBy(text(answer))
                .shouldBe(visible);

        assertTrue(answerElement.isDisplayed(), "Ответ не отображается.");
    }
}


