package gardens;

public interface Level{
    
    abstract GardenModel getLevel();
        
    public static final Level 
            
    level_1 = ()->{
        GardenModel out = new GardenModel(5,1);
        Garden g = out.gardens.get(0);
        Grid d = out.grid;
        g.addToGarden(d.getCell(0, 0),d.getCell(1, 0),d.getCell(2, 0),d.getCell(3, 0),d.getCell(3, 1));
        g=out.gardens.get(1);
        g.addToGarden(d.getCell(0, 1),d.getCell(1, 1),d.getCell(2, 1),d.getCell(0, 2),d.getCell(0, 3),
                d.getCell(1, 3),d.getCell(2, 3),d.getCell(3, 3));
        g=out.gardens.get(2);
        g.addToGarden(d.getCell(4, 0),d.getCell(4, 1),d.getCell(4, 2),d.getCell(1, 2),d.getCell(2, 2),
                d.getCell(3, 2));
        g=out.gardens.get(3);
        g.addToGarden(d.getCell(0, 4),d.getCell(1, 4));
        g=out.gardens.get(4);
        g.addToGarden(d.getCell(2, 4),d.getCell(3, 4),d.getCell(4, 4),d.getCell(4, 3));
        return out;
    },
            
    level_2 = () -> {
        GardenModel out = new GardenModel(5,1);
        Grid d = out.grid;
        Garden g = out.gardens.get(0);
        g.addToGarden(d.getCell(0, 0),d.getCell(0, 1));
        g=out.gardens.get(1);
        g.addToGarden(d.getCell(2, 0),d.getCell(3, 0),d.getCell(1, 1),d.getCell(2, 1),
                d.getCell(3, 1),d.getCell(2, 2),d.getCell(3, 2));
        g=out.gardens.get(2);
        g.addToGarden(d.getCell(4, 0),d.getCell(4, 1),d.getCell(4, 2));
        g=out.gardens.get(3);
        g.addToGarden(d.getCell(0, 1),d.getCell(0, 2),d.getCell(1, 2),d.getCell(1, 3));
        g=out.gardens.get(4);
        g.addToGarden(d.getCell(0, 3),d.getCell(0, 4),d.getCell(1, 4),d.getCell(2, 3),
                d.getCell(2, 4),d.getCell(3, 3),d.getCell(3, 4),d.getCell(4, 3),
                d.getCell(4, 4));
        return out;
    },
    
    level_83 = ()->{
        GardenModel model = new GardenModel(9,2);
        Garden g;
        Grid d = model.grid;
        g=model.gardens.get(0);
        g.addToGarden(d.getCell(0, 0), d.getCell(1, 0), d.getCell(2, 0), d.getCell(3, 0),
                d.getCell(4, 0), d.getCell(5, 0), d.getCell(2, 1), d.getCell(3, 1));
        g = model.gardens.get(1);
        g.addToGarden(d.getCell(6, 0), d.getCell(7, 0), d.getCell(8, 0), d.getCell(4, 1),
                d.getCell(5, 1), d.getCell(6, 1), d.getCell(7, 1), d.getCell(8, 1),
                d.getCell(5, 2), d.getCell(6, 2), d.getCell(7, 2),d.getCell(8, 2),
                d.getCell(7, 3),d.getCell(8, 3));
        g=model.gardens.get(2);
        g.addToGarden(d.getCell(0, 1),d.getCell(1, 1),d.getCell(0, 2),d.getCell(1, 2),
                d.getCell(0, 3),d.getCell(1, 3),d.getCell(0, 4),d.getCell(1, 4),
                d.getCell(0, 5),d.getCell(0, 6));
        g=model.gardens.get(3);
        g.addToGarden(d.getCell(2, 2),d.getCell(3, 2),d.getCell(4, 2),d.getCell(2, 3),
                d.getCell(3, 3),d.getCell(2, 4),d.getCell(3, 4));
        g=model.gardens.get(4);
        g.addToGarden(d.getCell(5, 3),d.getCell(6, 3),d.getCell(6, 4),d.getCell(7, 4),
                d.getCell(8, 4),d.getCell(7, 5),d.getCell(8, 5),d.getCell(8, 6));
        g=model.gardens.get(5);
        g.addToGarden(d.getCell(4, 3),d.getCell(4, 4),d.getCell(4, 5),d.getCell(4, 6),
                d.getCell(4, 7),d.getCell(4, 8), d.getCell(5, 4),d.getCell(5, 5),d.getCell(5, 6));
        g=model.gardens.get(6);
        g.addToGarden(d.getCell(1, 5), d.getCell(2, 5),d.getCell(3, 5),d.getCell(1, 6),
                d.getCell(2, 6),d.getCell(3, 6),d.getCell(2, 7),d.getCell(3, 7),
                d.getCell(3, 8));
        g=model.gardens.get(7);
        g.addToGarden(d.getCell(0, 7),d.getCell(0, 8),d.getCell(1, 7),d.getCell(1, 8),
                d.getCell(2, 8));
        g=model.gardens.get(8);
        g.addToGarden(d.getCell(5, 7),d.getCell(5, 8),d.getCell(6, 5),d.getCell(6, 6),
                d.getCell(6, 7),d.getCell(6, 8),d.getCell(7, 6),d.getCell(7, 7),d.getCell(7, 8),
                d.getCell(8, 7),d.getCell(8, 8));
        return model;
    },
            
