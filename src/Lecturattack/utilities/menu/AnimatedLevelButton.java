package Lecturattack.utilities.menu;

import Lecturattack.entities.Renderable;
import org.newdawn.slick.Image;

/**
 * AnimatedLevelButton inherits from AnimatedButton.
 * LevelButtons have to be set to active once, and keep their active status.
 *
 * @author Andreas Geis
 */
public class AnimatedLevelButton extends AnimatedButton implements Renderable {
  /**
   * Constructor for AnimatedButtons
   *
   * @param x             the x-position of the button
   * @param y             the y-position of the button
   * @param activeImage   the image which is displayed, when the button is currently selected
   * @param inactiveImage the image which is displayed, when the button is not currently selected
   */
  public AnimatedLevelButton(int x, int y, Image activeImage, Image inactiveImage) {
    super(x, y, activeImage, inactiveImage);
  }
}
