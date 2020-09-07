package swingy.model;

import swingy.controller.GameController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class InputHandler {
    @NotNull
	private static GameController gameController;

    public InputHandler(GameController gameController) {
        InputHandler.gameController = gameController;
    }

    public void startCreateHero() {
        gameController.consoleCreateHeroControls();
    }

    public boolean checkHeroName(String heroName) {
        return heroName.length() > 0;
    }

    public boolean checkHeroClass(String heroClass) {
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
            // while reactEnemySpace does not return defeat or smthn, do reactEnemySpace
            gameController.reactEnemySpace(enemyString);
        } else if (positionState == 3) {
            String artifact = gameController.getCurrArtifact().getArtifactName();
            String artifactString = "You notice a "+artifact+" arbitrarily lying on the ground.";
            gameController.reactArtifactSpace(artifactString);
        } else if (positionState < 0) {
            gameController.reactOutOfBounds();
        }
    }

    public void continueFight(String battleStatus) {
        if (battleStatus.equals("YOU DIED.")) {
            gameController.youDied(battleStatus);
        } else if (gameController.isOnEnemy()) {
            gameController.reactEnemySpace(battleStatus);
        } else {
            gameController.reactKilledEnemy(battleStatus);
        }
    }

    public void retreat(boolean success) {
        if (success)
            gameController.reactRetreated();
        else {
            String enemy = gameController.getCurrEnemy();
            String failedRetreat = "You trip over a hapless anteater and fail to escape. The "+enemy+" giggles.";
            gameController.reactEnemySpace(failedRetreat);
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
