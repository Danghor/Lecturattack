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
 * @author Tim Adamek
 */
public class Flag implements Renderable {
  private final InformationField windTitle;
  private final int positionX;
  private final int positionY;
  private float windScale;

  public Flag(int positionX, int positionY) {
    this.positionX = positionX;
    this.positionY = positionY;
    windTitle = new InformationField(positionX - 30, positionY - 10, "Wind");
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    Polygon triangle = new Polygon();
    triangle.addPoint(positionX, positionY + 20);
    triangle.addPoint(positionX, positionY + 80);
    triangle.addPoint(positionX + windScale, positionY + 50);
    graphics.fill(triangle, new GradientFill(gameContainer.getWidth() / 2, 40f, new Color(0x6E, 0x0D, 0x13), gameContainer.getWidth() / 2 + windScale, 40f, new Color(0xCB, 0x32, 0x2C)));
    graphics.draw(triangle);
    windTitle.render(gameContainer, stateBasedGame, graphics);
  }

  public void setWindScale(float windScale) {
    this.windScale = windScale * 100;
  }
}
