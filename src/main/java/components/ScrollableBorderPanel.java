package components;

import javax.swing.*;
import java.awt.*;


public class ScrollableBorderPanel extends BorderPanel implements Scrollable {

    public ScrollableBorderPanel(int radius, Color background, int shadowSize, int shadowArc, Color borderColor, int borderWidth) {
        super(radius, background, shadowSize, shadowArc, borderColor, borderWidth);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.VERTICAL ? visibleRect.height : visibleRect.width;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false; // đây là dòng quan trọng nhất để chống "phình to"
    }
}
