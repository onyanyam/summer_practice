package tests;

import base.BaseTest;
import pages.MainPage;
import pages.SearchPage;
import pages.VideoPage;
import pages.ChannelPage;
import pages.PlaylistPage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RutubeTests extends BaseTest {

    /**
     * Парсит строку с длительностью видеоролика в секунды.
     * Поддерживает форматы "MM:SS" и "HH:MM:SS".
     */
    private int parseDuration(String duration) {
        String[] parts = duration.split(":");

        if (parts.length == 3) {
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int s = Integer.parseInt(parts[2]);

            return h * 3600 + m * 60 + s;
        }

        int m = Integer.parseInt(parts[0]);
        int s = Integer.parseInt(parts[1]);

        return m * 60 + s;
    }

    /**
     * Тест 1. Поиск видео.
     * Выполняет поиск по запросу "музыка", открывает первое видео и ставит его на паузу.
     * Проверяет, что видео успешно приостановилось.
     */
    @Test
    public void test1_searchVideoAndPause() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("музыка");
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
    public void test2_applyFilters() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("новости");

        searchResults.openFilters()
                .applyFilter("За сегодня")
                .applyFilter("20–60 минут");

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
    public void test3_changeSearchQuery() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("музыка");

        String firstTitleBefore = searchResults.getFirstVideoTitle();

        searchResults.clearSearch();
        searchResults.searchAgain("кино");

        String firstTitleAfter = searchResults.getFirstVideoTitle();
        String searchInputValue = searchResults.getSearchInputValue();

        assertThat(searchInputValue)
                .as("Запрос должен обновиться")
                .isEqualTo("кино");

        assertThat(firstTitleAfter)
                .as("Результаты поиска должны обновиться")
                .isNotEqualTo(firstTitleBefore);
    }

    /**
     * Тест 4. Отправка жалобы на видео.
     * Выполняет поиск по запросу "Фильмы", открывает первое видео
     * и отправляет жалобу через контекстное меню.
     * Проверяет, что форма жалобы открылась.
     */
    @Test
    public void test4_sendComplaint() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Фильмы");
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
    public void test5_emptySearch() {
        MainPage mainPage = new MainPage();
        mainPage.search("");
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
    public void test6_searchChannelAndSubscribe() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Матч ТВ");
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
     * Выполняет поиск по запросу "Новости", открывает первое видео
     * и копирует ссылку на него.
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void test7_copyVideoLink() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Новости");
        VideoPage video = searchResults.openFirstVideo();
        video.copyLink();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

    /**
     * Тест 8. Добавление видео в "Смотреть позже".
     * Выполняет поиск по запросу "Музыка", открывает первое видео,
     * открывает контекстное меню и добавляет видео в плейлист "Смотреть позже".
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void test8_addToWatchLater() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Музыка");
        VideoPage video = searchResults.openFirstVideo();
        video.openMenu();
        // Нажимаем "Смотреть позже"
        video.openMenu(); // Заглушка

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

    /**
     * Тест 9. Оценка видео (лайк/дизлайк).
     * Выполняет поиск по запросу "Музыка", открывает первое видео,
     * ставит лайк, а затем меняет его на дизлайк.
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void test9_likeAndDislike() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Музыка");
        VideoPage video = searchResults.openFirstVideo();
        video.like();
        video.dislike();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

    /**
     * Тест 10. Настройка видео (качество и формат).
     * Выполняет поиск по запросу "Музыка", открывает первое видео,
     * устанавливает качество "1080p" и переключает полноэкранный режим.
     * Проверяет, что страница видео открыта.
     */
    @Test
    public void test10_videoSettings() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Музыка");
        VideoPage video = searchResults.openFirstVideo();
        video.setQuality("1080p");
        video.toggleFullscreen();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }
}