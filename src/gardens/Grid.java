package gardens;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;

public class Grid extends JComponent implements Iterable<Cell>{

    Cell[][] cells;
    int size;
    
    public Grid(int size) {
        this.size=size;
        
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i,j);
            }
        }
        
        this.setLayout(new GridBagLayout());
        this.arrangeCells();
    }
    
    public Line getColumn(int c){
        Line out = new Line(size);
        for (int i = 0; i < size; i++) {
            out.cells[i]=cells[c][i];
        }
        return out;
    }
    
    public Line getRow(int r){
        Line out = new Line(size);
        for (int i = 0; i < size; i++) {
            out.cells[i] = cells[i][r];
        }
        return out;
    }
    
    @Override
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int[] ad  = new int[]{0,0};
            @Override
            public boolean hasNext() {
                return ad[0]<size && ad[1]<size;
            }

            @Override
            public Cell next() {
                Cell out = cells[ad[0]][ad[1]];
                if (ad[0]<size-1) {
                    ad[0]++;
                }else{
                    ad[0]=0; ad[1]++;
                }
                return out;
            }
        };
    }
    
    public Iterator<Cell> iterateAdjacent(Cell in){
        ArrayList<Cell> temparray = new ArrayList();
        for (Cell c : this) {
            if (c.isAdjacent(in)) {
                temparray.add(c);
            }
        }
        return temparray.iterator();
    }
    
    public Cell getCell(int x, int y){
        return cells[x][y];
    }
    
    public ArrayList<Cell> getCells(State... states){
        ArrayList<Cell> out = new ArrayList();
        for (Cell c : this) {
            if(states==null){
                out.add(c);
                continue;
            }
            for (State state : states) {
                if (c.getState()==state){
                    out.add(c);
                    break;
                }
            }
        }
        return out;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size*Cell.cellWidth, size*Cell.cellWidth);
    }
    
    public void arrangeCells(){
        GridBagConstraints cs = new GridBagConstraints();
        for (Cell c : this) {
            cs.gridx = c.x;
            cs.gridy = c.y;
            this.add(c, cs);
        }
    }
    
    public Iterator<Line> iterateColumns(){
        return new Iterator<Line>() {
            int m=0;
            @Override
            public boolean hasNext() {
                return m<size;
            }

            @Override
            public Line next() {
                Line l = getColumn(m);
                m++;
                return l;
            }
        };
    }
    public Iterator<Line> iterateRows(){
        return new Iterator<Line>() {
            int m =0;
            @Override
            public boolean hasNext() {
                return m<size;
            }

            @Override
            public Line next() {
                Line l = getRow(m);
                m++;
                return l;
            }
        };
    }
    
    public ArrayList<Line> getColumns(){
        ArrayList<Line> out = new ArrayList();
        for (int i = 0; i < this.size; i++) {
            out.add(this.getColumn(i));
        }
        return out;
    }
    public ArrayList<Line> getRows(){
        ArrayList<Line> out = new ArrayList();
        for (int i = 0; i < size; i++) {
            out.add(this.getRow(i));
        }
        return out;
    }
    public ArrayList<Line> getColumnsAndRows(){
        ArrayList<Line> out = this.getColumns();
        out.addAll(this.getRows());
        return out;
    }
    
    ArrayList<LineGroup> getAllAdjacentColumnCombinations(){
        ArrayList<LineGroup> groups = new ArrayList();
        for (int dimension = 1; dimension < this.size; dimension++) {
            for (int first = 0; first+dimension-1 < size; first++) {
                LineGroup group = new LineGroup();
                for (int i = 0; i < dimension; i++) {
                    group.lines.add(this.getColumn(first+i));
                }
                groups.add(group);
            }
        }
        return groups;
        
//        ArrayList<ArrayList<Line>> out = new ArrayList();
//        for (int dimension = 1; dimension < this.size; dimension++) {
//            for (int first = 0; first+dimension-1 < size; first++) {
//                ArrayList<Line> group = new ArrayList();
//                for (int i = 0; i < dimension; i++) {
//                    group.add(this.getColumn(first+i));
//                }
//                out.add(group);
//            }
//        }
//        return out;
    }
    
    
    ArrayList<LineGroup> getAllAdjacentRowCombinations(){
        ArrayList<LineGroup> groups = new ArrayList();
        for (int dimension = 1; dimension < this.size; dimension++) {
            for (int first = 0; first+dimension-1 < size; first++) {
                LineGroup group = new LineGroup();
                for (int i = 0; i < dimension; i++) {
                    group.lines.add(this.getRow(first+i));
                }
                groups.add(group);
            }
        }
        return groups;
        
//        ArrayList<ArrayList<Line>> out = new ArrayList();
//        for (int dimension = 1; dimension < this.size; dimension++) {
//            for (int first = 0; first+dimension-1 < size; first++) {
//                ArrayList<Line> group = new ArrayList();
//                for (int i = 0; i < dimension; i++) {
//                    group.add(this.getRow(first+i));
//                }
//                out.add(group);
//            }
//        }
//        return out;
    }
    
    public LineSegment getColumnSegment(int x, int... ys){
        LineSegment out = new LineSegment(ys.length);
        for (int i = 0; i < out.cells.length; i++) {
            out.cells[i] = this.getCell(x, ys[i]);
        }
        return out;
    }
    
    public LineSegment getRowSegment(int y, int... xs){
        LineSegment out = new LineSegment(xs.length);
        for (int i = 0; i < xs.length; i++) {
            out.cells[i] = this.getCell(xs[i], y);
        }
        return out;
    }
    
    public Cell[] toArray(){
        Cell[] out = new Cell[(int)Math.pow(this.size, 2)];
        int m=0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out[m] = this.getCell(i, j);
                m++;
            }
        }
        return out;
    }
    
}