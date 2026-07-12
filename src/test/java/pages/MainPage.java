package pages;

import elements.Input;
import elements.Button;
import elements.Link;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, представляющий главную страницу Rutube.
 * Содержит элементы и методы для навигации по главной странице
 */
public class MainPage extends BasePage {

    // Реальные элементы на главной странице Rutube
    private final Input searchInput = Input.byPlaceholder("Поиск");
    private final Button searchButton = Button.byText("Найти");
    private final Button logoLink = Button.byXpath("//button[@data-testid='rutube-logo']");
    private final Button topLink = Button.byXpath("//a[contains(@href, '/feeds/top/')]");

    /**
     * Конструктор главной страницы.
     */
    public MainPage() {
        super(MainPage.class, "//div[contains(@class, 'main-page')]", "");
    }

    /**
     * Выполняет поиск по указанному запросу.
     * Если запрос пустой или null, выполняет поиск с пустым полем.
     */
    public SearchPage search(String query) {
        if (query == null || query.isEmpty()) {
            searchInput.fill("");
            searchInput.pressEnter();
        } else {
            searchInput.fill(query);
            searchInput.pressEnter();
        }
        return new SearchPage();
    }

    /**
     * Переходит в раздел "В топе" через ссылку в левом меню.
     */
    public void goToTop() {
        topLink.click();
    }

    /**
     * Возвращается на главную страницу через клик по логотипу Rutube.
     */
    public void openMainPage() {
        logoLink.click();
    }
}