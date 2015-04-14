package Lecturattack.statemachine;

import Lecturattack.entities.*;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.Level;
import Lecturattack.utilities.LevelGenerator;
import Lecturattack.utilities.PhysicsEngine;
import Lecturattack.utilities.xmlHandling.configLoading.PlayerStandard;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 * @author Nick Steyer
 */

public class GameState extends BasicGameState implements InputListener {
  private static final int DEGREE_ARM_MOVE = 1;
  public static int stateID;
  private StateBasedGame stateBasedGame;
  private int currentLevel;
  private ArrayList<Player> players;
  private int currentPlayerIndex;
  private Level level;
  private Projectile projectile;
  private Flag flag;
  private InformationField scoreField;
  private InformationField playerName;
  private Image background;
  private int score;

  public GameState(int stateID) {
    GameState.stateID = stateID;
  }

  //todo: save wind for current level and only refresh when new level is loaded
  private static float getRandomWind() {
    return (float) ((Math.random() * 10) % 5 - 2.5);
  }

  private Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  public void loadLevel(int level) {
    setCurrentLevel(level);
    try {
      List<LevelElement> levelElements = FileHandler.getLevelData(level);
      this.level = LevelGenerator.getGeneratedLevel(levelElements);
    } catch (SlickException | IOException e) {
      e.printStackTrace();
    }
    for (Player player : players) {
      player.setPosition(this.level.getPlayerPositionX(), this.level.getPlayerPositionY());
      player.reset();
    }
    projectile = null;

    scoreField = new InformationField(1000, 25, "Score: ");
    // set a starting score
    score = 100;
    playerName = new InformationField(1000, 0, "Dozent: ");
    playerName.setDynamicText(getCurrentPlayer().getName());
  }

  private void resetLevel() {
    loadLevel(getCurrentLevel());
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.loadImage("background");
    players = new ArrayList<>();
    List<PlayerStandard> playerStandards = FileHandler.getPlayerData();
    for (PlayerStandard meta : playerStandards) {
      players.add(new Player(meta.getBodyImageAsImage(), meta.getArmImageAsImage(), meta.getProjectileMeta(), meta.getName()));
    }
    currentPlayerIndex = 0;
    setCurrentLevel(1); // default

  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    getCurrentPlayer().render(gameContainer, stateBasedGame, graphics);
    for (Target target : level.getTargets()) {
      target.render(gameContainer, stateBasedGame, graphics);
    }
    // render projectile here, if the player doesn't have it
    // the projectile is only not null if it was returned by the player
    if (projectile != null) {
      projectile.render(gameContainer, stateBasedGame, graphics);
    }

    scoreField.render(gameContainer, stateBasedGame, graphics);
    playerName.render(gameContainer, stateBasedGame, graphics);

  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
    if (projectile != null) {
      if (projectile.isUnreachable()) {
        projectile = null;
        getCurrentPlayer().reset();
      }
    }

    changeThrowingDegreeWithUserInput(gameContainer);

    score += PhysicsEngine.calculateStep(projectile, level.getTargets(), getRandomWind(), delta, level.getGroundLevel());
    scoreField.setDynamicText(Integer.toString(score));

    getCurrentPlayer().updatePowerSlider(delta);
  }

  @Override
  public void keyPressed(int key, char c) {
    switch (key) {
      case Input.KEY_SPACE:
        Projectile checkProjectile = getCurrentPlayer().throwProjectile();
        if (checkProjectile != null) {
          this.projectile = checkProjectile;
          score -= 10;
        }
        break;
      case Input.KEY_ESCAPE:
        stateBasedGame.enterState(Lecturattack.PAUSESTATE);
        break;
      case Input.KEY_UP:
        if (getCurrentPlayer().getPlayerState() == Player.PlayerState.ANGLE_SELECTION) {
          selectNextPlayer();
        }
        break;
      case Input.KEY_DOWN:
        if (getCurrentPlayer().getPlayerState() == Player.PlayerState.ANGLE_SELECTION) {
          selectPreviousPlayer();
        }
        break;
    }
  }

  private void changeThrowingDegreeWithUserInput(GameContainer gameContainer) {
    if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
      getCurrentPlayer().moveArm(DEGREE_ARM_MOVE);
    } else if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
      getCurrentPlayer().moveArm(-DEGREE_ARM_MOVE);
    }
  }

  private void selectNextPlayer() {
    float previousAngle = getCurrentPlayer().getAngle();

    if (currentPlayerIndex >= players.size() - 1) {
      currentPlayerIndex = 0;
    } else {
      currentPlayerIndex++;
    }

    getCurrentPlayer().setAngle(previousAngle);
    playerName.setDynamicText(getCurrentPlayer().getName());
  }

  private void selectPreviousPlayer() {
    float previousAngle = getCurrentPlayer().getAngle();

    if (currentPlayerIndex <= 0) {
      currentPlayerIndex = players.size() - 1;
    } else {
      currentPlayerIndex--;
    }

    getCurrentPlayer().setAngle(previousAngle);
    playerName.setDynamicText(getCurrentPlayer().getName());
  }

  public int getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(int currentLevel) {
    this.currentLevel = currentLevel;
  }
}
