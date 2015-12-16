package gardens;

import java.util.ArrayList;
import java.util.Iterator;

public class Line implements Iterable<Cell>{

    Cell[] cells;
    
    

    public Line(int size) {
        cells = new Cell[size];
    }
    
    
    @Override
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int m = 0;
            @Override
            public boolean hasNext() {
                return m<cells.length;
            }

            @Override
            public Cell next() {
                Cell out = cells[m];
                m++;
                return out;
            }
        };
    }
    
    public int getUnknownCount(){
        int i =0;
        for (Cell c : this) {
            if (c.getState()==State.Unknown) i++;
        }
        return i;
    }

    int getTreeCount() {
        int i=0;
        for (Cell c : this) {
            if (c.getState()==State.Tree) i++;
        }
        return i;
    }
    
    int getEmptyCount(){
        int i=0;
        for (Cell c : this) {
            if (c.getState()==State.Empty) i++;
        }
        return i;
    }
    
    public ArrayList<Cell> getCells(State... states){
        ArrayList<Cell> out = new ArrayList();
        for (Cell c : this) {
            for (State s : states) {
                if (c.getState()==s){
                    out.add(c);
                }
            }
        }
        return out;
    }
    
    public boolean containsCell(Cell c){
        for (Cell cell : this) {
            if (c.equals(cell)) return true;
        }
        return false;
    }
    
    public boolean areThereEnoughFreeNonAdjacentCells(int trees){
        int remaining = trees-this.getTreeCount();
        switch (remaining) {
            case 0:
                return true;
            case 1:
                return this.getUnknownCount()>0;
            case 2:
                ArrayList<Cell> cells = this.getCells(State.Unknown);
                for (Cell c1 : cells) {
                    for (Cell c2 : cells) {
                        if (!c1.isAdjacent(c2) && !c1.equals(c2)) return true;
                    }
                }
            return false;
            default:
                throw new AssertionError();
        }
    }
    
    
}

class LineSegment extends Line{
    
    public LineSegment(int size) {
        super(size);
    }

    
}

class LineGroup implements Iterable<Line>{

    ArrayList<Line> lines;

    public LineGroup() {
        lines = new ArrayList();
    }
    
    @Override
    public Iterator<Line> iterator() {
        return lines.iterator();
    }
    
    public boolean containsCell(Cell c){
        for (Line l : this) {
            if (l.containsCell(c)) return true;
        }
        return false;
    }
    
    public ArrayList<Cell> getCells(State... states){
        ArrayList<Cell> out = new ArrayList();
        for (Line l : this) {
            here:
            for (Cell c : l) {
                for (State s : states) {
                    if (c.getState()==s){
                        out.add(c);
                        continue here;
                    }
                }
            }
        }
        return out;
    }
    
    
    public int getCount(State... states){
        int out = 0;
        for (Line l : this) {
            here:
            for (Cell c : l) {
                for (State s : states) {
                    if (c.getState().equals(s)){
                        out++;
                        continue here;
                    }
                }
            }
        }
        return out;
    }

    int size() {
        return this.lines.size();
    }
    
}