package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public class BasePage {

    protected final SelenideElement basePage;
    protected final Class<? extends BasePage> pageClass;

    protected BasePage(Class<? extends BasePage> pageClass, String xpath, String param) {
        this.basePage = Selenide.$x(String.format(xpath, param));
        this.pageClass = pageClass;
    }

    public boolean isDisplayed() {
        try {
            return basePage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}