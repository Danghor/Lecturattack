package Lecturattack.utilities.menu;

import Lecturattack.entities.Renderable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * AnimatedLevelButton inherits from AnimatedButton.
 * LevelButtons have to be set to active once, and keep their active status.
 * 
 * @author Andreas Geis
 */
public class AnimatedLevelButton extends AnimatedButton implements Renderable {
  public AnimatedLevelButton(int x, int y, Image active, Image inactive) {
    super(x, y, active, inactive);
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    super.render(gameContainer, stateBasedGame, graphics);
  }
}
