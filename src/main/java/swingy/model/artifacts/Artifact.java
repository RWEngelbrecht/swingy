package swingy.model.artifacts;

import org.hibernate.validator.constraints.Length;

public class Artifact {
    @Length(min=1)
    private String name;
    protected int atk;
    protected int def;

    public Artifact(String name) {
        this.name = name;
    }

    public String getArtifactName() {
        return this.name;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getDef() {
        return this.def;
    }
}
