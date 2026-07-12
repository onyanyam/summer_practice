package pages;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Link;
import elements.Tab;
import elements.Input;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class SearchPage extends BasePage {

    private final SelenideElement filtersPanel =
            $x("//div[contains(@class, 'search-filters-module__searchFiltersAdvanced')]");
    private final Button filterButton = Button.byXpath("//div[contains(text(), 'Фильтры')]/parent::button");
    private final Button channelsTab = Button.byXpath("//button[@role='tab' and contains(., 'Каналы')]");
    private final Button firstVideo = Button.byXpath("//a[contains(@class, 'wdp-card-poster-module__posterWrapperBase')]");
    private final Button clearButton = Button.byXpath("//button[@aria-label='Очистить поле']");
    private final Input searchInput = Input.byPlaceholder("Поиск");

    private boolean isFiltersOpened() {
        return filtersPanel.isDisplayed();
    }

    private void waitForResultsLoad() {
        // Проверяем загрузку результатов по первому видео
        firstVideo.waitForLoad();
    }

    public SearchPage() {
        super(SearchPage.class, "//div[contains(@class, 'search-results')]", "");
    }

    public SearchPage openFilters() {
        // Ждем загрузки результатов и открываем фильтры, если они не открыты
        waitForResultsLoad();

        if (!isFiltersOpened()) {
            filterButton.click();
            filtersPanel.shouldBe(visible);
        }

        return this;
    }

    public SearchPage applyFilter(String filterValue) {
        // Ждем загрузки результатов
        waitForResultsLoad();

        // Ждем появления необходимого фильтра
        SelenideElement option = $x(String.format("//button[contains(text(), '%s')]", filterValue));
        option.shouldBe(visible);

        // Выбираем фильтр и ожидаем активации
        option.click();
        option.shouldHave(
                cssClass("search-filters-module__searchFiltersAdvancedBlockButtonActive")
        );

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