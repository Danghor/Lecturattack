package Lecturattack.entities;/*
 * Copyright (c) 2015.
 */

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class InformationField implements Renderable {
  private int x;
  private int y;
  private String fixedText;
  private String dynamicText;
  private TrueTypeFont trueTypeFont;

  public InformationField(int x, int y, String fixedText) {
    this.x = x;
    this.y = y;
    this.fixedText = fixedText;
    java.awt.Font awtFont;
    awtFont = new java.awt.Font("Sanserif", java.awt.Font.BOLD, 24);
    trueTypeFont = new TrueTypeFont(awtFont, false);
  }

  public void setDynamicText(String dynamicText) {
    this.dynamicText = dynamicText;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    trueTypeFont.drawString(x, y, fixedText + dynamicText);
  }
}
