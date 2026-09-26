package view;

import components.BorderPanel;
import theme.AppColor;

import javax.swing.*;

public class BooksPage extends BorderPanel implements LanguageUpdatable{
    public BooksPage(){
        super(0, AppColor.WHITE,0,0,null,0);
        add(new JLabel("Sách"));
    }

    @Override
    public void updateLanguage() {
    }
}