            level_71 = ()->{
                GardenModel out = new GardenModel(9,2);
                Garden g;
                Grid d = out.grid;
                g = out.gardens.get(0);
                g.addToGarden(d.getCell(0, 0),d.getCell(0, 1),d.getCell(0, 2),d.getCell(0, 3),
                        d.getCell(0, 5),d.getCell(0, 4),d.getCell(1, 3),d.getCell(1, 1),
                        d.getCell(1, 2),d.getCell(1, 4),d.getCell(1, 5),d.getCell(2, 3),
                        d.getCell(2, 4));
                g=out.gardens.get(1);
                g.addToGarden(d.getCell(1, 0),d.getCell(2, 0),d.getCell(3, 0),d.getCell(4, 0),
                        d.getCell(2, 1),d.getCell(3, 1),d.getCell(4, 1),d.getCell(2, 2),
                        d.getCell(3, 2),d.getCell(4, 2),d.getCell(3, 3),d.getCell(4, 3),
                        d.getCell(5, 3));
                g=out.gardens.get(2);
                g.addToGarden(d.getCell(5, 0),d.getCell(6, 0),d.getCell(7, 0),d.getCell(8, 0),
                        d.getCell(5, 1),d.getCell(6, 1),d.getCell(7, 1),d.getCell(8, 1),
                        d.getCell(5, 2));
                g=out.gardens.get(3);
                g.addToGarden(d.getCell(6, 2),d.getCell(7, 2),d.getCell(8, 2),
                        d.getCell(6, 3),d.getCell(7, 3),d.getCell(8, 3));
                g=out.gardens.get(4);
                g.addToGarden(d.getCell(0, 6),d.getCell(0, 7),d.getCell(0, 8),
                        d.getCell(1, 6),d.getCell(1, 7),d.getCell(1, 8));
                g=out.gardens.get(5);
                g.addToGarden(d.getCell(3, 4),d.getCell(4, 4),d.getCell(5, 4),d.getCell(6, 4),
                        d.getCell(2, 5),d.getCell(3, 5),d.getCell(4, 5),d.getCell(5, 5),
                        d.getCell(2, 6),d.getCell(3, 6));
                g=out.gardens.get(6);
                g.addToGarden(d.getCell(7, 4),d.getCell(8, 4),d.getCell(7, 5),d.getCell(8, 5),
                        d.getCell(7, 6),d.getCell(8, 6),d.getCell(6, 7),d.getCell(7, 7),d.getCell(8, 7),d.getCell(8, 8));
                g=out.gardens.get(7);
                g.addToGarden(d.getCell(6, 5),d.getCell(4, 6),d.getCell(5, 6),d.getCell(6, 6),
                        d.getCell(3, 7),d.getCell(4, 7),d.getCell(5, 7));
                g=out.gardens.get(8);
                g.addToGarden(d.getCell(2, 7),d.getCell(2, 8),d.getCell(3, 8),d.getCell(4, 8),
                        d.getCell(5, 8),d.getCell(6, 8),d.getCell(7, 8));
                return out;
            },
    
