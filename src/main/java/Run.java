import components.page.ContentPanel;
import components.sidebar.SideBarMain;
import registry.lang.Lang;

import javax.swing.*;
import java.awt.*;

public class Run {
    public static final int WIDTH = 1440;
    public static final int HEIGHT = 820;

    void main(){
        Lang.load("vi");

        JFrame frame = new JFrame(Lang.get("app.title"));
        frame.setSize(new Dimension(WIDTH,HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ContentPanel contentPanel = new ContentPanel();
        SideBarMain sideBarMain = new SideBarMain(contentPanel);

        frame.add(sideBarMain,BorderLayout.WEST);
        frame.add(contentPanel,BorderLayout.CENTER);
        frame.setVisible(true);

    }
}
