package java.ua.net.itlabs.v1;

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

    public ElementsCollection todoTasks = $$("#todo-list>li");
    public SelenideElement clearCompleted = $("#clear-completed");
    public SelenideElement filterAll = $("[href ='#/']");
    public SelenideElement filterActive = $("[href *='active']");
    public SelenideElement filterCompleted = $("[href ='#/completed']");

   @Step
    public void openTodos() {
       open("http://todomvc.com/examples/troopjs_require/#/");
    }

    @Step
    public void addTask(String taskName) {

        $("#new-todo").setValue(taskName).pressEnter();
    }


    @Step
    public void destroyTask(String taskText) {
        todoTasks.findBy(text(taskText)).hover();
        todoTasks.findBy(text(taskText)).find(".destroy").click();
    }

    @Step
    public void toggleTask(String taskText) {
        todoTasks.findBy(text(taskText)).hover();
        todoTasks.findBy(text(taskText)).find(".toggle").click();
    }

    @Step
    public void toggleAllTask() {

        $("#toggle-all").click();
    }

    @Step
    public void clearCompleted() {
        clearCompleted.click();
        clearCompleted.shouldBe(hidden);
    }


    @Step
    public void editTask(String from, String to) {

        doubleClick(todoTasks.find(exactText(from)).find("label"));
        todoTasks.findBy(cssClass("editing")).find("input.edit").setValue(to).pressEnter();
    }

    @Step
    public void checkItemsLeftCounter(int number) {
        $("#todo-count>strong").shouldHave(text(Integer.toString(number)));
    }

    @Step
    public void checkItemsRightCounter(int number) {
        $("#clear-completed").shouldHave(text("(" + Integer.toString(number) + ")"));
    }

}
