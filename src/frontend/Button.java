package frontend;

import javax.swing.*;

public class Button implements Composite <JButton> {
    private final JButton self;
    private BtnIcon icon;
    public static final java.awt.Color GREEN = new java.awt.Color(107, 111, 71);

    public Button(ChessBoardGUI board, int i, int j) {
        self = new JButton();
        if (7 - i == 0) {
            switch (j) {
                case 0:
                case 7:
                    icon = new BtnIcon("WhiteRook");
                    self.setIcon(icon.getSelf());
                    break;
                case 1:
                case 6:
                    icon = new BtnIcon("WhiteKnight");
                    self.setIcon(icon.getSelf());
                    break;
                case 2:
                case 5:
                    icon = new BtnIcon("WhiteBishop");
                    self.setIcon(icon.getSelf());
                    break;
                case 3:
                    icon = new BtnIcon("WhiteQueen");
                    self.setIcon(icon.getSelf());
                    break;
                case 4:
                    icon = new BtnIcon("WhiteKing");
                    self.setIcon(icon.getSelf());
                    break;
            }
        } else if (7 - i == 1) {
            icon = new BtnIcon("WhitePawn");
            self.setIcon(icon.getSelf());
        } else if (7 - i == 7) {
            switch (j) {
                case 0:
                case 7:
                    icon = new BtnIcon("BlackRook");
                    self.setIcon(icon.getSelf());
                    break;
                case 1:
                case 6:
                    icon = new BtnIcon("BlackKnight");
                    self.setIcon(icon.getSelf());
                    break;
                case 2:
                case 5:
                    icon = new BtnIcon("BlackBishop");
                    self.setIcon(icon.getSelf());
                    break;
                case 3:
                    icon = new BtnIcon("BlackQueen");
                    self.setIcon(icon.getSelf());
                    break;
                case 4:
                    icon = new BtnIcon("BlackKing");
                    self.setIcon(icon.getSelf());
                    break;
            }
        } else if (7 - i == 6) {
            icon = new BtnIcon("BlackPawn");
            self.setIcon(icon.getSelf());
        }

        self.setContentAreaFilled(false);
        self.setOpaque(false);
        self.setFocusPainted(false);
        self.setBorderPainted(false);
        self.setBorderPainted(false);

        self.setBackground(GREEN);

        self.addActionListener(new TilesListener(board, 7 - i, j));
    }

    @Override
    public BtnIcon getChild() {
        return icon;
    }

    @Override
    public void setChild(Component child) {
        this.icon = (BtnIcon) child;
        if (child != null) {
            self.setIcon(((BtnIcon)child).getSelf());
        } else {
            self.setIcon(null);
        }
    }

    @Override
    public JButton getSelf() {
        return self;
    }

    public void setOpaque(boolean opaque) {
        self.setOpaque(opaque);
    }

    public void setContentAreaFilled(boolean areaFilled) {
        self.setContentAreaFilled(areaFilled);
    }

    public void setBackground(java.awt.Color color) {
        self.setBackground(color);
    }
}
