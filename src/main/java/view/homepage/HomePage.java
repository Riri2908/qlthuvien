package view.homepage;

import components.BasePanel;
import components.BorderPanel;
import components.ScrollableBorderPanel;
import components.page.ContentPanel;
import components.page.PageKey;
import components.table.CustomRenderer;
import components.table.TablePanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import registry.lang.Lang;
import theme.AppColor;
import theme.AppFont;
import view.LanguageUpdatable;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends BorderPanel implements LanguageUpdatable {
    private final int Y_AXIS_PADDING = 20;
    private final int X_AXIS_PADDING = 20;

    private final ContentPanel contentPanel;

    private JLabel title;
    private JLabel sub_title;

    private TablePanel tableDueBook;

    private TablePanel tableLateFee;
    private JLabel totalLateFee;

    private TablePanel tableBookManagement;
    private JLabel labelBiewAllBookManagement;
    private BorderPanel viewAllBookManagement;

    public HomePage(ContentPanel contentPanel){
        super(0, AppColor.LIGHT_GRAY,0,0,null,0);
        this.contentPanel = contentPanel;
        setLayout(new BorderLayout());

        ScrollableBorderPanel content = new ScrollableBorderPanel(0, AppColor.LIGHT_GRAY, 0, 0, null, 0);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(10,20,10,20));

        settingTableDueBook();
        settingTableLateFeePanel();
        settingTableBookManagement();

        content.add(createTitleSection());
        content.add(Box.createVerticalStrut(Y_AXIS_PADDING));
        content.add(createBanner());
        content.add(Box.createVerticalStrut(Y_AXIS_PADDING));
        content.add(createStatPanel());
        content.add(Box.createVerticalStrut(Y_AXIS_PADDING));
        content.add(dashBoard());
        content.add(Box.createVerticalStrut(Y_AXIS_PADDING));
        content.add(this.tableDueBook);
        content.add(Box.createVerticalStrut(Y_AXIS_PADDING));
        content.add(this.tableLateFee);
        content.add(Box.createVerticalStrut(Y_AXIS_PADDING));
        content.add(tableBookManagement);

        JScrollPane pane = BasePanel.createScroll(content);
        add(pane, BorderLayout.CENTER);
    }

    //Tạo bảng thống kê
    private BorderPanel dashBoard(){
        BorderPanel panel = new BorderPanel(0, AppColor.LIGHT_GRAY,0,0,null,0);
        panel.setLayout(new BorderLayout());
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        BorderPanel dashBoard = new BorderPanel(0, AppColor.LIGHT_GRAY,0,0,null,0);
        dashBoard.setLayout(new GridLayout(2,2,X_AXIS_PADDING,Y_AXIS_PADDING));
        dashBoard.setBorder(new EmptyBorder(10,0,10,0));
        dashBoard.setAlignmentX(Component.LEFT_ALIGNMENT);

        dashBoard.add(createBarChartPanel());
        dashBoard.add(createTopBookPanel());
        dashBoard.add(createPieChartPanel());
        dashBoard.add(createQuickActionPanel());

        panel.add(dashBoard,BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTitleSection(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.title = BasePanel.createTitle(Lang.get("homePage.title"), AppFont.TITLE, AppColor.BLACK);

        this.sub_title = BasePanel.createTitle(Lang.get("homePage.sub_title"), AppFont.SUB_TITLE, AppColor.GRAY);

        panel.add(title);
        panel.add(sub_title);

        return panel;
    }

    private BorderPanel createBanner(){
        BorderPanel banner = new BorderPanel(16 ,AppColor.LIGHT_BLUE,0,0,null,0);
        banner.setLayout(new BorderLayout());
        banner.setBorder(new EmptyBorder(10,20,10,20));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        banner.setAlignmentX(Component.LEFT_ALIGNMENT);

        banner.add(createBannerLeft(), BorderLayout.WEST);
        banner.add(createBannerButton(), BorderLayout.EAST);

        return banner;
    }

    private JLabel titleBanner;
    private JLabel subBanner;

    private JPanel createBannerLeft(){
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        this.titleBanner = BasePanel.createTitle(Lang.get("homePage.banner.title"), AppFont.NORMAL_BOLD, AppColor.BLACK);
        this.titleBanner.setIcon(BasePanel.createIcon(getClass(), "/icons/sidebar/header", 20, 20));

        this.subBanner = BasePanel.createTitle(Lang.get("homePage.banner.quote"), AppFont.SMALL, AppColor.GRAY);

        left.add(this.titleBanner);
        left.add(this.subBanner);

        return left;
    }

    private JLabel lblButtonBanner;
    private BorderPanel createBannerButton(){
        BorderPanel btn = new BorderPanel(16,AppColor.BLUE,0,0,null,0);
        btn.setLayout(new BorderLayout());
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(new EmptyBorder(10,20,10,20));

        this.lblButtonBanner = BasePanel.createTitle(
                Lang.get("homePage.banner.action"),AppFont.SMALL_BOLD,AppColor.WHITE,
                BasePanel.createIcon(getClass(),"/icons/homepage/bill",15,15)
        );
        this.lblButtonBanner.setHorizontalAlignment(SwingConstants.CENTER);

        btn.add(lblButtonBanner,BorderLayout.CENTER);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contentPanel.showPage(PageKey.LOAN_RECORD);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(AppColor.PRIMARY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(AppColor.BLUE);
            }
        });

        return btn;
    }

    private JLabel lblTotalBooksTitle, lblTotalBooksIncrease;
    private JLabel lblActiveReadersTitle, lblActiveReadersIncrease;
    private JLabel lblMonthlyBorrowsTitle, lblMonthlyBorrowsIncrease;
    private JLabel lblMonthlyReturnsTitle, lblMonthlyReturnsIncrease;

    private BorderPanel createStatPanel(){
        BorderPanel panel = new BorderPanel(0, AppColor.LIGHT_GRAY,0,0,null,0);
        panel.setLayout(new BorderLayout());
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        BorderPanel content = new BorderPanel(0, AppColor.LIGHT_GRAY,0,0,null,0);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.setLayout(new GridLayout(1,4,X_AXIS_PADDING,0));


        content.add(createStat("/icons/sidebar/header", "homePage.stats.total_books", "1200" ));

        content.add(createStat("/icons/homepage/reader", "homePage.stats.active_readers", "320"));

        content.add(createStat("/icons/homepage/book", "homePage.stats.monthly_borrows", "540"));

        content.add(createStat("/icons/homepage/pay", "homePage.stats.monthly_returns", "500"));

        panel.add(content,BorderLayout.CENTER);

        return panel;
    }

    private BorderPanel createStat(String iconName, String key, String value){
        BorderPanel stat = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        stat.setLayout(new BoxLayout(stat,BoxLayout.Y_AXIS));
        stat.setBorder(new EmptyBorder(15,15,15,15));

        JLabel icon = new JLabel(BasePanel.createIcon(getClass(),iconName,25,25));
        JLabel lblValue = BasePanel.createTitle(value, AppFont.BIG_BOLD, AppColor.BLACK);

        JLabel lblTitle = BasePanel.createTitle(Lang.get(key+".label"), AppFont.SMALL, AppColor.GRAY);
        JLabel lblIncrease = BasePanel.createTitle(Lang.get(key+".increase"), AppFont.SMALL, AppColor.GRAY);

        switch (key) {
            case "homePage.stats.total_books":
                lblTotalBooksTitle = lblTitle;
                lblTotalBooksIncrease = lblIncrease;
                break;
            case "homePage.stats.active_readers":
                lblActiveReadersTitle = lblTitle;
                lblActiveReadersIncrease = lblIncrease;
                break;
            case "homePage.stats.monthly_borrows":
                lblMonthlyBorrowsTitle = lblTitle;
                lblMonthlyBorrowsIncrease = lblIncrease;
                break;
            case "homePage.stats.monthly_returns":
                lblMonthlyReturnsTitle = lblTitle;
                lblMonthlyReturnsIncrease = lblIncrease;
                break;
        }

        stat.add(icon);
        stat.add(lblValue);
        stat.add(lblTitle);
        stat.add(lblIncrease);

        return stat;
    }

    private JLabel titleBarChart,unitBarChart;
    private List<String> monthsOfBarChart;
    private DefaultCategoryDataset datasetOfBarChart;

    private BorderPanel createBarChartPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.titleBarChart= BasePanel.createTitle(Lang.get("homePage.charts.borrow_by_month.title"),AppFont.BIG_BOLD,AppColor.BLACK);
        this.titleBarChart.setIcon(BasePanel.createIcon(getClass(),"/icons/homepage/chart_icon",30,30));
        this.titleBarChart.setIconTextGap(10);
        this.unitBarChart = BasePanel.createTitle(Lang.get("homePage.charts.borrow_by_month.unit"),AppFont.SMALL,AppColor.GRAY);

        this.datasetOfBarChart = new DefaultCategoryDataset();

        int[] luotMuon = {20, 30, 40, 35, 50, 45, 60, 65, 55, 70, 75, 60};
        this.monthsOfBarChart = Lang.getList("homePage.charts.borrow_by_month.months");
        for (int i = 0; i < luotMuon.length; i++) {
            datasetOfBarChart.addValue(luotMuon[i], "Lượt mượn", this.monthsOfBarChart.get(i));
        }

        JFreeChart chart = ChartFactory.createBarChart("", "", "Lượt mượn", datasetOfBarChart);
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(AppFont.NORMAL_BOLD);
        chart.getTitle().setPaint(AppColor.BLACK);
        chart.removeLegend();

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(235, 235, 235));
        plot.setDomainGridlinesVisible(false);

        RoundedBarRenderer renderer = new RoundedBarRenderer(14);
        renderer.setSeriesPaint(0, AppColor.BLUE);
        renderer.setMaximumBarWidth(0.055);
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setPreferredSize(new Dimension(0, 250));

        panel.add(this.titleBarChart,BorderLayout.NORTH);
        panel.add(chartPanel,BorderLayout.CENTER);
        panel.add(this.unitBarChart,BorderLayout.SOUTH);

        return panel;
    }

    private DefaultPieDataset<String> pieDataset;
    private DonutPlot piePlot;
    private JLabel titlePieChart;

    private JPanel createPieChartPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,30));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.pieDataset = new DefaultPieDataset<>();

        // set data lần đầu
        loadPieChartData();

        double total = 0;
        for (int i = 0; i < pieDataset.getItemCount(); i++) {
            total += pieDataset.getValue(i).doubleValue();
        }

        this.piePlot = new DonutPlot(
                pieDataset,
                (int) total + "",
                Lang.get("homePage.charts.category_stats.total"), // 🔥 fix đa ngôn ngữ
                AppFont.BIG_BOLD,
                AppFont.SMALL,
                AppColor.BLACK,
                AppColor.GRAY
        );

        piePlot.setSectionDepth(0.35);
        piePlot.setSeparatorsVisible(false);
        piePlot.setLabelGenerator(null);
        piePlot.setOutlineVisible(false);
        piePlot.setBackgroundPaint(Color.WHITE);
        piePlot.setShadowPaint(null);

        applyPieChartColor();

        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, piePlot, true);
        chart.setBackgroundPaint(Color.WHITE);

        LegendTitle legend = chart.getLegend();
        if (legend != null) {
            legend.setPosition(RectangleEdge.RIGHT);
            legend.setItemFont(AppFont.SMALL);
            legend.setBackgroundPaint(Color.WHITE);
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(0, 250));

        this.titlePieChart = BasePanel.createTitle(
                Lang.get("homePage.charts.category_stats.title"),
                AppFont.BIG_BOLD,
                AppColor.BLACK,
                BasePanel.createIcon(getClass(),"/icons/homepage/pie_chart",30,30)
        );

        panel.add(this.titlePieChart,BorderLayout.NORTH);
        panel.add(chartPanel,BorderLayout.CENTER);

        return panel;
    }

    private void loadPieChartData(){
        pieDataset.clear();

        pieDataset.setValue(Lang.get("homePage.charts.category_stats.categories.education"), 28);
        pieDataset.setValue(Lang.get("homePage.charts.category_stats.categories.literature"), 22);
        pieDataset.setValue(Lang.get("homePage.charts.category_stats.categories.life_skill"), 18);
        pieDataset.setValue(Lang.get("homePage.charts.category_stats.categories.economy"), 12);
        pieDataset.setValue(Lang.get("homePage.charts.category_stats.categories.technology"), 10);
        pieDataset.setValue(Lang.get("homePage.charts.category_stats.categories.other"), 10);
    }

    private void applyPieChartColor(){
        piePlot.setSectionPaint(Lang.get("homePage.charts.category_stats.categories.education"), AppColor.BLUE);
        piePlot.setSectionPaint(Lang.get("homePage.charts.category_stats.categories.literature"), new Color(76, 175, 80));
        piePlot.setSectionPaint(Lang.get("homePage.charts.category_stats.categories.life_skill"), new Color(255, 152, 0));
        piePlot.setSectionPaint(Lang.get("homePage.charts.category_stats.categories.economy"), new Color(103, 58, 183));
        piePlot.setSectionPaint(Lang.get("homePage.charts.category_stats.categories.technology"), new Color(233, 30, 99));
        piePlot.setSectionPaint(Lang.get("homePage.charts.category_stats.categories.other"), new Color(200, 200, 200));
    }

    //sau này thêm tên sách và fix lại
    private JLabel titleTopBook;
    private final List<JLabel> topBookItems = new ArrayList<>();
    private BorderPanel createTopBookPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setBackground(Color.WHITE);

        // Title
        this.titleTopBook = BasePanel.createTitle(
                Lang.get("homePage.top_books.title"),
                AppFont.BIG_BOLD,
                AppColor.BLACK,
                BasePanel.createIcon(getClass(),"/icons/homepage/book",30,30)
        );
        panel.add(this.titleTopBook);
        panel.add(Box.createVerticalStrut(10));

        // Demo data
        int[] values = {55, 40, 35, 30};
        Color[] colors = {AppColor.BLUE, AppColor.RED, AppColor.BLUE, AppColor.BLUE};

        for (int i = 0; i < values.length; i++) {
            JLabel item = BasePanel.createTitle(
                    "", // set sau
                    AppFont.NORMAL,
                    AppColor.BLACK,
                    BasePanel.createIcon(getClass(),"/icons/homepage/top_book",30,30, colors[i])
            );

            topBookItems.add(item);
            panel.add(item);
            panel.add(Box.createVerticalStrut(10));
        }

        // set text lần đầu
        updateTopBookText(values);

        return panel;
    }

    private void updateTopBookText(int[] values){
        for (int i = 0; i < topBookItems.size(); i++) {
            JLabel item = topBookItems.get(i);

            item.setText(
                    "Sách"
                            + " " + values[i] + " " +
                            Lang.get("homePage.top_books.unit")
            );
        }
    }

    private JLabel titleQuickAction;
    private JLabel lblAddBook;
    private JLabel lblAddReader;
    private JLabel lblLoanRecord;
    private JLabel lblReport;

    private JLabel lblRuleTitle;
    private JLabel lblRuleItems1;
    private JLabel lblRuleItems2;
    private JLabel lblRuleItems3;

    private BorderPanel createQuickActionPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        this.lblAddBook = BasePanel.createTitle(
                Lang.get("homePage.quick_actions.add_book"),AppFont.NORMAL_BOLD,AppColor.WHITE,
                BasePanel.createIcon(getClass(),"/icons/homepage/btn_add_book",30,30)
        );

        this.lblAddReader = BasePanel.createTitle(
                Lang.get("homePage.quick_actions.add_reader"),AppFont.NORMAL_BOLD,AppColor.WHITE,
                BasePanel.createIcon(getClass(),"/icons/homepage/btn_add_reader",30,30)
        );

        this.lblLoanRecord = BasePanel.createTitle(
                Lang.get("homePage.quick_actions.loan_record"),AppFont.NORMAL_BOLD,AppColor.DARK_BLUE,
                BasePanel.createIcon(getClass(),"/icons/homepage/btn_add_loan_record",30,30)
        );

        this.lblReport = BasePanel.createTitle(
                Lang.get("homePage.quick_actions.report"),AppFont.NORMAL_BOLD,AppColor.DARK_BLUE,
                BasePanel.createIcon(getClass(),"/icons/homepage/btn_add_report",30,30)
        );

        BorderPanel btnPanel = new BorderPanel(16,AppColor.WHITE,0,0, null,0);
        btnPanel.setLayout(new GridLayout(2,2,10,10));
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnPanel.add(createButtonQuickAction(AppColor.BLUE,this.lblAddBook));
        btnPanel.add(createButtonQuickAction(AppColor.BLUE,this.lblAddReader));
        btnPanel.add(createButtonQuickAction(AppColor.LIGHT_BLUE,this.lblLoanRecord));
        btnPanel.add(createButtonQuickAction(AppColor.LIGHT_BLUE,this.lblReport));


        List<String> rules = Lang.getList("homePage.rules.items");
        this.lblRuleTitle = BasePanel.createTitle(
                Lang.get("homePage.rules.title"),AppFont.BIG_BOLD,AppColor.BLACK,
                BasePanel.createIcon(getClass(),"/icons/homepage/pin",20,20)
        );
        this.lblRuleItems1 = BasePanel.createTitle(rules.get(0),AppFont.SMALL,AppColor.GRAY);
        this.lblRuleItems2 = BasePanel.createTitle(rules.get(1),AppFont.SMALL,AppColor.GRAY);
        this.lblRuleItems3 = BasePanel.createTitle(rules.get(2),AppFont.SMALL,AppColor.GRAY);

        this.titleQuickAction = BasePanel.createTitle(
                Lang.get("homePage.quick_actions.title"),AppFont.BIG_BOLD,AppColor.BLACK,
                BasePanel.createIcon(getClass(),"/icons/homepage/flash",30,30)
        );

        panel.add(this.titleQuickAction);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnPanel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(this.lblRuleTitle);
        panel.add(Box.createVerticalStrut(10));
        panel.add(this.lblRuleItems1);
        panel.add(Box.createVerticalStrut(10));
        panel.add(this.lblRuleItems2);
        panel.add(Box.createVerticalStrut(10));
        panel.add(this.lblRuleItems3);

        return panel;
    }

    private BorderPanel createButtonQuickAction(Color background,JLabel label){
        BorderPanel btn = new BorderPanel(16,background,0,0, null,0,new Cursor(Cursor.HAND_CURSOR));
        btn.setLayout(new GridLayout(1,1));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        btn.add(label,BorderLayout.CENTER);
        return btn;
    }

    private void settingTableDueBook(){

        CustomRenderer renderer = new CustomRenderer();
        this.tableDueBook = new TablePanel();
        JTable table = this.tableDueBook.getTable();
        int height = table.getRowHeight()*table.getRowCount()+250;
        this.tableDueBook.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        this.tableDueBook.setTitle(
                Lang.get("homePage.due_books.title"),
                BasePanel.createIcon(getClass(),"/icons/homepage/date_book",35,35)
        );
        this.tableDueBook.addColumn(0,"homePage.due_books.columns.index");
        this.tableDueBook.addColumn(1,"homePage.due_books.columns.book_name");
        this.tableDueBook.addColumn(2,"homePage.due_books.columns.reader");
        this.tableDueBook.addColumn(3,"homePage.due_books.columns.due_date");
        this.tableDueBook.addColumn(4,"homePage.due_books.columns.status");

        renderer.setHeaderSetting(label  -> {
            label.setBackground(AppColor.LIGHT_GRAY);
            label.setForeground(AppColor.GRAY);
            label.setFont(AppFont.SMALL_BOLD);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label ;
        });

        renderer.setDefaultSetting((label,_) ->{
            label.setForeground(AppColor.BLACK);
            label.setFont(AppFont.SMALL);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(0,0,1,0,AppColor.BORDER_TABLE));
            return label;
        });

        renderer.addLabel(4,((label, _,_) -> {
            BorderPanel panel = new BorderPanel(0, AppColor.WHITE,0,0,null,0);
            panel.setBorder(new EmptyBorder(10,10,10,10));
            panel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,AppColor.BORDER_TABLE));

            BorderPanel content = new BorderPanel(30, AppColor.WARNING,0,0,AppColor.GRAY,0);
            content.setLayout(new BorderLayout());
            content.setBorder(new EmptyBorder(5,10,5,10));
            JLabel text =BasePanel.createTitle(label.getText(),AppFont.SMALL,AppColor.ORANGE);
            text.setHorizontalAlignment(SwingConstants.CENTER);
            content.add(text,BorderLayout.CENTER);

            panel.add(content);
            return panel;
        }));

        renderer.applyHeader(this.tableDueBook.getTable());
        this.tableDueBook.setRenderer(renderer);
        this.tableDueBook.addRow(new Object[]{"1","Demo","Nguyễn văn A","10/10/2022","Sắp đến hạn"});
        this.tableDueBook.setPreferredSize(new Dimension(Integer.MAX_VALUE,height));
    }

    private void settingTableLateFeePanel(){

        CustomRenderer renderer = new CustomRenderer();
        this.tableLateFee = new TablePanel();
        JTable table = this.tableLateFee.getTable();
        int height = table.getRowHeight()*table.getRowCount()+250;
        this.tableLateFee.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.tableLateFee.setTitle(
                Lang.get("homePage.late_fees.title"),
                BasePanel.createIcon(getClass(),"/icons/homepage/late_fee",35,35)
        );
        this.tableLateFee.addColumn(0,"homePage.late_fees.columns.index");
        this.tableLateFee.addColumn(1,"homePage.late_fees.columns.book");
        this.tableLateFee.addColumn(2,"homePage.late_fees.columns.return_date");
        this.tableLateFee.addColumn(3,"homePage.late_fees.columns.late_days");
        this.tableLateFee.addColumn(4,"homePage.late_fees.columns.fee");

        renderer.setHeaderSetting(label  -> {
            label.setBackground(AppColor.LIGHT_GRAY);
            label.setForeground(AppColor.GRAY);
            label.setFont(AppFont.SMALL_BOLD);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label ;
        });

        renderer.setDefaultSetting((label,_) ->{
            label.setForeground(AppColor.BLACK);
            label.setFont(AppFont.SMALL);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label;
        });

        renderer.addLabel(4,((label, _,_) -> {

            label.setForeground(AppColor.RED);
            label.setFont(AppFont.SMALL_BOLD);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            return label;
        }));

        renderer.applyHeader(this.tableLateFee.getTable());

        this.totalLateFee = BasePanel.createTitle(Lang.get("homePage.late_fees.total"),AppFont.NORMAL_BOLD,AppColor.BLACK);
        this.totalLateFee.setHorizontalAlignment(SwingConstants.RIGHT);

        this.tableLateFee.setRenderer(renderer);
        this.tableLateFee.addRow(new Object[]{"1","Demo","10/10/2022","6","25.000đ"});
        this.tableLateFee.add(this.totalLateFee,BorderLayout.SOUTH);
        this.tableLateFee.setPreferredSize(new Dimension(Integer.MAX_VALUE,height));
    }

    private void settingTableBookManagement(){

        CustomRenderer renderer = new CustomRenderer();
        this.tableBookManagement = new TablePanel();
        JTable table = this.tableBookManagement.getTable();
        int height = table.getRowHeight()*table.getRowCount()+250;
        this.tableBookManagement.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.tableBookManagement.setTitle(
                Lang.get("homePage.book_management.title"),
                BasePanel.createIcon(getClass(),"/icons/homepage/book_management",35,35)
        );
        this.tableBookManagement.addColumn(0,"homePage.book_management.columns.index");
        this.tableBookManagement.addColumn(1,"homePage.book_management.columns.name");
        this.tableBookManagement.addColumn(2,"homePage.book_management.columns.author");
        this.tableBookManagement.addColumn(3,"homePage.book_management.columns.category");
        this.tableBookManagement.addColumn(4,"homePage.book_management.columns.quantity");
        this.tableBookManagement.addColumn(5,"homePage.book_management.columns.status");

        renderer.setHeaderSetting(label  -> {
            label.setBackground(AppColor.LIGHT_GRAY);
            label.setForeground(AppColor.GRAY);
            label.setFont(AppFont.SMALL_BOLD);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label ;
        });

        renderer.setDefaultSetting((label,_) ->{
            label.setForeground(AppColor.BLACK);
            label.setFont(AppFont.SMALL);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            label.setBorder(BorderFactory.createMatteBorder(0,0,1,0,AppColor.BORDER_TABLE));
            return label;
        });

        renderer.addLabel(5,((label, _,_) -> {

            BorderPanel panel = new BorderPanel(0, AppColor.WHITE,0,0,null,0);
            panel.setBorder(new EmptyBorder(10,10,10,10));
            panel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,AppColor.BORDER_TABLE));

            BorderPanel content = new BorderPanel(30, AppColor.LIGHT_GREEN,0,0,AppColor.GRAY,0);
            content.setLayout(new BorderLayout());
            content.setBorder(new EmptyBorder(5,10,5,10));
            JLabel text =BasePanel.createTitle(label.getText(),AppFont.SMALL_BOLD,AppColor.GREEN);
            text.setHorizontalAlignment(SwingConstants.CENTER);
            content.add(text,BorderLayout.CENTER);

            panel.add(content);
            return panel;
        }));



        BorderPanel panel = new BorderPanel(16,AppColor.WHITE,0,0,null,0);
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.setLayout(new BorderLayout());
        this.viewAllBookManagement = new BorderPanel(16,AppColor.BLUE,0,0,null,0);
        this.labelBiewAllBookManagement = BasePanel.createTitle(Lang.get("homePage.book_management.actions.view_all"),AppFont.SMALL_BOLD,AppColor.WHITE);
        this.labelBiewAllBookManagement.setHorizontalAlignment(SwingConstants.CENTER);
        this.labelBiewAllBookManagement.setBorder(new EmptyBorder(5,10,5,10));

        this.viewAllBookManagement.add(this.labelBiewAllBookManagement);
        panel.add(this.viewAllBookManagement,BorderLayout.EAST);
        renderer.applyHeader(this.tableBookManagement.getTable());
        this.tableBookManagement.setRenderer(renderer);
        this.tableBookManagement.addRow(new Object[]{"1","Demo","Nguyễn Văn A","Văn học","25","Còn"});
        this.tableBookManagement.add(panel,BorderLayout.SOUTH);
        this.tableBookManagement.setPreferredSize(new Dimension(Integer.MAX_VALUE,height));

        this.viewAllBookManagement.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.viewAllBookManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewAllBookManagement.setBackground(AppColor.BLUE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                viewAllBookManagement.setBackground(AppColor.DARK_BLUE);
            }
        });
    }

    @Override
    public void updateLanguage() {

        // 1. TITLE SECTION
        // Header của trang (title + subtitle)
        this.title.setText(Lang.get("homePage.title"));
        this.sub_title.setText(Lang.get("homePage.sub_title"));


        // 2. BANNER
        // Banner top: title + quote + button
        this.lblButtonBanner.setText(Lang.get("homePage.banner.action"));
        this.titleBanner.setText(Lang.get("homePage.banner.title"));
        this.subBanner.setText(Lang.get("homePage.banner.quote"));


        //  3. STAT PANEL
        // 4 ô thống kê
        if (lblTotalBooksTitle != null) {
            lblTotalBooksTitle.setText(Lang.get("homePage.stats.total_books.label"));
            lblTotalBooksIncrease.setText(Lang.get("homePage.stats.total_books.increase"));
        }

        if (lblActiveReadersTitle != null) {
            lblActiveReadersTitle.setText(Lang.get("homePage.stats.active_readers.label"));
            lblActiveReadersIncrease.setText(Lang.get("homePage.stats.active_readers.increase"));
        }

        if (lblMonthlyBorrowsTitle != null) {
            lblMonthlyBorrowsTitle.setText(Lang.get("homePage.stats.monthly_borrows.label"));
            lblMonthlyBorrowsIncrease.setText(Lang.get("homePage.stats.monthly_borrows.increase"));
        }

        if (lblMonthlyReturnsTitle != null) {
            lblMonthlyReturnsTitle.setText(Lang.get("homePage.stats.monthly_returns.label"));
            lblMonthlyReturnsIncrease.setText(Lang.get("homePage.stats.monthly_returns.increase"));
        }


        //  4. BAR CHART
        // Chart mượn theo tháng
        if (titleBarChart != null) {
            titleBarChart.setText(Lang.get("homePage.charts.borrow_by_month.title"));
        }

        if (unitBarChart != null) {
            unitBarChart.setText(Lang.get("homePage.charts.borrow_by_month.unit"));
        }

        // Update dataset
        if (datasetOfBarChart != null) {
            datasetOfBarChart.clear();

            int[] luotMuon = {20, 30, 40, 35, 50, 45, 60, 65, 55, 70, 75, 60};

            this.monthsOfBarChart = Lang.getList("homePage.charts.borrow_by_month.months");

            for (int i = 0; i < luotMuon.length; i++) {
                datasetOfBarChart.addValue(
                        luotMuon[i],
                        Lang.get("homePage.charts.borrow_by_month.series"),
                        this.monthsOfBarChart.get(i)
                );
            }
        }


        // 5. QUICK ACTION
        if (this.titleQuickAction != null) {
            this.titleQuickAction.setText(Lang.get("homePage.quick_actions.title"));
        }

        if (this.lblAddBook != null) {
            this.lblAddBook.setText(Lang.get("homePage.quick_actions.add_book"));
        }

        if (this.lblAddReader != null) {
            this.lblAddReader.setText(Lang.get("homePage.quick_actions.add_reader"));
        }

        if (this.lblLoanRecord != null) {
            this.lblLoanRecord.setText(Lang.get("homePage.quick_actions.loan_record"));
        }

        if (this.lblReport != null) {
            this.lblReport.setText(Lang.get("homePage.quick_actions.report"));
        }


        //6. RULES LIBRARY
        // Nội quy thư viện
        if (this.lblRuleTitle != null) {
            this.lblRuleTitle.setText(Lang.get("homePage.rules.title"));
        }

        List<String> rules = Lang.getList("homePage.rules.items");

        if (rules.size() >= 3) {
            if (lblRuleItems1 != null) lblRuleItems1.setText(rules.get(0));
            if (lblRuleItems2 != null) lblRuleItems2.setText(rules.get(1));
            if (lblRuleItems3 != null) lblRuleItems3.setText(rules.get(2));
        }


        //7. TABLE: DUE BOOK
        if (this.tableDueBook != null) {
            this.tableDueBook.setTitle(
                    Lang.get("homePage.due_books.title"),
                    BasePanel.createIcon(getClass(), "/icons/homepage/date_book", 35, 35)
            );
            this.tableDueBook.updateHeaderLanguage();

        }

        //8. TABLE: LATE FEE
        if (this.tableLateFee != null) {
            this.tableLateFee.setTitle(
                    Lang.get("homePage.late_fees.title"),
                    BasePanel.createIcon(getClass(), "/icons/homepage/late_fee", 35, 35)
            );

            this.tableLateFee.updateHeaderLanguage();
        }

        if (this.totalLateFee != null) {
            this.totalLateFee.setText(Lang.get("homePage.late_fees.total"));
        }


        //9. TABLE: BOOK MANAGEMENT
        if (this.tableBookManagement != null) {
            this.tableBookManagement.setTitle(
                    Lang.get("homePage.book_management.title"),
                    BasePanel.createIcon(getClass(), "/icons/homepage/book_management", 35, 35)
            );

            this.tableBookManagement.updateHeaderLanguage();
        }

        if (this.labelBiewAllBookManagement != null) {
            this.labelBiewAllBookManagement.setText(
                    Lang.get("homePage.book_management.actions.view_all")
            );
        }
        // 10. PIE CHART
        if (titlePieChart != null) {
            titlePieChart.setText(Lang.get("homePage.charts.category_stats.title"));
        }

        if (pieDataset != null && piePlot != null) {
            loadPieChartData();      // update label dataset
            applyPieChartColor();    // update màu đúng label mới

            // update total + unit giữa donut
            double total = 0;
            for (int i = 0; i < pieDataset.getItemCount(); i++) {
                total += pieDataset.getValue(i).doubleValue();
            }

            piePlot.setCenterText((int) total  + Lang.get("homePage.charts.category_stats.unit"));
        }

        //11. TOP BOOK
        if (titleTopBook != null) {
            titleTopBook.setText(Lang.get("homePage.top_books.title"));
        }

        if (!topBookItems.isEmpty()) {
            int[] values = {55, 40, 35, 30}; // hoặc lấy từ DB
            updateTopBookText(values);
        }
    }
}
