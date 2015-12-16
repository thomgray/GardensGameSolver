package tatami;

public interface Levels{
    
    public abstract Tatami getLevel();
    
    public static Levels
            level_1 = ()->{
                Tatami out = new Tatami(8);
                Grid d = out.grid;
                Mat m = out.mats.get(0);
                m.addCellsVertically(0, 0, out.grid);
                m= out.mats.get(1);
                m.addCellsHorizontally(1, 0, out.grid);
                m= out.mats.get(2);
                m.addCellsHorizontally(1, 1, d);
                m= out.mats.get(3);
                m.addCellsHorizontally(1, 2, d);
                m= out.mats.get(4);
                m.addCellsHorizontally(1, 3, d);
                m= out.mats.get(5);
                m.addCellsVertically(5, 0, d);
                m=out.mats.get(6);
                m.addCellsVertically(6, 0, d);
                m=out.mats.get(7);
                m.addCellsVertically(7, 0, d);
                m=out.mats.get(8);
                m.addCellsHorizontally(0, 4, d);
                m=out.mats.get(9);
                m.addCellsHorizontally(0, 5, d);
                m=out.mats.get(10);
                m.addCellsHorizontally(0, 6, d);
                m=out.mats.get(11);
                m.addCellsHorizontally(0, 7, d);
                m=out.mats.get(12);
                m.addCellsHorizontally(4, 4, d);
                m=out.mats.get(13);
                m.addCellsHorizontally(4, 5, d);
                m=out.mats.get(14);
                m.addCellsHorizontally(4, 6, d);
                m=out.mats.get(15);
                m.addCellsHorizontally(4, 7, d);
                
                Cell c;
                c = out.grid.getCell(5, 0);
                c.setState(3);
                c=out.grid.getCell(7, 0); c.setState(4);
                c=out.grid.getCell(3, 1); c.setState(2);
                c=out.grid.getCell(4, 1); c.setState(3);
                c=out.grid.getCell(7, 1); c.setState(2);
                c=out.grid.getCell(4, 2); c.setState(1);
                out.grid.getCell(7, 2).setState(1);
                out.grid.getCell(3, 3).setState(1);
                out.grid.getCell(6, 4).setState(4);
                out.grid.getCell(7, 4).setState(1);
                out.grid.getCell(2, 5).setState(2);
                out.grid.getCell(5, 5).setState(3);
                out.grid.getCell(7, 5).setState(2);
                out.grid.getCell(2, 6).setState(3);
                out.grid.getCell(6, 6).setState(3);
                out.grid.getCell(7, 6).setState(4);
                out.grid.getCell(1, 7).setState(1);
                out.grid.getCell(2, 7).setState(4);
                out.grid.getCell(3, 7).setState(2);
                        
                return out;
            }
            ;
    
}