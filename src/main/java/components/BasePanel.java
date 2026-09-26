package components;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import components.ModernScrollBarUI;
import theme.AppFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public abstract class BasePanel {

    public static Image createImageLogo(Class <?> clazz, String name, int width, int height) {
        ImageIcon logo=new ImageIcon(Objects.requireNonNull(clazz.getResource(name+".png")));
        return logo.getImage().getScaledInstance(width,height, Image.SCALE_SMOOTH);
    }

    public static Icon createIcon(Class<?> clazz, String name, int width, int height, Color color) {

        FlatSVGIcon icon = new FlatSVGIcon(Objects.requireNonNull(clazz.getResource(  name + ".svg")));

        icon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> color));

        return icon.derive(width, height);
    }

    public static Icon createIcon(Class<?> clazz, String name, int width, int height) {

        FlatSVGIcon icon = new FlatSVGIcon(Objects.requireNonNull(clazz.getResource(name + ".svg")));

        return icon.derive(width, height);
    }

    public static JLabel createTitle(String text, Font font, Color color) {
        JLabel title = new JLabel(text);
        title.setFont(font);
        title.setForeground(color);
        return title;
    }

    public static JPanel createItem(String title, JComponent component){
        JPanel item = new JPanel();
        item.setOpaque(false);
        item.setLayout(new BoxLayout(item,BoxLayout.Y_AXIS));

        JLabel label = createTitle(title, AppFont.NORMAL, new Color(71, 71, 71));
        label.setBorder(new EmptyBorder(0,5,0,0));

        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        item.add(label);
        item.add(Box.createVerticalStrut(5));
        item.add(component);
        return item;
    }

    public static BorderPanel createIconPanel(JLabel iconLabel,Color color) {
        BorderPanel icon =  new BorderPanel(24, color, 0, 0,Color.WHITE,0);
        icon.setOpaque(false);

        icon.setPreferredSize(new Dimension(48, 48));
        icon.setMinimumSize(new Dimension(48, 48));
        icon.setLayout(new BorderLayout());
        icon.setBorder(new EmptyBorder(0, 0, 0, 0));

        icon.add(iconLabel, BorderLayout.CENTER);

        return icon;
    }

    public static JScrollPane createScroll(Component panel){
        JScrollPane scrollPane = new JScrollPane(panel);

        JScrollBar vBar = scrollPane.getVerticalScrollBar();
        vBar.setUI(new ModernScrollBarUI());
        vBar.setPreferredSize(new Dimension(6, 0));
        vBar.setUnitIncrement(16);
        vBar.setOpaque(false);

        JScrollBar hBar = scrollPane.getHorizontalScrollBar();
        hBar.setUI(new ModernScrollBarUI());
        hBar.setPreferredSize(new Dimension(0, 6));
        hBar.setOpaque(false);

        scrollPane.setBorder(new EmptyBorder(0,0,8,8));

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    public static String formatNumber(long number) {

        if (number >= 1_000_000_000)
            return String.format("%.1fB", number / 1_000_000_000.0);

        if (number >= 1_000_000)
            return String.format("%.1fM", number / 1_000_000.0);

        if (number >= 1_000)
            return String.format("%.1fK", number / 1_000.0);

        return String.valueOf(number);
    }
}