package iamdilipkumar.com.spacedig.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created on 06/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class ApplicationPersistence {

    private static final String PREFERENCES_STRING = "session";
    private static final String PREFERENCES_SELECTION = "selectionitem";

    public static void storeSelectedItem(Context context, int selection) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(PREFERENCES_STRING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREFERENCES_SELECTION, selection);
        editor.apply();
    }

    public static int getSelectedItem(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(PREFERENCES_STRING, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREFERENCES_SELECTION, 0);
    }
}
