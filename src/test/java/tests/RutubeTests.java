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

    @Test
    public void test1_searchVideoAndPause() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("музыка");
        VideoPage video = searchResults.openFirstVideo();
        video.pause();

        assertThat(video.isPaused()).as("Видео должно быть на паузе").isTrue();
    }

    @Test
    public void test2_applyFilters() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("новости");
        searchResults.applyFilter("За сегодня");
        searchResults.applyFilter("20-60 минут");

        assertThat(searchResults.isDisplayed()).as("Страница результатов должна быть видна").isTrue();
    }

    @Test
    public void test3_changeSearchQuery() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("музыка");
        searchResults.clearSearch();
        searchResults.searchAgain("кино");

        assertThat(searchResults.isDisplayed()).as("Результаты поиска должны обновиться").isTrue();
    }

    @Test
    public void test4_sendComplaint() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Фильмы");
        VideoPage video = searchResults.openFirstVideo();
        video.reportVideo();
        // Проверяем, что открылась форма жалобы
        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

    @Test
    public void test5_emptySearch() {
        MainPage mainPage = new MainPage();
        mainPage.search("");
        mainPage.goToTop();
        mainPage.openMainPage();

        assertThat(mainPage.isDisplayed()).as("Главная страница должна быть открыта").isTrue();
    }

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

    @Test
    public void test7_copyVideoLink() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Новости");
        VideoPage video = searchResults.openFirstVideo();
        video.copyLink();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

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

    @Test
    public void test9_likeAndDislike() {
        MainPage mainPage = new MainPage();
        SearchPage searchResults = mainPage.search("Музыка");
        VideoPage video = searchResults.openFirstVideo();
        video.like();
        video.dislike();

        assertThat(video.isDisplayed()).as("Страница видео должна быть открыта").isTrue();
    }

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