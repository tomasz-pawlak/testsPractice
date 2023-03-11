import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void shouldNewCoordinatesThrowExceptionIfXisLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-5, 3));
    }

    @Test
    void shouldNewCoordinatesThrowExceptionIfYisLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(5, -3));
    }

    @Test
    void shouldNewCoordinatesThrowExceptionIfYisHigherThan100() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(555, -3));
    }

    @Test
    void shouldNewCoordinatesThrowExceptionIfXisHigherThan100() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(5, 555));
    }

    @Test
    void shouldNewCoordinatesDoNotThrowException() {
        assertDoesNotThrow(() -> new Coordinates(5,6));
    }


    @Test
    void shouldCopiedCoordinatesShouldNotBeSameObject() {
        Coordinates coordinates = new Coordinates(5,6);

        Coordinates copy = Coordinates.copy(coordinates, 5 , 6);

        assertThat(coordinates, is(not(copy)) );
    }

    @Test
    void shouldCopiedCoordinatesShouldNotHaveSameArguments() {
        Coordinates coordinates = new Coordinates(5,6);

        Coordinates copy = Coordinates.copy(coordinates, 5 , 6);

        assertNotEquals(copy.getX(), coordinates.getX());
    }
}