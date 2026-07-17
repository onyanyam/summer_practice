package pages;

import base.BasePage;
import com.codeborne.selenide.Selenide;
import elements.Input;
import elements.Button;

/**
 * Класс, представляющий главную страницу Rutube.
 * Содержит элементы и методы для навигации по главной странице
 */
public class MainPage extends BasePage {

    private static final String SEARCH_PLACEHOLDER = "Поиск";
    private static final String SEARCH_BUTTON_TEXT = "Найти";

    private static final String LOGO_BUTTON_XPATH =
            "//*[@data-testid='rutube-logo']";

    private static final String TOP_LINK_XPATH =
            "//nav[contains(@class, 'menu')]//a[contains(., 'В топе')]";

    private static final String MAIN_PAGE_ROOT =
            "//div[contains(@class, 'main-page')]";
    private static final String RUTUBE_LINK =
            "https://rutube.ru/";
    private static final String RUTUBE_TOP_LINK =
            "https://rutube.ru/feeds/top/";

    private final Input searchInput = Input.byPlaceholder(SEARCH_PLACEHOLDER);
    private final Button searchButton = Button.byText(SEARCH_BUTTON_TEXT);
    private final Button logoLink = Button.byXpath(LOGO_BUTTON_XPATH);
    private final Button topLink = Button.byXpath(TOP_LINK_XPATH);

    public MainPage() {
        super(MainPage.class, MAIN_PAGE_ROOT, "");
    }

    /**
     * Выполняет поиск по указанному запросу.
     * Если запрос пустой, выполняет поиск с пустым полем.
     */
    public SearchPage search(String query) {
        if (query != null && !query.isEmpty()) {
            searchInput.fill(query);
        } else {
            searchInput.fill("");;
        }
        return new SearchPage();
    }

    /**
     * Переходит в раздел "В топе" через ссылку в левом меню.
     */
    public void goToTop() {
        Selenide.open(RUTUBE_TOP_LINK);
    }

    /**
     * Возвращается на главную страницу через клик по логотипу Rutube.
     */
    public void openMainPage() {
        try {
            if (logoLink.isDisplayed()) {
                logoLink.click();
            } else {
                Selenide.open(RUTUBE_LINK);
            }
        } catch (Exception e) {
            Selenide.open(RUTUBE_LINK);
        }
    }
}