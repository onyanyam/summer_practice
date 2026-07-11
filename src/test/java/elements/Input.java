package elements;

public class Input extends BaseElement {

    private static final String ID_XPATH = "//input[@id='%s']";
    private static final String NAME_XPATH = "//input[@name='%s']";
    private static final String PLACEHOLDER_XPATH = "//input[@placeholder='%s']";
    private static final String CLASS_XPATH = "//input[contains(@class, '%s')]";

    private Input(String xpath, String param) {
        super(xpath, param);
    }

    public void fill(String value) {
        baseElement.clear();
        baseElement.setValue(value);
    }

    public void clear() {
        baseElement.clear();
    }

    public void pressEnter() {
        baseElement.pressEnter();
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