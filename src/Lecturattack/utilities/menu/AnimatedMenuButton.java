package Lecturattack.utilities.menu;

import Lecturattack.entities.Renderable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * AnimatedMenuButton inherits from AnimatedButton.
 * MenuButtons only keep their active status for one render step.
 *
 * @author Andreas Geis
 */
public class AnimatedMenuButton extends AnimatedButton implements Renderable {
  /**
   * Constructor for AnimatedButtons
   *
   * @param x             the x-position of the button
   * @param y             the y-position of the button
   * @param activeImage   the image which is displayed, when the button is currently selected
   * @param inactiveImage the image which is displayed, when the button is not currently selected
   */
  public AnimatedMenuButton(int x, int y, Image activeImage, Image inactiveImage) {
    super(x, y, activeImage, inactiveImage);
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    super.render(gameContainer, stateBasedGame, graphics);
    active = false;
  }
}
