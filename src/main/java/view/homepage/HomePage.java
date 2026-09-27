package view.homepage;

import components.BasePanel;
import components.BorderPanel;
import components.ScrollableBorderPanel;
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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends BorderPanel implements LanguageUpdatable {
    private final int Y_AXIS_PADDING = 20;
    private final int X_AXIS_PADDING = 20;

    private JLabel title;
    private JLabel sub_title;

    private JFreeChart chart;

    private TablePanel tableDueBook;

    private TablePanel tableLateFee;
    private JLabel totalLateFee;

    private TablePanel tableBookManagement;
    private JLabel labelBiewAllBookManagement;
    private BorderPanel viewAllBookManagement;

    public HomePage(){
        super(0, AppColor.LIGHT_GRAY,0,0,null,0);
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

        updateLanguage();
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

    private JPanel createBannerLeft(){
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel titleBanner = BasePanel.createTitle(Lang.get("homePage.banner.title"), AppFont.NORMAL_BOLD, AppColor.BLACK);
        titleBanner.setIcon(BasePanel.createIcon(getClass(), "/icons/sidebar/header", 20, 20));

        JLabel subBanner = BasePanel.createTitle(Lang.get("homePage.banner.quote"), AppFont.SMALL, AppColor.GRAY);

        left.add(titleBanner);
        left.add(subBanner);

        return left;
    }

    private BorderPanel createBannerButton(){
        BorderPanel btn = BasePanel.createButton(
                Lang.get("homePage.banner.action"),AppColor.WHITE,AppColor.BLUE,
                BasePanel.createIcon(getClass(),"/icons/homepage/bill",15,15)
        );

        btn.addMouseListener(new MouseAdapter() {
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

    //Phần stat sau này sửa lại
    private BorderPanel statBook = createStat("/icons/sidebar/header","Tổng số sách");

    private BorderPanel createStatPanel(){
        BorderPanel panel = new BorderPanel(0, AppColor.LIGHT_GRAY,0,0,null,0);
        panel.setLayout(new BorderLayout());
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        BorderPanel content = new BorderPanel(0, AppColor.LIGHT_GRAY,0,0,null,0);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.setLayout(new GridLayout(1,4,X_AXIS_PADDING,0));
        content.add(statBook);
        content.add(createStat("/icons/homepage/reader","Số lượng độc giả"));
        content.add(createStat("/icons/homepage/book","Lượt mượn trong tháng"));
        content.add(createStat("/icons/homepage/pay","Số lượng độc giả"));

        panel.add(content,BorderLayout.CENTER);

        return panel;
    }

    private BorderPanel createStat(String iconName,String text){
        BorderPanel stat = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        stat.setLayout(new BoxLayout(stat,BoxLayout.Y_AXIS));
        stat.setBorder(new EmptyBorder(15,15,15,15));
        stat.setAlignmentX(Component.LEFT_ALIGNMENT);
        stat.add(new JLabel(BasePanel.createIcon(getClass(),iconName,25,25)));
        stat.add(BasePanel.createTitle("Số lượng", AppFont.BIG_BOLD,AppColor.BLACK));
        stat.add(BasePanel.createTitle(text, AppFont.SMALL,AppColor.GRAY));
        stat.add(BasePanel.createTitle("Như thế nào so với tháng trước", AppFont.SMALL,AppColor.GRAY));
        return stat;
    }

    private BorderPanel createBarChartPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = BasePanel.createTitle(Lang.get("homePage.charts.borrow_by_month.title"),AppFont.BIG_BOLD,AppColor.BLACK);
        title.setIcon(BasePanel.createIcon(getClass(),"/icons/homepage/chart_icon",30,30));
        title.setIconTextGap(10);
        JLabel unit = BasePanel.createTitle(Lang.get("homePage.charts.borrow_by_month.unit"),AppFont.SMALL,AppColor.GRAY);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int[] luotMuon = {20, 30, 40, 35, 50, 45, 60, 65, 55, 70, 75, 60};
        for (int i = 0; i < luotMuon.length; i++) {
            dataset.addValue(luotMuon[i], "Lượt mượn", "T" + (i + 1));
        }

        this.chart = ChartFactory.createBarChart("", "", "Lượt mượn", dataset);
        this.chart.setBackgroundPaint(Color.WHITE);
        this.chart.getTitle().setFont(AppFont.NORMAL_BOLD);
        this.chart.getTitle().setPaint(AppColor.BLACK);
        this.chart.removeLegend();

        CategoryPlot plot = this.chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(235, 235, 235));
        plot.setDomainGridlinesVisible(false);

        RoundedBarRenderer renderer = new RoundedBarRenderer(14);
        renderer.setSeriesPaint(0, AppColor.BLUE);
        renderer.setMaximumBarWidth(0.055);
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(this.chart);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setPreferredSize(new Dimension(0, 250));

        panel.add(title,BorderLayout.NORTH);
        panel.add(chartPanel,BorderLayout.CENTER);
        panel.add(unit,BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPieChartPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,30));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        dataset.setValue(Lang.get("homePage.charts.category_stats.categories.education"), 28);
        dataset.setValue(Lang.get("homePage.charts.category_stats.categories.literature"), 22);
        dataset.setValue(Lang.get("homePage.charts.category_stats.categories.life_skill"), 18);
        dataset.setValue(Lang.get("homePage.charts.category_stats.categories.economy"), 12);
        dataset.setValue(Lang.get("homePage.charts.category_stats.categories.technology"), 10);
        dataset.setValue(Lang.get("homePage.charts.category_stats.categories.other"), 10);

        double total = 0;
        for (int i = 0; i < dataset.getItemCount(); i++) {
            total += dataset.getValue(i).doubleValue();
        }

        DonutPlot plot = new DonutPlot(
                dataset,
                (int) total + "",
                "lượt mượn",
                AppFont.BIG_BOLD,
                AppFont.SMALL,
                AppColor.BLACK,
                AppColor.GRAY
        );

        plot.setSectionDepth(0.35);          // độ dày viền donut
        plot.setSeparatorsVisible(false);
        plot.setLabelGenerator(null);        // ẩn nhãn % trên từng lát để giống hình mẫu
        plot.setOutlineVisible(false);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setShadowPaint(null);

        plot.setSectionPaint("Sách giáo khoa", AppColor.BLUE);
        plot.setSectionPaint("Văn học", new Color(76, 175, 80));
        plot.setSectionPaint("Kỹ năng sống", new Color(255, 152, 0));
        plot.setSectionPaint("Kinh tế", new Color(103, 58, 183));
        plot.setSectionPaint("Công nghệ", new Color(233, 30, 99));
        plot.setSectionPaint("Khác", new Color(200, 200, 200));

        JFreeChart chart = new JFreeChart(
                "",
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                true // có legend
        );
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(AppFont.NORMAL_BOLD);
        chart.getTitle().setPaint(AppColor.BLACK);

        LegendTitle legend = chart.getLegend();
        if (legend != null) {
            legend.setPosition(RectangleEdge.RIGHT); // legend hiện bên phải, mỗi mục có 1 ô màu
            legend.setItemFont(AppFont.SMALL);
            legend.setBackgroundPaint(Color.WHITE);
        }
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(0, 250));

        JLabel title = BasePanel.createTitle(
                Lang.get("homePage.charts.category_stats.title"),
                AppFont.BIG_BOLD,
                AppColor.BLACK,
                BasePanel.createIcon(getClass(),"/icons/homepage/pie_chart",30,30)
        );

        panel.add(title,BorderLayout.NORTH);
        panel.add(chartPanel,BorderLayout.CENTER);

        return panel;
    }

    //sau này thêm tên sách và fix lại
    private BorderPanel createTopBookPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setBackground(Color.WHITE);
        panel.add(BasePanel.createTitle(Lang.get("homePage.top_books.title"),AppFont.BIG_BOLD,AppColor.BLACK,BasePanel.createIcon(getClass(),"/icons/homepage/book",30,30)));
        panel.add(BasePanel.createTitle(
                "Sách \t55 "+Lang.get("homePage.top_books.unit"),
                AppFont.NORMAL,AppColor.BLACK,BasePanel.createIcon(getClass(),
                        "/icons/homepage/top_book",30,30,
                        AppColor.BLUE
                )
        ));

        panel.add(BasePanel.createTitle(
                "Sách \t55 "+Lang.get("homePage.top_books.unit"),
                AppFont.NORMAL,AppColor.BLACK,BasePanel.createIcon(getClass(),
                        "/icons/homepage/top_book",30,30,
                        AppColor.RED
                )
        ));

        panel.add(BasePanel.createTitle(
                "Sách \t55 "+Lang.get("homePage.top_books.unit"),
                AppFont.NORMAL,AppColor.BLACK,BasePanel.createIcon(getClass(),
                        "/icons/homepage/top_book",30,30,
                        AppColor.BLUE
                )
        ));

        panel.add(BasePanel.createTitle(
                "Sách \t55 "+Lang.get("homePage.top_books.unit"),
                AppFont.NORMAL,AppColor.BLACK,BasePanel.createIcon(getClass(),
                        "/icons/homepage/top_book",30,30,
                        AppColor.BLUE
                )
        ));


        return panel;
    }

    private BorderPanel createQuickActionPanel(){
        BorderPanel panel = new BorderPanel(16, AppColor.WHITE,10,10,AppColor.GRAY,0);
        panel.setLayout(new GridLayout(2,2,10,10));
        panel.setBorder(new EmptyBorder(10,10,10,10));
        panel.setBackground(Color.WHITE);
//
//        panel.add(new JButton("Thêm sách"));
//        panel.add(new JButton("Thêm độc giả"));
//        panel.add(new JButton("Mượn - trả"));
//        panel.add(new JButton("Báo cáo"));

        return panel;
    }

    private void settingTableDueBook(){

        CustomRenderer renderer = new CustomRenderer();
        this.tableDueBook = new TablePanel();
        JTable table = this.tableDueBook.getTable();
        int height = table.getRowHeight()*table.getRowCount()+250;
        this.tableDueBook.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        this.tableDueBook.setTitle(Lang.get("homePage.due_books.title"));
        this.tableDueBook.addColumn(0,Lang.get("homePage.due_books.columns.index"));
        this.tableDueBook.addColumn(1,Lang.get("homePage.due_books.columns.book_name"));
        this.tableDueBook.addColumn(2,Lang.get("homePage.due_books.columns.reader"));
        this.tableDueBook.addColumn(3,Lang.get("homePage.due_books.columns.due_date"));
        this.tableDueBook.addColumn(4,Lang.get("homePage.due_books.columns.status"));

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

        this.tableLateFee.setTitle(Lang.get("homePage.late_fees.title"));
        this.tableLateFee.addColumn(0,Lang.get("homePage.late_fees.columns.index"));
        this.tableLateFee.addColumn(1,Lang.get("homePage.late_fees.columns.book"));
        this.tableLateFee.addColumn(2,Lang.get("homePage.late_fees.columns.return_date"));
        this.tableLateFee.addColumn(3,Lang.get("homePage.late_fees.columns.late_days"));
        this.tableLateFee.addColumn(4,Lang.get("homePage.late_fees.columns.fee"));

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

        this.tableBookManagement.setTitle(Lang.get("homePage.book_management.title"));
        this.tableBookManagement.addColumn(0,Lang.get("homePage.book_management.columns.index"));
        this.tableBookManagement.addColumn(1,Lang.get("homePage.book_management.columns.name"));
        this.tableBookManagement.addColumn(2,Lang.get("homePage.book_management.columns.author"));
        this.tableBookManagement.addColumn(3,Lang.get("homePage.book_management.columns.category"));
        this.tableBookManagement.addColumn(4,Lang.get("homePage.book_management.columns.quantity"));
        this.tableBookManagement.addColumn(5,Lang.get("homePage.book_management.columns.status"));

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

        BorderPanel panel = new BorderPanel(16,AppColor.WHITE,0,0,null,0);
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.setLayout(new BorderLayout());
        this.viewAllBookManagement = new BorderPanel(16,AppColor.BLUE,0,0,null,0);
        this.labelBiewAllBookManagement = BasePanel.createTitle(Lang.get("homePage.book_management.actions.view_all"),AppFont.SMALL_BOLD,AppColor.WHITE);
        this.labelBiewAllBookManagement.setHorizontalAlignment(SwingConstants.CENTER);
        this.viewAllBookManagement.setMaximumSize(new Dimension(300,40));
        this.viewAllBookManagement.setPreferredSize(new Dimension(300,40));
        this.viewAllBookManagement.add(this.labelBiewAllBookManagement);
        panel.add(this.viewAllBookManagement,BorderLayout.EAST);
        renderer.applyHeader(this.tableBookManagement.getTable());
        this.tableBookManagement.setRenderer(renderer);
        this.tableBookManagement.addRow(new Object[]{"1","Demo","10/10/2022","6","25.000đ"});
        this.tableBookManagement.add(panel,BorderLayout.SOUTH);
        this.tableBookManagement.setPreferredSize(new Dimension(Integer.MAX_VALUE,height));
    }

    @Override
    public void updateLanguage() {
        this.title.setText(Lang.get("homePage.title"));
    }
}
