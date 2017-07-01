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
class NasaDatabase {

    static final int VERSION = 1;

    @Table(NeoColumns.class)
    static final String NEOTTABLE = "neo";

    @Table(CadColumns.class)
    static final String CADTABLE = "cad";
}
