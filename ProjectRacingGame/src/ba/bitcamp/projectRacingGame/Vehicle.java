package ba.bitcamp.projectRacingGame;

import java.awt.Color;
import java.awt.Graphics;

public class Vehicle implements GameArtifact {
	public static int WIDTH = 40;
	public static int HEIGHT = 60;

	public static int WIDTH_HALF = WIDTH / 2;
	public static int HEIGHT_HALF = HEIGHT / 2;

	protected int x;
	protected int y;

	private int crashFrame;

	public Vehicle(int x, int y) {
		setPosition(x, y);
	}

	public void setPosition(int x, int y) {
		this.x = x - WIDTH_HALF;
		this.y = y - HEIGHT_HALF;
	}

	public void setX(int x) {
		this.x = x - WIDTH_HALF;
	}

	@Override
	public void draw(Graphics graphics) {
		if (crashFrame > 0) {
			drawCar(graphics, Color.RED);
			crashFrame--;
		} else {
			drawCar(graphics, Color.BLUE);
		}

	}

	protected void drawCar(Graphics graphics, Color c) {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(x - 2, y + 5, WIDTH + 4, HEIGHT - 45);
		graphics.fillRect(x - 2, y + 40, WIDTH + 4, HEIGHT - 45);
		graphics.setColor(c);
		graphics.fillOval(x - 1, y - 10, WIDTH + 1, 15);
		graphics.fillOval(x - 1, y + 50, WIDTH + 1, 15);
		graphics.fillRect(x, y, WIDTH, HEIGHT);
		graphics.setColor(Color.BLACK);
		graphics.fillOval(x + 5, y + 4, WIDTH - 10, 10);
		graphics.fillRect(x + 5, y + 8, WIDTH - 10, HEIGHT - 43);
		
		graphics.fillOval(x + 5, y + 47, WIDTH - 10, 10);
		graphics.fillRect(x + 5, y + 40, WIDTH - 10, HEIGHT - 46);
	}

	public boolean colade(Vehicle v) {
		return Math.abs(x - v.x) < WIDTH + 4 && Math.abs(y - v.y) < HEIGHT + 15;
	}
	
	public boolean colide(OilStain os){
		return Math.abs(x - os.x) < WIDTH + 4 && Math.abs(y - os.y) < HEIGHT + 15;
	}

	public void crash() {
		crashFrame = 20;
	}

	public boolean isCrashed() {
		return crashFrame > 0;
	}
}
