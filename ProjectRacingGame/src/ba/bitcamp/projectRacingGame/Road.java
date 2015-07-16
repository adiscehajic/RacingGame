package ba.bitcamp.projectRacingGame;

import java.awt.Color;
import java.awt.Graphics;

public class Road implements AnimatedGameArtifact {
	private int left;
	private int right;
	private int speed;
	private int height;
	private int center;
	
	private int center1;
	private int center2;
	private int center3;
	private int center4;
	
	public static int RODE_SIDE = 200;
	public static int LINE_WIDTH = 10;
	
	private int lineHeight = 50;
	private int lineSpace = 20;
	private int totalLineHeight = lineHeight + lineSpace;
	
	private int start;

	public Road(int width, int height, int speed) {
		this.height = height;
		this.speed = speed;
		this.left = RODE_SIDE - LINE_WIDTH;
		this.right = width - RODE_SIDE;
		this.center = width / 2 - 5;
		this.center1 = RODE_SIDE + 100;
		this.center2 = RODE_SIDE + 210;
		this.center3 = width / 2 + 100;
		this.center4 = width / 2 + 210;
	}

	@Override
	public void animateFrame(long frameNumber) {
		start = (int) (frameNumber % totalLineHeight) * speed;
	}
	
	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		drawLine(graphics, left);
		drawFullLine(graphics, left - 20);
		drawLine(graphics, right);
		drawLine(graphics, center);
		
		drawLine(graphics, center1);
		drawLine(graphics, center2);
		drawLine(graphics, center3);
		drawLine(graphics, center4);
		
		drawFullLine(graphics, right + 20);
		drawGrass(graphics, 0);
		drawGrass(graphics, right + 30);
	}

	private void drawLine(Graphics graphics, int position) {
		int y = start - speed * totalLineHeight;
		while (y < height + totalLineHeight) {
			graphics.fillRect(position, y, LINE_WIDTH, lineHeight);
			y += 70;
		}
	}
	
	public void drawFullLine(Graphics graphics, int position) {
		int y = start - speed * totalLineHeight;
		while (y < height + totalLineHeight) {
			graphics.fillRect(position, y, LINE_WIDTH, height);
			y += 70;
		}
	}
	
	public void drawGrass(Graphics graphics, int position) {
		int y = start - speed * totalLineHeight;
		while (y < height + totalLineHeight) {
			graphics.setColor(Color.GREEN);
			graphics.fillRect(position, y, RODE_SIDE - 30, height);
			y += 70;
		}
	}
	
	
}
