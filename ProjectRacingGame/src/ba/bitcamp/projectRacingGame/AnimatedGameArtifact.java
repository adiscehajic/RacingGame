package ba.bitcamp.projectRacingGame;

public interface AnimatedGameArtifact extends GameArtifact {
	void animateFrame(long frameNumber);
	
	void setSpeed(int speed);
}
