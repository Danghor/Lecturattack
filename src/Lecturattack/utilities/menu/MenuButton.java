package Lecturattack.utilities.menu;

import Lecturattack.entities.Renderable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class MenuButton extends Button implements Renderable {
  public MenuButton(int x, int y, Image activeImage, Image inactiveImage) {
    super(x, y, activeImage, inactiveImage);
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    super.render(gameContainer, stateBasedGame, graphics);
    active = false;
  }
}
