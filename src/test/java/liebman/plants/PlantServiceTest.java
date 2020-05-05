package liebman.plants;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class PlantServiceTest {

    @Test
    public void getPlant() throws IOException {
        //given
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trefle.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlantService service = retrofit.create((PlantService.class));

        //when
        PlantFeed.Species feed = service.getSpecies("southern red oak").execute().body().get(0);

        //then
        assertNotNull(feed);

        assertNotNull(feed.id);
        assertNotNull(feed.completeData);
    }

    @Test
    public void getPantInfo() throws IOException {
        //given
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trefle.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlantService service = retrofit.create((PlantService.class));

        //when
        PlantFeed.Species feed = service.getSpecies("southern red oak").execute().body().get(0);
        PlantFeed feed2 = service.getPlantInfo(feed.id).execute().body();

        //then
        assertNotNull(feed2);

        assertNotNull(feed2.images.get(0).url);
        assertNotNull(feed2.commonName);
        assertNotNull(feed2.growth);
        assertNotNull(feed2.growth.droughtTolerance);
        assertNotNull(feed2.growth.shadeTolerance);
        assertNotNull(feed2.growth.tempMin);
        assertNotNull(feed2.growth.tempMin.deg_f);
        assertNotNull(feed2.specitifcation);
        assertNotNull(feed2.specitifcation.growthForm);
        assertNotNull(feed2.specitifcation.growthPeriod);
        assertNotNull(feed2.specitifcation.toxicity);
    }

}