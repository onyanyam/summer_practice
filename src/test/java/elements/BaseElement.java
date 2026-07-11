package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class BaseElement {

    protected static final int WAIT_SECONDS = 10;
    protected final SelenideElement baseElement;

    protected BaseElement(String xpath, String attributeValue) {
        this.baseElement = $x(String.format(xpath, attributeValue));
    }

    protected BaseElement(SelenideElement element) {
        this.baseElement = element;
    }

    public boolean isDisplayed() {
        try {
            return baseElement
                    .shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void click() {
        baseElement.click();
    }

    public String getText() {
        return baseElement.getText();
    }

    public SelenideElement getBaseElement() {
        return baseElement;
    }
}