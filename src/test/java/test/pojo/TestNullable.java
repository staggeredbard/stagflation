package test.pojo;

import com.straightsixstudios.utility.validation.annotations.Validate;
import com.straightsixstudios.utility.validation.annotations.ValidateString;
import lombok.Data;

/**
 * @author charles
 */
@Validate
@Data
public class TestNullable {

    @ValidateString(nullable = true)
    private String field;

}
