package iamdilipkumar.com.spacedig.utils.parsing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.data.NeoColumns;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.cad.Cad;

/**
 * Created on 02/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class CadUtils {

    private static String collisionImage = "http://www.killerasteroids.org/images/impact_intro.jpg";

    public static List<SimpleItemModel> getCadDataIntoContentProvider(Cad cad, Context context) {
        List<SimpleItemModel> simpleItemModels = new ArrayList<>();

        for (List<String> item : cad.getData()) {
            String name = "", information = "", shortDescription = "";
            for (int i = 0; i < cad.getFields().size(); i++) {
                if (i == 0) {
                    name = item.get(i);
                }

                if (cad.getFields().get(i).equalsIgnoreCase("dist")) {
                    shortDescription = context.getString(R.string.
                            collision_approach_short_description)
                            + " " + item.get(i);
                }
                information += cad.getFields().get(i) + ": " + item.get(i) + "\n";
            }

            ContentValues neoData = new ContentValues();
            neoData.put(NeoColumns.NEO_ID, "");
            neoData.put(NeoColumns.NAME, name);
            neoData.put(NeoColumns.DESCRIPTION, information);
            neoData.put(NeoColumns.SHORT_DESCRIPTION, shortDescription);
            neoData.put(NeoColumns.IMAGEURL, collisionImage);
            context.getContentResolver().insert(NasaProvider.CadData.CONTENT_URI, neoData);
        }

        return simpleItemModels;
    }

    public static List<SimpleItemModel> getCadContent(Context context) {
        List<SimpleItemModel> simpleItemModels = new ArrayList<>();

        Cursor neoCursor = context.getContentResolver().query(NasaProvider.CadData.CONTENT_URI,
                null, null, null, null);
        if (neoCursor != null) {
            if (neoCursor.moveToFirst()) {
                do {
                    SimpleItemModel itemModel =
                            new SimpleItemModel(neoCursor.getString(
                                    neoCursor.getColumnIndex(NeoColumns.NEO_ID)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.NAME)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.SHORT_DESCRIPTION)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.IMAGEURL)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.DESCRIPTION)),
                                    0);
                    simpleItemModels.add(itemModel);
                } while (neoCursor.moveToNext());
            }
            neoCursor.close();
        }

        return simpleItemModels;
    }
}
