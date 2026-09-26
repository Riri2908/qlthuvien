package view;

import components.BorderPanel;
import theme.AppColor;

import javax.swing.*;

public class HomePage extends BorderPanel implements LanguageUpdatable {
    public HomePage(){
        super(0, AppColor.WHITE,0,0,null,0);
        add(new JLabel("Trang chủ"));
    }

    @Override
    public void updateLanguage() {
    }
}
