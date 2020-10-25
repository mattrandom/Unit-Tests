package testing.meal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MealRepositoryTest {

     private MealRepository mealRepository;

    @BeforeEach
     void init() {
        mealRepository = new MealRepository();
    }

    @AfterEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldBeAbleToAddMealToRepository() {
        //given
        Meal meal = new Meal(10, "Pizza");

        //when
        mealRepository.add(meal);

        //then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));
    }

    @Test
    void shouldBeAbleToRemoveMealFromRepository() {
        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);

        //when
        mealRepository.delete(meal);

        //then
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));
    }

    @Test
    void shouldBeAbleToFindMealByName() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(15, "Burger");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.findByName("Pizza");

        //then
        assertThat(results, hasSize(1));
    }

    @Test
    void shouldBeAbleToFindMealByPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(15, "Burger");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.findByPrice(15);

        //then
        assertThat(results, hasSize(1));
        assertThat(results.get(0).getName(), equalTo("Burger"));
    }
}
