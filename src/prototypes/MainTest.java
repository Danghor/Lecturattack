package prototypes;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.awt.*;

public class MainTest {
  public static final int DESIGN_WIDTH = 800;
  public static final int DESIGN_HEIGHT = 600;

  public static void main(String[] args) {
    try {
      AppGameContainer app = new AppGameContainer(new PhysicsPrototype()); // instanciate Prototype here

      int sWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
      int sHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

      float widthScale = (float) sWidth / (float) DESIGN_WIDTH;
      float heightScale = (float) sHeight / (float) DESIGN_HEIGHT;

      float minScale = Math.min(widthScale, heightScale);

      float finalScale = minScale * 0.8f;

      app.setDisplayMode(DESIGN_WIDTH * 2, DESIGN_HEIGHT * 2, false);
      app.setTargetFrameRate(120);
      app.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }

}
