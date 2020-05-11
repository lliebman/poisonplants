package liebman.plants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class PlantController {

    private PlantService service;

    public PlantController(PlantService service) {
        this.service = service;
    }

   public void requestData(String plantName) {
        service.getSpecies(plantName).enqueue(new Callback<List<PlantFeed.Species>>() {
            @Override
            public void onResponse(Call<List<PlantFeed.Species>> call, Response<List<PlantFeed.Species>> response) {
                try{
                    PlantFeed.Species species = response.body().get(0);

                    service.getPlantInfo(species.id).enqueue(new Callback<PlantFeed>() {
                        @Override
                        public void onResponse(Call<PlantFeed> call, Response<PlantFeed> response) {
                            try{
                                PlantFrame.errorLabel.setText("");

                                PlantFeed plant = response.body();
                                PlantFeed.Specifications specializations = response.body().mainSpecies.specifications;
                                PlantFeed.Growth growth = response.body().mainSpecies.growth;

                                PlantFrame.nameLabel.setText("Common Name: " + String.valueOf(plant.commonName));
                                PlantFrame.toxicityLabel.setText("Toxicity Level: " + String.valueOf(specializations.toxicity));

                                URL url = new URL(plant.images.get(0).url);
                                Image image = ImageIO.read(url);
                                image = image.getScaledInstance(100,100, 1);
                                PlantFrame.image.setIcon(new ImageIcon(image));

                                PlantFrame.growthFormLabel.setText("Growth Form: " + String.valueOf(specializations.growthForm));
                                PlantFrame.growthPeriodLabel.setText("Growth Period: " + String.valueOf(specializations.growthPeriod));
                                PlantFrame.degFLabel.setText("Min temperature preferred: " + String.valueOf(growth.tempMin.degF) + " F");
                                PlantFrame.droughtToleranceLabel.setText("Drought Tolerance: " + String.valueOf(growth.droughtTolerance));
                                PlantFrame.shadeToleranceLabel.setText("Shade Tolerance: " + String.valueOf(growth.shadeTolerance));
                            }
                            catch (Exception e){
                                PlantFrame.errorLabel.setText("An error ocurred");
                                PlantFrame.errorLabel.setForeground(Color.RED);
                            }
                        }

                        @Override
                        public void onFailure(Call<PlantFeed> call, Throwable t) {
                            System.out.println("Failure in PlantController.getPlantInfo");
                            PlantFrame.errorLabel.setText("An error ocurred. " + plantName + " not found.");
                            PlantFrame.errorLabel.setForeground(Color.RED);
                        }
                    });
                }
                catch (Exception e){
                    PlantFrame.errorLabel.setText("An error ocurred. " + plantName + " not found.");
                    PlantFrame.errorLabel.setForeground(Color.RED);
                }

            }

            @Override
            public void onFailure(Call<List<PlantFeed.Species>> call, Throwable t) {
                System.out.println("Failure in PlantController.getSpecies");
                PlantFrame.errorLabel.setText("An error ocurred. " + plantName + " not found.");
                PlantFrame.errorLabel.setForeground(Color.RED);
            }
        });


   }


}
