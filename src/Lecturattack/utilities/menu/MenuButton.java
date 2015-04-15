package Lecturattack.utilities.menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * @author Andreas Geis
 */
public class AnimatedButton {
  private float x;
  private float y;
  private Image active;
  private Image inactive;

  public AnimatedButton(int x, int y, Image active, Image inactive) {
    this.x = x;
    this.y = y;
    this.active = active;
    this.inactive = inactive;
  }

  public void render(Graphics g, boolean selected) {
    if (selected) {
      g.drawImage(active, x, y);
    } else {
      g.drawImage(inactive, x, y);
    }
  }
}
