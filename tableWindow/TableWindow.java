package tableWindow;

import utils.TableModel;

import javax.swing.*;
import java.awt.*;

public class TableWindow extends JFrame {
    private JPanel rootPanel;
    private JScrollPane scrollPane;

    public TableWindow(TableModel tableModel, String title) {
        setSize(500, 500);
        setContentPane(rootPanel);
        setTitle(title);

        JTable table = new JTable(tableModel.data, tableModel.header);
        scrollPane.setViewportView(table);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(0, 0));
        scrollPane = new JScrollPane();
        rootPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
