package iamdilipkumar.com.spacedig.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created on 28/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public interface CommonDatabaseColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String NAME = "name";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String NUMBER = "description";

    @DataType(DataType.Type.TEXT)
    String IMAGEURL = "image";
}
