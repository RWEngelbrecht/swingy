package swingy.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class InputValidator {
    private static int validatorType;
    @NotEmpty(message="Enter a valid name")
    private String heroName;
    public InputValidator(int type) {
        validatorType = type;
    }

    public boolean validateWithContext(String value, String context) {
        if (context.equals("heroName"))
            return validateHeroName(value);
        return false;
    }

    private boolean validateHeroName(@NotEmpty String name) {
        heroName = name;
        System.out.println("From inputValidator: heroName = "+heroName);
        return true;
    }
}
