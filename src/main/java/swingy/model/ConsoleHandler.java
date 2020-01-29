package swingy.model;

import swingy.controller.GameController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class ConsoleHandler {
    private static GameController gameController;
    public ConsoleHandler(GameController gameController) {
        this.gameController = gameController;
    }

    public void startCreateHero() {
        gameController.consoleCreateHeroControls();
    }

    public boolean checkHeroName(@NotNull String heroName) {
        return heroName.length() > 0;
    }

    public boolean checkHeroClass(@NotNull String heroClass) {
        return heroClass.equals("1") || heroClass.equals("2") || heroClass.equals("3");
    }

    public void startGame() {
        gameController.generateMap();
        gameController.consoleStartGame();
    }

    public void continueGame(int positionState) {
        if (positionState == 0) {
            gameController.reactEmptySpace();
        } else if (positionState == 2) {
            String enemy = gameController.getCurrEnemy();
            String enemyString = "You are confronted by a "+enemy;
            gameController.reactEnemySpace(enemyString);
        } else if (positionState == 3) {
            String artifact = gameController.getCurrArtifact();
            String artifactString = "You notice a "+artifact+" arbitrarily lying on the ground.";
            gameController.reactArtifactSpace(artifactString);
        }
    }

    public void startLoadHero() {
        ArrayList<String> saves = gameController.getPrintableSaves();
        if (saves != null)
            gameController.consoleLoadHeroControls(saves);
        else
            gameController.consoleGenerateMainMenu();
    }
}
