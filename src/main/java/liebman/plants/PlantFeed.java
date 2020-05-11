package liebman.plants;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlantFeed {

    //feed returned from getSpecies call

   // List<Species> species;

    class Species{
        String id;
        @SerializedName("complete_data")
        String completeData;
    }


    //feed returned from getPlantInfo
    @SerializedName("common_name")
    String commonName;
    ArrayList<Images> images;
    @SerializedName("main_species")
    MainSpecies mainSpecies;

    class MainSpecies {
        Growth growth;
        Specifications specifications;
    }

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
        @SerializedName("deg_f")
        int degF;
    }


}

