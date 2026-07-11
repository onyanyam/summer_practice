package pages;

import elements.Button;
import elements.Link;
import elements.Tab;
import elements.Input;

import static com.codeborne.selenide.Selenide.$x;

public class SearchPage extends BasePage {

    private final Button filterButton = Button.byXpath("//div[contains(text(), 'Фильтры')]/parent::button");
    private final Button channelsTab = Button.byXpath("//button[@role='tab' and contains(., 'Каналы')]");
    private final Button firstVideo = Button.byXpath("//a[contains(@class, 'wdp-card-poster-module__posterWrapperBase')]");
    private final Button clearButton = Button.byXpath("//button[@aria-label='Очистить поле']");
    private final Input searchInput = Input.byPlaceholder("Поиск");

    public SearchPage() {
        super(SearchPage.class, "//div[contains(@class, 'search-results')]", "");
    }

    public SearchPage applyFilter(String filterValue) {
        filterButton.click();
        // Ждем появления списка фильтров и выбираем нужный
        $x(String.format("//span[contains(text(), '%s')]", filterValue)).click();
        return this;
    }

    public VideoPage openFirstVideo() {
        firstVideo.click();
        return new VideoPage();
    }

    public SearchPage goToChannelsTab() {
        channelsTab.click();
        return this;
    }

    public SearchPage clearSearch() {
        clearButton.click();
        return this;
    }

    public SearchPage searchAgain(String query) {
        searchInput.clear();
        searchInput.fill(query);
        searchInput.pressEnter();
        return this;
    }
}