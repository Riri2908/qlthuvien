package components.table;

import components.BorderPanel;
import components.BasePanel;
import theme.AppFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class TablePanel extends BorderPanel {
    public DefaultTableCellRenderer renderer;
    public Map<Integer,String> column= new HashMap<>();

    private final JLabel titleLabel;
    private int hoveredRow = -1;
    private int hoveredCol = -1;
    private int hoveredX = -1;

    public JTable table;
    private final DefaultTableModel model;

    public TablePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10,10,10,10));

        titleLabel = BasePanel.createTitle("", AppFont.TITLE, Color.BLACK);
        titleLabel.setBorder(new EmptyBorder(20,20,20,20));
        add(titleLabel, BorderLayout.NORTH);
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(
                        super.getPreferredSize().width,
                        getRowHeight() * getRowCount()
                );
            }
        };

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );
        table.setRowHeight(45);
        table.setFont(AppFont.NORMAL);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(Color.WHITE);

        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hoveredRow = table.rowAtPoint(e.getPoint());
                hoveredCol = table.columnAtPoint(e.getPoint());
                hoveredX = e.getPoint().x;
                table.repaint();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                hoveredCol = -1;
                hoveredX = -1;
                table.repaint();
            }
        });

        table.setDefaultRenderer(Object.class,renderer);

        JScrollPane scroll = BasePanel.createScroll(table);

        scroll.setOpaque(false);
        scroll.setBorder(new EmptyBorder(0, 1, 10, 4));
        scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);

        scroll.addMouseWheelListener(e -> {
            Component parent = scroll.getParent();

            while (parent != null && !(parent instanceof JScrollPane)) {
                parent = parent.getParent();
            }

            if (parent != null) {
                parent.dispatchEvent(
                        SwingUtilities.convertMouseEvent(scroll, e, parent)
                );
            }

            e.consume(); // chặn scroll của table
        });

    }
    public void addColumn(int order, String name){
        column.put(order,name);
        model.addColumn(name);
    }

    public void addColumn(int order, String name, int width) {

        column.put(order, name);
        model.addColumn(name);

        TableColumn tableColumn = table.getColumnModel().getColumn(order);

        tableColumn.setPreferredWidth(width);
        tableColumn.setMinWidth(width);
        tableColumn.setMaxWidth(width);
    }
    public Map<Integer,String> getColumn(){
        return column;
    }

    public void setTitle(String title){
        titleLabel.setText(title);
    }
    public void setTitle(String title,Icon icon){
        titleLabel.setText(title);
        titleLabel.setIcon(icon);
        titleLabel.setIconTextGap(10);
    }

    public JLabel getTitle(){
        return titleLabel;
    }

    public void setRenderer(DefaultTableCellRenderer renderer){
        this.renderer = renderer;
        this.table.setDefaultRenderer(Object.class, renderer);
    }
    public DefaultTableCellRenderer getRenderer(){
        return renderer;
    }

    public void addRow(Object[] row){
        model.addRow(row);
    }

    public DefaultTableModel getModel(){
        return this.model;
    }
    public JTable getTable(){
        return table;
    }

    public void setRowSorter(RowSorter<? extends TableModel> sorter){
        table.setRowSorter(sorter);
    }

    public int getHoveredRow(){
        return this.hoveredRow;
    }
    public int getHoveredCol() {
        return this.hoveredCol;
    }
    public int getHoveredX() {
        return this.hoveredX;
    }

    public List<Object[]> getAllRows(){

        List<Object[]> rows = new java.util.ArrayList<>();

        DefaultTableModel model = getModel();

        for(int r = 0; r < model.getRowCount(); r++){

            Object[] row = new Object[model.getColumnCount()];

            for(int c = 0; c < model.getColumnCount(); c++){
                row[c] = model.getValueAt(r,c);
            }

            rows.add(row);
        }

        return rows;
    }

    public Object[] getRowData(int viewRowIndex){

        if(viewRowIndex < 0) return null;

        int modelRow = table.convertRowIndexToModel(viewRowIndex);

        DefaultTableModel model = getModel();

        Object[] data = new Object[model.getColumnCount()];

        for(int i = 0; i < data.length; i++){
            data[i] = model.getValueAt(modelRow, i);
        }

        return data;
    }

    public Object[] getSelectedRow(){
        return getRowData(table.getSelectedRow());
    }

}
