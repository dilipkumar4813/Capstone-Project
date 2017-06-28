package iamdilipkumar.com.spacedig.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created on 28/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

@Database(version = NasaDatabase.VERSION)
public class NasaDatabase {

    public static final int VERSION = 1;

    @Table(NasaDatabase.class)
    public static final String NASADATABASE = "nasa";
}
