package statemachine;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Nick Steyer on 09/03/2015
 */
public class LevelSelectState extends BasicGameState {
  private int ID;

  public LevelSelectState(int iStateID) {
    this.ID = iStateID;
  }

  @Override
  public int getID() {
    return ID;
  }
  
  @Override
  public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

  }

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

  }
}
