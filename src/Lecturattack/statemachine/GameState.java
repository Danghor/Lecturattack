package Lecturattack.statemachine;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Lecturattack.entities.Flag;
import Lecturattack.entities.InformationField;
import Lecturattack.entities.Player;
import Lecturattack.entities.Projectile;
import Lecturattack.entities.Target;
import Lecturattack.entities.types.TargetType;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.Level;
import Lecturattack.utilities.LevelGenerator;
import Lecturattack.utilities.PhysicsEngine;
import Lecturattack.utilities.xmlHandling.configLoading.PlayerStandard;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelData;

/**
 * @author Tim Adamek
 * @author Andreas Geis
 * @author Nick Steyer
 * @author Laura Hillenbrand
 */

public class GameState extends BasicGameState implements InputListener {
  private static final int MAX_LEVEL = 6;
  private static final int MAX_THROW_DURATION_IN_SECONDS = 7;

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
  private Sound victorySound;
  private Sound defeatSound;
  private float wind;
  private GameStatus gameStatus;
  // this indicates, if a player sound already played in this turn
  private boolean playerSoundPlayed;

  /**
   * a list of all Targets that have been hit and are not part of the game
   * anymore, but are still falling out of the frame and therefore have to
   * be rendered
   */
  private ArrayList<Target> deadTargets;

  private Color previousColor;//this saves the previous global line color to returned back to default on state leave

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
    victorySound = FileHandler.loadSound("victory");
    defeatSound = FileHandler.loadSound("defeat");

    deadTargets = new ArrayList<>();
    players = new ArrayList<>();

    List<PlayerStandard> playerStandards = FileHandler.getPlayerData();
    for (PlayerStandard meta : playerStandards) {
      players.add(new Player(meta.getBodyImageAsImage(), meta.getArmImageAsImage(), meta.getProjectileMeta(), meta.getName(), meta.getSoundAsSound()));
    }
    currentPlayerIndex = 0;
    setCurrentLevel(1); // default TODO don't use a default but instead use the actual level which should be loaded

    flag = new Flag();
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
    if (projectile != null) {
      if (projectile.isUnreachable() || ((System.currentTimeMillis() - getCurrentPlayer().getThrowStart()) >= MAX_THROW_DURATION_IN_SECONDS * 1000)) {
        initiateNextThrow();
      }
    }
    getCurrentPlayer().updateArmAnimation();

    Projectile checkProjectile = getCurrentPlayer().getProjectile();//TODO comment
    if (checkProjectile != null) {
      this.projectile = checkProjectile;
      level.reduceScore(10);
    }

    changeThrowingAngleWithUserInput(gameContainer);
    flag.setWindScale(wind);

    int currentAmountOfDeadTargets = deadTargets.size();


    try {
      //the physics engine returns the additional score for every update
      level.addScore(PhysicsEngine.calculateStep(projectile, level.getTargets(), deadTargets, wind, delta, level.getGroundLevel()));
    } catch (IllegalArgumentException e) {
      System.out.print(e.getMessage());
      System.out.println(" Delta: " + delta);
    }

    //reset player if an enemy has been hit
    if (deadTargets.size() > currentAmountOfDeadTargets) {
      initiateNextThrow();
    }

    scoreField.setDynamicText(Integer.toString(level.getScore()));
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

  private void resetPlayer() {
    projectile = null;
    getCurrentPlayer().reset();
  }

  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    previousColor = gameContainer.getGraphics().getColor();
    gameContainer.getGraphics().setColor(Color.black);
    playerSoundPlayed = false;
  }

  @Override
  public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    container.getGraphics().setColor(previousColor);
  }

  /**
   * This method is called, when the projectile is not moving anymore
   * and the previous turn is over
   */
  private void initiateNextThrow() {
    // check if there are no more enemies alive
    boolean enemiesOnScreen = false;

    for (Target target : level.getTargets()) {
      if (target.getType() == TargetType.ENEMY) {
        enemiesOnScreen = true;
      }
    }

    projectile = null;

    if (!enemiesOnScreen) {
      gameStatus = GameStatus.LEVEL_WON;
      victorySound.play();
      saveGameProgress();
    } else if (level.getScore() <= 0) {
      gameStatus = GameStatus.LEVEL_LOST;
      defeatSound.play();
    } else {
      resetPlayer();
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
        playerSoundPlayed = false;
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
        resetPlayer();
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
    LevelData levelData = FileHandler.getLevelData(level);
    this.level = LevelGenerator.getGeneratedLevel(levelData);
    for (Player player : players) {
      player.setPosition(this.level.getPlayerPositionX(), this.level.getPlayerPositionY());
      player.reset();
    }

    // when a new level is loaded the player holds the projectile, so it has to be null in the gamestate
    projectile = null;

    // generate a start wind
    randomizeWind();

    // set a starting score
    scoreField = new InformationField(10, 25, "Score: ");
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
    playPlayerSound();
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
    playPlayerSound();
    playerName.setDynamicText(getCurrentPlayer().getName());
  }
  
  private void playPlayerSound() {
    if (!playerSoundPlayed){
      getCurrentPlayer().playSound();
      playerSoundPlayed = true;
    }
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
