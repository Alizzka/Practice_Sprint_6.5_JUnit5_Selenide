import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Variant1Test {

    @BeforeEach
    public void setup() {
        Configuration.browser = "chrome"; // Устанавливаем браузер
        Configuration.headless = false; // Открытие браузера в видимом режиме
        Configuration.timeout = 5000; // Установка тайм-аута для ожидания элементов
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver(); // Закрытие браузера после теста
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/questions.csv")
    public void allQuestionsHaveAnswerText(String question, String answer) {
        open("https://qa-scooter.praktikum-services.ru/"); // Открытие страницы
        $(".App_CookieButton__3cvqF").click(); // Нажатие на кнопку cookies

        // Нахождение элемента вопроса по тексту и нажатие на него
        SelenideElement questionElement = $$("div.accordion__button")
                .findBy(text(question)) // Поиск элемента по тексту вопроса
                .shouldBe(visible); // Убедиться, что элемент виден

        questionElement.click();

        // Нахождение элемента ответа по тексту
        SelenideElement answerElement = $$("div.accordion__panel p") // Ищем все ответы внутри панели
                .findBy(text(answer)) // Поиск по тексту ответа
                .shouldBe(visible); // Убеждаемся, что ответ видим

        assertTrue(answerElement.isDisplayed(), "Ответ не отображается."); // Проверка, отображается ли ответ
    }
}








