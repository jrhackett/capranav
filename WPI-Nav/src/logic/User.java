package logic;

/**
 * Created by ikeandmike on 11/30/15.
 *
 * NOTE: Setters return updated object, so calls can be stringed together if this is desired
 * ie. User Mike = new User().setName("Mike").setEmail("mjgiancola@wpi.edu).setSpeed(10);
 * Getters are straight forward
 */
public class User {
    private String name;
    private String emailAddr;
    private double walkSpeed;

    public User() {}

    public User setName(String s) {
        name = s;
        return this;
    }
    public User setEmail(String s) {
        emailAddr = s;
        return this;
    }
    public User setSpeed(double d) {
        walkSpeed = d;
        return this;
    }

    public String getName()  { return name;      }
    public String getEmail() { return emailAddr; }
    public double getSpeed() { return walkSpeed; }
}
