/*
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

/**
 * Created by Andreas Geis on 18/03/2015
 */
public class MainMenuState extends BasicGameState implements InputListener {

  private int iStateID;
  private StateBasedGame stateBasedGame;
  private Image logo;
  private AnimatedButton[] menuButton = new AnimatedButton[3];
  private int iMenuSelector = 0;

  public MainMenuState(int iStateID) {
    this.iStateID = iStateID;
  }

  @Override
  public int getID() {
    return iStateID;
  }

  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    this.stateBasedGame = stateBasedGame;
    logo = new Image("resources/images/logo.png");
    // TODO: check if the player is in level 2 or higher and replace the
    // "Spiel Starten" image with "Spiel fortsetzen" for Button 1
    menuButton[0] = new AnimatedButton(245, 500, new Image("resources/images/startGame_down.png"), new Image("resources/images/startGame.png"));
    menuButton[1] = new AnimatedButton(495, 500, new Image("resources/images/levelSelect_down.png"), new Image("resources/images/levelSelect.png"));
    menuButton[2] = new AnimatedButton(745, 500, new Image("resources/images/endGame_down.png"), new Image("resources/images/endGame.png"));
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
      if (iMenuSelector < 2) {
        iMenuSelector++;
      }
    } else if (key == Input.KEY_ENTER) {
      if (iMenuSelector == 0) {
        stateBasedGame.enterState(2);
      }
      if (iMenuSelector == 1) {
        stateBasedGame.enterState(1);
      }
      if (iMenuSelector == 2) {
        System.exit(0);
      }
    }
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    graphics.drawImage(logo, 270, 70);
    for (int i = 0; i < menuButton.length; i++) {
      // check if the button currently has focus
      menuButton[i].render(graphics, iMenuSelector == i);
    }
  }

}
