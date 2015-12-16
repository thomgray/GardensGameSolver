package gardens;

import java.util.ArrayList;
import java.util.Collection;

public class GardenModel{
    
    int size;
    int trees;
    Grid grid;
    ArrayList<Garden> gardens;

    public GardenModel(int size, int trees) {
        this.size=size;
        this.trees = trees;
        
        grid = new Grid(size);
        gardens = new ArrayList();
        
        ColorWheel.resetColorWheel();
        for (int i = 0; i < size; i++) {
            gardens.add(new Garden(trees));
        }
    }
    
    public void addCellToGarden(Garden g, Cell... cells){
        for (Cell c : cells) {
            for (Garden garden : gardens) {
                if (garden.contains(c)) {
                    garden.remove(c);
                }
            }
            g.addToGarden(cells);
        }
    }
    
    public Garden getGardenOfCell(Cell c){
        for (Garden g : gardens) {
            if (g.contains(c)) {
                return g;
            }
        }
        return null;
    }
    
    public GardenModel duplicate(){
        ColorWheel.resetColorWheel();
        GardenModel out = new GardenModel(size, trees);
        for (int i = 0; i < gardens.size(); i++) {
            Garden g = gardens.get(i);
            for (Cell c : g) {
                out.addCellToGarden(out.gardens.get(i), out.grid.getCell(c.x, c.y));
            }
        }
        for (Cell c : grid) {
            Cell newc = out.grid.getCell(c.x, c.y);
            newc.setState(c.getState());
        }
        return out;
    }
    
    public void copyGardens(ArrayList<Garden> in){
        if (in.size()!=gardens.size()) throw new java.lang.IllegalArgumentException("Source garden must be the same size as this garden");
        
        for (int i = 0; i < size; i++) {
            Garden g = in.get(i);
            for (Cell c : g) {
                this.addCellToGarden(this.gardens.get(i), this.grid.getCell(c.x, c.y));
            }
        }
    }
    
}