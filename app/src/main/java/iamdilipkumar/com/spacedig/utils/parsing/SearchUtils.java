package iamdilipkumar.com.spacedig.utils.parsing;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.search.Item;
import iamdilipkumar.com.spacedig.models.search.Search;

/**
 * Created on 04/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SearchUtils {

    public static List<SimpleItemModel> getSearchDetails(Context context, Search search) {
        List<SimpleItemModel> searchItems = new ArrayList<>();

        for(Item item:search.getCollection().getItems()){
            String id, title, description, shortDescription, imageUrl;
            id = item.getData().get(0).getNasaId();
            title = item.getData().get(0).getTitle();
            description = item.getData().get(0).getDescription() + "\n\n" +
                    context.getString(R.string.created) + " " + item.getData().get(0).getDateCreated();
            shortDescription = context.getString(R.string.media_type)
                    + " " + item.getData().get(0).getMediaType();
            imageUrl = item.getLinks().get(0).getHref();

            searchItems.add(new SimpleItemModel(id, title,
                    shortDescription, imageUrl, description, 0));
        }

        return searchItems;
    }
}
