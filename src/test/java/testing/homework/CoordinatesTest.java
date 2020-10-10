package testing.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for Coordinates")
class CoordinatesTest {

    @Test
    void shouldThrowIAExcWhenBothConstructorParametersAreLessThan0() {
        //given & when & then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-1, -1));
    }

    @Test
    void shouldThrowIAExcWhenSomeOfConstructorParametersIsLessThan0() {
        //given & when & then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(0, -1));
    }

    @Test
    void shouldThrowIAExcWhenBothConstructorParametersIsGreaterThan100() {
        //given & when & then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(666, 999));
    }

    @Test
    void shouldThrowIAExcWhenSomeOfConstructorParametersIsGreaterThan100() {
        //given & when & then
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(0, 101));
    }

    @Test
    void copyShouldReturnNewObject() {
        //given
        Coordinates coordinates = new Coordinates(10, 10);

        //when
        Coordinates checkCopy = Coordinates.copy(coordinates, 5, 5);

        //then
        assertThat(checkCopy, not(sameInstance(coordinates)));
        assertNotSame(coordinates, checkCopy);
        assertNotEquals(coordinates, checkCopy);
    }

    @Test
    void copyShouldReturnNewCoordinates() {
        //given
        Coordinates coordinates = new Coordinates(25, 20);

        //when
        Coordinates copy = Coordinates.copy(coordinates, 15, 10);

        //then
        assertThat(copy.getX(), is(equalTo(40)));
        assertThat(copy.getY(), equalTo(30));

        assertAll(
                () -> assertThat(copy.getX(), is(equalTo(40))),
                () -> assertThat(copy.getY(), equalTo(30))
        );

        assertSame(40, copy.getX());
        assertEquals(30, copy.getY());
    }
}