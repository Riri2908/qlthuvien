package view;

import components.BorderPanel;
import theme.AppColor;

import javax.swing.*;

public class ReportPage extends BorderPanel implements LanguageUpdatable{
    public ReportPage(){
        super(0, AppColor.WHITE,0,0,null,0);
        add(new JLabel("Báo cáo"));
    }

    @Override
    public void updateLanguage() {
    }
}
