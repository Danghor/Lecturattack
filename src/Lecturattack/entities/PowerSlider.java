package Lecturattack.entities;

import Lecturattack.utilities.FileHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Andreas Geis
 */
public class PowerSlider implements Renderable {
  // powerslide.png has 255px and powserslideLine is 5px width
  private static final int MAX_FORCE = 250;
  private static final float SPEED_SCALE = 0.1f;
  private float selectedForce;
  private boolean movingRight;
  private Image powerslide;
  private Image powerslideLine;

  public PowerSlider() {
    powerslide = FileHandler.loadImage("powerslide");
    powerslideLine = FileHandler.loadImage("powerslideLine");
  }

  @Override
  public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
    graphics.drawImage(powerslide, 10, 380);//TODO.andreas constants for the position
    graphics.drawImage(powerslideLine, 10 + selectedForce, 368);
  }

  /**
   * updates the PowerSlider, depending on direction it either moves right or left
   *
   * @param delta The time-difference compared to the previous step.
   */
  public void update(int delta) {

    if (movingRight) {
      selectedForce += SPEED_SCALE * delta;
      if (selectedForce > MAX_FORCE) {
        selectedForce = MAX_FORCE;
        movingRight = false;
      }
    } else {
      selectedForce -= SPEED_SCALE * delta;
      if (selectedForce < 0) {
        selectedForce = 0;
        movingRight = true;
      }
    }
  }

  public float getSelectedForce() {
    return selectedForce;
  }

  public void reset() {
    selectedForce = 0;
    movingRight = true;
  }
}
