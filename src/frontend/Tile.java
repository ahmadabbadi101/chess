package frontend;

import javax.swing.*;
import java.awt.*;

public class Tile implements Composite <JPanel> {
    JPanel self;
    Button btn;

    private static final java.awt.Color WHITE = new java.awt.Color(248, 215, 161);
    private static final java.awt.Color BLACK = new java.awt.Color(168, 113, 73);

    public Tile(ChessBoardGUI board, int i, int j) {
        self = new JPanel(new GridLayout());

        if (((7 - i) % 2 == 0 && j % 2 == 0) || ((7 - i) % 2 != 0 && j % 2 != 0))
            self.setBackground(BLACK);
        else
            self.setBackground(WHITE);
    }

    @Override
    public JPanel getSelf() {
        return self;
    }

    @Override
    public Button getChild() {
        return this.btn;
    }

    @Override
    public void setChild(Component child) {
        this.btn = (Button) child;

        self.add(((Button)child).getSelf(), BorderLayout.CENTER);
    }
}
