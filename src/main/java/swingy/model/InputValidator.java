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


    private boolean validateHeroName(@NotEmpty String name) {
        heroName = name;
        System.out.println("From inputValidator: heroName = "+heroName);
        return true;
    }
}
