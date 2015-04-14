package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Nick Steyer on 08/03/2015
 */
public class Flag implements Renderable {
  private float windScale;
  public void setWindScale(float windScale){
    this.windScale=windScale;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    Polygon triangle = new Polygon();
    triangle.addPoint(100,200);
    triangle.addPoint(100,300);
    triangle.addPoint(200*windScale,250);
    graphics.draw(triangle);


  }
}
