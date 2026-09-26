package view;

import components.BorderPanel;
import theme.AppColor;

import javax.swing.*;

public class ReaderPage extends BorderPanel implements LanguageUpdatable{
    public ReaderPage(){
        super(0, AppColor.WHITE,0,0,null,0);
        add(new JLabel("độc giả"));
    }

    @Override
    public void updateLanguage() {
    }
}
