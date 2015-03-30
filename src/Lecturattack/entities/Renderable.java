package Lecturattack.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Tim Adamek
 */
public interface Renderable {
  void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics);
}
