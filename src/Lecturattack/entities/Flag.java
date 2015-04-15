package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Nick Steyer on 08/03/2015
 */
public class Flag implements Renderable {
  private float windScale;
  public void setWindScale(float windScale){
    this.windScale=windScale*100;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    Polygon triangle = new Polygon();
    triangle.addPoint(gameContainer.getWidth()/2,10);
    triangle.addPoint(gameContainer.getWidth()/2,70);
    triangle.addPoint(gameContainer.getWidth()/2 + windScale,40);

    graphics.fill(triangle);
    graphics.draw(triangle);
    System.out.println(System.getProperty("os.version"));

    
  }
}
