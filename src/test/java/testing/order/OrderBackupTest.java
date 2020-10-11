package testing.order;

import org.junit.jupiter.api.*;
import testing.Meal;
import testing.order.Order;
import testing.order.OrderBackup;

import java.io.FileNotFoundException;
import java.io.IOException;

class OrderBackupTest {
    private static OrderBackup orderBackup;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }

    @AfterAll
    static void tearDown() throws IOException {
        orderBackup.closeFile();
    }

    @BeforeEach
    void appendAtTheStartOfTheLine() throws IOException {
        orderBackup.getWriter().append("New order: ");
    }

    @AfterEach
    void appendAtTheEndOfLine() throws IOException {
        orderBackup.getWriter().append(" backed up.");
    }

    @Tag("fries")
    @Test
    void backUpOrderWithOneMeal() throws IOException {
        //given
        Meal meal = new Meal(20, "Fries");
        testing.order.Order order = new Order();
        order.addMealToOrder(meal);

        //when
        orderBackup.backupOrder(order);

        //then
        System.out.println("Order: " + order.toString() + " backed up.");
    }
}