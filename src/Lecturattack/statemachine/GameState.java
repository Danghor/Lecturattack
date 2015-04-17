package Lecturattack.statemachine;

import Lecturattack.entities.*;
import Lecturattack.entities.types.TargetType;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.Level;
import Lecturattack.utilities.LevelGenerator;
import Lecturattack.utilities.PhysicsEngine;
import Lecturattack.utilities.xmlHandling.configLoading.PlayerStandard;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 * @author Nick Steyer
 */

public class GameState extends BasicGameState implements InputListener {
  public static int stateID;
  private StateBasedGame stateBasedGame;//TODO find another way
  private int currentLevel;
  private ArrayList<Player> players;
  private int currentPlayerIndex;
  private Level level;
  private Projectile projectile;
  private Flag flag;
  private InformationField scoreField;
  private InformationField playerName;
  private Image background;
  private Image victory;
  private Image defeat;
  private int score;
  private float wind;
  private GameStatus gameStatus;
  private ArrayList<Target> deadTargets; //a list of all Targets that have been hit and are not part of the game anymore, but are still falling out of the frame and therefore have to be rendered

  public enum GameStatus {
    PLAYING, LEVEL_WON, LEVEL_LOST
  }

  public GameState(int stateID) {
    GameState.stateID = stateID;
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.loadImage("background");
    victory = FileHandler.loadImage("victory");
    defeat = FileHandler.loadImage("defeat");
    deadTargets = new ArrayList<>();
    players = new ArrayList<>();
    List<PlayerStandard> playerStandards = FileHandler.getPlayerData();
    for (PlayerStandard meta : playerStandards) {
      players.add(new Player(meta.getBodyImageAsImage(), meta.getArmImageAsImage(), meta.getProjectileMeta(), meta.getName()));
    }
    currentPlayerIndex = 0;
    setCurrentLevel(1); // default TODO don't use a default but instead use the actual level which should be loaded
    flag = new Flag();
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
    if (projectile != null) {
      if (projectile.isUnreachable()) {
        initiateNextThrow();
      }
    }

    //the player is updated every step and returns a projectile when it is thrown, otherwise it returns null
    // to prevent overwriting the already existing projectile it is necessary to save it in a second variable and check whenever it is null
    Projectile checkProjectile = getCurrentPlayer().update(gameContainer, delta);
    if (checkProjectile != null) {
      this.projectile = checkProjectile;
      score -= 10;
    }

    flag.setWindScale(wind);
    score += PhysicsEngine.calculateStep(projectile, level.getTargets(), deadTargets, wind, delta, level.getGroundLevel());
    scoreField.setDynamicText(Integer.toString(score));
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    getCurrentPlayer().render(gameContainer, stateBasedGame, graphics);
    for (Target target : level.getTargets()) {
      target.render(gameContainer, stateBasedGame, graphics);
    }
    for (Target deadTarget : deadTargets) {
      deadTarget.render(gameContainer, stateBasedGame, graphics);
    }
    /*
     * Render projectile here, if the player doesn't have it
     * If the player has the projectile, it's null
     */
    if (projectile != null) {
      projectile.render(gameContainer, stateBasedGame, graphics);
    }
    scoreField.render(gameContainer, stateBasedGame, graphics);
    playerName.render(gameContainer, stateBasedGame, graphics);
    flag.render(gameContainer, stateBasedGame, graphics);
    if (gameStatus == GameStatus.LEVEL_WON) {
      graphics.drawImage(victory, 0, 0);
    } else if (gameStatus == GameStatus.LEVEL_LOST) {
      graphics.drawImage(defeat, 0, 0);
    }
  }

  /**
   * gets called when the projectile is not moving anymore and the previous turn
   * is over
   */
  public void initiateNextThrow() {
    // check if there are no more enemies alive
    boolean enemiesAlive = false;
    for (Target target : level.getTargets()) {
      if (target.getType() == TargetType.ENEMY) {
        enemiesAlive = true;
      }
    }
    if (!enemiesAlive) {
      gameStatus = GameStatus.LEVEL_WON;
      FileHandler.setLastLevelNumber(currentLevel + 1);
    } else if (score <= 0) {
      gameStatus = GameStatus.LEVEL_LOST;
    } else {
      projectile = null;
      getCurrentPlayer().reset();
      randomizeWind();
    }
  }

  @Override
  public void keyPressed(int key, char c) {
    switch (key) {
      case Input.KEY_SPACE:
        if (gameStatus == GameStatus.LEVEL_WON) {
          currentLevel++;
          loadLevel(currentLevel);
        } else if (gameStatus == GameStatus.LEVEL_LOST) {
          loadLevel(currentLevel);
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
      case Input.KEY_R:
        getCurrentPlayer().reset();
    }
  }

  /**
   * load the specified level in the gamestate
   *
   * @param level the integer value which indicates the level (1= first level , 2 = second level, ...)
   */
  public void loadLevel(int level) {
    setCurrentLevel(level);
    // every time a level is loaded the player have to be returned to their original state and their position is set for every leveel
    List<LevelElement> levelElements = FileHandler.getLevelData(level);
    this.level = LevelGenerator.getGeneratedLevel(levelElements);
    for (Player player : players) {
      player.setPosition(this.level.getPlayerPositionX(), this.level.getPlayerPositionY());
      player.reset();
    }
    // when a new level is loaded the player holds the projectile, so it has to be null in the gamestate
    projectile = null;
    // generate a start wind
    randomizeWind();

    projectile = null;
    randomizeWind();

    scoreField = new InformationField(10, 25, "Score: ");
    // set a starting score
    scoreField = new InformationField(10, 25, "Score: ");
    score = 100;
    playerName = new InformationField(10, 0, "Dozent: ");
    playerName.setDynamicText(getCurrentPlayer().getName());
    gameStatus = GameStatus.PLAYING;
  }

  /**
   * return the level to its original state
   */
  private void resetLevel() {
    //to reset the level it is only necessary to load the current level again
    loadLevel(getCurrentLevel());
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

  /**
   * generate a random wind
   */
  private void randomizeWind() {
    wind = (float) ((Math.random() * 6) % 3 - 1.5);
  }

  public int getCurrentLevel() {
    return currentLevel;
  }

  public void setCurrentLevel(int currentLevel) {
    this.currentLevel = currentLevel;
  }

  private Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

}
