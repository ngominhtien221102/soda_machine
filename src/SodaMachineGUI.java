import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SodaMachineGUI {
    private JFrame frame;
    private JTextArea display;
    private SodaMachine machine;
    private JTextField moneyInput;
    private JLabel clockLabel;
    private SimpleDateFormat timeFormat;

    public SodaMachineGUI() {
        machine = new SodaMachine();
        frame = new JFrame("Automatic Soda Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        display = new JTextArea();
        display.setEditable(false);
        display.setFont(new Font("Monospaced", Font.PLAIN, 14));
        pane.add(new JScrollPane(display), BorderLayout.CENTER);

        // Add clock label
        clockLabel = new JLabel();
        clockLabel.setHorizontalAlignment(JLabel.CENTER);
        clockLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        pane.add(clockLabel, BorderLayout.NORTH);

        timeFormat = new SimpleDateFormat("HH:mm:ss");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        pane.add(panel, BorderLayout.SOUTH);

        JPanel moneyPanel = new JPanel();
        moneyPanel.setLayout(new FlowLayout());
        JLabel moneyLabel = new JLabel("Enter amount: ");
        moneyInput = new JTextField(10);
        JButton insertMoneyButton = new JButton("Insert Money");
        moneyPanel.add(moneyLabel);
        moneyPanel.add(moneyInput);
        moneyPanel.add(insertMoneyButton);
        panel.add(moneyPanel);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new FlowLayout());
        JButton selectCoke = new JButton("Select Coke");
        JButton selectPepsi = new JButton("Select Pepsi");
        JButton selectSoda = new JButton("Select Soda");
        productPanel.add(selectCoke);
        productPanel.add(selectPepsi);
        productPanel.add(selectSoda);
        panel.add(productPanel);

        JPanel cancelPanel = new JPanel();
        cancelPanel.setLayout(new FlowLayout());
        JButton cancel = new JButton("Cancel");
        cancelPanel.add(cancel);
        panel.add(cancelPanel);

        insertMoneyButton.addActionListener(e -> insertMoney());
        selectCoke.addActionListener(e -> selectProduct("Coke"));
        selectPepsi.addActionListener(e -> selectProduct("Pepsi"));
        selectSoda.addActionListener(e -> selectProduct("Soda"));
        cancel.addActionListener(e -> cancelRequest());

        frame.setVisible(true);

        //Giả lập end day với time là 1 phút 1 ngày
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            machine.endDay();
            display.append("End of day process completed.\n");
        }, 1, 1, TimeUnit.MINUTES);

        Timer timer = new Timer(1000, e -> updateClock());
        timer.start();
    }

    private void insertMoney() {
        try {
            int amount = Integer.parseInt(moneyInput.getText());
            if (machine.insertMoney(amount)) {
                display.append("Inserted: " + amount + " VND\n");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid amount. Please insert 10,000, 20,000, 50,000, 100,000, or 200,000 VND.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        moneyInput.setText("");
    }

    private void selectProduct(String product) {
        display.append(machine.selectProduct(product) + "\n");
    }

    private void cancelRequest() {
        display.append("Request canceled, refund: " + machine.cancelRequest() + " VND\n");
    }

    private void updateClock() {
        clockLabel.setText(timeFormat.format(new Date()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SodaMachineGUI::new);
    }
}
