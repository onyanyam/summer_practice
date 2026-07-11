package pages;

import elements.Button;
import elements.Link;

public class ChannelPage extends BasePage {

    private final Button subscribeButton = Button.byXpath("//button[contains(@class, 'wdp-subscribe-button-module__button')]");
    private final Link channelLogo = Link.byClass("wdp-video-options-row-module__authorAvatar");

    public ChannelPage() {
        super(ChannelPage.class, "//div[contains(@class, 'channel-page')]", "");
    }

    public ChannelPage subscribe() {
        if (subscribeButton.isDisplayed()) {
            subscribeButton.click();
        }
        return this;
    }

    public boolean isSubscribed() {
        return Button.byXpath("//button[contains(@class, 'wdp-subscribe-button-module__button') and contains(., 'Вы подписаны')]").isDisplayed();
    }

    public ChannelPage goToChannelPage() {
        channelLogo.click();
        return this;
    }
}