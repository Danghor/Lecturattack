package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;

public class MainTest extends BasicGame {
	private Rectangle myRect = new Rectangle(80, 30, 50, 50);
	private float gravity = 1.0f;
	private float currentYVelocity = 0;
	private long count = 0;

	public MainTest() {
		super("SimpleTest");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		count++;
		if (count % 35 == 0) { // nur zum Test, später würde ich über Ticks
								// gehen (oder TargetFPS)
			myRect.setY(myRect.getY() - currentYVelocity);
			currentYVelocity -= gravity;
		}

		if (count % 1500 == 0) {
			currentYVelocity = 0;
			myRect = new Rectangle(80, 30, 50, 50);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.fill(myRect);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new MainTest());
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}