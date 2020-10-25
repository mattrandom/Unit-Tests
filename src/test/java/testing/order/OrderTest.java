package testing.order;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import testing.meal.Meal;
import testing.extensions.BeforeAfterExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {
    private Order order;

    @BeforeEach
    void initializedOrder() {
        System.out.println("BEFORE EACH");
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        System.out.println("AFTER EACH");
        order.cancel();
    }

    @Test
    void testAssertArrayEquals() {
        //given
        int[] ints1 = {1, 2, 3};
        int[] ints2 = {1, 2, 3};

        //then
        assertArrayEquals(ints1, ints2);
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        //then
        assertThat(order.getMeals(), is(empty()));
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals(), hasSize(0));
        MatcherAssert.assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        //given
        Meal meal = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");

        //when
        order.addMealToOrder(meal);

        //then
        assertThat(order.getMeals().size(), equalTo(1));
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), contains(meal));
        assertThat(order.getMeals(), hasItem(meal));
        assertThat(order.getMeals().get(0).getPrice(), equalTo(15));
    }

    @Test
    void removeMealFromOrderShouldDecreaseOrderSize() {
        //given
        Meal meal = new Meal(15, "Burger");

        //when
        order.addMealToOrder(meal);
        order.removeMealFromOrder(meal);

        //then
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals(), hasSize(0));
        assertThat(order.getMeals(), is(empty()));
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
        assertThat(order.getMeals(), not(contains(meal)));
    }

    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
        //given
        Meal meal1 = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
//        assertThat(order.getMeals(), contains(meal1, meal2));
//        assertThat(order.getMeals(), containsInAnyOrder(meal2, meal1));
        assertThat(order.getMeals().get(0), is(meal1));
        assertThat(order.getMeals().get(1), is(meal2));
    }

    @Test
    void testIfTwoMealListAreTheSame() {
        //given
        Meal meal1 = new Meal(15, "Burger");
        Meal meal2 = new Meal(5, "Sandwich");
        Meal meal3 = new Meal(11, "Kebab");

        List<Meal> mealsList1 = Arrays.asList(meal1, meal2);
        List<Meal> mealsList2 = Arrays.asList(meal1, meal2);
        List<Meal> mealsList3 = Arrays.asList(meal1, meal2, meal3);

        //then
        assertThat(mealsList1, is(mealsList2));
        assertThat(mealsList1, is(not(mealsList3)));
    }

    @Test
    void orderTotalPriceShouldNotExceedMaxIntValue() {
        //given
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Burger");
        Meal meal2 = new Meal(Integer.MAX_VALUE, "Sandwich");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThrows(IllegalStateException.class, () -> order.totalPrice());
    }

    @Test
    void emptyOrderTotalPriceShouldEqual0() {
        //given
        // Order is created in BeforeEach

        //then
        assertThat(order.totalPrice(), is(0));
    }

    @Test
    void cancellingOrderShouldRemoveAllItemsFromMealsList() {
        //given
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Burger");
        Meal meal2 = new Meal(Integer.MAX_VALUE, "Sandwich");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        order.cancel();

        //then
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }
}
