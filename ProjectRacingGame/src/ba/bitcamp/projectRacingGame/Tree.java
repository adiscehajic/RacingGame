package ba.bitcamp.projectRacingGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Tree implements AnimatedGameArtifact {

	private int right;
	private int left;
	private int speed;
	private int height;
	
	public static int RODE_SIDE = 150;
	public static int RECT_WIDTH = 100;
	
	private int rectHeight = 100;
	private int rectSpace = 20;
	private int totalLineHeight = rectHeight + rectSpace;
	
	private int start;
	
	private BufferedImage tree1;
	private BufferedImage tree2;
	private BufferedImage tree3;
	private BufferedImage tree4;
	private BufferedImage tree5;
	
	Random rand = new Random();
	
	BufferedImage[] images = new BufferedImage[5];
	
	public Tree(int width, int height, int speed) {
		this.height = height;
		this.speed = speed;
		this.left = 20;
		this.right = width - RODE_SIDE;
		
		try{
			tree1 = ImageIO.read(ResourceLoader.load("tree1.png"));
			tree2 = ImageIO.read(ResourceLoader.load("tree2.png"));
			tree3 = ImageIO.read(ResourceLoader.load("tree3.png"));
			tree4 = ImageIO.read(ResourceLoader.load("tree4.png"));
			tree5 = ImageIO.read(ResourceLoader.load("tree5.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		images[0] = tree1;
		images[1] = tree2;
		images[2] = tree3;
		images[3] = tree4;
		images[4] = tree5;
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		drawTree(graphics, left);
		drawTree(graphics, right);
		
	}

	@Override
	public void animateFrame(long frameNumber) {
		start = (int) (frameNumber % totalLineHeight) * speed;
		
	}

	@Override
	public void setSpeed(int speed) {
		this.speed = speed;
		
	}
	
	private void drawTree(Graphics graphics, int position) {
		int y = start - speed * totalLineHeight;
		while (y < height + totalLineHeight) {
			for (int i = 0; i < images.length; i++) {
				graphics.drawImage(images[i], position, y, null);
				y += 250;				
			}
		}
	}

}
