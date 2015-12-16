package gardens;

import java.awt.Color;
import java.util.Iterator;
import javax.swing.JFrame;

public class Main{
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Game g = new Game();
        });
        
//        JFrame f =new JFrame();
//        f.setVisible(true);
//        f.setDefaultCloseOperation(3);
//        
//        GardenModel model = new GardenModel(5,1);
//        f.add(model.grid);
//        f.pack();
//        
//        GameEditor editor = new GameEditor(5,1);
    }
}