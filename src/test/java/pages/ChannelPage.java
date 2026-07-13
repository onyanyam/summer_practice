package pages;

import elements.Button;
import elements.Link;

/**
 * Класс, представляющий страницу канала на Rutube.
 * Содержит методы для управления подпиской и навигации по каналу
 */
public class ChannelPage extends BasePage {

    private static final String SUBSCRIBE_BUTTON_XPATH =
            "//button[contains(@class, 'wdp-subscribe-button-module__button')]";

    private static final String SUBSCRIBED_BUTTON_XPATH =
            "//button[contains(@class, 'wdp-subscribe-button-module__button') and contains(., 'Вы подписаны')]";

    private static final String CHANNEL_LOGO_CLASS = "wdp-video-options-row-module__authorAvatar";

    private static final String CHANNEL_PAGE_ROOT = "//div[contains(@class, 'channel-page')]";

    // Кнопка подписки на канал
    private final Button subscribeButton = Button.byXpath(SUBSCRIBE_BUTTON_XPATH);
    // Ссылка на логотип канала
    private final Link channelLogo = Link.byClass(CHANNEL_LOGO_CLASS);

    /**
     * Конструктор страницы канала.
     */
    public ChannelPage() {
        super(ChannelPage.class, CHANNEL_PAGE_ROOT, "");
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
        return Button.byXpath(SUBSCRIBED_BUTTON_XPATH).isDisplayed();
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