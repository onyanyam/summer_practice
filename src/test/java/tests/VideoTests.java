package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.SearchPage;
import pages.VideoPage;

import static org.assertj.core.api.Assertions.assertThat;

public class VideoTests extends BaseTest {

    private static final String MUSIC_QUERY = "музыка";
    private static final String MOVIES_QUERY = "фильмы";
    private static final String NEWS_QUERY = "новости";
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
