package statemachine;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Nick Steyer on 09/03/2015
 */
public class Lecturattack extends StateBasedGame {
  private GameState gameState;
  private MainMenuState mainMenuState;
  private LevelSelectState levelSelectState;
  private PauseState pauseState;
  public Lecturattack(String name) {
    super(name);
  }

  public static void main(String[] args) {
  }

  @Override
  public void initStatesList(GameContainer gameContainer) throws SlickException {

  }
}
