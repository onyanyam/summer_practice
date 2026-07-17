package pages;

import base.BasePage;
import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Link;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, представляющий страницу канала на Rutube.
 * Содержит методы для управления подпиской и навигации по каналу
 */
public class ChannelPage extends BasePage {

    private static final String SUBSCRIBE_BUTTON_XPATH =
            "//button[contains(@class, 'wdp-subscribe-button-module__button')]";

    private static final String SUBSCRIBED_BUTTON_XPATH =
            "//button[contains(@class, 'wdp-subscribe-button-module__button')]";

    private static final String CHANNEL_LOGO_CLASS = "wdp-video-options-row-module__authorAvatar";

    private static final String CHANNEL_PAGE_ROOT = "//div[contains(@class, 'channel-page')]";

    private final Button subscribeButton = Button.byXpath(SUBSCRIBE_BUTTON_XPATH);
    private final Link channelLogo = Link.byClass(CHANNEL_LOGO_CLASS);

    public ChannelPage() {
        super(ChannelPage.class, CHANNEL_PAGE_ROOT, "");
    }

    /**
     * Оформляет подписку на канал.
     * Проверяет, отображается ли кнопка подписки, и если да, то кликает по ней.
     * Если кнопка уже в статусе "Вы подписаны", метод ничего не делает.
     */
    public ChannelPage subscribe() {
        if (subscribeButton.isDisplayed() && !isSubscribed()) {
            subscribeButton.click();
        }
        return this;
    }

    /**
     * Проверяет, оформлена ли подписка на канал.
     * Ищет кнопку с текстом "Вы подписаны" и проверяет её отображение.
     */
    public boolean isSubscribed() {
        try {
            SelenideElement btn = $x("//button[contains(@class, 'wdp-subscribe-button-module__button')]");

            String text = btn.getText();
            return !text.contains("Подписаться");
        } catch (Exception e) {
            return false;
        }
    }
}