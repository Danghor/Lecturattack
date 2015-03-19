package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import com.sun.glass.ui.Screen;

public class MainTest {


  public static void main(String[] args) {
    try {
      AppGameContainer app = new AppGameContainer(new PhysicsPrototype()); // instanciate Prototype here
      app.setDisplayMode(800, 800, false);
      app.setTargetFrameRate(120);
      app.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }


}
