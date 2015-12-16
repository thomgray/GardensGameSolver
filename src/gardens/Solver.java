package gardens;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import logic.Set;

public class Solver extends SwingWorker<Void,Void>{
    GardenModel model;
    private int tierRoof;
    
    public Solver(GardenModel model) {
        this.model = model;
        maxProgress = model.grid.getCells(State.Empty,State.Tree,State.Unknown).size();
    }
    
    public void solve(){
        progress= 0;
        
        regularSolve();
        
        if (Analysis.isComplete(model)) return;
        
        //do{}while(solveTestCells());
            
        //solveProjections2();
        
        tierRoof = 1;
        
//        do{
//            do{}while(solveTestCells2(0));
//            tierRoof++;
//        }while(!Analysis.isComplete(model) && tierRoof<2);
        
        while (!Analysis.isComplete(model) && tierRoof<3) {
            do{}while(solveTestCells2(0));
            tierRoof++;
            System.out.println("current tier roof = "+tierRoof);
        }
    }
    
    private boolean regularSolve(){
        boolean out = false;
        boolean goon;
        do {
            goon = solveFillSpaces();
            fireProgressChange();
            //goon = solveProjections();
            goon = solveProjections2() || goon;
            fireProgressChange();
            if (goon) {
                out = goon;
            }
        } while (goon);
        return out;
    }
    
    /**
     * Scans the model for gardens/rows/columns with unknown + trees == model.trees, and places trees where appropriate
     */
    public boolean solveFillSpaces(){
        boolean out = false;
        
        for (Garden g : model.gardens) {
            if (g.getUnknownCount()+g.getTreeCount()==model.trees && g.getUnknownCount()>0){
                mark(g, State.Unknown, State.Tree);
                out = true;
            }
        }
        
        for (Iterator<Line> it = model.grid.iterateColumns(); it.hasNext();) {
            Line col = it.next();
            if (col.getUnknownCount()+col.getTreeCount()==model.trees && col.getUnknownCount()>0){
                mark(col, State.Unknown, State.Tree);
                fireProgressChange();
                out = true;
            }
        }
        
        for (Iterator<Line> it = model.grid.iterateRows(); it.hasNext();){
            Line row = it.next();
            if (row.getTreeCount()+row.getUnknownCount()==model.trees && row.getUnknownCount()>0){
                mark(row, State.Unknown, State.Tree);
                fireProgressChange();
                out = true;
            }
        }
        
        return out;
    }
    
    private boolean solveProjections(){
        boolean out = false;
        for (Garden g : model.gardens) {
            ArrayList<Cell> cells = g.getCells(State.Unknown, State.Tree);
            Line[] lines = Analysis.getCommonLines(cells, model.grid);
            for (Line line : lines) {
                if (line!=null){
                    for (Cell c : line) {
                        if (!g.contains(c) && c.getState()==State.Unknown){
                            c.setState(State.Empty);
                            out = true;
                        }
                    }
                }
            }
        }
        
        //mark empty all cells inside a garden whose potential trees lie on one line
        for(Line line: model.grid.getColumnsAndRows()){
            ArrayList<Cell> cells = line.getCells(State.Unknown, State.Tree);
            if (cells.isEmpty()) continue;
            
            Garden g = model.getGardenOfCell(cells.get(0));
            boolean check = true;
            for (Cell cell : cells) {
                if (model.getGardenOfCell(cell)!=g){check = false;}
            }
            if (check) {
                for (Cell c : g) {
                    if(!cells.contains(c) && c.getState()==State.Unknown){
                        c.setState(State.Empty);
                        out=true;
                    }
                }
            }
        }
        return out;
    }
    
