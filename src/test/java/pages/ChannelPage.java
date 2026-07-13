package pages;

import elements.Button;
import elements.Link;

/**
 * Класс, представляющий страницу канала на Rutube.
 * Содержит методы для управления подпиской и навигации по каналу
 */
public class ChannelPage extends BasePage {

    // Кнопка подписки на канал
    private final Button subscribeButton = Button.byXpath("//button[contains(@class, 'wdp-subscribe-button-module__button')]");
    // Ссылка на логотип канала
    private final Link channelLogo = Link.byClass("wdp-video-options-row-module__authorAvatar");

    /**
     * Конструктор страницы канала.
     */
    public ChannelPage() {
        super(ChannelPage.class, "//div[contains(@class, 'channel-page')]", "");
    }

    /**
     * Оформляет подписку на канал.
     * Проверяет, отображается ли кнопка подписки, и если да, то кликает по ней.
     * Если кнопка уже в статусе "Вы подписаны", метод ничего не делает.
     */
    public ChannelPage subscribe() {
        if (subscribeButton.isDisplayed()) {
            subscribeButton.click();
        }
        return this;
    }

    /**
     * Проверяет, оформлена ли подписка на канал.
     * Ищет кнопку с текстом "Вы подписаны" и проверяет её отображение.
     */
    public boolean isSubscribed() {
        return Button.byXpath("//button[contains(@class, 'wdp-subscribe-button-module__button') and contains(., 'Вы подписаны')]").isDisplayed();
    }

    /**
     * Переходит на страницу канала через клик по логотипу канала.
     * Используется для навигации со страницы видео на страницу канала.
     */
    public ChannelPage goToChannelPage() {
        channelLogo.click();
        return this;
    }
}