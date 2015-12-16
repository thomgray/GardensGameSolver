package tatami;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

public class Grid extends JComponent implements Iterable<Cell>{

    Cell[][] cells;
    int size;
    
    

    public Grid(int size) {
        this.size=size;
        cells = (Cell[][])Array.newInstance(Cell.class,size, size);
        
        Class c = cells.getClass().getComponentType();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i,j,size/2);
            }
        }
        this.setLayout(new GridBagLayout());
        arrangeCells();
    }
    
    @Override
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int[] ad = new int[]{0,0};
            
            @Override
            public boolean hasNext() {
                return ad[0]<size && ad[1]<size;
            }

            @Override
            public Cell next() {
                Cell out = cells[ad[0]][ad[1]];
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
    
    private void arrangeCells() {
        GridBagConstraints cs = new GridBagConstraints();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cs.gridx=i; cs.gridy=j;
                this.add(cells[i][j], cs);
            }
        }
    }

    public Cell getCell(int x, int y){
        return cells[x][y];
    }
    
    public ArrayList<Cell> getEmptyCells(){
        ArrayList<Cell> out = new ArrayList();
        for (Cell thi : this) {
            if (thi.state==0) out.add(thi);
        }
        return out;
    }
    public ArrayList<Cell> getCellsOfState(int i){
        ArrayList<Cell> out = new ArrayList();
        for (Cell c : this) {
            if (c.state==i) out.add(c);
        }
        return out;
    }
    
    public int getRowIntCount(int row, int x){
        int out = 0;
        for (Cell c : this) {
            if (c.state==x && c.y==row) out++;
        }
        return out;
    }
    
    public int getColIntCount(int col, int x){
        int out = 0;
        for (Cell c : this) {
            if (c.state==x && c.x==col) out++;
        }
        return out;
    }
    
    public ArrayList<Cell> getColumn(int x){
        ArrayList<Cell> out = new ArrayList();
        for (int i = 0; i < size; i++) out.add(cells[x][i]);
        return out;
    }
    public ArrayList<Cell> getRow(int y){
        ArrayList<Cell> out = new ArrayList();
        for (int i = 0; i<size; i++) out.add(cells[i][y]);
        return out;
    }
    
    public ArrayList<Cell> getTouchingCells(Cell in){
        ArrayList<Cell> out = new ArrayList();
        int x = in.x, y = in.y;
        
        if (y>0) out.add(cells[x][y-1]);
        if (y<size-1) out.add(cells[x][y+1]);
        if (x>0) out.add(cells[x-1][y]);
        if (x<size-1) out.add(cells[x+1][y]);
        return out;
    }
    
    
    
    
    
}