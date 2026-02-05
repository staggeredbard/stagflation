package test.pojo;

import com.straightsixstudios.utility.validation.annotations.Validate;
import com.straightsixstudios.utility.validation.annotations.ValidateString;

@Validate
public class TestIllegalAccess {

    @ValidateString(nullable = true)
    private String field;

}
