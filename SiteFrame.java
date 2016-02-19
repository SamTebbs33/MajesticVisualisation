import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by samtebbs on 19/02/2016.
 */
public class SiteFrame extends JFrame {
    public JTable table;

    public SiteFrame() {
        super("Category visualisaer");
        setLayout(new BorderLayout());
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table = new JTable(new DefaultTableModel(CategoryVisualisation.tableColumns, 0));
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.NORTH);
        JPanel categoryPanel = new CategoryPanel();
        add(categoryPanel, BorderLayout.SOUTH);
    }
}
