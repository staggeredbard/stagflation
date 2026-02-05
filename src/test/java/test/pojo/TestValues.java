package test.pojo;

import com.straightsixstudios.utility.validation.annotations.Validate;
import com.straightsixstudios.utility.validation.annotations.ValidateString;

/**
 * @author charles
 */
@Validate
public class TestValues {

    @ValidateString(nullable = false, values = {"ONE","TWO","THREE"})
    public String inputValue;

    private TestValues(Builder builder){
        this.inputValue = builder.inputValue;
    }

    public static class Builder {

        private String inputValue;

        public Builder setInputValue(String inputValue) {
            this.inputValue = inputValue;
            return this;
        }

        public TestValues build() {
            return new TestValues(this);
        }
    }
}
