package gardens;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

public class GameEditor extends JFrame{

    int size;
    int trees;
    Grid grid;
    ArrayList<Garden> gardens;
    
    JComboBox<Garden> gardenBox;
    JComboBox<Integer> sizeBox;
    JComboBox<Integer> treeBox;
    
    JLabel gardenLabel, sizeLabel, treeLabel;
    
    JButton done;
    
    KeyListener defaultListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getExtendedKeyCode()==KeyEvent.VK_TAB){
                gardenBox.requestFocus();
                for (KeyListener klist : gardenBox.getKeyListeners()) {
                    klist.keyTyped(e);
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };
    
    public GameEditor(int size, int trees) {
        this.size=size; this.trees=trees;
        
        this.grid = new Grid(size);
        setNewMouseListener(grid);
        
        gardenBox = new JComboBox();
        gardenBox.setFocusTraversalKeysEnabled(false);
        gardenBox.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getExtendedKeyCode()==KeyEvent.VK_TAB){
                    int x = gardenBox.getSelectedIndex();
                    if (x<gardenBox.getItemCount()-1) x++;
                    else x=0;
                    
                    gardenBox.setSelectedIndex(x);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        sizeBox = new JComboBox();
        sizeBox.setFocusTraversalKeysEnabled(false);
        sizeBox.addKeyListener(defaultListener);
        treeBox = new JComboBox();
        treeBox.setFocusTraversalKeysEnabled(false);
        treeBox.addKeyListener(defaultListener);
        
        treeLabel = new JLabel("Trees");
        gardenLabel = new JLabel("Garden");
        sizeLabel = new JLabel("Size");
        
        done = new JButton("Done");
        done.setFocusTraversalKeysEnabled(false);
        done.addKeyListener(defaultListener);
        
        initGardens();
        
        for (int i = 5; i < 13; i++) {
            sizeBox.addItem(i);
        }
        sizeBox.setSelectedItem(size);
        for (int i = 1; i < 3; i++) {
            treeBox.addItem(i);
        }
        treeBox.setSelectedItem(trees);
        
        initBoxes();
        
        arrangeComponents();
        
        this.setVisible(true);
    }
    
    
    public Garden getSelectedGarden(){
        return gardenBox.getItemAt(gardenBox.getSelectedIndex());
    }
    
    public void setSize(int s){
        this.size = s;
        this.remove(grid);
        this.grid = new Grid(size);
        setNewMouseListener(grid);
        
        this.initGardens();
    }
    
    public void addCellToGarden(Cell in){
        for (Garden garden : gardens) {
            garden.remove(in);
        }
        this.getSelectedGarden().addToGarden(in);
        this.repaint();
    }

    private void setNewMouseListener(Grid grid) {
        MouseAdapter newl = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addCellToGarden((Cell)e.getSource());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK){
                    addCellToGarden((Cell)e.getSource());
                }
            }
            
        };
        
        for (Cell c : grid) {
            for (MouseListener l : c.getMouseListeners()) {
                c.removeMouseListener(l);
            }
            c.addMouseListener(newl);
        }
    }

    private void arrangeComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints cs= new GridBagConstraints();
        
        cs.gridwidth=2;
        this.add(grid, cs);
        
        cs.gridwidth=1;
        cs.gridy=1;
        this.add(gardenLabel, cs);
        
        cs.gridx=1;
        this.add(gardenBox, cs);
        
        cs.gridx=0;
        cs.gridy=2;
        this.add(sizeLabel, cs);
        
        cs.gridx=1;
        this.add(sizeBox, cs);
        
        cs.gridy=3;
        cs.gridx=0;
        this.add(treeLabel, cs);
        cs.gridx=1;
        this.add(treeBox, cs);
        
        cs.gridx=0; cs.gridy=4;
        cs.gridwidth=2;
        this.add(done, cs);
        
        this.pack();
    }

    private void initGardens() {
        ColorWheel.resetColorWheel();
        gardens = new ArrayList();
        for (int i = 0; i < size; i++) {
            gardens.add(new Garden(trees));
        }
        
        gardenBox.removeAllItems();
        for (Garden garden : gardens) {
            gardenBox.addItem(garden);
        }
    }

    private void initBoxes() {
        sizeBox.addActionListener((ActionEvent e) -> {
            this.setSize(sizeBox.getItemAt(sizeBox.getSelectedIndex()));
            arrangeComponents();
        });
        
        treeBox.addActionListener((ActionEvent e) -> {
            this.trees = treeBox.getItemAt(treeBox.getSelectedIndex());
        });
    }

    

    
    
    
}

