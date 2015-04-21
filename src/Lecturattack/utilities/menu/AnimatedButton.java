package Lecturattack.utilities.menu;

import Lecturattack.entities.Renderable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Animated Button is the parent class for all MenuButtons.
 *
 * @author Andreas Geis
 */
public class AnimatedButton implements Renderable {
  protected float x;
  protected float y;
  protected Image activeImage;
  protected Image inactiveImage;
  protected boolean active;

  /**
   * Constructor for AnimatedButtons
   *
   * @param x             the x-position of the button
   * @param y             the y-position of the button
   * @param activeImage   the image which is displayed, when the button is currently selected
   * @param inactiveImage the image which is displayed, when the button is not currently selected
   */
  public AnimatedButton(int x, int y, Image activeImage, Image inactiveImage) {
    this.x = x;
    this.y = y;
    this.activeImage = activeImage;
    this.inactiveImage = inactiveImage;
    this.active = false;
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    if (active) {
      graphics.drawImage(activeImage, x, y);
    } else {
      graphics.drawImage(inactiveImage, x, y);
    }
  }

  /**
   * returns the x-position of the button
   *
   * @return x
   */
  public float getX() {
    return x;
  }

  /**
   * returns the y-position of the button
   *
   * @return y
   */
  public float getY() {
    return y;
  }

  /**
   * Set the button to an active Status.
   * This changes the picture, which is rendered, to activeImage.
   */
  public void setActive() {
    this.active = true;
  }

  /**
   * Set the button to an inactive Status.
   * This changes the picture, which is rendered, to inactiveImage.
   */
  public void setInactive() {
    this.active = false;
  }

  /**
   * Check if the button is currently active.
   *
   * @return true, if the button is active, otherwise false
   */
  public boolean getActive() {
    return active;
  }
}
