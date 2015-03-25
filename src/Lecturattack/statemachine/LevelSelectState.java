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
  private int iMenuSelector;

  public LevelSelectState(int iStateID) {
    this.stateID = iStateID;
  }

  @Override
  public int getID() {
    return stateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.createMenuBackground();
    menuButton = FileHandler.createLevelSelectButtons();
    iMenuSelector = 0;
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
      if (iMenuSelector > 0) {
        iMenuSelector--;
      }
    } else if (key == Input.KEY_RIGHT) {
      if (iMenuSelector <= 5) {
        iMenuSelector++;
      }
    } else if (key == Input.KEY_UP) {
      if (iMenuSelector >= 3) {
        iMenuSelector -= 3;
      } else {
        iMenuSelector = 0;
      }
    } else if (key == Input.KEY_DOWN) {
      if (iMenuSelector <= 2) {
        iMenuSelector += 3;
      } else {
        iMenuSelector = 6;
      }
    } else if (key == Input.KEY_ENTER) {
      if (iMenuSelector >= 0 && iMenuSelector <= 5) {
        // TODO: give the GameState the selected level
        stateBasedGame.enterState(2);
      } else if (iMenuSelector == 6) {
        stateBasedGame.enterState(0);
      }
    }
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(background, 0, 0);
    for (int i = 0; i < menuButton.length; i++) {
      // check if the button currently has focus
      menuButton[i].render(graphics, iMenuSelector == i);
    }
  }

}
