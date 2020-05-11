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

    public static JPanel centerPanel;
    public static JLabel nameLabel;
    public static JLabel toxicityLabel;
    public static ImageIcon icon;
    public static JLabel image;
    public static JLabel growthFormLabel;
    public static JLabel growthPeriodLabel;
    public static JLabel droughtToleranceLabel;
    public static JLabel shadeToleranceLabel;
    public static JLabel degFLabel;

    public static JPanel lowerPanel;
    public static JLabel errorLabel;


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

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(8, 1));

        nameLabel = new JLabel("", SwingConstants.CENTER);
        toxicityLabel = new JLabel("", SwingConstants.CENTER);
        image = new JLabel(icon, SwingConstants.CENTER);
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
        lowerPanel.add(errorLabel);

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
        PlantController controller = new PlantController(service);
        controller.requestData(answerField.getText());
    }

    public static void main(String[] args) {
        new PlantFrame().setVisible(true);
    }

}

