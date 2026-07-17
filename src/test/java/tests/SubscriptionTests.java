package tests;

import base.BaseTest;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ChannelPage;
import pages.MainPage;
import pages.SearchPage;
import pages.VideoPage;
import utils.Logger;

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
    @DisplayName("Поиск канала и оформление подписки")
    @Test
    public void searchChannelAndSubscribe() {
        MainPage mainPage = new MainPage();

        Logger.info("Выполняем поиск по запросу: " + MATCH_TV_QUERY);
        SearchPage searchResults = mainPage.search(MATCH_TV_QUERY);

        Logger.info("Переходим на вкладку \"Каналы\"");
        searchResults.goToChannelsTab();

        Logger.info("Подписываемся на первый канал...");
        ChannelPage channel = searchResults.subscribeToFirstChannelAndGoToChannel();

        assertThat(channel.isSubscribed())
                .as("Подписка должна быть оформлена")
                .isTrue();
    }
}