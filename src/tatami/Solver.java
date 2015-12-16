package tatami;

import java.util.ArrayList;
import javax.swing.JFrame;
import tools.Arrays;

public class Solver{
    Tatami game;
    int size;
    int count;
    int order;
    static int roof;
    int tier;
    
    public Solver(Tatami in) {
        this.game = in;
        this.size = in.size;
        this.count = size/2;
        this.order=2;
    }
    
    public void solve(){
        roof = 2;
        tier=0;
        do {
            basicSolve();
        } while (solveTestCells());
    }
    
    private void basicSolve(){
        boolean temp;
        do {
            temp = false;
            temp = solveWeighCells();
            temp = solveConsiderMats() || temp;
            temp = solveConsiderLines() || temp;
        } while (temp);
    }
    
    private boolean solveWeighCells(){
        boolean out = false;
        for (Cell c : game.grid.getEmptyCells()) {
            ArrayList<Integer> candidates = new ArrayList();
            for (int i = 1; i <= count; i++) {candidates.add(i);}
            
            for (int i = 1; i <= count; i++) {
                if (!cellCanBe(c,i)){
                    candidates.remove((Integer)i);
                }
            }
            if (candidates.size()==1){
                c.setState(candidates.get(0));
                out = true;
            }
        }
        return out;
    }
    
    private boolean solveConsiderMats(){
        boolean out = false;
        for (Mat mat : game.mats) {
            for (int i = 1; i <= count; i++) {
                ArrayList<Cell> candidates =(ArrayList<Cell>)mat.cells.clone();
                for (Cell c : mat.cells) {
                    if (!cellCanBe(c,i)){
                        candidates.remove(c);
                    }
                }
                if (candidates.size()==1){
                    candidates.get(0).setState(i);
                    out = true;
                }
            }    
        }
        return out;
    }
    
    private boolean solveConsiderLines(){
        boolean out = false;
        for (int i = 0; i < size; i++) {
            ArrayList<Cell> row = game.grid.getRow(i);
            for (int j = 1; j <= count; j++) {
                ArrayList<Cell> cands = (ArrayList<Cell>)row.clone();
                for (Cell c : row) {
                    if (!cellCanBe(c,j)){
                        cands.remove(c);
                    }
                }
                if (cands.size()==1){
                    cands.get(0).setState(j);
                    out = true;
                }
            }
            
            ArrayList<Cell> col = game.grid.getColumn(i);
            
            for (int j = 1; j <= count; j++) {
                ArrayList<Cell> cands = (ArrayList<Cell>)col.clone();
                for (Cell c : col) {
                    if (!cellCanBe(c,j)){
                        cands.remove(c);
                    }
                }
                if (cands.size()==1){
                    cands.get(0).setState(j);
                    out = true;
                }
            }
        }
        return out;
    }
    
    private boolean cellCanBe(Cell c, int i){
        if (c.state!=0) return false;
        for (Cell cell : game.getMatOfCell(c).cells) {
            if (cell.state==i) return false;
        }
        if (game.grid.getColIntCount(c.x, i)==order) return false;
        if (game.grid.getRowIntCount(c.y, i)==order) return false;
        for (Cell cell : game.grid.getTouchingCells(c)) {
            if (cell.state==i){
                return false;
            }
        }
        return true;
    }
    
    private boolean solveTestCells(){
        boolean out = false;
        for (Cell cell : game.grid.getCellsOfState(0)) {
            int[] states = getPossibleStates(cell);
            
            //System.out.println("cell "+cell.toString()+" can be "+Arrays.getString(states));
            
            ArrayList<Integer> candStates = new ArrayList();
            for (int i =0;i<states.length;i++) candStates.add(states[i]);
            
            for (int state : states) {
                Boolean b = this.testCell(cell, state);
                if (b==null) continue;
                else if (!b){candStates.remove(state);}
                else return true;
            }
            if (candStates.size()==1){
                cell.setState(candStates.get(0));
                out = true;
            }
        }
        return out;
    }
    
    private Boolean testCell(Cell c, int i){
        Tatami dupGame = game.duplicate();
        dupGame.grid.getCell(c.x, c.y).setState(i);
        
        Solver s = new Solver(dupGame);
        s.basicSolve();
        
        if (s.isComplete()){
            this.game.copyGame(dupGame);
            return true;
        }
        if (!s.isValid()) return false;
        
//        s.tier=tier+1;
//        if (tier<roof){
//            do{}while(s.solveTestCells());
//            if (!s.isValid()) return false;
//        }
        
        return null;
    }
    
    private int[] getPossibleStates(Cell c){
        ArrayList<Integer> out = new ArrayList();
        for (int i = 1; i <= count; i++) {
            if (cellCanBe(c,i)) out.add(i);
        }
        int[] out2 = new int[out.size()];
        for (int i = 0; i < out.size(); i++) {
            out2[i] = out.get(i);
        }
        return out2;
    }
    
    
    boolean isValid(){
        for (Cell c : game.grid) {
            if (c.state==0)continue;
            ArrayList<Cell> adjacents = game.grid.getTouchingCells(c);
            for (Cell adj : adjacents) {if (adj.state==c.state){
                return false;
            }}
        }
        for (int i = 0; i < size; i++) { //iterate rows/cols
            for (int j = 1; j <= count; j++) { //iterate numbers
                if (game.grid.getRowIntCount(i, j)>order){
                    return false;
                }
                if (game.grid.getColIntCount(i, j)>order){
                    return false;
                }
            }
        }
        for (Mat mat : game.mats) {
            for (int i = 1; i <= count; i++) {
                if (mat.getIntCount(i)>1){
                    return false;
                }
            }
            
        }
        return true;
    }
    
    boolean isComplete(){
        for (Cell c : game.grid) {
            if (c.state==0) return false;
        }
        return isValid();
    }
}