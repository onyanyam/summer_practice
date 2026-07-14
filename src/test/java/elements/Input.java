package elements;

import base.BaseElement;

/**
 * Класс-обёртка для элемента "поле ввода".
 * Предоставляет фабричные методы поиска поля по id, name, placeholder или классу,
 * а также методы заполнения и очистки поля.
 */
public class Input extends BaseElement {

    private static final String ID_XPATH = "//input[@id='%s']";
    private static final String NAME_XPATH = "//input[@name='%s']";
    private static final String PLACEHOLDER_XPATH = "//input[@placeholder='%s']";
    private static final String CLASS_XPATH = "//input[contains(@class, '%s')]";

    private Input(String xpath, String param) {
        super(xpath, param);
    }

    private Button clearButton;

    public Input withClearButton(Button button) {
        this.clearButton = button;
        return this;
    }

    public void fill(String value) {
        if (clearButton != null && clearButton.isDisplayed()) {
            clearButton.click();
        }
        baseElement.setValue(value);
        baseElement.pressEnter();
    }

    public void clear() {
        baseElement.clear();
    }

    public static Input byId(String id) {
        return new Input(ID_XPATH, id);
    }

    public static Input byName(String name) {
        return new Input(NAME_XPATH, name);
    }

    public static Input byPlaceholder(String placeholder) {
        return new Input(PLACEHOLDER_XPATH, placeholder);
    }

    public static Input byClass(String className) {
        return new Input(CLASS_XPATH, className);
    }
}