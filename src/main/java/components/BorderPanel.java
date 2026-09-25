package components;

import javax.swing.*;
import java.awt.*;

public class BorderPanel extends JPanel {

    private int arc = 20;
    private Color bgColor = Color.WHITE;

    private int shadowSize = 6;
    private int shadowOpacity = 20;

    private Color borderColor = new Color(220,220,220);
    private int borderWidth = 1;

    public BorderPanel() {
        setOpaque(false);
    }

    public BorderPanel(int arc, Color bgColor, int shadowSize, int shadowOpacity, Color borderColor, int borderWidth) {
        this.arc = arc;
        this.bgColor = bgColor;
        this.shadowSize = shadowSize;
        this.shadowOpacity = shadowOpacity;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        setOpaque(false);
    }

    public void setBorder(Color borderColor, int borderWidth){
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        repaint();
    }

    @Override
    public void setBackground(Color bg) {
        this.bgColor = bg;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < shadowSize; i++) {
            g2.setColor(new Color(0, 0, 0, shadowOpacity / (i + 2)));
            g2.fillRoundRect(i, i, width - i * 2, height - i * 2, arc, arc);
        }

        int rectWidth = width - shadowSize / 2;
        int rectHeight = height - shadowSize / 2;

        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, rectWidth, rectHeight, arc, arc);

        if (borderWidth > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderWidth));
            g2.drawRoundRect(0, 0, rectWidth - borderWidth, rectHeight - borderWidth, arc, arc);
        }

        g2.dispose();
        super.paintComponent(g);
    }

}
