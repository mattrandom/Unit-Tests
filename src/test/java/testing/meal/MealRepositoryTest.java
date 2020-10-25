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
    void shouldBeAbleToFindMealByExactName() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(15, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.findByName("Pizza", true);

        //then
        assertThat(results, hasSize(1));
    }

    @Test
    void shouldBeAbleToFindMealByStartingLetters() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(15, "Pi");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.findByName("P", false);

        //then
        assertThat(results, hasSize(2));
    }

    @Test
    void shouldBeAbleToFindMealByPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(15, "Burger");
        Meal meal3 = new Meal(20, "Spaghetti");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        mealRepository.add(meal3);

        //when
        List<Meal> resultOfEqualPrice = mealRepository.findByPrice(15, PriceSearchType.EQUAL);
        List<Meal> resultOfLowerPrice = mealRepository.findByPrice(15, PriceSearchType.LOWER);
        List<Meal> resultOfHigherPrice = mealRepository.findByPrice(15, PriceSearchType.HIGHER);

        //then
        assertThat(resultOfEqualPrice, hasSize(1));
        assertThat(resultOfEqualPrice.get(0).getName(), equalTo(meal2.getName()));

        assertThat(resultOfLowerPrice, hasSize(1));
        assertThat(resultOfLowerPrice.get(0).getName(), equalTo(meal.getName()));

        assertThat(resultOfHigherPrice, hasSize(1));
        assertThat(resultOfHigherPrice.get(0).getName(), equalTo(meal3.getName()));
    }

    @Test
    void shouldFindByExactNameAndExactPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(9, "Burger");

        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.find("Pizza", true, 10, PriceSearchType.EQUAL);

        //then
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(meal));
    }

    @Test
    void shouldFindByFirstLetterAndExactPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(9, "Burger");

        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.find("B", false, 9, PriceSearchType.EQUAL);

        //then
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(meal2));
    }

    @Test
    void shouldFindByExactNameAndLowerPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(9, "Burger");

        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.find("Pizza", true, 11, PriceSearchType.LOWER);

        //then
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(meal));
    }

    @Test
    void shouldFindByFirstLetterAndLowerPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(9, "Burger");

        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.find("B", false, 10, PriceSearchType.LOWER);

        //then
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(meal2));
    }

    @Test
    void shouldFindByExactNameAndHigherPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(9, "Burger");

        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.find("Pizza", true, 9, PriceSearchType.HIGHER);

        //then
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(meal));
    }

    @Test
    void shouldFindByFirstLetterAndHigherPrice() {
        //given
        Meal meal = new Meal(10, "Pizza");
        Meal meal2 = new Meal(9, "Burger");

        mealRepository.add(meal);
        mealRepository.add(meal2);

        //when
        List<Meal> results = mealRepository.find("B", false, 8, PriceSearchType.HIGHER);

        //then
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(meal2));
    }
}
