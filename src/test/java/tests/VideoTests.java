package tests;

import base.BaseTest;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.PlaylistPage;
import pages.SearchPage;
import pages.VideoPage;
import utils.Logger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для функциональности просмотра и взаимодействия с видео на Rutube:
 * поиск, управление воспроизведением, настройка видео
 * и выполнение основных пользовательских действий.
 */
public class VideoTests extends BaseTest {

    private static final String MUSIC_QUERY = "музыка";
    private static final String MOVIES_QUERY = "фильмы";
    private static final String NEWS_QUERY = "новости";
    private static final String QUALITY_480P = "480p";

    /**
     * Тест 1. Поиск видео.
     * Выполняет поиск по запросу "музыка", открывает первое видео и ставит его на паузу.
     * Проверяет, что видео успешно приостановилось.
     */
    @DisplayName("Поиск видео и проверка паузы")
    @Test
    public void searchVideoAndPause() {
        MainPage mainPage = new MainPage();

        Logger.info("Выполняем поиск по запросу: " + MUSIC_QUERY);
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);

        Logger.info("Открываем первое видео...");
        VideoPage video = searchResults.openFirstVideo();

        Logger.info("Ставим видео на паузу...");
        video.pause();

        assertThat(video.isPaused())
                .as("Видео должно быть на паузе")
                .isTrue();
    }

    /**
     * Тест 4. Отправка жалобы на видео.
     * Выполняет поиск по запросу "фильмы", открывает первое видео
     * и открывает жалобу через контекстное меню.
     * Проверяет, что форма жалобы открыта и закрывает ее.
     */
    @DisplayName("Проверка формы жалобы")
    @Test
    public void sendComplaint() {
        MainPage mainPage = new MainPage();

        Logger.info("Выполняем поиск по запросу: " + MOVIES_QUERY);
        SearchPage searchResults = mainPage.search(MOVIES_QUERY);

        Logger.info("Открываем первое видео...");
        VideoPage video = searchResults.openFirstVideo();

        Logger.info("Нажимаем кнопку \"Пожаловаться\"...");
        video.reportVideo();

        assertThat(video.isComplaintFormDisplayed())
                .as("Форма жалобы должна открыться")
                .isTrue();

        video.closeComplaintForm();
    }

    /**
     * Тест 7. Копирование ссылки на видео.
     * Выполняет поиск по запросу "новости", открывает первое видео
     * и копирует ссылку на него.
     * Проверяет, что ссылка на видео скопирована.
     */
    @DisplayName("Копирование ссылки на видео")
    @Test
    public void copyVideoLink() {
        MainPage mainPage = new MainPage();

        Logger.info("Выполняем поиск по запросу: " + NEWS_QUERY);
        SearchPage searchResults = mainPage.search(NEWS_QUERY);

        Logger.info("Открываем первое видео...");
        VideoPage video = searchResults.openFirstVideo();

        Logger.info("Открываем меню \"Поделиться\" и нажимаем \"Копировать ссылку\"...");
        video.copyLink();

        assertThat(video.isLinkCopied())
                .as("Ссылка на видео должна быть успешно скопирована в буфер обмена")
                .isTrue();
    }

    /**
     * Тест 8. Добавление видео в "Смотреть позже".
     * Выполняет поиск по запросу "музыка", открывает первое видео,
     * открывает контекстное меню и добавляет видео в плейлист "Смотреть позже".
     * Проверяет, что видео находится в разделе смотреть позже.
     */
    @DisplayName("Добавление видео в \"Смотреть позже\"")
    @Test
    public void addToWatchLater() {
        MainPage mainPage = new MainPage();

        Logger.info("Выполняем поиск по запросу: " + MUSIC_QUERY);
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);

        Logger.info("Открываем первое видео...");
        VideoPage video = searchResults.openFirstVideo();

        Logger.info("Получаем данные о видео...");
        String videoTitle = video.getVideoTitle();

        Logger.info("Добавляем видео в \"Смотреть позже\"...");
        video.addToWatchLater();

        Logger.info("Открываем плейлист \"Смотреть позже\"...");
        PlaylistPage watchLater = PlaylistPage.watchLaterPlaylist();

        assertThat(watchLater.isVideoInPlaylist(videoTitle))
                .as("Видео должно быть в плейлисте 'Смотреть позже'")
                .isTrue();
    }

    /**
     * Тест 9. Оценка видео (лайк/дизлайк).
     * Выполняет поиск по запросу "музыка", открывает первое видео,
     * ставит лайк, а затем меняет его на дизлайк.
     * Проверяет, что видео НЕ находится в разделе "Понравилось".
     */
    @DisplayName("Оценка видео (лайк / дизлайк)")
    @Test
    public void likeAndDislike() {
        MainPage mainPage = new MainPage();

        Logger.info("Выполняем поиск по запросу: " + MUSIC_QUERY);
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);

        Logger.info("Открываем первое видео...");
        VideoPage video = searchResults.openFirstVideo();

        Logger.info("Получаем данные о первом видео...");
        String videoTitle = video.getVideoTitle();

        Logger.info("Ставим лайк...");
        video.like();

        Logger.info("Ставим дизлайк...");
        video.dislike();

        Logger.info("Открываем плейлист \"Понравилось\"");
        PlaylistPage liked = PlaylistPage.likedPlaylist();

        assertThat(liked.isVideoNotInPlaylist(videoTitle))
                .as("После дизлайка видео не должно быть в 'Понравилось'")
                .isTrue();
    }

    /**
     * Тест 10. Настройка видео (качество и формат).
     * Выполняет поиск по запросу "музыка", открывает первое видео,
     * устанавливает качество "480p" и переключает полноэкранный режим.
     * Проверяет, что качество и формат успешно применены к видео.
     */
    @DisplayName("Качество видеоролика и формат")
    @Test
    public void videoSettings() {
        MainPage mainPage = new MainPage();

        Logger.info("Выполняем поиск по запросу: " + MUSIC_QUERY);
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);

        Logger.info("Открываем первое видео...");
        VideoPage video = searchResults.openFirstVideo();

        Logger.info("Ждем загрузки видеоплеера...");
        video.getVideoPlayer().waitForLoad();

        video.getVideoPlayer().hover();

        Logger.info("Устанавливаем новое качество видео: " + QUALITY_480P);
        video.setQuality(QUALITY_480P);

        Logger.info("Открываем видео на весь экран...");
        video.toggleFullscreen();

        assertThat(video.getCurrentQuality())
                .as("Качество должно быть 480p")
                .isEqualTo(QUALITY_480P);

        assertThat(video.isFullscreen())
                .as("Широкий экран должен быть включён")
                .isTrue();
    }
}
