package test.pojo;

import com.straightsixstudios.utility.validation.annotations.Validate;
import com.straightsixstudios.utility.validation.annotations.ValidateString;
import lombok.Builder;
import lombok.Getter;

/**
 * @author charles
 */
@Validate
@Builder
@Getter
public class TestValues {

    @ValidateString(nullable = false, values = {"ONE","TWO","THREE"})
    private String inputValue;

}
