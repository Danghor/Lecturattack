/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * Created by Nick Steyer on 09/03/2015
 */

public class GameState extends BasicGameState {
  private int currentLevel;
  private float wind;
  private Player player;
  private ArrayList<Target> targets;
  private Projectile projectile;
  private Flag flag;
  private PowerSlider slider;
  private InformationField score;
  private InformationField playerName;

  public void loadLevel(int level) {
    //load player and targets with LevelLoader
  }

  private void resetLevel() {
  }

  @Override
  public int getID() {
    return 0;
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
