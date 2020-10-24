package testing.homework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @InjectMocks
    private UnitService unitService;
    @Mock
    private CargoRepository cargoRepositoryMock;
    @Mock
    private UnitRepository unitRepositoryMock;

    @Test
    void addedCargoShouldBeLoadedOnUnit() {
        //given
        Unit unit = new Unit(new Coordinates(0, 0), 10, 10);
        Cargo cargo = new Cargo("package", 5);

        given(cargoRepositoryMock.findCargoByName("package")).willReturn(Optional.of(cargo));

        //when
        unitService.addCargoByName(unit, "package");

        //then
        verify(cargoRepositoryMock).findCargoByName("package");
        assertThat(cargo.getWeight(), is(5));
        assertThat(unit.getLoad(), is(5));
        assertThat(unit.getCargo().get(0), equalTo(cargo));
        assertThat(unit.getCargo(), hasSize(1));
    }

    @Test
    void shouldThrowExceptionIfNoCargoIsFoundToAdd() {
        //given
        Unit unit = new Unit(new Coordinates(0, 0), 10, 10);

        given(cargoRepositoryMock.findCargoByName("package")).willReturn(Optional.empty());

        //when
        //then
        verify(cargoRepositoryMock, never()).findCargoByName("package");
        assertThrows(NoSuchElementException.class, () -> unitService.addCargoByName(unit, "package"));
    }

    @Test
    void shouldReturnUnitByCoordinates() {
        //given
        Coordinates coordinates = new Coordinates(0, 0);
        Unit unit = new Unit(coordinates, 10, 10);

        given(unitRepositoryMock.getUnitByCoordinates(coordinates)).willReturn(unit);

        //when
        Unit result = unitService.getUnitOn(coordinates);

        //then
        verify(unitRepositoryMock).getUnitByCoordinates(coordinates);
        assertThat(result, sameInstance(unit));
    }

    @Test
    void shouldReturnExceptionIfUnitNotFound() {
        //given
        Coordinates coordinates = new Coordinates(0, 0);

        given(unitRepositoryMock.getUnitByCoordinates(coordinates)).willReturn(null);

        //when
        //then
        verify(unitRepositoryMock, never()).getUnitByCoordinates(coordinates);
        assertThrows(NoSuchElementException.class, () -> unitService.getUnitOn(coordinates));
    }
}