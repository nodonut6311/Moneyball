import java.text.*;

public class Utils {
    public static boolean isValidDate(String dateStr) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}