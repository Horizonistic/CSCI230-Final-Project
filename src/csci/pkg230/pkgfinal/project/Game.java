package csci.pkg230.pkgfinal.project;

// Represents game scoring and mechanics.
public class Game {
    
    // State
    public enum State {
        READY,
        IN_PROGRESS,
        PAUSED,
        GAME_OVER
    };

    // Current score.
    private int score = 0;

    private int pointValue = 1;
    
    private State state = State.READY;

    // Constructor.
    public Game() {
    }

    // Increment
    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    // Score
    public void addPoint() {
        score += pointValue;
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public int getScore() {
        return score;
    }

}
