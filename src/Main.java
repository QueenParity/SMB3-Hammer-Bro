import javax.swing.*;

//The hammer brother movement pattern code is possible thanks to 0r4ng3, see: https://smb3.bf0.org/
public class Main
{
    public static void main(String[] args)
    {
        Gui gui = new Gui();
        gui.setSize(1200, 800);
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setVisible(true);
        gui.setResizable(false);
    }
}