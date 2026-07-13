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

    private static final String SEARCH_PLACEHOLDER = "Поиск";


    private static final String FILTERS_PANEL_XPATH =
            "//div[contains(@class, 'search-filters-module__searchFiltersAdvanced')]";

    private static final String FILTER_BUTTON_XPATH =
            "//div[contains(text(), 'Фильтры')]/parent::button";

    private static final String FILTER_OPTION_XPATH =
            "//button[contains(text(), '%s')]";

    private static final String ACTIVE_FILTER_CLASS =
            "search-filters-module__searchFiltersAdvancedBlockButtonActive";


    private static final String FIRST_CARD_XPATH =
            "//article[@data-pos-num='0']";

    private static final String FIRST_VIDEO_XPATH =
            "//a[contains(@class, 'wdp-card-poster-module__posterWrapperBase')]";

    private static final String FIRST_VIDEO_TITLE_XPATH =
            ".//a[contains(@class, 'videoTitle')]";

    private static final String FIRST_VIDEO_PUBLISH_DATE_XPATH =
            ".//p[contains(@class, 'metaInfoPublishDate')]";

    private static final String FIRST_VIDEO_DURATION_XPATH =
            ".//span[contains(@class, 'duration')]";


    private static final String CHANNELS_TAB_XPATH =
            "//button[@role='tab' and contains(., 'Каналы')]";

    private static final String CLEAR_BUTTON_XPATH =
            "//button[@aria-label='Очистить поле']";

    private static final String SEARCH_RESULTS_ROOT =
            "//div[contains(@class, 'search-results')]";


    private final SelenideElement filtersPanel = $x(FILTERS_PANEL_XPATH);
    private final SelenideElement firstCard = $x(FIRST_CARD_XPATH);

    private final Button filterButton = Button.byXpath(FILTER_BUTTON_XPATH);
    private final Button channelsTab = Button.byXpath(CHANNELS_TAB_XPATH);
    private final Button firstVideo = Button.byXpath(FIRST_VIDEO_XPATH);
    private final Button clearButton = Button.byXpath(CLEAR_BUTTON_XPATH);
    private final Input searchInput = Input.byPlaceholder(SEARCH_PLACEHOLDER);

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
        super(SearchPage.class, SEARCH_RESULTS_ROOT, "");
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
        waitForResultsLoad();

        SelenideElement option = findFilterOption(filterValue);
        option.click();
        waitForFilterActivation(option);

        return this;
    }

    // Метод для поиска фильтра
    private SelenideElement findFilterOption(String filterValue) {
        SelenideElement option = $x(String.format(FILTER_OPTION_XPATH, filterValue));
        option.shouldBe(visible);
        return option;
    }

    // Метод для ожидания активации
    private void waitForFilterActivation(SelenideElement option) {
        option.shouldHave(cssClass(ACTIVE_FILTER_CLASS));
    }

    /**
     * Открывает первое видео в результатах поиска.
     */
    public VideoPage openFirstVideo() {
        firstVideo.click();
        return new VideoPage();
    }

    /**
     * Метод, который находит элемент по Xpath внутри первого видео.
     */
    private String getFirstVideoAttribute(String xpath) {
        return firstCard.$x(xpath).getText();
    }

    /**
     * Геттеры
     */
    public String getFirstVideoTitle() {
        return getFirstVideoAttribute(FIRST_VIDEO_TITLE_XPATH);
    }

    public String getFirstVideoPublishDate() {
        return getFirstVideoAttribute(FIRST_VIDEO_PUBLISH_DATE_XPATH);
    }

    public String getFirstVideoDuration() {
        return getFirstVideoAttribute(FIRST_VIDEO_DURATION_XPATH);
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
        searchInput.fill(query);
        return this;
    }
}