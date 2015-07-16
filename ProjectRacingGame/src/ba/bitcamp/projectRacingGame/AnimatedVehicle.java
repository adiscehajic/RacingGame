package ba.bitcamp.projectRacingGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class AnimatedVehicle extends Vehicle implements AnimatedGameArtifact {

	private int speed;
	private int roadHeight;
	private int minX;
	private int maxX;
	private Color color;

	Random rand = new Random();

	private Color[] colors = new Color[] { Color.RED, Color.GREEN,
			Color.YELLOW, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
			Color.MAGENTA, Color.ORANGE, Color.PINK };

	public AnimatedVehicle(int minX, int maxX, int roadHeight, int speed) {
		super(0, 0);
		this.minX = minX + 20;
		this.maxX = maxX - 20;
		this.roadHeight = roadHeight;
		setSpeed(speed);
		resetPosition();
	}

	public void resetPosition() {
		setPosition((int) (minX + Math.random() * (maxX - minX)),
				(int) (-HEIGHT * Math.random() * 10));
		for (int i = 0; i < colors.length; i++) {
			color = colors[rand.nextInt(colors.length)];
		}
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed / 2;
	}

	@Override
	public void animateFrame(long frameNumber) {
		// move down or reset position if passed
		if (y < roadHeight) {
			y += speed;
		} else {
			resetPosition();
		}
	}

	@Override
	public void draw(Graphics graphics) {
		// draw car only if in visible area
		if (y > -HEIGHT && y < roadHeight) {
			for (int i = 0; i < colors.length; i++) {
				drawCar(graphics, color);
			}
		}
	}

}
