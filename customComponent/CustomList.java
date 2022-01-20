package customComponent;

import javax.swing.*;
import java.awt.*;

public class CustomList extends JList<String> {

    public CustomList(DefaultListModel<String> dlm) {
        super(dlm);
        setFont(new Font("SansSerif", Font.BOLD, 15));
    }
}
