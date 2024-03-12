package pl.otto.creditcard;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HelloTest {
    @Test
    void HelloTest() {

        //A /Arrange / Given
        var a=2;
        var b=4;
        //A /Act /When
        var result = a+b;

        //A /Assert / Then / Expected
        assert 6 == result;
    }

    @Test
    void itGreetUsername() {
        //Arrange
        String name = "Jakub";
        //Act
        String message = String.format("Hello %s", name);
        //Assert
        assertEquals("Hello Jadkub",message);
    }
}
