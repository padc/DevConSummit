package ph.devcon.android.util;

/**
 * Created by lope on 10/6/14.
 */
public class Util {
    public static String toTime(int section) {
        switch (section) {
            case 0:
                return "08:00 AM";
            case 1:
                return "09:00 AM";
            case 2:
                return "10:00 AM";
            case 3:
                return "11:00 AM";
            case 4:
                return "12:00 NN";
            case 5:
                return "01:00 PM";
            case 6:
                return "02:00 PM";
            case 7:
                return "03:00 PM";
            case 8:
                return "04:00 PM";
            default:
                return "UNKNOWN";
        }
    }
}
