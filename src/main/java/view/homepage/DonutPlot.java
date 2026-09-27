package view.homepage;

import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class DonutPlot extends RingPlot {

    private final String centerMainText;
    private final String centerSubText;
    private final Font mainFont;
    private final Font subFont;
    private final Color mainColor;
    private final Color subColor;

    public DonutPlot(PieDataset dataset, String centerMainText, String centerSubText,
                      Font mainFont, Font subFont, Color mainColor, Color subColor) {
        super(dataset);
        this.centerMainText = centerMainText;
        this.centerSubText = centerSubText;
        this.mainFont = mainFont;
        this.subFont = subFont;
        this.mainColor = mainColor;
        this.subColor = subColor;
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
                      PlotState parentState, PlotRenderingInfo info) {
        super.draw(g2, area, anchor, parentState, info);

        double centerX = area.getCenterX();
        double centerY = area.getCenterY();

        g2.setFont(mainFont);
        FontMetrics fmMain = g2.getFontMetrics();
        int mainWidth = fmMain.stringWidth(centerMainText);

        g2.setFont(subFont);
        FontMetrics fmSub = g2.getFontMetrics();
        int subWidth = fmSub.stringWidth(centerSubText);

        g2.setFont(mainFont);
        g2.setPaint(mainColor);
        g2.drawString(centerMainText, (float) (centerX - mainWidth / 2.0), (float) centerY);

        g2.setFont(subFont);
        g2.setPaint(subColor);
        g2.drawString(centerSubText, (float) (centerX - subWidth / 2.0), (float) (centerY + fmSub.getHeight()));
    }
}
