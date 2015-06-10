package java.ua.net.itlabs.v1;

import com.codeborne.selenide.SelenideElement;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.actions;

/**
 * Created by svetasuhusina on 27.05.15.
 */
public class Helpers {

    @Step
    public void doubleClick(SelenideElement element) {

        actions().doubleClick(element).perform();
    }

}
