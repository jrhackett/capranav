package logic;

/**
 * Created by ikeandmike on 11/30/15.
 *
 * Created a singleton, so entire class is essentially "static"
 * To set things, use User.setName() etc.
 * To get things, use User.getName() etc.
 */
public class User {
    private String emailAddr;
    private double walkSpeed;

    private boolean newUser;

    private static final User user = new User(); //Singleton
    private static final User ignored = new User(false); //Necessary to force fromFile to run on opening of app

    public User() { newUser = true; } //Don't use

    private User (boolean ignored) {
        new Parser().fromFileUser();
    }

    public static void userIsNotNew() { user.newUser = false; toFile(); }
    public static boolean isUserNew() { return user.newUser; }

    public static void setEmail(String s) { user.emailAddr = s; toFile(); }
    public static void setSpeed(double d) { user.walkSpeed = d; toFile(); }

    private static void toFile() { new Parser().toFile(user); }

    public static String getEmail() { return user.emailAddr; }
    public static double getSpeed() { return user.walkSpeed; }

    //Necessary for parsing user from json.. ignore the weirdness
    public static void pSetEmail(String s) { user.emailAddr = s; }
    public static void pSetSpeed(double d) { user.walkSpeed = d; }
    public String pGetEmail() { return emailAddr; }
    public double pGetSpeed() { return walkSpeed; }
}
