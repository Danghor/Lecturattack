package test;

import org.newdawn.slick.*;

import java.util.ArrayList;

public class PhysicsPrototype extends BasicGame {

  private ArrayList<RigidRectangle> rects;
  private float deltaScale = 0.01f;

  public PhysicsPrototype() {
    super("SimpleTest");
  }

  @Override
  public void init(GameContainer container) throws SlickException {
    rects = new ArrayList<RigidRectangle>();
    rects.add(new RigidRectangle(50, 50, 80, 30, 1));
  }

  @Override
  public void update(GameContainer container, int delta) throws SlickException {
    Input input = container.getInput();
    if (input.isKeyDown(Input.KEY_LEFT)) {
      rects.get(0).applyForce(-1, 0);
    } else if (input.isKeyDown(Input.KEY_RIGHT)) {
      rects.get(0).applyForce(1, 0);
    }

    if (input.isKeyDown(Input.KEY_UP)) {
      rects.get(0).applyForce(0, -1);
    } else if (input.isKeyDown(Input.KEY_DOWN)) {
      rects.get(0).applyForce(0, 1);
    }

    if (input.isKeyDown(Input.KEY_1)) {
      rects.get(0).applyTorque(-1);
    } else if (input.isKeyDown(Input.KEY_2)) {
      rects.get(0).applyTorque(1);
    }

    for (RigidRectangle rect : rects) {
      rect.update(delta * deltaScale);
    }
  }

  @Override
  public void render(GameContainer container, Graphics g) throws SlickException {
    g.drawString("Use 1, 2 and Arrow-Keys", 10, 25);

    for (RigidRectangle rect : rects) {
      rect.draw(g);
    }
  }
}
