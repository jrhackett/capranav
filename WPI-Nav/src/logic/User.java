package logic;

/**
 * Created by ikeandmike on 11/30/15.
 *
 * Created a singleton, so entire class is essentially "static"
 * To set things, use User.setName() etc.
 * To get things, use User.getName() etc.
 */
public class User {
    private String name;
    private String emailAddr;
    private double walkSpeed;

    private static final User user = new User(); //Singleton

    private User() {}

    public static void setName(String s) {
        user.name = s;
    }
    public static void setEmail(String s) {
        user.emailAddr = s;
    }
    public static void setSpeed(double d) {
        user.walkSpeed = d;
    }

    public static String getName()  { return user.name;      }
    public static String getEmail() { return user.emailAddr; }
    public static double getSpeed() { return user.walkSpeed; }
}
