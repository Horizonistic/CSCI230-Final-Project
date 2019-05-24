package csci.pkg230.pkgfinal.project;

// Represents game scoring and mechanics.
public class Game {

    // Current score.
    private int score = 0;

    private int pointValue = 1;

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
    
    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

}
