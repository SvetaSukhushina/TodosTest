package java.ua.net.itlabs.v2;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ua.net.itlabs.v2.Helpers.doubleClick;

/**
 * Created by student on 22.05.15.
 */
public class TodoMVCPage {

    public static ElementsCollection todoTasks = $$("#todo-list>li");
    public static SelenideElement clearCompleted = $("#clear-completed");

    @Step
    public static void openTodos() {
        open("http://todomvc.com/examples/troopjs_require/#/");
    }

    @Step
    public static void filterAll() {
        $("[href ='#/']").click();
    }

    @Step
    public static void filterActive() {
        $("[href *='active']").click();
    }

    @Step
    public static void filterCompleted() {
        $("[href ='#/completed']").click();
    }

    @Step
    public static void addTask(String name) {
        $("#new-todo").setValue(name).pressEnter();
    }


    @Step
    public static void destroyTask(String text) {
        todoTasks.findBy(text(text)).hover();
        todoTasks.findBy(text(text)).find(".destroy").click();
    }

    @Step
    public static void toggleTask(String text) {
        todoTasks.findBy(text(text)).hover();
        todoTasks.findBy(text(text)).find(".toggle").click();
    }

    @Step
    public static void toggleAllTask() {
        $("#toggle-all").click();
    }


    @Step
    public static void clearCompleted() {
        clearCompleted.click();
        clearCompleted.shouldBe(hidden);
    }


    @Step
    public static void editTask(String from, String to) {

        doubleClick(todoTasks.find(exactText(from)).find("label"));
        todoTasks.findBy(cssClass("editing")).find("input.edit").setValue(to).pressEnter();
    }

    @Step
    public static void checkItemsLeftCounter(int number) {
        $("#todo-count>strong").shouldHave(text(Integer.toString(number)));
    }

    @Step
    public static void checkItemsRightCounter(int number) {
        $("#clear-completed").shouldHave(text("(" + Integer.toString(number) + ")"));
    }

}
