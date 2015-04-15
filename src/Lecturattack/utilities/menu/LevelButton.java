package Lecturattack.utilities.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import Lecturattack.entities.Renderable;

/**
 * @author Andreas Geis
 */
public class LevelButton extends Button implements Renderable {
  public LevelButton(int x, int y, Image active, Image inactive) {
    super(x, y, active, inactive);
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    super.render(gameContainer, stateBasedGame, graphics);
  }
}
