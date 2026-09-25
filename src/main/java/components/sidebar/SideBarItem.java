package components.sidebar;

import components.BasePanel;
import theme.AppFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SideBarItem extends JPanel {

    private boolean active=false;

    private final JLabel title;
    private final ImageIcon iconNormal;
    private final ImageIcon iconActive;

    public SideBarItem(String Text, String logoName) {

        this.iconNormal = new ImageIcon(BasePanel.createImageLogo(getClass(),"sidebar/"+logoName+"_gray",24,24));
        this.iconActive = new ImageIcon(BasePanel.createImageLogo(getClass(),"sidebar/"+logoName+"_blue",24,24));
        setOpaque(false);
        setBackground(Color.WHITE);
        setPreferredSize(SideBarMain.SIZE_BUTTON);
        setMaximumSize(SideBarMain.MAX_SIZE_BUTTON);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(0,SideBarMain.MENU_PADDING_LEFT,0,0));

        this.title=BasePanel.createTitle(Text, AppFont.NORMAL, new Color(121, 121, 121));

        title.setIcon(iconNormal);
        title.setIconTextGap(SideBarMain.MENU_PADDING_LEFT);
        add(title);

        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                contentPanel.showPage(Text);
//            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!active) setBackground(new Color(232, 232, 232));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(!active) setBackground(new Color(255, 255, 255));
            }
        });
    }

    public void setActive(boolean active) {
        this.active = active;
        setBackground(active ? new Color(231, 231, 255) : Color.WHITE);
        title.setForeground(active ? new Color(110, 153, 255) : new Color(121, 121, 121));
        title.setIcon(active ? iconActive : iconNormal);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = active ? new Color(231, 231, 255) : getBackground();

        g2.setColor(bg);

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

        g2.dispose();
        super.paintComponent(g);
    }


}