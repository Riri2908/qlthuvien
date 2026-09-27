package view.homepage;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleEdge; // JFreeChart 1.5.x. Nếu dùng bản cũ hơn -> org.jfree.chart.util.RectangleEdge
import org.jfree.data.category.CategoryDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;


public class RoundedBarRenderer extends BarRenderer {

    private final int arcSize;

    public RoundedBarRenderer(int arcSize) {
        this.arcSize = arcSize;
        setBarPainter(new StandardBarPainter());
        setShadowVisible(false);
        setDrawBarOutline(false);
    }

    @Override
    public void drawItem(Graphics2D g2, CategoryItemRendererState state,
                          Rectangle2D dataArea, CategoryPlot plot,
                          CategoryAxis domainAxis, ValueAxis rangeAxis,
                          CategoryDataset dataset, int row, int column, int pass) {

        if (!getItemVisible(row, column)) {
            return;
        }

        Number dataValue = dataset.getValue(row, column);
        if (dataValue == null) {
            return;
        }

        double value = dataValue.doubleValue();
        PlotOrientation orientation = plot.getOrientation();

        double barW0 = calculateBarW0(plot, orientation, dataArea, domainAxis, state, row, column);
        double[] barL0L1 = calculateBarL0L1(value);
        if (barL0L1 == null) {
            return;
        }

        RectangleEdge edge = plot.getRangeAxisEdge();
        double transL0 = rangeAxis.valueToJava2D(barL0L1[0], dataArea, edge);
        double transL1 = rangeAxis.valueToJava2D(barL0L1[1], dataArea, edge);

        double barL0 = Math.min(transL0, transL1);
        double barLength = Math.max(Math.abs(transL1 - transL0), 0.001);
        double barW = state.getBarWidth();

        Shape bar;
        if (orientation == PlotOrientation.HORIZONTAL) {
            bar = new RoundRectangle2D.Double(barL0, barW0, barLength, barW, arcSize, arcSize);
        } else {
            bar = new RoundRectangle2D.Double(barW0, barL0, barW, barLength, arcSize, arcSize);
        }

        Paint itemPaint = getItemPaint(row, column);
        g2.setPaint(itemPaint);
        g2.fill(bar);

        CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
        if (generator != null && isItemLabelVisible(row, column)) {
            drawItemLabel(g2, dataset, row, column, plot, generator, bar.getBounds2D(), value < 0);
        }
    }
}