    level_112_NY = ()->{
        GardenModel out = new GardenModel(10,2);
        Garden g;
        Grid d = out.grid;
        g=out.gardens.get(0);
        g.addToGarden(d.getCell(0, 0), d.getCell(1, 0), d.getCell(2, 0),d.getCell(3, 0),
                d.getCell(0, 1),d.getCell(1, 1),d.getCell(2, 1),d.getCell(3, 1), d.getCell(0, 2),
                d.getCell(1, 2),d.getCell(0, 3),d.getCell(0, 4));
        g=out.gardens.get(1);
        g.addToGarden(d.getCell(4, 0), d.getCell(5, 0),d.getCell(5,1),d.getCell(4, 1),d.getCell(5, 2),
                d.getCell(4, 2), d.getCell(3, 2),d.getCell(5, 3),d.getCell(5, 4),d.getCell(6, 4),
                d.getCell(7, 4),d.getCell(6, 5),d.getCell(7, 3));
        g=out.gardens.get(2);
        g.addToGarden(d.getCell(6, 0), d.getCell(7, 0),d.getCell(8, 0),d.getCell(6, 1),
                d.getCell(8, 1),d.getCell(6, 2),d.getCell(6, 3));
        g=out.gardens.get(3);
        g.addToGarden(d.getCell(9, 0),d.getCell(9, 1),d.getCell(9, 2),d.getCell(9, 3),
                d.getCell(7, 1),d.getCell(7, 2),d.getCell(8, 2),
                d.getCell(8,3));
        g=out.gardens.get(4);
        g.addToGarden(d.getCell(2, 2),d.getCell(1, 3),d.getCell(2, 3),d.getCell(3, 3),d.getCell(1, 4),
                d.getCell(2, 4),d.getCell(3,4),d.getCell(0, 5),d.getCell(1, 5),d.getCell(3, 5),
                d.getCell(0, 6),d.getCell(3,6),d.getCell(4, 6),d.getCell(0, 7),d.getCell(1, 7),
                d.getCell(3, 7),d.getCell(4, 7),d.getCell(1, 8),d.getCell(2, 8),d.getCell(3, 8),
                d.getCell(4, 8),d.getCell(5, 8));
        g=out.gardens.get(5);
        g.addToGarden(d.getCell(2, 5),d.getCell(1, 6),d.getCell(2, 6),d.getCell(2, 7));
        g=out.gardens.get(6);
        g.addToGarden(d.getCell(0, 8),d.getCell(0, 9),d.getCell(1, 9),d.getCell(2, 9),d.getCell(3, 9),
                d.getCell(4, 9));
        g=out.gardens.get(7);
        g.addToGarden(d.getCell(4, 3),d.getCell(4, 4),d.getCell(4, 5),d.getCell(5, 5),d.getCell(5, 6),
                d.getCell(6, 6),d.getCell(7, 6));
        g=out.gardens.get(8);
        g.addToGarden(d.getCell(8, 4),d.getCell(9, 4),d.getCell(7, 5),d.getCell(8, 5),d.getCell(9, 5),
                d.getCell(8, 6),d.getCell(9, 6),d.getCell(7, 7),d.getCell(8, 7),d.getCell(9, 7),
                d.getCell(9, 8));
        g=out.gardens.get(9);
        g.addToGarden(d.getCell(5, 7),d.getCell(6, 7),d.getCell(6, 8),d.getCell(7, 8),d.getCell(8, 8),
                d.getCell(5, 9),d.getCell(6, 9),d.getCell(7, 9),d.getCell(8, 9),d.getCell(9, 9));
        return out;
    }
    
    
            ;
    
}