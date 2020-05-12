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
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel toxicityLabel;
    private JLabel growthFormLabel;
    private JLabel growthPeriodLabel;
    private JLabel droughtToleranceLabel;
    private JLabel shadeToleranceLabel;
    private JLabel degFLabel;
    private JLabel errorLabel;

    public PlantController(PlantService service, JLabel image, JLabel nameLabel, JLabel toxicityLabel,
                           JLabel growthFormLabel, JLabel growthPeriodLabel, JLabel droughtToleranceLabel, JLabel
                                   shadeToleranceLabel, JLabel degFLabel, JLabel errorLabel) {
        this.service = service;
        this.imageLabel = image;
        this.nameLabel = nameLabel;
        this.toxicityLabel = toxicityLabel;
        this.growthFormLabel = growthFormLabel;
        this.growthPeriodLabel = growthPeriodLabel;
        this.droughtToleranceLabel = droughtToleranceLabel;
        this.shadeToleranceLabel = shadeToleranceLabel;
        this.degFLabel = degFLabel;
        this.errorLabel = errorLabel;
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
                                errorLabel.setText("");

                                PlantFeed plant = response.body();
                                PlantFeed.Specifications specializations = response.body().mainSpecies.specifications;
                                PlantFeed.Growth growth = response.body().mainSpecies.growth;

                                nameLabel.setText("Common Name: " + String.valueOf(plant.commonName));
                                toxicityLabel.setText("Toxicity Level: " + String.valueOf(specializations.toxicity));
                                toxicityLabel.setBackground(Color.YELLOW);

                                URL url = new URL(plant.images.get(0).url);
                                Image image = ImageIO.read(url);
                                image = image.getScaledInstance(400,400, Image.SCALE_SMOOTH);
                                imageLabel.setIcon(new ImageIcon(image));

                                growthFormLabel.setText("Growth Form: " + String.valueOf(specializations.growthForm));
                                growthPeriodLabel.setText("Growth Period: " + String.valueOf(specializations.growthPeriod));
                                degFLabel.setText("Min temperature survivable: " + String.valueOf(growth.tempMin.degF) + " F");
                                droughtToleranceLabel.setText("Drought Tolerance: " + String.valueOf(growth.droughtTolerance));
                                shadeToleranceLabel.setText("Shade Tolerance: " + String.valueOf(growth.shadeTolerance));
                            }
                            catch (Exception e){
                                errorLabel.setText("An error ocurred");
                                errorLabel.setForeground(Color.RED);
                            }
                        }

                        @Override
                        public void onFailure(Call<PlantFeed> call, Throwable t) {
                            System.out.println("Failure in PlantController.getPlantInfo");
                            errorLabel.setText("An error ocurred. " + plantName + " not found.");
                            errorLabel.setForeground(Color.RED);
                        }
                    });
                }
                catch (Exception e){
                    errorLabel.setText("An error ocurred. " + plantName + " not found.");
                    errorLabel.setForeground(Color.RED);
                }
            }

            @Override
            public void onFailure(Call<List<PlantFeed.Species>> call, Throwable t) {
                System.out.println("Failure in PlantController.getSpecies");
                errorLabel.setText("An error ocurred. " + plantName + " not found.");
                errorLabel.setForeground(Color.RED);
            }
        });


   }


}
