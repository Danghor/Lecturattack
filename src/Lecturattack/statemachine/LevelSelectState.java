package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.menu.AnimatedButton;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class LevelSelectState extends BasicGameState implements InputListener {
  private int stateID;
  private StateBasedGame stateBasedGame;
  private Image background;
  private AnimatedButton[] menuButton;
  private int currentSelection;

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
    menuButton = new AnimatedButton[7];
    menuButton[0] = new AnimatedButton(150, 150, FileHandler.loadImage("level1_down"), FileHandler.loadImage("level1"));
    menuButton[1] = new AnimatedButton(489, 150, FileHandler.loadImage("level2_down"), FileHandler.loadImage("level2"));
    menuButton[2] = new AnimatedButton(828, 150, FileHandler.loadImage("level3_down"), FileHandler.loadImage("level3"));
    menuButton[3] = new AnimatedButton(150, 350, FileHandler.loadImage("level4_down"), FileHandler.loadImage("level4"));
    menuButton[4] = new AnimatedButton(489, 350, FileHandler.loadImage("level5_down"), FileHandler.loadImage("level5"));
    menuButton[5] = new AnimatedButton(828, 350, FileHandler.loadImage("level6_down"), FileHandler.loadImage("level6"));
    menuButton[6] = new AnimatedButton(150, 600, FileHandler.loadImage("back_down"), FileHandler.loadImage("back"));
  }

  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    currentSelection = 6;
  }

  @Override
  public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

  }

  /*
   * listen for user input
   */
  @Override
  public void keyPressed(int key, char c) {
    if (key == Input.KEY_LEFT) {
      if (currentSelection > 0) {
        currentSelection--;
      }
    } else if (key == Input.KEY_RIGHT) {
      if (currentSelection <= 5) {
        currentSelection++;
      }
    } else if (key == Input.KEY_UP) {
      if (currentSelection >= 3) {
        currentSelection -= 3;
      } else {
        currentSelection = 0;
      }
    } else if (key == Input.KEY_DOWN) {
      if (currentSelection <= 2) {
        currentSelection += 3;
      } else {
        currentSelection = 6;
      }
    } else if (key == Input.KEY_ENTER) {
      if (currentSelection >= 0 && currentSelection <= 5) {
        ((GameState) stateBasedGame.getState(GameState.stateID)).loadLevel(currentSelection + 1);
        stateBasedGame.enterState(GameState.stateID);
      } else if (currentSelection == 6) {
        stateBasedGame.enterState(0);
      }
    }
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    for (int i = 0; i < menuButton.length; i++) {
      menuButton[i].render(graphics, currentSelection == i);
    }
  }

}
