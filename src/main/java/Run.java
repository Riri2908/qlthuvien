import components.sidebar.SideBarMain;

import javax.swing.*;
import java.awt.*;

public class Run {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    void main(){
        JFrame frame = new JFrame("Quản lý thư viện");
        frame.setSize(new Dimension(WIDTH,HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        SideBarMain sideBarMain = new SideBarMain();

        frame.add(sideBarMain,BorderLayout.WEST);
        frame.setVisible(true);
    }
}
