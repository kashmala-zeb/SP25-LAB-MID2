import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Interface (Strategy Pattern)
interface PaymentStrategy {
    String pay(double amount);
}

// Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    public String pay(double amount) {
        return "Paid " + amount + " via Credit Card.";
    }
}

class BankTransferPayment implements PaymentStrategy {
    public String pay(double amount) {
        return "Paid " + amount + " via Bank Transfer.";
    }
}

class WalletPayment implements PaymentStrategy {
    public String pay(double amount) {
        return "Paid " + amount + " via Wallet.";
    }
}

// Controller (GRASP Controller Pattern)
class PaymentController {
    private PaymentStrategy strategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public String processPayment(double amount) {
        if (strategy == null) return "Payment method not selected.";
        return strategy.pay(amount);
    }
}

// Main GUI Class
public class AuctionPaymentUI extends JFrame {
    private JComboBox<String> methodBox;
    private JTextField amountField;
    private JButton payButton;
    private JTextArea resultArea;

    private PaymentController controller = new PaymentController();

    public AuctionPaymentUI() {
        setTitle("Auction Payment System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI Components
        JLabel label = new JLabel("Enter Amount:");
        amountField = new JTextField(10);

        JLabel methodLabel = new JLabel("Select Payment Method:");
        String[] methods = {"Credit Card", "Bank Transfer", "Wallet"};
        methodBox = new JComboBox<>(methods);

        payButton = new JButton("Pay Now");
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(label);
        panel.add(amountField);
        panel.add(methodLabel);
        panel.add(methodBox);
        panel.add(payButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Event Handling
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String method = (String) methodBox.getSelectedItem();
                double amount;
                try {
                    amount = Double.parseDouble(amountField.getText());
                } catch (NumberFormatException ex) {
                    resultArea.setText("Invalid amount entered.");
                    return;
                }

                switch (method) {
                    case "Credit Card":
                        controller.setPaymentStrategy(new CreditCardPayment());
                        break;
                    case "Bank Transfer":
                        controller.setPaymentStrategy(new BankTransferPayment());
                        break;
                    case "Wallet":
                        controller.setPaymentStrategy(new WalletPayment());
                        break;
                }

                String result = controller.processPayment(amount);
                resultArea.setText(result);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AuctionPaymentUI().setVisible(true);
        });
    }
}
