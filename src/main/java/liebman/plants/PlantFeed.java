package liebman.plants;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlantFeed {

    //feed returned from common_name call

        String id;
        @SerializedName("complete_data")
        String completeData;


    //feed returned from plant_id call
    @SerializedName("common_name")
    String commonName;
    Specifications specitifcation;
    ArrayList<Images> images;
    Growth growth;

    class Specifications
    {
        @SerializedName("growth_form")
        String growthForm;
        String toxicity;
        @SerializedName("growth_period")
        String growthPeriod;
    }

    class Images
    {
        String url;
    }

    class Growth
    {
        @SerializedName("temperature_minimum")
        TempMin tempMin;
        @SerializedName("drought_tolerance")
        String droughtTolerance;
        @SerializedName("shade_tolerance")
        String shadeTolerance;
    }

    class TempMin
    {
        int deg_f;
    }


}

