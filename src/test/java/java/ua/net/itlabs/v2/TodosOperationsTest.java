package java.ua.net.itlabs.v2;

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
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ua.net.itlabs.v2.TodoMVCPage.*;


/**
 * Created by svetasuhusina on 24.05.15.
 */
public class TodosOperationsTest {

    @Before
    public void loadTodoMVC() {
        openTodos();
    }

    @After
    public void postScreanshotAndClearData() {

        executeJavaScript("localStorage.clear()");
        open("http://todomvc.com");
    }

public void tearDown() throws IOException{
    screenshot();
}


    @Attachment(type = "image/png")
    public static byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }


    @Test
    public void testAtAllFilter() {

        // Create tasks
        addTask("1");
        addTask("2");
        addTask("3");
        addTask("4");
        addTask("5");
        todoTasks.shouldHave(texts("1", "2", "3", "4", "5"));
        checkItemsLeftCounter(5);

        // Edit task
        editTask("1", "1 edited");
        todoTasks.shouldHave(texts("1 edited", "2", "3", "4", "5"));

        // Delete task
        destroyTask("2");
        todoTasks.shouldHave(texts("1 edited", "3", "4", "5"));
        checkItemsLeftCounter(4);

        // Complete task
        toggleTask("4");
        toggleTask("5");
        checkItemsLeftCounter(2);
        checkItemsRightCounter(2);

        //Filters
        filterActive();
        todoTasks.filter(visible).shouldHave(texts("1 edited", "3"));
        filterCompleted();
        todoTasks.filter(visible).shouldHave(texts("4", "5"));
        filterAll();
        todoTasks.shouldHave(texts("1 edited", "3", "4", "5"));

        // Reopen task
        toggleTask("4");
        checkItemsLeftCounter(3);
        checkItemsRightCounter(1);
        todoTasks.shouldHave(texts("1 edited", "3", "4", "5"));


        // Clear completed task
        clearCompleted();
        todoTasks.shouldHave(texts("1 edited", "3", "4"));

        // Complete all task
        filterAll();
        toggleAllTask();
        clearCompleted();
        todoTasks.shouldBe(empty);

    }

    @Test
    public void testAtActiveFilter() {

        // Create tasks
        addTask("1");
        addTask("2");
        addTask("3");
        addTask("4");
        filterActive();
        checkItemsLeftCounter(4);
        todoTasks.shouldHave(texts("1", "2", "3", "4"));

        // Edit task
        editTask("1", "1 active edited");
        checkItemsLeftCounter(4);
        todoTasks.filter(visible).shouldHave(texts("1 active edited", "2", "3", "4"));
        filterAll();
        todoTasks.shouldHave(texts("1 active edited", "2", "3", "4"));

        // Delete task
        filterActive();
        destroyTask("2");
        checkItemsLeftCounter(3);
        todoTasks.filter(visible).shouldHave(texts("1 active edited", "3", "4"));
        filterAll();
        checkItemsLeftCounter(3);
        todoTasks.shouldHave(texts("1 active edited", "3", "4"));

        // Complete task
        toggleTask("4");
        checkItemsLeftCounter(2);
        checkItemsRightCounter(1);
        filterCompleted();
        todoTasks.filter(visible).shouldHave(texts("4"));

    }

    @Test
    public void testAtCompletedFilter() {

        // Given tasks
        addTask("1");
        addTask("2");
        addTask("3");
        toggleAllTask();
        checkItemsLeftCounter(0);
        checkItemsRightCounter(3);

        // Edit task
        filterCompleted();
        editTask("1", "1 completed edited");
        todoTasks.filter(visible).shouldHave(texts("1 completed edited", "2", "3"));

        // Delete task
        destroyTask("2");
        checkItemsLeftCounter(0);
        checkItemsRightCounter(2);
        todoTasks.filter(visible).shouldHave(texts("1 completed edited", "3"));
        filterActive();
        todoTasks.filter(visible).shouldBe(empty);
        filterAll();
        todoTasks.filter(visible).shouldHave(texts("1 completed edited", "3"));


        // Reopen task
        filterCompleted();
        toggleTask("3");
        checkItemsLeftCounter(1);
        checkItemsRightCounter(1);
        todoTasks.filter(visible).shouldHave(texts("1 completed edited"));
        filterAll();
        todoTasks.shouldHave(texts("1 completed edited", "3"));
        filterActive();
        todoTasks.filter(visible).shouldHave(texts("3"));

        // Clear completed
        addTask("4");
        toggleAllTask();
        checkItemsLeftCounter(0);
        checkItemsRightCounter(3);
        filterCompleted();
        clearCompleted();
        todoTasks.filter(visible).shouldBe(empty);

    }
}

