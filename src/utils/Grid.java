package utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

public class Grid<T extends Cell> extends JComponent implements Iterable<T>{

    T[][] cells;
    int size;
    
    

    public Grid(int size) {
        this.size=size;
        cells = (T[][])Array.newInstance(Cell.class,size, size);
        
        Class c = cells.getClass().getComponentType();
        //cells = new T[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                try {
                    cells[i][j] = (T)c.newInstance();
                } catch (InstantiationException ex) {
                    Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                }
                cells[i][j].x=i; cells[i][j].y = j;
                //cells[i][j] = new T(i,j);
            }
        }
        this.setLayout(new GridBagLayout());
        arrangeCells();
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int[] ad = new int[]{0,0};
            
            @Override
            public boolean hasNext() {
                return ad[0]<size || ad[1]<size;
            }

            @Override
            public T next() {
                T out = cells[ad[0]][ad[1]];
                if (ad[0]<size-1) ad[0]++;
                else{
                    ad[0]=0;
                    ad[1]++;
                }
                return out;
            }
        };
    }

    

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size*Cell.cellWidth, size*Cell.cellWidth);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
    }

    private void arrangeCells() {
        GridBagConstraints cs = new GridBagConstraints();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cs.gridx=i; cs.gridy=j;
                this.add(cells[i][j], cs);
            }
        }
    }
    
    
    
}