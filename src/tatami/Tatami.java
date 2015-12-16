package tatami;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;

public class Tatami extends JFrame{

    Grid grid;
    ArrayList<Mat> mats;
    int size;
    
    
    public Tatami(int size){
        this.size = size;

        grid = new Grid(size);
        mats = new ArrayList();
        for (int i = 0; i < size*2; i++) {mats.add(new Mat());}
    }
    
    public void addCellToMat(int mat, int cellx, int celly){
        Mat m = mats.get(mat);
        m.addCell(grid.getCell(cellx, celly));
    }
    
    public Mat getMatOfCell(Cell in){
        for (Mat mat : mats) {
            if (mat.containsCell(in)) return mat;
        }
        throw new java.lang.RuntimeException("Something went wrong. Cell not in any mat!");
    }
    
    public Tatami duplicate(){
        Tatami out = new Tatami(this.size);
        int i = 0;
        Iterator<Cell> it = out.grid.iterator();
        for (Cell c : grid) {
            Cell c2 = it.next();
            c2.setState(c.state);
        }
        Iterator<Mat> it2 = out.mats.iterator();
        for (Mat mat : mats) {
            Mat mat2 = it2.next();
            for (Cell cell : mat.cells) {
                mat2.addCell(out.grid.getCell(cell.x, cell.y));
            }
        }
        return out;
    }

    void copyGame(Tatami dupGame) {
        Iterator<Cell> it = dupGame.grid.iterator();
        for (Cell c : grid) {
            Cell c2 = it.next();
            c.setState(c2.state);
        }
    }
}