    private boolean solveProjections2(){
        boolean out = false;
        
        ArrayList<LineGroup> lineCombos = model.grid.getAllAdjacentColumnCombinations();
        lineCombos.addAll(model.grid.getAllAdjacentRowCombinations());
        
        for (LineGroup lineCombo : lineCombos) {
            int dimension = lineCombo.size();
            ArrayList<Cell> potTrees = new ArrayList();
            for (Line line : lineCombo) {
                potTrees.addAll(line.getCells(State.Unknown, State.Tree));
            }
            
            //are all potential trees in <dimension> many gardens?
            //count the gardens
            
            HashSet<Garden> gardens= new HashSet();
            for (Cell potTree : potTrees) gardens.add(model.getGardenOfCell(potTree));
            
            //is it = the dimension of the lines? if so, then these gardens cannot contain 
            //trees outside of these lines
            if (gardens.size()<dimension){
                //model is invalid! IMPLEMENT!
            }else if (gardens.size()==dimension){
                for (Garden garden : gardens) {
                    for (Cell c : garden) {
                        if (c.getState()!=State.Unknown) continue;
                        fireProgressChange();
                        
                        if (!lineCombo.containsCell(c)){
                            c.setState(State.Empty);
                            out=true;
                        }
                    }
                }
            }
            
            //is the !whole! of <dimension> many gardens contained in this group?
            //we already have a set of gardens in the group. We must remove any that are wholly in the group
            HashSet<Garden> toRemove = new HashSet(); //to avoid concurrent modification
            here:
            for (Garden garden : gardens) {
                for (Cell c : garden) {
                    if (!lineCombo.containsCell(c)){
                        toRemove.add(garden);
                        continue here;
                    }
                }
            }
            for (Garden togo : toRemove) gardens.remove(togo);
            
            if (gardens.size()>dimension){
                //IMPLEMENT INVALID
            }else if (gardens.size()==dimension){ //all cells outside of the gardens in the group are empty
                for (Cell c : lineCombo.getCells(State.Unknown)) {
                    boolean empty=true;
                    for (Garden g : gardens) {
                        if (g.contains(c)){
                            empty = false;
                            break;
                        }
                    }
                    if (empty){
                        c.setState(State.Empty);
                        fireProgressChange();
                        out = true;
                    }
                }
            }
        }
        return out;
    }
    
    public boolean solveTestCells(){
        
        boolean out = false;
        
        for (Cell c : model.grid) {
            if (testCell(c)) {
                regularSolve();
                out = true;
            }
        }
        
        for (Cell c : model.grid) {
            if (testCell2(c)){
                regularSolve();
                out=true;
            }
        }
        
        return out;
    }
    
    private boolean testCell(Cell c){
        if (c.getState()!=State.Unknown) return false;
        
        GardenModel newMod = model.duplicate();
        Solver s = new Solver(newMod);
        Cell newcell = newMod.grid.getCell(c.x, c.y);
        s.placeTree(newcell);
        
        s.regularSolve();
        
        if (!Analysis.isValid(newMod)){
            c.setState(State.Empty);
            return true;
        }
        
        return false;
    }
    
    public boolean solveTestCells2(int tier){
        
        System.out.println("solving tier "+tier);
        
        int nextTier = tier+1;
        
        ArrayList<Cell> cells = model.grid.getCells(State.Unknown);
        boolean out = false;
        
        for (Cell cell : cells) {
            
            GardenModel dupemod = this.model.duplicate();
            Cell dupecell = dupemod.grid.getCell(cell.x, cell.y);
            Solver s = new Solver(dupemod);
            s.placeTree(dupecell);
            
            s.regularSolve();
            if (nextTier<tierRoof) while(s.solveTestCells2(nextTier)){};
            if (!Analysis.isValid(dupemod)){
                cell.setState(State.Empty);
                fireProgressChange();
                out = true;
                this.regularSolve();
                if (Analysis.isComplete(model)){
                    return out;
                }else continue;
            }else if (Analysis.isComplete(dupemod)) {
                this.copyModel(dupemod);
                return out;
            }
            
            dupemod = this.model.duplicate();
            dupecell = dupemod.grid.getCell(cell.x, cell.y);
            
            s = new Solver(dupemod);
            dupecell.setState(State.Empty);
            s.regularSolve();
            if (nextTier<tierRoof) while(s.solveTestCells2(nextTier)){};
            
            if (!Analysis.isValid(dupemod)){
                this.placeTree(cell);
                this.regularSolve();
                out=true;
                if (Analysis.isComplete(model)){
                    return true;
                }
            }else if (Analysis.isComplete(dupemod)) {
                this.copyModel(dupemod);
                return out;
            }
        }
        return out;
    }
    
