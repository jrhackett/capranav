package visuals;

/**
 * Created by jacobhackett on 11/30/15.
 */
public class Walking {
    String description;
    double walkingSpeed;

    public Walking(String x, double y) {
        this.description = x;
        this.walkingSpeed = y;
    }

    public String getDescription() {
        return description;
    }

    public double getWalkingSpeed() {
        return walkingSpeed;
    }

    public String toString() { return this.description; }
}
