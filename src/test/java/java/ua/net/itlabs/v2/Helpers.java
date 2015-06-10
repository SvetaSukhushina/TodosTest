package java.ua.net.itlabs.v2;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.actions;

/**
 * Created by svetasuhusina on 27.05.15.
 */
public class Helpers {

    public static void doubleClick(SelenideElement element) {

        actions().doubleClick(element).perform();
    }

}
