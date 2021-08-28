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
public class TestPatternMatching {

    @ValidateString(pattern = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$", matches = true)
    public String guid;

}
