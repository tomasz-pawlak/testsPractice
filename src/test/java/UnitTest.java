import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class UnitTest {
    Coordinates coordinates;
    Unit unit;

    @BeforeEach
    public void setUp() {
        coordinates = new Coordinates(5, 6);
        unit = new Unit(coordinates, 50, 1000);
    }

    @Test
    void shouldXbeFive() {
        assertEquals(5, coordinates.getX());
    }

    @Test
    void shouldCoordinatesBeNull() {
        assertThat(coordinates, is(not(nullValue())));
    }

    @Test
    void shouldUnitNotMoveToThisCoords() {
        assertThrows(IllegalStateException.class, () -> unit.move(50, 30));
    }

    @Test
    void shouldUnitBeAbleToMove() {
        unit.move(10, 10);
        assertThat(unit.getFuel(), is(not(lessThan(0))));
    }

    @Test
    void shouldUnitHave20FuelAfterMovingToNewCoords() {
        unit.move(10, 20);
        assertEquals(20, unit.getFuel());
    }

    @RepeatedTest(20)
    void shouldFuelShouldBeHigherAfterTankUp() {

        assumingThat(unit.getFuel() > 0,
                () -> {
                    unit.move(20, 20);
                    int restFuel = unit.getFuel();
                    assertThat(restFuel, is(not(lessThan(0))));

                    unit.tankUp();
                    assertThat(unit.getFuel(), is(not(lessThan(restFuel))));

                });
    }

    @Test
    void shouldUnitNotBeAbleToGetTooMuchCargo() {
        assertThrows(IllegalStateException.class, () -> {
                    unit.loadCargo(new Cargo("Cargo", 2000));
                }
        );
    }

    @Test
    void shouldUnitBeAbleToGetCargo() {
        Cargo cargo = new Cargo("Cargo", 500);
        unit.loadCargo(cargo);
        assertThat(cargo.getWeight(), is(not(greaterThan(unit.getMaxCargoWeight()))));
    }

    @Test
    void shouldUnitUpdateCargoWeightAfterUnload() {
        Cargo cargo = new Cargo("Cargo", 500);
        Cargo cargo2 = new Cargo("Cargo", 200);
        unit.loadCargo(cargo);
        unit.loadCargo(cargo2);
        unit.unloadCargo(cargo);
        assertEquals(200, unit.getLoad());
    }

    @Test
    void shouldUnitCurrentCargoWeightBeEmptyAfterUnloadAll() {
        Cargo cargo = new Cargo("Cargo", 500);
        Cargo cargo2 = new Cargo("Cargo", 200);
        unit.loadCargo(cargo);
        unit.loadCargo(cargo2);
        unit.unloadAllCargo();
        assertEquals(0, unit.getLoad());
    }

    @Test
    void unitShouldHasNotEmptyCargoAndBeNotNull() {
        Cargo cargo = new Cargo("Cargo", 500);
        unit.loadCargo(cargo);

        assertAll(
                () -> assertThat(unit.getLoad(), is(not(greaterThan(unit.getMaxCargoWeight())))),
                () -> assertThat(unit.getFuel(), is(not(lessThan(0)))),
                () -> assertNotNull(unit),
                () -> assertThat(unit.getLoad(), is(not(greaterThan(unit.getMaxCargoWeight()))))
        );

    }

    @ParameterizedTest
    @MethodSource("createCargos")
    void unitShouldHaveLoadNotLessThanTen(String name, int weight){
        unit.loadCargo(new Cargo(name, weight));

        assertThat(unit.getLoad(), is(not(lessThan(10))));
    }

    private static Stream<Arguments> createCargos() {
        return Stream.of(
                Arguments.of("Cargo", 500),
                Arguments.of("Cargo2", 100),
                Arguments.of("Cargo3", 20)
        );
    }


}