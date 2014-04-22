/**
 * This class contains constants
 */
public final class Constants {
    /**
     *  String that is a flag to stop session.
     */
    public static final String SHUTDOWN_MESSAGE = "BYE";

    /**
     * No object should be created
     */
    private Constants() {
        throw new AssertionError("Shouldn't be instantiated");
    }
}
