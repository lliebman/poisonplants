package liebman.plants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.swing.*;
import java.awt.*;

public class PlantFrame extends JFrame {

    private JPanel upperPanel;
    private JLabel plantNameLabel;
    private JTextField answerField;
    private JButton checkButton;

    private final JLabel image;

    private final JPanel centerPanel;
    private final JLabel nameLabel;
    private final JLabel toxicityLabel;
    private final JLabel growthFormLabel;
    private final JLabel growthPeriodLabel;
    private final JLabel droughtToleranceLabel;
    private final JLabel shadeToleranceLabel;
    private final JLabel degFLabel;

    private final JPanel lowerPanel;
    private final JLabel errorLabel;

    public PlantFrame() {
        setSize(800, 680);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Poison Plant Checker");
        setLayout(new BorderLayout());

        setLayout(new BorderLayout());

        upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout());

        plantNameLabel = new JLabel("Enter plant name:");
        plantNameLabel.setOpaque(true);

        answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(160, 30));

        checkButton = new JButton("Eat me?");
        checkButton.addActionListener(actionEvent -> {
            try {
                getPlant();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        upperPanel.add(plantNameLabel);
        upperPanel.add(answerField);
        upperPanel.add(checkButton);

        image = new JLabel();

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7, 1));

        nameLabel = new JLabel("", SwingConstants.CENTER);
        toxicityLabel = new JLabel("", SwingConstants.CENTER);
        growthFormLabel = new JLabel("", SwingConstants.CENTER);
        growthPeriodLabel = new JLabel("", SwingConstants.CENTER);
        droughtToleranceLabel = new JLabel("", SwingConstants.CENTER);
        shadeToleranceLabel = new JLabel("", SwingConstants.CENTER);
        degFLabel = new JLabel("", SwingConstants.CENTER);

        centerPanel.add(nameLabel);
        centerPanel.add(toxicityLabel);
        centerPanel.add(image);
        centerPanel.add(growthFormLabel);
        centerPanel.add(growthPeriodLabel);
        centerPanel.add(degFLabel);
        centerPanel.add(droughtToleranceLabel);
        centerPanel.add(shadeToleranceLabel);

        lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout());

        errorLabel = new JLabel();

        add(image, BorderLayout.LINE_START);
        add(upperPanel, BorderLayout.PAGE_START);
        add(centerPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.PAGE_END);
    }

    public void getPlant() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trefle.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlantService service = retrofit.create(PlantService.class);
        PlantController controller = new PlantController(service, image, nameLabel, toxicityLabel, growthFormLabel,
                growthPeriodLabel, droughtToleranceLabel, shadeToleranceLabel, degFLabel, errorLabel);
        controller.requestData(answerField.getText());
    }

    public static void main(String[] args) {
        new PlantFrame().setVisible(true);
    }
}

