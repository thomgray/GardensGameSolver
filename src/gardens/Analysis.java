package gardens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Analysis{
    
    public static boolean isComplete(GardenModel mod){
        for (Garden g : mod.gardens) {
            if (g.getTreeCount()!=mod.trees) return false;
        }
        for(Iterator<Line> it= mod.grid.iterateColumns(); it.hasNext();){
            Line col = it.next();
            if (col.getTreeCount()!=mod.trees)return false;
        }
        for (Iterator<Line> it = mod.grid.iterateRows(); it.hasNext();){
            Line row = it.next();
            if (row.getTreeCount()!=mod.trees) return false;
        }
        
        return Analysis.isValid(mod);
    }

    public static boolean isValid(GardenModel mod) {
        for (Garden g : mod.gardens) {
            if (g.getTreeCount()>mod.trees) return false;
            if (g.getUnknownCount()+g.getTreeCount()<mod.trees) return false;
        }
        for(Iterator<Line> it = mod.grid.iterateColumns(); it.hasNext();){
            Line col = it.next();
            if (col.getTreeCount()>mod.trees) return false;
            if (col.getUnknownCount()+col.getTreeCount()<mod.trees) return false;
        }
        for (Iterator<Line> it = mod.grid.iterateRows(); it.hasNext();){
            Line row = it.next();
            if (row.getTreeCount()>mod.trees) return false;
            if (row.getUnknownCount()+row.getTreeCount()<mod.trees) return false;
        }
        
        for (Cell c : mod.grid) {
            if (c.getState()==State.Tree){
                for(Iterator<Cell> it = mod.grid.iterateAdjacent(c); it.hasNext();){
                    if (it.next().getState()==State.Tree) return false;
                }
            }
        }
        
        for (Garden g : mod.gardens) {
            if (!g.areThereEnoughFreeNonAdjacentCells()) return false;
        }
        for (Line l : mod.grid.getColumnsAndRows()) {
            if (!l.areThereEnoughFreeNonAdjacentCells(mod.trees)) return false;
        }
        
        return checkBalance(mod);
    }
    
    
    /**
     * Returns a 2-valued array of Lines: 0: col, 1:row ; non-null lines if the parameter cells 
     * are all contained on the same col/row. So don't be flippant in assessing the array as there are likely 
     * null values.
     * @param cells
     * @param g
     * @return 
     */
    public static Line[] getCommonLines(ArrayList<Cell> cells, Grid g){
        Line[] out = new Line[2];
        if (cells.isEmpty()) {
            return new Line[2];
        }
        
        int x = cells.get(0).x;
        int outlength = 0;
        boolean yes1 = true;
        for (Cell c : cells) {
            if (c.x!=x){
                yes1 = false;
                break;
            }
        }
        if (yes1) {
            out[0] = g.getColumn(x);
            outlength++;
        }
        int y = cells.get(0).y;
        boolean yes2=true;
        for (Cell c : cells) {
            if (c.y!=y) {
                yes2=false;
                break;
            }
        }
        if (yes2) {
            out[1] = g.getRow(y);
        }
        return out;
    }
    
    public static boolean checkBalance(GardenModel mod){
        ArrayList<LineGroup> groups = mod.grid.getAllAdjacentColumnCombinations();
        groups.addAll(mod.grid.getAllAdjacentRowCombinations());
        
        for (LineGroup group : groups) {
            int dim = group.size();
            GardenGroup gardens = new GardenGroup();
            ArrayList<Cell> potTrees = group.getCells(State.Tree, State.Unknown);
            
            //get set of all gardens in group
            for (Cell c : potTrees) gardens.add(mod.getGardenOfCell(c));
            //if there are too few gardens...    
            if (gardens.size()<dim) return false;
            
            //get complete garden set...
            HashSet<Garden> toremove = new HashSet();
            here:
            for (Garden g : gardens) {
                for (Cell c : g) {
                    if (!group.containsCell(c)){
                        toremove.add(g);
                        continue here;
                    }
                }
            }
            for (Garden togo : toremove) gardens.remove(togo);
            
            if (gardens.size()>dim) return false;
        }
        return true;
    }
    
}

class GardenGroup extends HashSet<Garden>{

    boolean containsCell(Cell c){
        for (Garden g : this) {
            if (g.contains(c)) return true;
        }
        return false;
    }
    
}