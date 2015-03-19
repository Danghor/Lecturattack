package NoPackageCreatedYetPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Tim on 19.03.2015.
 */
public interface Renderable {
  void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics);
}
