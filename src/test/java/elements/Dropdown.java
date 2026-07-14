package elements;

import base.BaseElement;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс-обёртка для элемента "выпадающий список".
 * Позволяет открыть список и выбрать в нём опцию по тексту.
 */
public class Dropdown extends BaseElement {

    private static final String CLASS_XPATH = "//div[contains(@class, '%s')]";
    private static final String OPTION_XPATH = ".//*[contains(text(), '%s')]";

    private Dropdown(String xpath, String param) {
        super(xpath, param);
    }

    public void selectOption(String optionText) {
        baseElement.click();
        SelenideElement option = $x(String.format(OPTION_XPATH, optionText));
        option.click();
    }

    public static Dropdown byClass(String className) {
        return new Dropdown(CLASS_XPATH, className);
    }
}