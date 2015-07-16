package ba.bitcamp.projectRacingGame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RacingGame extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1451480708431972837L;

	private static final int MOVE_STEP = 5;
	private static final int MIN_SPEED = 10;
	private static final int MAX_SPEED = 100;
	private static final int DEFAULT_SPEED = 20;
	private static final int SPEED_CHANGE = 5;

	private long frameNumber;

	private GameArtifact[] gameArtifacts;
	private AnimatedGameArtifact[] animatedGameArtifacts;
	private AnimatedVehicle[] animatedVehicles;
	private OilStain oil;

	private int minX, maxX, minX1, minX2, minX3;
	private Vehicle myVehicle;
	private int speed;
	private Timer timer;

	public static JFrame window = new JFrame("Racing Game");
	private int counter = 0;
	
	private boolean isStarted = false;

	/**
	 * Constructs new RacingGame for provided road width and height with
	 * starting game speed
	 * 
	 * @param roadWidth
	 * @param roadHeight
	 * @param speed
	 */
	public RacingGame(int roadWidth, int roadHeight, int speed) {
		this.speed = speed;
		this.minX = Road.RODE_SIDE + 5;
		this.maxX = roadWidth - Road.RODE_SIDE - 45;
		this.minX1 = Road.RODE_SIDE + 50;
		this.minX2 = Road.RODE_SIDE + 260;
		this.minX3 = Road.RODE_SIDE + 470;

		this.myVehicle = new Vehicle(512, 680);
		Road road = new Road(roadWidth, roadHeight, speed);

		Tree tree = new Tree(roadWidth, roadHeight, speed);
		oil = new OilStain(minX, maxX, roadHeight, speed);

		AnimatedVehicle otherVehicle = new AnimatedVehicle(minX1, minX1,
				roadHeight, speed);
		AnimatedVehicle otherVehicle1 = new AnimatedVehicle(minX2, minX2,
				roadHeight, speed);
		AnimatedVehicle otherVehicle2 = new AnimatedVehicle(minX3, minX3,
				roadHeight, speed);

		gameArtifacts = new GameArtifact[] { road, oil, myVehicle, tree,
				otherVehicle, otherVehicle1, otherVehicle2 };
		animatedGameArtifacts = new AnimatedGameArtifact[] { road, oil, tree,
				otherVehicle, otherVehicle1, otherVehicle2 };
		animatedVehicles = new AnimatedVehicle[] { otherVehicle, otherVehicle1,
				otherVehicle2 };

		// timer created with this RacingGame as ActionListener
		timer = new Timer(1000 / 10, this);

		// added key listener as anonymous class that extends KeyAdapter
		// (KeyAdapter implements KeyListener)
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					myVehicle.x = Math.max(minX, myVehicle.x - MOVE_STEP);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					myVehicle.x = Math.min(maxX, myVehicle.x + MOVE_STEP);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					setSpeed(SPEED_CHANGE);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					setSpeed(-SPEED_CHANGE);
				}
			}
		});

		// added mouse listener as anonymous class that extends MouseAdapter
		// used to obtain focus on mouse click
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				requestFocus();
			}
		});

		// added focus listener as anonymous class that implements FocusListener
		// animation is started only if RacingGame is focused - at that time you
		// can only move vehicle
		addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent evt) {
				isStarted = true;
				timer.start();
				repaint();
			}

			public void focusLost(FocusEvent evt) {
				timer.stop();
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw every game artifact on any repaint
		for (GameArtifact ga : gameArtifacts) {
			if (!isStarted) {
				g.setColor(Color.GREEN);
				g.fillRect(270, 160, 500, 140);
				g.setColor(Color.WHITE);
				g.drawRect(270, 160, 500, 140);
				g.setColor(Color.BLUE);
				g.setFont(new Font("Serif", Font.BOLD, 30));
				g.drawString("Use LEFT and RIGHT keys to stear", 290, 200);
				g.drawString("Use UP and DOWN keys to set speed", 285, 240);
				g.drawString("Click to START", 415, 280);
			} 
			ga.draw(g);
		
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// increment frame number and inform every animated artifact that frame
		// changed
		frameNumber++;
		for (AnimatedGameArtifact aga : animatedGameArtifacts) {
			aga.animateFrame(frameNumber);
		}

		// check if vehicles collided
		for (AnimatedVehicle av : animatedVehicles) {
			if (myVehicle.colade(av)) {
				myVehicle.crash();
				counter++;
				if (counter == 5) {
					timer.stop();
				}
				av.resetPosition();
			}

		}

		if (myVehicle.colide(oil)) {
			myVehicle.crash();
		}

		repaint();

		if (!timer.isRunning()) {
			restartWindow();
		}
	}

	/**
	 * Calculates new speed and informs every animated artifact that speed has
	 * changed.
	 * 
	 * @param speedChange
	 *            speed increment
	 */
	public void setSpeed(int speedChange) {
		speed = Math.min(Math.max(MIN_SPEED, speed + speedChange), MAX_SPEED);
		for (AnimatedGameArtifact aga : animatedGameArtifacts) {
			aga.setSpeed(speed);
		}
	}

	public static void main(String[] args) {
		RacingGame racingGame = new RacingGame(1024, 768, DEFAULT_SPEED);

		racingGame.setBackground(new Color(111, 110, 99));
		window.setSize(1024, 768);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setContentPane(racingGame);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	/**
	 * Restarts game.
	 */
	public void restartWindow() {
		// Opening option dialog that asks user if he wants to play again.
		int choice = JOptionPane.showOptionDialog(null,
				String.format("Do you want to play again? "), "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, new String[] { "Yes", "No", "See on GitHub" }, null);

		// If option YES is chosen closing old window and restarting game.
		if (choice == JOptionPane.YES_OPTION) {
			window.dispose();
			restart();
			// If option NO is chosen exiting game.
		} else if (choice == JOptionPane.NO_OPTION) {
			System.exit(0);
		} else if (choice == JOptionPane.CANCEL_OPTION) {
			try {
				Desktop.getDesktop()
						.browse(new URI(
								"https://github.com/adiscehajic/RacingGame/tree/master/ProjectRacingGame"));
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Restarts opening of JFrame.
	 */
	public static void restart() {
		main(null);
	}

}