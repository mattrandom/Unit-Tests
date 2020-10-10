package testing.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for Unit")
class UnitTest {

    @Test
    void unitShouldNotMoveWithoutFuel() {
        //given
        Coordinates coords = new Coordinates(0, 0);
        Unit unit = new Unit(coords, 0, 50);

        //when & then
        assertThrows(IllegalStateException.class, () -> unit.move(10, 10));
    }

    @Test
    void unitShouldLoseFuelWhenMoving() {
        //given
        Coordinates coords = new Coordinates(0, 0);
        Unit unit = new Unit(coords, 100, 50);

        //when
        Coordinates unitMoving = unit.move(25, 35);

        //then
        assertThat(unit.getFuel(), is(40));
    }

    @Test
    void movedUnitShouldReturnNewCoordinates() {
        //given
        Coordinates coords = new Coordinates(0, 0);
        Unit unit = new Unit(coords, 50, 20);

        //when
        Coordinates coordsAfterMoving = unit.move(5, 10);

        //then
        assertThat(coordsAfterMoving.getX(), is(5));
        assertThat(coordsAfterMoving.getY(), is(10));

        assertAll(
                () -> assertThat(coordsAfterMoving.getX(), equalTo(5)),
                () -> assertThat(coordsAfterMoving.getY(), is(equalTo(10)))
        );

        assertEquals(new Coordinates(5, 10), coordsAfterMoving);

        assertThat(coordsAfterMoving, equalTo(new Coordinates(5, 10)));
    }

    @Test
    void fuelingShouldNotExceedMaxFuelLimit() {
        //given
        Unit unit = new Unit(new Coordinates(0,0),10,10);

        //when
        unit.tankUp();

        //then
        assertThat(unit.getFuel(), is(10));
    }

    @Test
    void cargoCanNotExceedMaxWeightLimit() {
        //given
        Unit unit = new Unit(new Coordinates(0,0), 50, 100);
        Cargo c1 = new Cargo("C1", 60);
        Cargo c2 = new Cargo("C2", 90);

        //when
        unit.loadCargo(c1);

        //then
        assertThrows(IllegalStateException.class, () -> unit.loadCargo(c2));
    }

    @Test
    void addingCargoToUnitShouldIncreaseListSize() {
        //given
        Unit unit = new Unit(new Coordinates(0,0), 50, 100);
        Cargo c1 = new Cargo("C1", 15);
        Cargo c2 = new Cargo("C2", 20);

        //when
        unit.loadCargo(c1);
        unit.loadCargo(c2);

        //then
        assertThat(unit.getCargo(), hasSize(2));
        assertThat(unit.getCargo().size(), is(2));
        assertThat(unit.getCargo().size(), equalTo(2));
        assertThat(unit.getCargo().size(), is(equalTo(2)));

        assertThat(unit.getCargo().get(0).getWeight(), equalTo(15));
        assertThat(unit.getCargo(), hasItem(c1));
        assertThat(unit.getCargo(), contains(c1, c2));
        assertThat(unit.getCargo(), containsInAnyOrder(c2, c1));
    }

    @Test
    void unloadingAllCargoShouldReduceUnitLoadToZero() {
        //given
        Unit unit = new Unit(new Coordinates(0,0),10,10);

        Cargo c1 = new Cargo("c1", 5);
        Cargo c2 = new Cargo("c2", 5);

        unit.loadCargo(c1);
        unit.loadCargo(c2);

        //when
        unit.unloadAllCargo();

        //then
        assertThat(unit.getLoad(), is(0));
    }
}