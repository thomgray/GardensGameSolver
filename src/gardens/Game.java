package gardens;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class Game extends JFrame{
    
    JMenuBar menubar;
    JMenu menu;
    JMenuItem newGameItem;
    JMenuItem solveItem;
    
    JLabel treeCount;
    
    GardenModel model;
    
    public Game(){
        
        arrangeMenuBar();
        
        //ititalise the model first
        model = Level.level_112_NY.getLevel();
        
        treeCount = new JLabel("Trees: "+model.trees);
        
        this.setLayout(new GridBagLayout());
        arrangeComponents();
        
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.pack();
               
    }

    private void arrangeComponents() {
        GridBagConstraints cs = new GridBagConstraints();
        cs.gridx=1;
        cs.anchor=GridBagConstraints.BASELINE_TRAILING;
        this.add(treeCount,cs);
        
        cs.anchor = GridBagConstraints.CENTER;
        cs.gridy=1; cs.gridx=0;
        cs.gridwidth=2;
        this.add(model.grid, cs);
        
        this.pack();
    }

    private void arrangeMenuBar() {
        menubar = new JMenuBar();
        menu = new JMenu("Game");
        newGameItem = new JMenuItem("New Game");
        solveItem = new JMenuItem("Solve");
        menu.add(newGameItem); menu.add(solveItem);
        menubar.add(menu);
        this.setJMenuBar(menubar);
        
        newGameItem.addActionListener((ActionEvent e) ->{
            launchEditor();
        });
        
        solveItem.addActionListener((ActionEvent e)->{
            solve();
        });
    }
    
    public void launchEditor(){
        GameEditor editor = new GameEditor(this.model.size, this.model.trees);
        editor.done.addActionListener((ActionEvent e)->{
            GardenModel newmod =  new GardenModel(editor.size, editor.trees);
            newmod.copyGardens(editor.gardens);
            editor.dispose();
            
            setModel(newmod);
        });
    }
    
    public void setModel(GardenModel mod){
        
        if(model!=null) this.remove(model.grid);
        this.model = mod;
        for (Cell c : mod.grid) {
            if (model.getGardenOfCell(c)==null){
                c.setState(State.Void);
            }
        }
        
        treeCount.setText("Trees: "+model.trees);
        
        this.arrangeComponents();
        
        
    }

    public int getProgressMax(){
        return ((int)Math.pow(this.model.size, 2));
    }
    
    JProgressBar progbar;
    
    private void solve() {
        Solver s = new Solver(this.model);
        
//        if (progbar==null) progbar = new JProgressBar(0,100);
//        progbar.setMaximum(s.getMaxProgress());
//        
//        JFrame f = new JFrame();
//        f.setVisible(true);
//        
//        progbar.setVisible(true);
//        
////        GridBagConstraints cs = new GridBagConstraints();
////        cs.anchor=GridBagConstraints.BASELINE_LEADING;
////        cs.gridx=0; cs.gridy=2;
////        cs.gridwidth=2;
////        this.add(progbar, cs);
////        this.pack();
//        
//        progbar.setValue(0);
//        progbar.setStringPainted(true);
//        
//        f.add(progbar);
//        f.pack();
        
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        //s.execute();
//        s.addPropertyChangeListener((evt) -> {
//            System.out.println("prog report");
//            if (evt.getPropertyName()=="progress"){
//                int prog = s.getProgress();
//                progbar.setValue(prog);
//            }
//        });
        //s.execute();
        
        s.solve();
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        this.remove(progbar);
        this.pack();
    }

}

class Test extends SwingWorker<Void,Void>{

    
    @Override
    protected Void doInBackground() throws Exception {
        int progress=0;
        
        while (progress<100) {
            System.out.println("progrss "+progress);
            Thread.sleep(500);
            progress+=10;
            this.setProgress(progress);
        }
        return null;
    }

    @Override
    protected void done() {
        Toolkit.getDefaultToolkit().beep();
    }
    
}