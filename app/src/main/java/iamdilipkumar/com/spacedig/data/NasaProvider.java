package iamdilipkumar.com.spacedig.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created on 28/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

@ContentProvider(authority = NasaProvider.AUTHORITY, database = NasaProvider.class)
public class NasaProvider {

    public static final String AUTHORITY = "iamdilipkumar.com.spacedig.data.provider";

    @TableEndpoint(table = NasaDatabase.NASADATABASE)
    public static class MainContacts {

        @ContentUri(path = "nasa", type = "vnd.android.cursor.dir/contacts", defaultSort = CommonDatabaseColumns._ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/nasa");

        @InexactContentUri(
                path = "nasa/#",
                name = "LIST_ID",
                type = "vnd.android.cursor.item/nasa",
                whereColumn = CommonDatabaseColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/nasa/" + id);
        }
    }
}
