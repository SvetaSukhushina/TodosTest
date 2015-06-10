package java.ua.net.itlabs.v1;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by svetasuhusina on 23.05.15.
 */
public class TodosOperationsTest {

    TodoMVCPage page = new TodoMVCPage();

    @Before
    public void loadTodoMVC() {
       page.openTodos();
    }

    @After
    public void clearData() {
        open("http://todomvc.com");
        executeJavaScript("localStorage.clear()");
    }

    @After
    public void tearDown() throws IOException {
        screenshot();
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }


    @Test
    public void testAtAllFilter() {

        // Create tasks
        page.addTask("1");
        page.addTask("2");
        page.addTask("3");
        page.addTask("4");
        page.addTask("5");
        page.todoTasks.shouldHave(texts("1", "2", "3", "4", "5"));
        page.checkItemsLeftCounter(5);

        // Edit task
        page.editTask("1", "1 edited");
        page.todoTasks.shouldHave(texts("1 edited", "2", "3", "4", "5"));

        // Delete task
        page.destroyTask("2");
        page.todoTasks.shouldHave(texts("1 edited", "3", "4", "5"));
        page.checkItemsLeftCounter(4);

        // Complete task
        page.toggleTask("4");
        page.toggleTask("5");
        page.checkItemsLeftCounter(2);
        page.checkItemsRightCounter(2);

        //Filters
        page.filterActive.click();
        page.todoTasks.filter(Condition.visible).shouldHave(texts("1 edited", "3"));
        page.filterCompleted.click();
        page.todoTasks.filter(Condition.visible).shouldHave(texts("4", "5"));
        page.filterAll.click();
        page.todoTasks.shouldHave(texts("1 edited", "3", "4", "5"));

        // Reopen task
        page.toggleTask("4");
        page.checkItemsLeftCounter(3);
        page.checkItemsRightCounter(1);
        page.todoTasks.shouldHave(texts("1 edited", "3", "4", "5"));


        // Clear completed task
        page.clearCompleted();
        page.todoTasks.shouldHave(texts("1 edited", "3", "4"));

        // Complete all task
        page.filterAll.click();
        $("#toggle-all").click();
        page.clearCompleted();
        page.todoTasks.shouldBe(empty);

    }

    @Test
    public void testAtActiveFilter() {

        // Create tasks
        page.addTask("1");
        page.addTask("2");
        page.addTask("3");
        page.addTask("4");
        page.filterActive.click();
        page.checkItemsLeftCounter(4);
        page.todoTasks.shouldHave(texts("1", "2", "3", "4"));

        // Edit task
        page.editTask("1", "1 active edited");
        page.checkItemsLeftCounter(4);
        page.todoTasks.filter(Condition.visible).shouldHave(texts("1 active edited", "2", "3", "4"));
        page.filterAll.click();
        page.todoTasks.shouldHave(texts("1 active edited", "2", "3", "4"));

        // Delete task
        page.filterActive.click();
        page.destroyTask("2");
        page.checkItemsLeftCounter(3);
        page.todoTasks.filter(Condition.visible).shouldHave(texts("1 active edited", "3", "4"));
        page.filterAll.click();
        page.checkItemsLeftCounter(3);
        page.todoTasks.shouldHave(texts("1 active edited", "3", "4"));

        // Complete task
        page.toggleTask("4");
        page.checkItemsLeftCounter(2);
        page.checkItemsRightCounter(1);
        page.filterCompleted.click();
        page.todoTasks.filter(Condition.visible).shouldHave(texts("4"));

    }

    @Test
    public void testAtCompletedFilter() {

        // Given tasks
        page.addTask("1");
        page.addTask("2");
        page.addTask("3");
        $("#toggle-all").click();
        page.checkItemsLeftCounter(0);
        page.checkItemsRightCounter(3);

        // Edit task
        page.filterCompleted.click();
        page.editTask("1", "1 completed edited");
        page.todoTasks.filter(Condition.visible).shouldHave(texts("1 completed edited", "2", "3"));

        // Delete task
        page.destroyTask("2");
        page.checkItemsLeftCounter(0);
        page.checkItemsRightCounter(2);
        page.todoTasks.filter(Condition.visible).shouldHave(texts("1 completed edited", "3"));
        page.filterActive.click();
        page.todoTasks.filter(Condition.visible).shouldBe(empty);
        page.filterAll.click();
        page.todoTasks.filter(Condition.visible).shouldHave(texts("1 completed edited", "3"));


        // Reopen task
        page.filterCompleted.click();
        page.toggleTask("3");
        page.checkItemsLeftCounter(1);
        page.checkItemsRightCounter(1);
        page.todoTasks.filter(Condition.visible).shouldHave(texts("1 completed edited"));
        page.filterAll.click();
        page.todoTasks.shouldHave(texts("1 completed edited", "3"));
        page.filterActive.click();
        page.todoTasks.filter(Condition.visible).shouldHave(texts("3"));

        // Clear completed
        page.addTask("4");
        $("#toggle-all").click();
        page.checkItemsLeftCounter(0);
        page.checkItemsRightCounter(3);
        page.filterCompleted.click();
        page.clearCompleted();
        page.todoTasks.filter(Condition.visible).shouldBe(empty);

    }
}

