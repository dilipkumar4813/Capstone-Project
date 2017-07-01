package iamdilipkumar.com.spacedig.utils.parsing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.CadColumns;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.cad.Cad;

/**
 * Created on 02/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class CadUtils {

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

            ContentValues cadData = new ContentValues();
            cadData.put(CadColumns.NEO_ID, "");
            cadData.put(CadColumns.NAME, name);
            cadData.put(CadColumns.DESCRIPTION, information);
            cadData.put(CadColumns.SHORT_DESCRIPTION, shortDescription);
            cadData.put(CadColumns.IMAGEURL, "https://pbs.twimg.com/media/CQ5xYRXVEAAeiCy.jpg");
            context.getContentResolver().insert(NasaProvider.CadData.CONTENT_URI, cadData);
        }

        return simpleItemModels;
    }

    public static List<SimpleItemModel> getCadContent(Context context) {
        List<SimpleItemModel> simpleItemModels = new ArrayList<>();

        Cursor cadCursor = context.getContentResolver().query(NasaProvider.CadData.CONTENT_URI,
                null, null, null, null);
        if (cadCursor != null) {
            if (cadCursor.moveToFirst()) {
                do {
                    SimpleItemModel itemModel =
                            new SimpleItemModel(cadCursor.getString(
                                    cadCursor.getColumnIndex(CadColumns.NEO_ID)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.NAME)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.SHORT_DESCRIPTION)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.IMAGEURL)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.DESCRIPTION)),
                                    0);
                    simpleItemModels.add(itemModel);
                } while (cadCursor.moveToNext());
            }
            cadCursor.close();
        }

        return simpleItemModels;
    }
}
