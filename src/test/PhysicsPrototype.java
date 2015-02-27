package test;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class PhysicsPrototype extends BasicGame {
	private Rectangle myRect = new Rectangle(80, 30, 50, 50);
	private float gravity = 1.0f;
	private float currentYVelocity = 0;
	private long count = 0;
	private float e = -0.5f;

	public PhysicsPrototype() {
		super("SimpleTest");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		count++;
		if (count % 35 == 0) { // nur zum Test, später würde ich über Ticks
								// gehen (oder TargetFPS)

			if (myRect.getY() + myRect.getHeight() == container.getHeight()) { // berührt
				if (currentYVelocity < -2.667) {
					System.out.println(currentYVelocity);
					currentYVelocity *= e;
				}
			} else { // berührt nicht
				currentYVelocity -= gravity;
			}

			myRect.setY((int) (myRect.getY() - currentYVelocity));

			if (myRect.getY() + myRect.getHeight() > container.getHeight()) {
				myRect.setY((int) (container.getHeight() - myRect.getHeight()));
			}
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.fill(myRect);
	}
}
