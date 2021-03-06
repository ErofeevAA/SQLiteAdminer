import customComponent.CustomList;
import customComponent.CustomTable;
import customComponent.ErrorLabel;
import tableWindow.TableWindow;
import utils.CodeTextDocumentStyle;
import utils.SQLRequests;
import utils.TableModel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class EditorWindow extends JFrame {

    private JPanel rootPanel;
    private JPanel usefulButtonPanel;
    private JPanel tablesPanel;
    private JTabbedPane editorTabbedPanel;
    private JScrollPane codePanel;
    private JScrollPane outputPanel;
    private JTextPane codeTextPanel;
    private JButton redoButton;
    private JButton playButton;
    private JButton undoButton;
    private JButton addNewDBButton;
    private JScrollPane scrollTablesPanel;
    private JLabel noTablesLabel;
    private JLabel connectStateLabel;
    private JPanel innerTablesPanel;

    private SQLRequests sqlRequests = null;

    public EditorWindow() {
        setContentPane(rootPanel);
        setSize(900, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CodeTextDocumentStyle codeTextDocumentStyle = new CodeTextDocumentStyle();
        codeTextPanel.setStyledDocument(codeTextDocumentStyle);

        undoButton.addActionListener(actionEvent -> codeTextDocumentStyle.undo());
        redoButton.addActionListener(actionEvent -> codeTextDocumentStyle.redo());
        addNewDBButton.addActionListener(actionEvent -> clickAddNewDB());
        playButton.addActionListener(actionEvent -> clickPlay());
    }

    private void clickAddNewDB() {
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showDialog(null, "Choose DB");
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String pathToDB = file.getPath();
            System.out.println(pathToDB);
            connectStateLabel.setText("Connect to " + file.getName());
            sqlRequests = SQLRequests.getInstance();
            if (!sqlRequests.isClosed()) {
                sqlRequests.disconnect();
            }
            sqlRequests.setDB(pathToDB);
            ArrayList<String> list = sqlRequests.getTables();
            if (list != null) {
                fillTablesPanel(list);
            }

            playButton.setEnabled(true);
        }
    }

    private void clickPlay() {
        if (sqlRequests == null) {
            return;
        }
        String code = codeTextPanel.getSelectedText();
        if (code == null) {
         code = codeTextPanel.getText();
        }
        if (!code.equals("")) {
            try {
                //outputPanel.removeAll();
                TableModel tableModel = sqlRequests.request(code);
                CustomTable jTable = new CustomTable(tableModel);
                outputPanel.setViewportView(jTable);
            } catch (SQLException e) {
                ErrorLabel label = new ErrorLabel(e.getMessage());
                outputPanel.setViewportView(label);
            }
            editorTabbedPanel.setSelectedIndex(1);
            outputPanel.repaint();
            outputPanel.revalidate();

            ArrayList<String> list = sqlRequests.getTables();
            if (list != null) {
                fillTablesPanel(list);
            }
        }
    }

    private void fillTablesPanel(ArrayList<String> list) {
        innerTablesPanel.removeAll();
        if (list == null) {
            innerTablesPanel.add(noTablesLabel);
            innerTablesPanel.repaint();
            innerTablesPanel.revalidate();
            return;
        }
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (String l : list) {
            dlm.addElement(l);
        }
        CustomList tablesList = new CustomList(dlm);
        tablesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                tablesListMouseClick(event);
            }
        });
        innerTablesPanel.add(tablesList);
        innerTablesPanel.repaint();
        innerTablesPanel.revalidate();
    }

    private void tablesListMouseClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            JList<String> list = (JList<String>) event.getSource();
            int index = list.locationToIndex(event.getPoint());
            String table = list.getModel().getElementAt(index);

            final String req = "select * from " + table;
            try {
                TableModel tableModel = sqlRequests.request(req);
                TableWindow tableWindow = new TableWindow(tableModel, table);
                tableWindow.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
        rootPanel.setMinimumSize(new Dimension(900, 900));
        rootPanel.setPreferredSize(new Dimension(900, 900));
        usefulButtonPanel = new JPanel();
        usefulButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        usefulButtonPanel.setMinimumSize(new Dimension(0, 150));
        rootPanel.add(usefulButtonPanel, BorderLayout.NORTH);
        undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/assets/undo-arrow.png")));
        undoButton.setText("");
        usefulButtonPanel.add(undoButton);
        redoButton = new JButton();
        redoButton.setIcon(new ImageIcon(getClass().getResource("/assets/redo_arrow.png")));
        redoButton.setText("");
        usefulButtonPanel.add(redoButton);
        playButton = new JButton();
        playButton.setEnabled(false);
        playButton.setIcon(new ImageIcon(getClass().getResource("/assets/play.png")));
        playButton.setText("");
        usefulButtonPanel.add(playButton);
        addNewDBButton = new JButton();
        Font addNewDBButtonFont = this.$$$getFont$$$(null, Font.BOLD, 20, addNewDBButton.getFont());
        if (addNewDBButtonFont != null) addNewDBButton.setFont(addNewDBButtonFont);
        addNewDBButton.setLabel("+");
        addNewDBButton.setText("+");
        usefulButtonPanel.add(addNewDBButton);
        tablesPanel = new JPanel();
        tablesPanel.setLayout(new BorderLayout(0, 0));
        tablesPanel.setMaximumSize(new Dimension(150, 32767));
        tablesPanel.setMinimumSize(new Dimension(150, 18));
        tablesPanel.setPreferredSize(new Dimension(200, 37));
        rootPanel.add(tablesPanel, BorderLayout.WEST);
        connectStateLabel = new JLabel();
        Font connectStateLabelFont = this.$$$getFont$$$("SansSerif", Font.BOLD, 14, connectStateLabel.getFont());
        if (connectStateLabelFont != null) connectStateLabel.setFont(connectStateLabelFont);
        connectStateLabel.setHorizontalAlignment(0);
        connectStateLabel.setHorizontalTextPosition(0);
        connectStateLabel.setText("No connect");
        tablesPanel.add(connectStateLabel, BorderLayout.NORTH);
        scrollTablesPanel = new JScrollPane();
        scrollTablesPanel.setVerticalScrollBarPolicy(22);
        tablesPanel.add(scrollTablesPanel, BorderLayout.CENTER);
        innerTablesPanel = new JPanel();
        innerTablesPanel.setLayout(new BorderLayout(0, 0));
        scrollTablesPanel.setViewportView(innerTablesPanel);
        noTablesLabel = new JLabel();
        noTablesLabel.setHorizontalAlignment(0);
        noTablesLabel.setText("No tables");
        innerTablesPanel.add(noTablesLabel, BorderLayout.CENTER);
        editorTabbedPanel = new JTabbedPane();
        rootPanel.add(editorTabbedPanel, BorderLayout.CENTER);
        codePanel = new JScrollPane();
        codePanel.setHorizontalScrollBarPolicy(32);
        codePanel.setVerticalScrollBarPolicy(22);
        editorTabbedPanel.addTab("Code", codePanel);
        codeTextPanel = new JTextPane();
        Font codeTextPanelFont = this.$$$getFont$$$("DejaVu Sans Mono", -1, 16, codeTextPanel.getFont());
        if (codeTextPanelFont != null) codeTextPanel.setFont(codeTextPanelFont);
        codeTextPanel.setText("");
        codePanel.setViewportView(codeTextPanel);
        outputPanel = new JScrollPane();
        editorTabbedPanel.addTab("Output", outputPanel);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
