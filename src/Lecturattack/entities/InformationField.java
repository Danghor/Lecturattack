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
  int x;
  int y;
  private String fixedText;
  private String dynamicText;
  private java.awt.Font oAwtFont;
  private TrueTypeFont oTrueTypeFont;

  public InformationField(int x, int y, String fixedText) {
    this.x = x;
    this.y = y;
    this.fixedText = fixedText;
    oAwtFont = new java.awt.Font("Sanserif", java.awt.Font.BOLD, 24);
    oTrueTypeFont = new TrueTypeFont(oAwtFont, false);
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    oTrueTypeFont.drawString(x, y, fixedText + dynamicText);
  }

  public void setDynamicText(String dynamicText) {
    this.dynamicText = dynamicText;
  }

}
