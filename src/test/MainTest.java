package test;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class MainTest extends BasicGame {
	public MainTest() {
		super("SimpleTest");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.drawString("Hello, Slick world!", System.nanoTime()%0x191, System.nanoTime()%0x1c1);
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