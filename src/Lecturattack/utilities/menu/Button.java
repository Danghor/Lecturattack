package Lecturattack.utilities.menu;

import Lecturattack.entities.Renderable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class Button implements Renderable {
  protected float x;
  protected float y;
  protected Image activeImage;
  protected Image inactiveImage;
  protected boolean active;

  public Button(int x, int y, Image activeImage, Image inactiveImage) {
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

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public void setActive() {
    this.active = true;
  }

  public void setInactive() {
    this.active = false;
  }

  public boolean getActive() {
    return active;
  }
}
