package testing.homework;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class CargoRepositoryTest {

    @Test
    void cargoShouldBeAddedToList() {
        //given
        Cargo cargo = new Cargo("package", 100);
        CargoRepository cargoRepository = new CargoRepository();

        //when
        cargoRepository.addCargo(cargo);

        //then
        assertThat(cargoRepository.getCargoList(), hasSize(1));
    }

    @Test
    void cargoShouldBeRemovedFromList() {
        //given
        Cargo cargo = new Cargo("package", 100);
        CargoRepository cargoRepository = new CargoRepository();
        cargoRepository.addCargo(cargo);

        //when
        cargoRepository.removeCargo(cargo);

        //then
        assertThat(cargoRepository.getCargoList(), hasSize(0));
    }

    @Test
    void findFirstCargoFilteredByName() {
        //given
        Cargo cargo = new Cargo("package", 100);
        CargoRepository cargoRepository = new CargoRepository();
        cargoRepository.addCargo(cargo);

        //when
        Optional<Cargo> findPackage = cargoRepository.findCargoByName("package");

        //then
        assertThat(findPackage.isPresent(), equalTo(true));
        assertThat(cargoRepository.getCargoList(), hasSize(1));


    }

}