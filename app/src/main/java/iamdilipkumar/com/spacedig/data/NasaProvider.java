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

@ContentProvider(authority = NasaProvider.AUTHORITY, database = NasaDatabase.class)
public class NasaProvider {

    static final String AUTHORITY = "iamdilipkumar.com.spacedig.data.provider";

    @TableEndpoint(table = NasaDatabase.NEOTTABLE)
    public static class NeoData {

        @ContentUri(path = "neo", type = "vnd.android.cursor.dir/neo", defaultSort = NeoColumns._ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/neo");

        @InexactContentUri(
                path = "neo/#",
                name = "LIST_ID",
                type = "vnd.android.cursor.item/neo",
                whereColumn = NeoColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/neo/" + id);
        }
    }

    @TableEndpoint(table = NasaDatabase.NEOTTABLE)
    public static class CadData {

        @ContentUri(path = "cad", type = "vnd.android.cursor.dir/cad", defaultSort = NeoColumns._ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/cad");

        @InexactContentUri(
                path = "cad/#",
                name = "LIST_ID",
                type = "vnd.android.cursor.item/cad",
                whereColumn = NeoColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/cad/" + id);
        }
    }
}
