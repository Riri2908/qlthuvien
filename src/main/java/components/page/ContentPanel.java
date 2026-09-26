package components.page;

import components.BasePanel;
import components.BorderPanel;
import components.sidebar.SideBarItem;
import registry.lang.Lang;
import theme.AppColor;
import theme.AppFont;
import view.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ContentPanel extends BorderPanel {
    private final Map<PageKey, SideBarItem> menuMap = new HashMap<>();
    private final Map<PageKey, String> titleMap = new HashMap<>();

    private SideBarItem activeItem;

    public HomePage homePage;
    public BooksPage bookPage;
    public LoanRecordPage loanRecordPage;
    public ReaderPage readerPage;
    public ReportPage reportPage;

    private final CardLayout cardLayout=new CardLayout();
    private final BorderPanel contentPanel;
    private final JLabel topBar;

    public ContentPanel(){
        super(0,AppColor.WHITE,0,0,null,0);
        this.homePage = new HomePage();
        this.bookPage = new BooksPage();
        this.loanRecordPage = new LoanRecordPage();
        this.readerPage = new ReaderPage();
        this.reportPage = new ReportPage();
        this.topBar = BasePanel.createTitle("Trang chủ", AppFont.TITLE, AppColor.BLACK);

        setBorder(new EmptyBorder(10,10,10,10));
        setLayout(new BorderLayout());

        this.contentPanel = new BorderPanel(0,AppColor.WHITE,0,0,null,0);
        this.contentPanel.setLayout(cardLayout);

        this.contentPanel.add(homePage,PageKey.HOME.name());
        this.contentPanel.add(bookPage, PageKey.BOOKS.name());
        this.contentPanel.add(readerPage, PageKey.READER.name());
        this.contentPanel.add(loanRecordPage, PageKey.LOAN_RECORD.name());
        this.contentPanel.add(reportPage, PageKey.REPORT.name());

        add(topBar,BorderLayout.NORTH);
        add(contentPanel,BorderLayout.CENTER);

    }

    public void registerMenuAndTitle(PageKey pageKey, String title, SideBarItem item) {
        this.titleMap.put(pageKey, title);
        this.menuMap.put(pageKey, item);
    }

    public void showPage(PageKey pageKey) {
        String key = pageKey.name();
        this.topBar.setText(Lang.get(titleMap.get(pageKey)));
        this.cardLayout.show(contentPanel, key);

        if (activeItem != null) {
            activeItem.setActive(false);
        }

        activeItem = menuMap.get(pageKey);

        if (activeItem != null) {
            activeItem.setActive(true);
        }
    }

    public void refreshLang(PageKey pageKey){
        this.topBar.setText(Lang.get(titleMap.get(pageKey)));
    }
}
