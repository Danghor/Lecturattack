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
  private final int x;
  private final int y;
  private final String fixedText;
  private final TrueTypeFont trueTypeFont;
  private String dynamicText;

  /**
   * Constructor for InformationField
   *
   * @param x         the x-position of the information field
   * @param y         the y-position of the information field
   * @param fixedText one part of the text, which is rendered, this text doesn't change
   */
  public InformationField(int x, int y, String fixedText) {
    this.x = x;
    this.y = y;
    this.fixedText = fixedText;
    java.awt.Font awtFont;
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
    String stringToDraw;
    if (dynamicText != null) {
      stringToDraw = fixedText + dynamicText;
    } else {
      stringToDraw = fixedText;
    }
    trueTypeFont.drawString(x, y, stringToDraw);
  }
}