    private boolean testCell2(Cell c){
        if (c.getState()!=State.Unknown) return false;
        
        GardenModel newmod = model.duplicate();
        Solver s = new Solver(newmod);
        Cell newcell = newmod.grid.getCell(c.x, c.y);
        newcell.setState(State.Empty);
        
        s.regularSolve();
        
        if (!Analysis.isValid(newmod)){
            this.placeTree(c);
            return true;
        }
        return false;
    }
    
    public void placeTree(Cell c){
        if (c.getState()!=State.Unknown) return;
        
        c.setState(State.Tree);
        
        
        for(Iterator<Cell> it = model.grid.iterateAdjacent(c); it.hasNext();){
            Cell cell = it.next();
            if (cell.getState()==State.Unknown){
                cell.setState(State.Empty);
            }
        }
        
        Line col = model.grid.getColumn(c.x);    
        if (col.getTreeCount() == model.trees){
            mark(col, State.Unknown, State.Empty);
        }
        
        Line row = model.grid.getRow(c.y);
        if (row.getTreeCount()==model.trees) mark(row, State.Unknown, State.Empty);
        
        Garden g = model.getGardenOfCell(c);
        if (g.getTreeCount()==model.trees) mark(g, State.Unknown, State.Empty);
        
        fireProgressChange();
        
        model.grid.repaint();
    }
    
    public void mark(Line l, State oldstate, State newstate){
        for (Cell c : l) {
            if (c.getState()==oldstate) {
                if (newstate==State.Tree) {
                    this.placeTree(c);
                }else{
                    c.setState(newstate);
                }
                
            }
        }
        model.grid.repaint();
    }
    
    public void mark(Garden g, State oldstate, State newstate){
        for (Cell c : g) {
            if(c.getState()==oldstate){
                if (newstate==State.Tree) {
                    this.placeTree(c);
                }else{
                    c.setState(newstate);
                }
            }
        }
    }
    
    /**
     * Copies the information of the parameter model to the current model. <br>
     * This assumes that the parameter model is identical to the current model with the exception of the 
     * cell states, otherwise expect exceptions!
     * @param mod 
     */
    private void copyModel(GardenModel mod){
        Cell[] theseCells = this.model.grid.toArray();
        Cell[] sourceCells = mod.grid.toArray();
        for (int i = 0; i < theseCells.length; i++) {
            Cell c = theseCells[i], cc = sourceCells[i];
            if (c.getState()!=cc.getState()) c.setState(cc.getState());
        }
    }
    
    public static void displayGrid(Grid g){
        JFrame f = new JFrame();
        f.add(g);
        f.pack();
        f.setVisible(true);
    }

    @Override
    protected Void doInBackground() throws Exception {
        Random random = new Random();
        int progress = 0;
        //Initialize progress property.
        setProgress(0);
        while (progress < 100) {
            //Sleep for up to one second.
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ignore) {}
            //Make random progress.
            progress += random.nextInt(10);
            setProgress(Math.min(progress, 100));
        }
        return null;

    }
    
    int progress;
    int maxProgress;
    
    void fireProgressChange(){
        if (updateProgress()) this.getPropertyChangeSupport().firePropertyChange(new PropertyChangeEvent(this,"progress",null,null));
    }

    private boolean updateProgress() {
        int p = this.model.grid.getCells(State.Empty, State.Tree).size();
        if (p!=progress){
            progress=p;
            return true;
        }
        return false;
    }
    
    public int getMaxProgress(){
        return maxProgress;
    }
    
    
    
    
}