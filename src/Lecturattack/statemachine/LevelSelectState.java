package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.menu.AnimatedButton;
import Lecturattack.utilities.menu.AnimatedLevelButton;
import Lecturattack.utilities.menu.AnimatedMenuButton;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 * @author Stefanie Raschke
 * @author Tim Adamek
 */
public class LevelSelectState extends BasicGameState implements InputListener {
  private final int stateID;
  private StateBasedGame stateBasedGame;
  private Image background;
  private AnimatedButton[] menuButtons;
  private int currentSelection;
  private float previousWidth;
  private Color previousColor;

  /**
   * Constructor for LevelSelectState
   * Set the ID of this state to the given stateID
   * 
   * @param stateID
   */
  public LevelSelectState(int stateID) {
    this.stateID = stateID;
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.loadImage("backgroundMenu");
    menuButtons = new AnimatedButton[8];
    menuButtons[0] = new AnimatedLevelButton(150, 150, FileHandler.loadImage("level1"), FileHandler.loadImage("level1_locked"));
    menuButtons[1] = new AnimatedLevelButton(489, 150, FileHandler.loadImage("level2"), FileHandler.loadImage("level2_locked"));
    menuButtons[2] = new AnimatedLevelButton(828, 150, FileHandler.loadImage("level3"), FileHandler.loadImage("level3_locked"));
    menuButtons[3] = new AnimatedLevelButton(150, 350, FileHandler.loadImage("level4"), FileHandler.loadImage("level4_locked"));
    menuButtons[4] = new AnimatedLevelButton(489, 350, FileHandler.loadImage("level5"), FileHandler.loadImage("level5_locked"));
    menuButtons[5] = new AnimatedLevelButton(828, 350, FileHandler.loadImage("level6"), FileHandler.loadImage("level6_locked"));
    menuButtons[6] = new AnimatedMenuButton(150, 600, FileHandler.loadImage("back_down"), FileHandler.loadImage("back"));
    menuButtons[7] = new AnimatedMenuButton(888, 600, FileHandler.loadImage("reset_down"), FileHandler.loadImage("reset"));
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    // draw a red rectangle around the selected level
    int rectPosition = 6;
    for (int i = 0; i < menuButtons.length; i++) {
      if (currentSelection == i) {
        // only change the button image, if it is a menu button
        // level select buttons get changed from player progress
        if (menuButtons[i] instanceof AnimatedMenuButton) {
          menuButtons[i].setActive();
        }
        rectPosition = i;
      }
      menuButtons[i].render(gameContainer, stateBasedGame, graphics);
    }
    // check if a level is selected, or a menu button
    if (rectPosition <= 5) {
      graphics.drawRect(menuButtons[rectPosition].getX(), menuButtons[rectPosition].getY(), 302, 169);
    }
  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    // must be implemented because the method is abstract in the parent class;
  }

  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    int lastLevelNumber = FileHandler.getLastLevelNumber();
    currentSelection = 6;
    // initialize the level select with the current player progress
    for (int i = 0; i < lastLevelNumber; i++) {
      menuButtons[i].setActive();
    }
    previousWidth = gameContainer.getGraphics().getLineWidth();
    previousColor = gameContainer.getGraphics().getColor();
    gameContainer.getGraphics().setLineWidth(7);
    gameContainer.getGraphics().setColor(Color.red);

  }

  @Override
  public void leave(GameContainer container, StateBasedGame game) throws SlickException {
    container.getGraphics().setLineWidth(previousWidth);
    container.getGraphics().setColor(previousColor);
  }

  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_LEFT) {
      if (currentSelection > 0) {
        currentSelection--;
      }
    } else if (key == Input.KEY_RIGHT) {
      if (currentSelection <= 6) {
        currentSelection++;
      }
    } else if (key == Input.KEY_UP) {
      if (currentSelection >= 3 && currentSelection <= 6) {
        currentSelection -= 3;
      } else if (currentSelection == 7) {
        currentSelection = 5;
      } else {
        currentSelection = 0;
      }
    } else if (key == Input.KEY_DOWN) {
      if (currentSelection <= 3) {
        currentSelection += 3;
      } else if (currentSelection == 4) {
        currentSelection = 6;
      } else {
        currentSelection = 7;
      }
    } else if (key == Input.KEY_ENTER) {
      // check if the level is unlocked yet
      if (menuButtons[currentSelection] instanceof AnimatedLevelButton && menuButtons[currentSelection].getActive()) {
        ((GameState) stateBasedGame.getState(Lecturattack.GAMESTATE)).loadLevel(currentSelection + 1);
        stateBasedGame.enterState(Lecturattack.GAMESTATE);
      } else if (currentSelection == 6) {
        stateBasedGame.enterState(Lecturattack.MAINMENUSTATE);
      } else if (currentSelection == 7) {
        FileHandler.resetLastLevelNumber();
        for (int i = 1; i <= 5; i++) {
          menuButtons[i].setInactive();
        }
      }
    }
  }

}
