package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @Author Tim Adamek
 */
public class Flag implements Renderable {
  private float windScale;
  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    Polygon triangle= new Polygon();
    triangle.addPoint(gameContainer.getWidth() / 2, 10);
    triangle.addPoint(gameContainer.getWidth() / 2, 70);
    triangle.addPoint(gameContainer.getWidth() / 2 + windScale, 40);
    graphics.fill(triangle,new GradientFill(gameContainer.getWidth() / 2,40f, Color.yellow,gameContainer.getWidth() / 2+windScale,40f,Color.red));
    graphics.draw(triangle);
    System.out.println(System.getProperty("os.version"));
  }

  public void setWindScale(float windScale) {
    this.windScale = windScale * 100;
  }
}
