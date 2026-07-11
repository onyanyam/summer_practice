package elements;

public class Tab extends BaseElement {

    private static final String TEXT_XPATH = "//*[contains(@class, 'tab') and contains(text(), '%s')]";

    private Tab(String xpath, String param) {
        super(xpath, param);
    }

    public static Tab byText(String text) {
        return new Tab(TEXT_XPATH, text);
    }

}