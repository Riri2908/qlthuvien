package components.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CustomRenderer extends DefaultTableCellRenderer {

    private final Map<Integer, BiFunction<JLabel,Integer,Component>> renderers = new HashMap<>();
    private BiFunction<JLabel,Integer,JLabel> defaultSetting;
    private Function<JLabel, JLabel> headerSetting;
    private final Map<Integer, CellRendererFunction> valueRenderers = new HashMap<>();

    @Override
    public Component getTableCellRendererComponent(
            JTable table,Object value,boolean isSelected,
            boolean hasFocus,int row,int column){

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table,value,false,false,row,column);

        if(defaultSetting != null){
            label = defaultSetting.apply(label,row);
        }

        CellRendererFunction valueRenderer = valueRenderers.get(column);
        if(valueRenderer != null){
            return valueRenderer.apply(label,value,row);
        }

        BiFunction<JLabel,Integer,Component> renderer = renderers.get(column);
        if(renderer != null){
            return renderer.apply(label,row);
        }

        return label;
    }

    public void addLabel(int column, BiFunction<JLabel,Integer,Component> renderer){
        renderers.put(column,renderer);
    }

    public void addLabel(int column, CellRendererFunction renderer){
        valueRenderers.put(column,renderer);
    }

    public void setDefaultSetting(BiFunction<JLabel,Integer,JLabel> setting){
        this.defaultSetting = setting;
    }

    public void setHeaderSetting(Function<JLabel, JLabel> setting){
        this.headerSetting = setting;
    }

    public void applyHeader(JTable table){
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, false, false, row, column);

                if(headerSetting != null){
                    label = headerSetting.apply(label);
                }

                return label;
            }
        };

        table.getTableHeader().setDefaultRenderer(headerRenderer);
    }

    @FunctionalInterface
    public interface CellRendererFunction {
        Component apply(JLabel label, Object value, int row);
    }
}