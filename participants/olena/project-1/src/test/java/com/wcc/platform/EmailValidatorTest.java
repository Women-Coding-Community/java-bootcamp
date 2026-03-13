package com.wcc.platform;

import com.wcc.platform.validation.EmailValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @Test
    void shouldValidateCorrectEmail() {
        //Arrange
        String email = "test@gmail.com";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertTrue(result);
    }

    @Test
    void shouldRejectInvalidEmail() {
        //Arrange
        String email = "email.com";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertFalse(result, "Email is invalid");
    }

    @Test
    void shouldRejectEmptyEmail() {
        //Arrange
        String email = "";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertFalse(result);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        //Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> EmailValidator.isValid(null));
    }

    @Test
    void shouldRejectEmailWithSpaces() {
        //Arrange
        String email = "user name@gmail.com";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertFalse(result);
    }

    @Test
    void shouldRejectEmailWithMultipleAtSigns() {
        //Arrange
        String email = "user@@gmail.com";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertFalse(result);
    }

    @Test
    void shouldRejectEmailWithMissingDomain() {
        //Arrange
        String email = "user@";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertFalse(result);
    }

    @Test
    void shouldRejectEmailWithMissingLocalPart() {
        //Arrange
        String email = "@gmail.com";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertFalse(result);
    }


    @Test
    void shouldAcceptEmailWithUnderscoreAndDot() {
        //Arrange
        String email = "first.last_name@example.org";

        //Act
        boolean result = EmailValidator.isValid(email);

        //Assert
        assertTrue(result);
    }
}