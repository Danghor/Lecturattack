package test;

import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class PhysicsPrototype extends BasicGame {
	private ArrayList<RigidRectangle> rects;
	private float deltaScale = 0.01f;

	public PhysicsPrototype() {
		super("SimpleTest");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		rects = new ArrayList<RigidRectangle>();
		rects.add(new RigidRectangle(50, 800, 20, 100, 0.5f));
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_LEFT)) {
			rects.get(0).applyForce(-5, 0);
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			rects.get(0).applyForce(5, 0);
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			rects.get(0).applyForce(0, -5);
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			rects.get(0).applyForce(0, 5);
		}

		if (input.isKeyDown(Input.KEY_1)) {
			rects.get(0).applyTorque(-5);
		} else if (input.isKeyDown(Input.KEY_2)) {
			rects.get(0).applyTorque(5);
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
