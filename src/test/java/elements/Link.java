package elements;

public class Link extends BaseElement {

    private static final String TEXT_XPATH = "//a[contains(text(), '%s')]";
    private static final String HREF_XPATH = "//a[contains(@href, '%s')]";
    private static final String CLASS_XPATH = "//a[contains(@class, '%s')]";

    private Link(String xpath, String param) {
        super(xpath, param);
    }

    public static Link byText(String text) {
        return new Link(TEXT_XPATH, text);
    }

    public static Link byHref(String href) {
        return new Link(HREF_XPATH, href);
    }

    public static Link byClass(String className) {
        return new Link(CLASS_XPATH, className);
    }

}