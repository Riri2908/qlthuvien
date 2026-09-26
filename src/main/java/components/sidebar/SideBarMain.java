package components.sidebar;

import components.BasePanel;
import components.BorderPanel;
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

public class SideBarMain extends JPanel {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int SIDEBAR_WIDTH = 240;
    public static final int SIDEBAR_HEIGHT = 80;
    public static int PADDING_LEFT = 25;
    public static int MENU_PADDING_TOP = 20;
    public static int MENU_PADDING_BOTTOM = 20;
    public static int MENU_PADDING_LEFT = 20;
    public static int MENU_PADDING_RIGHT = 20;
    public static int MENU_PADDING_GAP=6;
    public static Dimension SIZE_BUTTON = new Dimension(200, 45);
    public static Dimension MAX_SIZE_BUTTON = new Dimension(Integer.MAX_VALUE, 45);

    public SideBarItem homePage;
    public SideBarItem book;
    public SideBarItem reader;
    public SideBarItem loanRecord;
    public SideBarItem report;

    private final ContentPanel contentPanel;

    public SideBarMain(ContentPanel contentPanel){
        this.contentPanel = contentPanel;
        this.homePage = new SideBarItem(PageKey.HOME,"menu.homePage", "dashBoard",contentPanel);
        this.book = new SideBarItem(PageKey.BOOKS,"menu.book", "book",contentPanel);
        this.reader = new SideBarItem(PageKey.READER,"menu.reader", "reader",contentPanel);
        this.loanRecord = new SideBarItem(PageKey.LOAN_RECORD,"menu.loanRecord", "loanRecord",contentPanel);
        this.report = new SideBarItem(PageKey.REPORT,"menu.report", "report",contentPanel);

        setLayout(new BorderLayout());
        setBackground(AppColor.BACKGROUND_SIDEBAR);
        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        setMinimumSize(new Dimension(Integer.MAX_VALUE, 0));
        setBorder(new EmptyBorder(MENU_PADDING_TOP,MENU_PADDING_LEFT,MENU_PADDING_BOTTOM,MENU_PADDING_RIGHT));

        add(header(),BorderLayout.NORTH);
        add(menu(),BorderLayout.CENTER);
        add(footer(),BorderLayout.SOUTH);
    }

    public JLabel header, sub_header;

    private BorderPanel header(){
        BorderPanel header = new BorderPanel(0,AppColor.BACKGROUND_SIDEBAR,0,0,null,0);

        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        this.header = BasePanel.createTitle(Lang.get("sideBar.header"), AppFont.TITLE,AppColor.WHITE);
        this.header.setIcon(BasePanel.createIcon(getClass(),"/icons/sidebar/header",25,25));
        this.header.setIconTextGap(10);
        this.sub_header = BasePanel.createTitle(Lang.get("sideBar.sub_header"), AppFont.SMALL_BOLD,AppColor.GRAY);

        header.add(this.header);
        header.add(sub_header);
        return header;
    }

    private BorderPanel menu(){
        BorderPanel menu = new BorderPanel(0,AppColor.BACKGROUND_SIDEBAR,0,0,null,0);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(MENU_PADDING_TOP,0,MENU_PADDING_BOTTOM,0));

        contentPanel.registerMenuAndTitle(PageKey.HOME, "menu.homePage",this.homePage);
        contentPanel.registerMenuAndTitle(PageKey.BOOKS, "menu.book",this.book);
        contentPanel.registerMenuAndTitle(PageKey.READER, "menu.reader",this.reader);
        contentPanel.registerMenuAndTitle(PageKey.LOAN_RECORD, "menu.loanRecord",this.loanRecord);
        contentPanel.registerMenuAndTitle(PageKey.REPORT, "menu.report",this.report);

        menu.add(this.homePage);
        menu.add(Box.createVerticalStrut(10));
        menu.add(this.book);
        menu.add(Box.createVerticalStrut(10));
        menu.add(this.reader);
        menu.add(Box.createVerticalStrut(10));
        menu.add(this.loanRecord);
        menu.add(Box.createVerticalStrut(10));
        menu.add(this.report);
        menu.add(Box.createVerticalStrut(150));
        menu.add(lang());
        return menu;
    }

    private String currentLang = "vi";

    private BorderPanel lang(){
        BorderPanel lang = new BorderPanel(0,AppColor.BACKGROUND_SIDEBAR,0,0,null,1);
        lang.setLayout(new BoxLayout(lang, BoxLayout.X_AXIS));
        lang.setPreferredSize(SideBarMain.SIZE_BUTTON);
        lang.setMaximumSize(SideBarMain.MAX_SIZE_BUTTON);

        BorderPanel viLang = new BorderPanel(16,AppColor.BACKGROUND_SIDEBAR_ENTERED,0,0,null,0);
        viLang.setAlignmentY(0.5f);
        JLabel vi = BasePanel.createTitle("VI",AppFont.NORMAL_BOLD,AppColor.WHITE);
        vi.setBorder(new EmptyBorder(5,0,0,0));
        vi.setIcon(BasePanel.createIcon(getClass(),"/icons/lang/vi",24,24));
        viLang.add(vi);

        BorderPanel usLang = new BorderPanel(16,AppColor.BACKGROUND_SIDEBAR,0,0,AppColor.GRAY,1);
        JLabel us = BasePanel.createTitle("US",AppFont.NORMAL_BOLD,AppColor.WHITE);
        us.setBorder(new EmptyBorder(5,0,0,0));
        us.setIcon(BasePanel.createIcon(getClass(),"/icons/lang/us",24,24));
        usLang.add(us);

        lang.add(viLang);
        lang.add(Box.createHorizontalStrut(10));
        lang.add(usLang);

        viLang.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                currentLang = "vi";
                Lang.load("vi");

                updateLangUI(viLang, usLang);

                refreshLang();
            }
        });

        usLang.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                currentLang = "en";
                Lang.load("en");

                updateLangUI(viLang, usLang);

                refreshLang();
            }
        });

        return lang;
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

    private void updateLangUI(BorderPanel viLang, BorderPanel usLang) {
        if (this.currentLang.equals("vi")) {
            viLang.setBackground(AppColor.BACKGROUND_SIDEBAR_ENTERED);
            viLang.setBorder(null,0);
            usLang.setBackground(AppColor.BACKGROUND_SIDEBAR);
            usLang.setBorder(AppColor.GRAY,1);

        } else {
            usLang.setBackground(AppColor.BACKGROUND_SIDEBAR_ENTERED);
            usLang.setBorder(null,0);
            viLang.setBackground(AppColor.BACKGROUND_SIDEBAR);
            viLang.setBorder(AppColor.GRAY,1);
        }
    }

//    private void update

    public void refreshLang() {
        homePage.refreshLang();
        book.refreshLang();
        reader.refreshLang();
        loanRecord.refreshLang();
        report.refreshLang();
        this.header.setText(Lang.get("sideBar.header"));
        this.sub_header.setText(Lang.get("sideBar.sub_header"));
    }
}
