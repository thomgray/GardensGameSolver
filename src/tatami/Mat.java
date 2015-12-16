package tatami;

import java.util.ArrayList;
import java.util.Iterator;

public class Mat implements Iterable<Cell>{
    ArrayList<Cell> cells;
    
    
    public Mat() {cells = new ArrayList();}

    public Mat(Cell... cells) {
        this();
        for (Cell cell : cells) {addCell(cell);}
    }

    @Override
    public Iterator<Cell> iterator() {
        return cells.iterator();
    }
    
    boolean containsCell(int x, int y){
        for (Cell c : this) {
            if (c.x==x && c.y==y) return true;
        }
        return false;
    }
    boolean containsCell(Cell in){
        for (Cell c : this) {
            if (c.equals(in)) return true;
        }
        return false;
    }
    
    public void addCell(Cell... c){
        for (Cell c1 : c) {
            this.cells.add(c1);
            c1.setMat(this);
        }
    }
    
    public void addCellsVertically(int cellx, int celly, Grid g){
        int n = g.size/2;
        for (int i = 0; i < n; i++) {
            this.addCell(g.getCell(cellx, celly));
            celly++;
        }
        
    }
    public void addCellsHorizontally(int cellx, int celly, Grid g){
        int n = g.size/2;
        for (int i = 0; i < n; i++) {
            this.addCell(g.getCell(cellx, celly));
            cellx++;
        }
    }
    
    public int getIntCount(int i){
        int out = 0;
        for (Cell c : this) {
            if (c.state==i) out++;
        }
        return out;
    }
    
    public ArrayList<Cell> getCellsOfState(int... i){
        ArrayList<Cell> out = new ArrayList();
        for (Cell c : this) {
            for (int j : i) {
                if (c.state==j){
                    out.add(c);
                    break;
                }
            }
        }
        return out;
    }
}