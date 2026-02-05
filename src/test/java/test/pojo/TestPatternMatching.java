package test.pojo;

import com.straightsixstudios.utility.validation.annotations.Validate;
import com.straightsixstudios.utility.validation.annotations.ValidateString;

import java.util.Objects;

/**
 * @author charles
 */
@Validate
public class TestPatternMatching {

    @ValidateString(pattern = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$", matches = true)
    public String guid;

    private TestPatternMatching(Builder builder) {
        this.guid = builder.guid;
    }

    public static class Builder {
        private String guid;

        public Builder setGuid(String guid) {
            this.guid = guid;
            return this;
        }

        public TestPatternMatching build() {
            return new TestPatternMatching(this);
        }
    }

    public String getGuid() {
        return guid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TestPatternMatching that = (TestPatternMatching) o;
        return Objects.equals(guid, that.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(guid);
    }
}
