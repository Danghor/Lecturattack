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
 * @author Laura Hillenbrand
 */

public class GameState extends BasicGameState implements InputListener {
  private static final int MAX_LEVEL = 6;
  private final int stateID;
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

  /**
   * a list of all Targets that have been hit and are not part of the game
   * anymore, but are still falling out of the frame and therefore have to
   * be rendered
   */
  private ArrayList<Target> deadTargets;

  /**
   * Set the ID of this state to the given stateID
   *
   * @param stateID The stateID to be set.
   */
  public GameState(int stateID) {
    this.stateID = stateID;
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
      if (projectile.isUnreachable() || ((System.currentTimeMillis()-Player.getThrowStart())>=10000)) {
        initiateNextThrow();
        
      }
      
    }
    getCurrentPlayer().updateArmAnimation();

    Projectile checkProjectile = getCurrentPlayer().getProjectile();//TODO comment
    if (checkProjectile != null) {
      this.projectile = checkProjectile;
      score -= 10;
    }

    changeThrowingAngleWithUserInput(gameContainer);
    flag.setWindScale(wind);

    try {
      score += PhysicsEngine.calculateStep(projectile, level.getTargets(), deadTargets, wind, delta, level.getGroundLevel());
    } catch (IllegalArgumentException e) {
      System.out.print(e.getMessage());
      System.out.println(" Delta: " + delta);
    }

    scoreField.setDynamicText(Integer.toString(score));
    getCurrentPlayer().updatePowerSlider(delta);
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
    /**
     * Render projectile here, if the player doesn't have it
     */
    if (projectile != null) {
      projectile.render(gameContainer, stateBasedGame, graphics);
    }

    scoreField.render(gameContainer, stateBasedGame, graphics);
    playerName.render(gameContainer, stateBasedGame, graphics);
    flag.render(gameContainer, stateBasedGame, graphics);

    if (gameStatus == GameStatus.LEVEL_WON) {
      // draw the images centered
      graphics.drawImage(victory, (Lecturattack.WIDTH - victory.getWidth()) / 2, 91);
    } else if (gameStatus == GameStatus.LEVEL_LOST) {
      graphics.drawImage(defeat, (Lecturattack.WIDTH - defeat.getWidth()) / 2, 91);
    }
  }

  /**
   * This method is called, when the projectile is not moving anymore
   * and the previous turn is over
   */
  private void initiateNextThrow() {
    // check if there are no more enemies alive
    boolean enemiesAlive = false;
    for (Target target : level.getTargets()) {
      if (target.getType() == TargetType.ENEMY) {
        enemiesAlive = true;
      }
    }
    projectile = null;
    if (!enemiesAlive) {
      gameStatus = GameStatus.LEVEL_WON;
      saveGameProgress();
    } else if (score <= 0) {
      gameStatus = GameStatus.LEVEL_LOST;
    } else {
      getCurrentPlayer().reset();
      randomizeWind();
    }
  }

  /**
   * This method will first check if the level just won unlocks the next level. If so, the game progress is updated.
   * If not, nothing happens; i.e. if the next level was already unlocked, the game progress is not overwritten.
   */
  private void saveGameProgress() {
    int savedProgress = FileHandler.getLastLevelNumber();
    if (currentLevel < MAX_LEVEL && savedProgress <= currentLevel) { //<=, because the file saves the last unlocked level
      FileHandler.setLastUnlockedLevel(currentLevel + 1);
    }
  }

  private void changeThrowingAngleWithUserInput(GameContainer gameContainer) {
    if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
      getCurrentPlayer().moveArmRight();
    } else if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
      getCurrentPlayer().moveArmLeft();
    }
  }

  @Override
  public void keyPressed(int key, char c) {
    switch (key) {
      case Input.KEY_SPACE:
        switch (gameStatus) {
          case PLAYING:
            getCurrentPlayer().throwProjectile();
            break;
          case LEVEL_WON:
            if (currentLevel < MAX_LEVEL) {
              currentLevel++;
              resetLevel();
            } else {
              stateBasedGame.enterState(Lecturattack.MAINMENU_STATE);
            }
            break;
          case LEVEL_LOST:
            resetLevel();
            break;
        }
        break;
      case Input.KEY_ESCAPE:
        stateBasedGame.enterState(Lecturattack.PAUSE_STATE);
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
        break;
    }
  }

  /**
   * load the specified level in the gamestate
   *
   * @param level the integer value which indicates the level (1= first level , 2 = second level, ...)
   */
  public void loadLevel(int level) {
    setCurrentLevel(level);
    // every time a level is loaded the player have to be returned to their original state and their position is set for every level
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
   * to reset the level it is only necessary to load the current level again
   */
  private void resetLevel() {
    loadLevel(currentLevel);
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
    wind = (float) ((Math.random() * 6) % 3 - 1.5); //todo: in config file
  }

  private void setCurrentLevel(int currentLevel) {
    this.currentLevel = currentLevel;
  }

  private Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  public enum GameStatus {
    PLAYING, LEVEL_WON, LEVEL_LOST
  }

}
