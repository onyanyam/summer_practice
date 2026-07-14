package base;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

/**
 * Базовый класс для всех Page Object классов проекта.
 * Хранит корневой элемент страницы и предоставляет общую проверку
 * того, что страница отображается.
 */
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