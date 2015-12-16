package tatami;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Frame extends JFrame{

    Tatami game;
    JMenuBar menubar;
    JMenu menu;
    JMenuItem solve;
    
    public Frame(){
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        
        game = Levels.level_1.getLevel();
        init();
    }
    
    private void init(){
        menubar = new JMenuBar();
        this.setJMenuBar(menubar);
        
        menu = new JMenu("Game");
        
        solve = new JMenuItem("Solve");
        menu.add(solve);
        menubar.add(menu);
        
        solve.addActionListener((ActionEvent e) -> {
            Solver solver = new Solver(game);
            solver.solve();
        });
        
        this.add(game.grid);
        this.pack();
    }
    
    
    
}