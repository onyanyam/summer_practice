package pages;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Link;
import elements.Tab;
import elements.Input;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, представляющий страницу поиска.
 * Содержит элементы и методы для получения информации о видео,
 * настройки фильтров и самого поиска
 */
public class SearchPage extends BasePage {

    private final SelenideElement filtersPanel =
            $x("//div[contains(@class, 'search-filters-module__searchFiltersAdvanced')]");
    private final SelenideElement firstCard = $x("//article[@data-pos-num='0']");

    private final Button filterButton = Button.byXpath("//div[contains(text(), 'Фильтры')]/parent::button");
    private final Button channelsTab = Button.byXpath("//button[@role='tab' and contains(., 'Каналы')]");
    private final Button firstVideo = Button.byXpath("//a[contains(@class, 'wdp-card-poster-module__posterWrapperBase')]");
    private final Button clearButton = Button.byXpath("//button[@aria-label='Очистить поле']");
    private final Input searchInput = Input.byPlaceholder("Поиск");

    /**
     * Проверяет, открыта ли панель фильтров.
     */
    private boolean isFiltersOpened() {
        return filtersPanel.isDisplayed();
    }

    /**
     * Ожидает загрузки результатов поиска.
     * Проверяет наличие первого видео на странице результатов.
     */
    private void waitForResultsLoad() {
        firstVideo.waitForLoad();
    }

    /**
     * Конструктор страницы поиска.
     */
    public SearchPage() {
        super(SearchPage.class, "//div[contains(@class, 'search-results')]", "");
    }

    /**
     * Открывает панель фильтров.
     * Предварительно ожидает загрузки результатов, затем проверяет,
     * открыты ли фильтры, и если нет, то кликает по кнопке "Фильтры".
     */
    public SearchPage openFilters() {
        // Ждем загрузки результатов и открываем фильтры, если они не открыты
        waitForResultsLoad();

        if (!isFiltersOpened()) {
            filterButton.click();
            filtersPanel.shouldBe(visible);
        }

        return this;
    }

    /**
     * Применяет фильтр по указанному значению.
     * Ожидает загрузки результатов, находит фильтр по тексту,
     * кликает по нему и проверяет, что он стал активным.
     */
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

    /**
     * Открывает первое видео в результатах поиска.
     */
    public VideoPage openFirstVideo() {
        firstVideo.click();
        return new VideoPage();
    }

    /**
     * Получает заголовок первого видео в результатах поиска.
     */
    public String getFirstVideoTitle() {
        return firstCard
                .$x(".//a[contains(@class, 'videoTitle')]")
                .getText();
    }

    /**
     * Получает дату публикации первого видео в результатах поиска.
     */
    public String getFirstVideoPublishDate() {
        return firstCard
                .$x(".//p[contains(@class, 'metaInfoPublishDate')]")
                .getText();
    }

    /**
     * Получает длительность первого видео в результатах поиска.
     */
    public String getFirstVideoDuration() {
        return firstCard
                .$x(".//span[contains(@class, 'duration')]")
                .getText();
    }

    /**
     * Получает текущее значение из поля поиска.
     */
    public String getSearchInputValue() {
        return searchInput.getBaseElement().getValue();
    }

    /**
     * Переключается на вкладку "Каналы" в результатах поиска.
     */
    public SearchPage goToChannelsTab() {
        channelsTab.click();
        return this;
    }

    /**
     * Очищает поле поиска с помощью кнопки "крестик".
     */
    public SearchPage clearSearch() {
        clearButton.click();
        return this;
    }

    /**
     * Выполняет новый поиск с указанным запросом.
     * Очищает поле, вводит новый запрос и нажимает Enter.
     */
    public SearchPage searchAgain(String query) {
        searchInput.clear();
        searchInput.fill(query);
        searchInput.pressEnter();
        return this;
    }
}