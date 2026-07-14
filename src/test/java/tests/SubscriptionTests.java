package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.ChannelPage;
import pages.MainPage;
import pages.SearchPage;
import pages.VideoPage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для функциональности подписки на канал на Rutube.
 */
public class SubscriptionTests extends BaseTest {

    private static final String MATCH_TV_QUERY = "Матч ТВ";

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

        VideoPage video = searchResults.openFirstVideo();
        ChannelPage channel = video.goToChannel();
        channel.subscribe();
        channel.goToChannelPage();

        assertThat(channel.isSubscribed()).as("Подписка должна быть оформлена").isTrue();
    }
}