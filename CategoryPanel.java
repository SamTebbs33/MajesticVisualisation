import javax.swing.*;
import java.awt.*;

/**
 * Created by samtebbs on 19/02/2016.
 */
public class CategoryPanel extends JPanel {

    public CategoryPanel() {
        JLabel label = new JLabel("Category: ");
        JTextField textField = new JTextField();
        JButton submitButton = new JButton("Search");
        submitButton.addActionListener(event -> CategoryVisualisation.onSearch(textField));
        setLayout(new BorderLayout());
        add(label, BorderLayout.LINE_START);
        add(textField, BorderLayout.CENTER);
        add(submitButton, BorderLayout.LINE_END);
    }
}
