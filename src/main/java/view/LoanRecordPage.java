package view;

import components.BasePanel;
import components.BorderPanel;
import registry.lang.Lang;
import theme.AppColor;

import javax.swing.*;

public class LoanRecordPage extends BorderPanel implements LanguageUpdatable {
    private JLabel title;
    public LoanRecordPage(){
        super(0, AppColor.WHITE,0,0,null,0);
        this.title = new JLabel();
        add(title);
    }

    @Override
    public void updateLanguage() {
        this.title.setText(Lang.get("menu.loanRecord"));
    }
}
