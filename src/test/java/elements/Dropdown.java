package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class Dropdown extends BaseElement {

    private static final String CLASS_XPATH = "//div[contains(@class, '%s')]";

    private Dropdown(String xpath, String param) {
        super(xpath, param);
    }

    public void selectOption(String optionText) {
        baseElement.click();
        SelenideElement option = $x(String.format(".//*[contains(text(), '%s')]", optionText));
        option.click();
    }

    public static Dropdown byClass(String className) {
        return new Dropdown(CLASS_XPATH, className);
    }
}