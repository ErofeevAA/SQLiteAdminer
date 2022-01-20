package customComponent;

import javax.swing.*;
import java.awt.*;

public class ErrorLabel extends JLabel {

    public ErrorLabel(String s) {
        super(s);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("SansSerif", Font.PLAIN, 16));
    }
}
