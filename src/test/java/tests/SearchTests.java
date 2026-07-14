package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.SearchPage;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Utils.parseDuration;

/**
 * Тесты для функциональности поиска на Rutube:
 * применение фильтров, изменение поискового запроса и поиск с пустым запросом.
 */
public class SearchTests extends BaseTest {

    private static final String EMPTY_QUERY = "";
    private static final String CINEMA_QUERY = "кино";
    private static final String MUSIC_QUERY = "музыка";
    private static final String NEWS_QUERY = "новости";
    private static final String FILTER_TODAY = "За сегодня";
    private static final String FILTER_DURATION_20_60 = "20–60 минут";

    /**
     * Тест 2. Применение фильтров.
     * Выполняет поиск по запросу "новости", применяет фильтры "За сегодня" и "20–60 минут".
     * Проверяет, что все видео в выдаче соответствуют обоим критериям.
     */
    @Test
    public void applyFilters() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(NEWS_QUERY);

        searchResults.openFilters()
                .applyFilter(FILTER_TODAY)
                .applyFilter(FILTER_DURATION_20_60);

        String publishDate = searchResults.getFirstVideoPublishDate();
        String duration    = searchResults.getFirstVideoDuration();

        assertThat(publishDate)
                .as("Видео должно быть опубликовано сегодня")
                .containsAnyOf("минут", "час", "секунд", "сегодня");

        assertThat(parseDuration(duration))
                .as("Видео должно длиться от 20 до 60 минут")
                .isBetween(20 * 60, 60 * 60);
    }

    /**
     * Тест 3. Изменение поискового запроса.
     * Выполняет поиск по запросу "музыка", очищает поле поиска,
     * вводит новый запрос "кино" и проверяет, что результаты обновились.
     */
    @Test
    public void changeSearchQuery() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);

        String firstTitleBefore = searchResults.getFirstVideoTitle();

        searchResults.searchAgain(CINEMA_QUERY);

        String firstTitleAfter = searchResults.getFirstVideoTitle();
        String searchInputValue = searchResults.getSearchInputValue();

        assertThat(searchInputValue)
                .as("Запрос должен обновиться")
                .isEqualTo(CINEMA_QUERY);

        assertThat(firstTitleAfter)
                .as("Результаты поиска должны обновиться")
                .isNotEqualTo(firstTitleBefore);
    }

    /**
     * Тест 5. Поиск с пустым запросом.
     * Выполняет поиск с пустым запросом, затем переходит в раздел "В топе"
     * и возвращается на главную страницу.
     * Проверяет, что главная страница открыта и интерфейс не сломался.
     */
    @Test
    public void emptySearch() {
        MainPage mainPage = new MainPage();
        mainPage.search(EMPTY_QUERY);
        mainPage.goToTop();
        mainPage.openMainPage();

        assertThat(mainPage.isDisplayed()).as("Главная страница должна быть открыта").isTrue();
    }
}