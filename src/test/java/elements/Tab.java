package elements;

import base.BaseElement;

/**
 * Класс-обёртка для элемента "вкладка".
 * Предоставляет фабричный метод поиска вкладки по тексту и метод клика по ней.
 */
public class Tab extends BaseElement {

    private static final String TEXT_XPATH = "//*[contains(@class, 'tab') and contains(text(), '%s')]";

    private Tab(String xpath, String param) {
        super(xpath, param);
    }

    public void click() {
        baseElement.click();
    }

    public static Tab byText(String text) {
        return new Tab(TEXT_XPATH, text);
    }

}