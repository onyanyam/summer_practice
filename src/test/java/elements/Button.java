package elements;

public class Button extends BaseElement {

    private static final String TEXT_XPATH = "//button[contains(text(), '%s')]";
    private static final String CLASS_XPATH = "//button[contains(@class, '%s')]";
    private static final String XPATH_XPATH = "%s";

    private Button(String xpath, String param) {
        super(xpath, param);
    }

    public static Button byText(String text) {
        return new Button(TEXT_XPATH, text);
    }

    public static Button byClass(String className) {
        return new Button(CLASS_XPATH, className);
    }

    public static Button byXpath(String xpath) {
        return new Button(XPATH_XPATH, xpath);
    }

}