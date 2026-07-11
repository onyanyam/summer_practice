package pages;

import elements.Input;
import elements.Button;
import elements.Link;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends BasePage {

    // Реальные элементы на главной странице Rutube
    private final Input searchInput = Input.byPlaceholder("Поиск");
    private final Button searchButton = Button.byText("Найти");
    private final Button logoLink = Button.byXpath("//button[@data-testid='rutube-logo']");
    private final Button topLink = Button.byXpath("//a[contains(@href, '/feeds/top/')]");

    public MainPage() {
        super(MainPage.class, "//div[contains(@class, 'main-page')]", "");
    }

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

    public void goToTop() {
        topLink.click();
    }

    public void openMainPage() {
        logoLink.click();
    }
}