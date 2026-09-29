package components.sidebar;

import components.BasePanel;
import components.page.ContentPanel;
import components.page.PageKey;
import registry.lang.Lang;
import theme.AppColor;
import theme.AppFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SideBarItem extends JPanel {

    private boolean active = false;

    private final String text;
    private final PageKey pageKey;
    private final JLabel title;
    private final ContentPanel contentPanel;

    public SideBarItem(PageKey pageKey, String text, String logoName, ContentPanel contentPanel) {
        this.contentPanel = contentPanel;
        this.pageKey = pageKey;
        this.text = text;

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setBackground(Color.WHITE);
        setPreferredSize(SideBarMain.SIZE_BUTTON);
        setMaximumSize(SideBarMain.MAX_SIZE_BUTTON);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(3,5,3,20));
        setBackground(AppColor.BACKGROUND_SIDEBAR);

        this.title=BasePanel.createTitle(Lang.get(text), AppFont.NORMAL_BOLD, AppColor.WHITE);

        title.setIcon(BasePanel.createIcon(getClass(),"/icons/sidebar/"+logoName,24,24));
        title.setIconTextGap(SideBarMain.MENU_PADDING_LEFT);
        add(title);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.showPage(pageKey);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!active) setBackground(AppColor.BACKGROUND_SIDEBAR_ENTERED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if(!active) setBackground(AppColor.BACKGROUND_SIDEBAR);
            }
        });
    }

    public void setActive(boolean active) {
        this.active = active;
        setBackground(active ? AppColor.BACKGROUND_SIDEBAR_ENTERED : AppColor.BACKGROUND_SIDEBAR);
        title.setForeground(active ? AppColor.WHITE : AppColor.GRAY);
        repaint();
    }

    public void refreshLang() {
        title.setText(Lang.get(this.text));
        this.contentPanel.refreshLang(this.pageKey);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = active ? AppColor.BACKGROUND_SIDEBAR_ENTERED : getBackground();

        g2.setColor(bg);

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

        g2.dispose();
        super.paintComponent(g);
    }


}