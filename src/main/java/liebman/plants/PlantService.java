package liebman.plants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlantService {
    @GET("api/plants?token=N3B6ekhpd1NHTUVJRUxYM1Z6dVcvQT09")
    Call<PlantFeed> getPlant(@Query("common_name") String plantName);

    @GET("api/plants/{id}?token=N3B6ekhpd1NHTUVJRUxYM1Z6dVcvQT09")
    Call<PlantFeed> getPlantInfo(@Path("id") String id);
}