package customComponent;

import utils.TableModel;

import javax.swing.*;
import java.awt.*;

public class CustomTable extends JTable {

    public CustomTable(TableModel model) {
        super(model.data, model.header);
        getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        setFont(new Font("SansSerif", Font.PLAIN, 18));
    }
}
