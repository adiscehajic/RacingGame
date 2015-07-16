package ba.bitcamp.projectRacingGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class OilStain implements AnimatedGameArtifact {

	public static int WIDTH = 200;
	public static int HEIGHT = 200;

	public static int WIDTH_HALF = WIDTH / 2;
	public static int HEIGHT_HALF = HEIGHT / 2;
	
	public int x;
	public int y;
	
	private int speed;
	private int roadHeight;
	private int minX;
	private int maxX;
	
	private BufferedImage oil;

	public OilStain(int minX, int maxX, int roadHeight, int speed) {
		super();
		this.minX = minX + 50;
		this.maxX = maxX - 50;
		this.roadHeight = roadHeight;
		setSpeed(speed);
		
		try{
			oil = ImageIO.read(ResourceLoader.load("oil.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		resetPosition();
	}
	
	public void resetPosition() {
		setPosition((int) (minX + Math.random() * (maxX - minX)), (int)(-HEIGHT * Math.random() * 10));
	}
	
	public void setPosition(int x, int y) {
		this.x = x - WIDTH_HALF;
		this.y = y - HEIGHT_HALF;
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
		// draw oil stain only if in visible area
		if (y > -HEIGHT && y < roadHeight) {
			graphics.drawImage(oil, x, y, null);
		}
	}
}
