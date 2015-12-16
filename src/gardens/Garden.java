package gardens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class Garden implements Iterable<Cell>{
    
    ArrayList<Cell> cells;
    Color color;
    int trees;

    public Garden(int trees) {
        color = ColorWheel.newColor();
        this.trees = trees;
        cells = new ArrayList();
    }
    
    public void addToGarden(Cell... in){
        for (Cell c : in) {
            cells.add(c);
            c.setColor(color);
        }
    }
    
    public void addToGarden(ArrayList<Cell> in){
        for (Cell c : in) {
            cells.add(c);
            c.setColor(color);
        }
    }

    @Override
    public Iterator<Cell> iterator() {
        return cells.iterator();
    }
    
    public boolean contains(Cell c){
        return this.cells.contains(c);
    }
    public boolean remove(Cell c){
        return cells.remove(c);
    } 

    @Override
    public String toString() {
        return ColorWheel.getString(color)+" Garden";
    }
    
    public int getUnknownCount(){
        int i =0;
        for (Cell c : this) {
            if (c.getState()==State.Unknown) i++;
        }
        return i;
    }
    public int getTreeCount(){
        int i=0;
        for (Cell c : this) {
            if (c.getState().equals(State.Tree)) i++;
        }
        return i;
    }
    public int getEmptyCount(){
        int i=0;
        for (Cell c : this) {
            if (c.getState()==State.Empty) i++;
        }
        return i;
    }
    
    /**
     * Returns an array of all cells of the parameter state(s)
     * @param states
     * @return 
     */
    public ArrayList<Cell> getCells(State... states){
        ArrayList<Cell> out = new ArrayList();
        for (Cell c : this) {
            for (State state : states) {
                if (c.getState()==state) out.add(c);
            }
        }
        return out;
    }
    
    public boolean areThereEnoughFreeNonAdjacentCells(){
        int remainingTrees = this.trees-this.getTreeCount();
        switch (remainingTrees) {
            case 0:
                return true;
            case 1:
                return this.getUnknownCount()>0;
            case 2:
                ArrayList<Cell> cells = this.getCells(State.Unknown);
                for (Cell c1 : cells) {
                    for (Cell c2 : cells) {
                        if(!c1.isAdjacent(c2) && !c1.equals(c2)) return true;
                    }
                }
                return false;
            default:
                throw new java.lang.UnsupportedOperationException("Doens't support >2 trees");
        }
        
        
    }
    
    
    
}
        