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
  private java.awt.Font awtFont;
  private TrueTypeFont trueTypeFont;

  /**
   * Constructor for InformationField
   * 
   * @param x the x-position of the information field
   * @param y the y-position of the information field
   * @param fixedText one part of the text, which is rendered, this text doesn't change
   */
  public InformationField(int x, int y, String fixedText) {
    this.x = x;
    this.y = y;
    this.fixedText = fixedText;
    awtFont = new java.awt.Font("Sanserif", java.awt.Font.BOLD, 24);
    trueTypeFont = new TrueTypeFont(awtFont, false);
  }

  /**
   * Dynamically change the second part of the text
   * 
   * @param dynamicText the dynamic text, which is rendered
   */
  public void setDynamicText(String dynamicText) {
    this.dynamicText = dynamicText;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    trueTypeFont.drawString(x, y, fixedText + dynamicText);
  }
}
