import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WeatherApp extends JFrame {

    private JTextField provinceField;
    private JTextField cityField;
    private JButton searchButton;
    private JTextArea weatherArea;
    private WeatherUtil weatherUtil;

    public WeatherApp() {
        weatherUtil = new WeatherUtil();
        createUI();
    }

    private void createUI() {
        setTitle("天气查询系统");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel provinceLabel = new JLabel("省份名：");
        add(provinceLabel);
        provinceField = new JTextField(15);
        add(provinceField);

        JLabel cityLabel = new JLabel("城市名：");
        add(cityLabel);
        cityField = new JTextField(15);
        add(cityField);

        searchButton = new JButton("查询天气");
        add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchWeather();
            }
        });

        weatherArea = new JTextArea(40, 90);
        weatherArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(weatherArea);
        add(scrollPane);

        setVisible(true);
    }

    private void searchWeather() {
        try {
            String provinceName = provinceField.getText();
            String cityName = cityField.getText();

            int provinceCode = weatherUtil.getProvinceCode(provinceName);
            int cityCode = weatherUtil.getCityCode(provinceCode, cityName);

            List<String> weatherList = weatherUtil.getWeather(cityCode);
            weatherArea.setText("");
            for (String weather : weatherList) {
                weatherArea.append(weather + "\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "查询失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WeatherApp();
            }
        });
    }
}