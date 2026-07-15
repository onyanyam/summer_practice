package tests;

import base.BaseTest;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.PlaylistPage;
import pages.SearchPage;
import pages.VideoPage;

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
    private static final String QUALITY_720P = "720p";

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

        assertThat(video.isPaused())
                .as("Видео должно быть на паузе")
                .isTrue();
    }

    /**
     * Тест 4. Отправка жалобы на видео.
     * Выполняет поиск по запросу "фильмы", открывает первое видео
     * и отправляет жалобу через контекстное меню.
     * Проверяет, что форма жалобы заполнена и успешно отправлена.
     */
    @Test
    public void sendComplaint() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MOVIES_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        video.reportVideo();

        assertThat(video.isComplaintSent())
                .as("Уведомление об отправке жалобы должно появиться")
                .isTrue();
    }

    /**
     * Тест 7. Копирование ссылки на видео.
     * Выполняет поиск по запросу "новости", открывает первое видео
     * и копирует ссылку на него.
     * Проверяет, что ссылка на видео скопирована.
     */
    @Test
    public void copyVideoLink() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(NEWS_QUERY);
        VideoPage video = searchResults.openFirstVideo();
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
    @Test
    public void addToWatchLater() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);
        VideoPage video = searchResults.openFirstVideo();

        String videoTitle = video.getVideoTitle();
        video.addToWatchLater();
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
    @Test
    public void likeAndDislike() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);
        VideoPage video = searchResults.openFirstVideo();

        String videoTitle = video.getVideoTitle();
        video.like();
        Selenide.sleep(1500);

        video.dislike();
        Selenide.sleep(1500);

        PlaylistPage liked = PlaylistPage.likedPlaylist();
        assertThat(liked.isVideoNotInPlaylist(videoTitle))
                .as("После дизлайка видео не должно быть в 'Понравилось'")
                .isTrue();
    }

    /**
     * Тест 10. Настройка видео (качество и формат).
     * Выполняет поиск по запросу "музыка", открывает первое видео,
     * устанавливает качество "1080p" и переключает полноэкранный режим.
     * Проверяет, что качество и формат успешно применены к видео.
     */
    @Test
    public void videoSettings() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search(MUSIC_QUERY);
        VideoPage video = searchResults.openFirstVideo();
        Selenide.sleep(25000);
        video.setQuality(QUALITY_720P);
        video.toggleFullscreen();

        assertThat(video.getCurrentQuality())
                .as("Качество должно быть 720p")
                .isEqualTo("720p");

        assertThat(video.isFullscreen())
                .as("Широкий экран должен быть включён")
                .isTrue();
    }
}
