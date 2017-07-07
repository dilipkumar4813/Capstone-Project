package iamdilipkumar.com.spacedig;

import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import iamdilipkumar.com.spacedig.models.MediaOptions;
import iamdilipkumar.com.spacedig.ui.activities.GeneralListActivity;
import iamdilipkumar.com.spacedig.utils.ParsingUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
@LargeTest
public class ParsingUnitTest {

    @Test
    public void validateParsing() throws Exception {

        String url = "[\"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~orig.mp4\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget.srt\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~large.mp4\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~large_thumb_00001.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~large_thumb_00002.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~medium.mp4\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~medium_thumb_00001.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~medium_thumb_00002.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~mobile.mp4\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~mobile_thumb_00001.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~mobile_thumb_00002.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~preview.mp4\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~preview_thumb_00001.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~preview_thumb_00002.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~small.mp4\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~small_thumb_00001.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/NHQ_2017_0523_FY18 State Of NASA Budget~small_thumb_00002.png\", \"http://images-assets.nasa.gov/video/NHQ_2017_0523_FY18 State Of NASA Budget/metadata.json\"]";
        MediaOptions mediaOptions = ParsingUtils.parseMediaAsset(url);

        assertEquals(mediaOptions.getSmall(), null);
    }
}