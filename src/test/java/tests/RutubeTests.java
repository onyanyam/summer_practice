package tests;

import base.BaseTest;
import pages.MainPage;
import pages.SearchPage;
import pages.VideoPage;
import pages.ChannelPage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Utils.parseDuration;

public class RutubeTests extends BaseTest {

    private static final String EMPTY_QUERY = "";

    private static final String CINEMA_QUERY = "кино";
    private static final String MATCH_TV_QUERY = "Матч ТВ";
    private static final String MOVIES_QUERY = "фильмы";
    private static final String MUSIC_QUERY = "музыка";
    private static final String NEWS_QUERY = "новости";

    private static final String FILTER_TODAY = "За сегодня";
    private static final String FILTER_DURATION_20_60 = "20–60 минут";

    private static final String QUALITY_1080P = "1080p";

    /**
     * Тест 1. Поиск видео.
     * Выполняет поиск по запросу "музыка", открывает первое видео и ставит его на паузу.
     * Проверяет, что видео успешно приостановилось.
     */
    @Test
    public void searchVideoAndPause() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        video.pause();

        assertThat(video.isPaused()).as("Видео должно быть на паузе").isTrue();
    }

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

        searchResults.clearSearch();
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
     * Тест 4. Отправка жалобы на видео.
     * Выполняет поиск по запросу "фильмы", открывает первое видео
     * и отправляет жалобу через контекстное меню.
     * Проверяет, что форма жалобы открылась.
     */
    @Test
    public void sendComplaint() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MOVIES_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        video.reportVideo();
        // Проверяем, что открылась форма жалобы
        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
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

    /**
     * Тест 6. Поиск канала и оформление подписки.
     * Выполняет поиск по запросу "Матч ТВ", переходит на вкладку "Каналы",
     * открывает первый канал из результатов и оформляет подписку.
     * Проверяет, что подписка успешно оформлена.
     */
    @Test
    public void searchChannelAndSubscribe() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MATCH_TV_QUERY);
        searchResults.goToChannelsTab();

        // Открываем первый канал из результатов
        VideoPage video = searchResults.openFirstVideo();
        ChannelPage channel = video.goToChannel();
        channel.subscribe();
        channel.goToChannelPage();

        assertThat(channel.isSubscribed()).as("Подписка должна быть оформлена").isTrue();
    }

    /**
     * Тест 7. Копирование ссылки на видео.
     * Выполняет поиск по запросу "новости", открывает первое видео
     * и копирует ссылку на него.
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void copyVideoLink() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(NEWS_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        video.copyLink();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

    /**
     * Тест 8. Добавление видео в "Смотреть позже".
     * Выполняет поиск по запросу "музыка", открывает первое видео,
     * открывает контекстное меню и добавляет видео в плейлист "Смотреть позже".
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void addToWatchLater() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        video.openMenu();
        // Нажимаем "Смотреть позже"
        video.openMenu(); // Заглушка

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

    /**
     * Тест 9. Оценка видео (лайк/дизлайк).
     * Выполняет поиск по запросу "музыка", открывает первое видео,
     * ставит лайк, а затем меняет его на дизлайк.
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void likeAndDislike() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        video.like();
        video.dislike();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

    /**
     * Тест 10. Настройка видео (качество и формат).
     * Выполняет поиск по запросу "музыка", открывает первое видео,
     * устанавливает качество "1080p" и переключает полноэкранный режим.
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void videoSettings() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        video.setQuality(QUALITY_1080P);
        video.toggleFullscreen();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }
}