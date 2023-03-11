import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @Mock
    CargoRepository cargoRepository;
    @Mock
    UnitRepository unitRepository;
    @InjectMocks
    UnitService unitService;

    @Test
    void addedCargoShouldBeLoadedOnUnit() {
        Unit unit = new Unit(new Coordinates(0, 0), 10, 20);
        Cargo cargo = new Cargo("Cargo", 5);

        given(cargoRepository.findCargoByName(cargo.getName())).willReturn(Optional.of(cargo));

        unitService.addCargoByName(unit, "Cargo");

        verify(cargoRepository).findCargoByName("Cargo");
        verify(cargoRepository, atMostOnce()).findCargoByName(cargo.getName());
        assertThat(unit.getLoad(), is(5));
        assertThat(unit.getCargo().get(0), equalTo(cargo));
    }

    @Test
    void addedCargoShouldNotBeLoadedOnUnit() {
        Unit unit = new Unit(new Coordinates(0, 0), 10, 20);

        verify(cargoRepository, never()).findCargoByName("cargo");
        assertThat(unit.getLoad(), is(0));
    }

    @Test
    void addedCargoShouldThrowException() {
        Unit unit = new Unit(new Coordinates(0, 0), 10, 20);

        verify(cargoRepository, never()).findCargoByName("cargo");
        assertThrows(NoSuchElementException.class, () -> unitService.addCargoByName(unit, "cargos"));
    }

    @Test
    void addingCargoShouldIncreaseListSize() {
        Unit unit = new Unit(new Coordinates(0, 0), 10, 20);
        given(cargoRepository.getCargoList()).willReturn(returnCargoList());

        verify(cargoRepository, never()).findCargoByName("cargo");
        assertThat(cargoRepository.getCargoList(), hasSize(3));
    }


    List<Cargo> returnCargoList() {
        return List.of(
                new Cargo("cargo", 5),
                new Cargo("tak", 1),
                new Cargo("chleb", 0)
        );
    }

    @Test
    void serviceShouldGetUnitByCoords() {
        Unit unit = new Unit(new Coordinates(0, 0), 50, 100);
        Coordinates coordinates = new Coordinates(5, 5);
        given(unitRepository.getUnitByCoordinates(coordinates)).willReturn(unit);

        Unit result = unitService.getUnitOn(new Coordinates(5, 5));

        assertThat(result, Matchers.equalTo(unit));
    }

    @Test
    void serviceShouldThrowExceptionByCoords() {
        given(unitRepository.getUnitByCoordinates(new Coordinates(0, 0))).willReturn(null);

        assertThrows(NoSuchElementException.class, () -> unitService.getUnitOn(new Coordinates(0, 0)));
    }

}