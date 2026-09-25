package components.sidebar;

import components.BasePanel;
import components.BorderPanel;
import theme.AppColor;
import theme.AppFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class SideBarMain extends JPanel {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int SIDEBAR_WIDTH = 180;
    public static final int SIDEBAR_HEIGHT = 80;
    public static int PADDING_LEFT = 25;
    public static int MENU_PADDING_TOP = 20;
    public static int MENU_PADDING_BOTTOM = 20;
    public static int MENU_PADDING_LEFT = 20;
    public static int MENU_PADDING_RIGHT = 20;
    public static int MENU_PADDING_GAP=6;
    public static Dimension SIZE_BUTTON = new Dimension(200, 45);
    public static Dimension MAX_SIZE_BUTTON = new Dimension(Integer.MAX_VALUE, 45);


    public SideBarMain(){
        setLayout(new BorderLayout());
        setBackground(AppColor.BACKGROUND_SIDEBAR);
        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        setMinimumSize(new Dimension(Integer.MAX_VALUE, 0));
        setBorder(new EmptyBorder(MENU_PADDING_TOP,MENU_PADDING_LEFT,MENU_PADDING_BOTTOM,MENU_PADDING_RIGHT));

        add(header(),BorderLayout.NORTH);
        add(footer(),BorderLayout.SOUTH);
    }

    private BorderPanel header(){
        BorderPanel header = new BorderPanel(0,AppColor.BACKGROUND_SIDEBAR,0,0,null,0);

        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = BasePanel.createTitle("Thư viện", AppFont.TITLE,AppColor.WHITE);
        title.setIcon(BasePanel.createIcon(getClass(),"sidebar/header",25,25));
        title.setIconTextGap(10);
        JLabel sub_title = BasePanel.createTitle("Quản lý thư viện", AppFont.SMALL_BOLD,AppColor.GRAY);

        header.add(title);
        header.add(sub_title);
        return header;
    }

    private BorderPanel menu(){
        BorderPanel menu = new BorderPanel(0,AppColor.BACKGROUND_SIDEBAR,0,0,null,0);
        
        return menu;

    }

    private BorderPanel footer(){
        BorderPanel footer = new BorderPanel(0,AppColor.BACKGROUND_SIDEBAR,0,0,null,0);
        footer.setBorder(BorderFactory.createMatteBorder(1,0,0,0,AppColor.GRAY));
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));

        JLabel title = BasePanel.createTitle("Nguyễn Văn A", AppFont.NORMAL_BOLD,AppColor.WHITE);
        title.setBorder(new EmptyBorder(10,0,0,0));
//        title.setIcon(BasePanel.createIcon(getClass(),"sidebar/header",25,25));
//        title.setIconTextGap(10);
        JLabel sub_title = BasePanel.createTitle("Quản trị viên", AppFont.SMALL_BOLD,AppColor.GRAY);

        footer.add(title);
        footer.add(sub_title);
        return footer;
    }
}
