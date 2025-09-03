import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FancyCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private JTextArea history;
    private String operator = "";
    private double num1, num2, result;
    private ArrayList<String> historyList = new ArrayList<>();

    public FancyCalculator() {
        setTitle("✨ Fancy Calculator ✨");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Display Panel
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Consolas", Font.BOLD, 26));
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Button Panel
        JPanel panel = new JPanel(new GridLayout(5, 4, 10, 10));
        panel.setBackground(new Color(30, 30, 30));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "CE", "←", "%"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setBackground(new Color(50, 50, 50));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            btn.addActionListener(this);

            // Hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(70, 130, 180));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(50, 50, 50));
                }
            });

            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);

        // History Panel
        history = new JTextArea();
        history.setEditable(false);
        history.setFont(new Font("Monospaced", Font.PLAIN, 14));
        history.setBackground(new Color(245, 245, 245));
        JScrollPane scroll = new JScrollPane(history);
        scroll.setPreferredSize(new Dimension(150, 0));
        add(scroll, BorderLayout.EAST);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9]") || cmd.equals(".")) {
            display.setText(display.getText() + cmd);
        } 
        else if (cmd.matches("[/*\\-+%]")) {
            if (!display.getText().isEmpty()) {
                operator = cmd;
                num1 = Double.parseDouble(display.getText());
                display.setText("");
            }
        } 
        else if (cmd.equals("=")) {
            if (!display.getText().isEmpty() && !operator.isEmpty()) {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/": result = num2 != 0 ? num1 / num2 : 0; break;
                    case "%": result = num1 % num2; break;
                }
                String equation = num1 + " " + operator + " " + num2 + " = " + result;
                historyList.add(equation);
                updateHistory();
                display.setText("" + result);
                operator = "";
            }
        } 
        else if (cmd.equals("C")) {
            display.setText("");
            num1 = num2 = result = 0;
            operator = "";
        } 
        else if (cmd.equals("CE")) {
            display.setText("");
        } 
        else if (cmd.equals("←")) {
            String text = display.getText();
            if (!text.isEmpty()) {
                display.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    private void updateHistory() {
        history.setText("");
        for (String h : historyList) {
            history.append(h + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FancyCalculator().setVisible(true);
        });
    }
}
