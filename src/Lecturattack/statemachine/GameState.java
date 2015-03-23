package Lecturattack.statemachine;/*
 * Copyright (c) 2015.
 */

import Lecturattack.entities.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import Lecturattack.utilities.FileHandler;
import Lecturattack.utilities.LevelGenerator;
import Lecturattack.utilities.xmlHandling.levelLoading.LevelElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Steyer on 09/03/2015
 */

public class GameState extends BasicGameState {
  private static int ID;
  private int currentLevel;
  private float wind;
  private Player player;
  private ArrayList<Target> targets;
  private Projectile projectile;
  private Flag flag;
  private InformationField score;
  private InformationField playerName;

	public GameState(int iStateID) {
    ID = iStateID;
  }

	public void loadLevel(int level) {
		// load player and targets with LevelLoader
	}
  private void resetLevel() {
    //todo: loadlevel(currentLevel)
  }

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    player = new Player();
    targets = new ArrayList<>();

    try {//TODO see if exeption can be dealt with somewhere else
      List<LevelElement> levelElements = FileHandler.getLevelData(1);
      FileHandler.loadTargetConfig();
      LevelGenerator.generateLevel(levelElements,player,targets);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
	  player.render(gameContainer,stateBasedGame,graphics);
    for (Target target:targets){
      target.render(gameContainer,stateBasedGame,graphics);
    }
	}

  @Override
  public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    //PhysicsEngine.calculateStep(null, null, 0, 0);//TODO real values
  }
}
