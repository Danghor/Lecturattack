package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.menu.AnimatedButton;

/**
 * @author Andreas Geis
 */
public class LevelSelectState extends BasicGameState implements InputListener {
  private int iStateID;
  private StateBasedGame stateBasedGame;
  private Image background;
  private AnimatedButton[] menuButton;
  private int iMenuSelector;

  public LevelSelectState(int iStateID) {
    this.iStateID = iStateID;
  }

  @Override
  public int getID() {
    return iStateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    background = FileHandler.loadImage("backgroundMenu");
    menuButton = new AnimatedButton[7];
    menuButton[0] = new AnimatedButton(245, 50, FileHandler.loadImage("level1_down"), FileHandler.loadImage("level1"));
    menuButton[1] = new AnimatedButton(495, 50, FileHandler.loadImage("level2_down"), FileHandler.loadImage("level2"));
    menuButton[2] = new AnimatedButton(745, 50, FileHandler.loadImage("level3_down"), FileHandler.loadImage("level3"));
    menuButton[3] = new AnimatedButton(245, 300, FileHandler.loadImage("level4_down"), FileHandler.loadImage("level4"));
    menuButton[4] = new AnimatedButton(495, 300, FileHandler.loadImage("level5_down"), FileHandler.loadImage("level5"));
    menuButton[5] = new AnimatedButton(745, 300, FileHandler.loadImage("level6_down"), FileHandler.loadImage("level6"));
    menuButton[6] = new AnimatedButton(245, 600, FileHandler.loadImage("back_down"), FileHandler.loadImage("back"));
  }
  
  @Override
  public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    iMenuSelector = 0;
  }

  @Override
  public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

  }

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
      menuButton[i].render(graphics, iMenuSelector == i);
    }
  }

}
