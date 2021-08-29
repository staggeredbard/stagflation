package test;

import com.straightsixstudios.utility.validation.FieldValidator;
import com.straightsixstudios.utility.validation.exception.FieldValidationException;
import com.straightsixstudios.utility.validation.output.ValidationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import test.pojo.TestNullable;
import test.pojo.TestPatternMatching;
import test.pojo.TestValues;

import java.util.UUID;

/**
 * @author charles
 */
@ExtendWith(MockitoExtension.class)
class TestValidator {

    @InjectMocks
    private FieldValidator fieldValidator;

    @Test
    void testFieldValidationException(){
        Assertions.assertThrows(FieldValidationException.class, () -> {
           fieldValidator.validate(new Object());
        });
    }

    @Test
    void testNullableField() throws FieldValidationException {
        ValidationResult result = fieldValidator.validate(new TestNullable());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isValid());
    }

    @Test
    void testValuesNull() throws FieldValidationException {
        ValidationResult result = fieldValidator.validate(TestValues.builder().build());

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertTrue(result.getErrors().containsKey("inputValue"));
        Assertions.assertEquals("Null value in a non-nullable field", result.getErrors().get("inputValue"));
    }

    @Test
    void testValuesWrongCase() throws FieldValidationException {
        ValidationResult result = fieldValidator.validate(TestValues.builder().inputValue("one").build());

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertTrue(result.getErrors().containsKey("inputValue"));
        Assertions.assertEquals("Value provided does not match required values", result.getErrors().get("inputValue"));
    }

    @Test
    void testValuesMatching() throws FieldValidationException {
        ValidationResult result = fieldValidator.validate(TestValues.builder().inputValue("THREE").build());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isValid());
        Assertions.assertEquals(0, result.getErrors().size());
    }

    @Test
    void testPatternMatchingInvalid() throws FieldValidationException {
        ValidationResult result = fieldValidator.validate(TestPatternMatching.builder().guid("Not a guid").build());

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(1, result.getErrors().size());
    }

    @Test
    void testPatternMatchingValid() throws FieldValidationException {
        ValidationResult result = fieldValidator.validate(TestPatternMatching.builder().guid(UUID.randomUUID().toString()).build());

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals(1, result.getErrors().size());
    }

}
