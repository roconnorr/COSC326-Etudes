public class Shape  {
    public int[][] shape =  {
    //Square
    { 0,0,1,0,0,1,1,1 },
      //I1
    { 0,0,1,0,2,0,3,0},
    //I2
    { 0,0,0,1,0,2,0,3},
    //J1
    { 0,0,1,0,2,0,2,1},
    //J2
    { 0,0,1,0,1,-1,1,-2},
    //J3
    { 0,0,0,1,1,1,2,1},
    //J4
    { 0,0,1,0,0,1,0,2},
    //L1
    { 0,0,1,0,2,0,0,1 },
    //L2
    { 0,0,1,0,1,1,1,2 },
    //L3
    { 0,0,1,0,2,0,2,-1 },
    //L4
    { 0,0,0,1,0,2,1,2 },
    //S1
    { 0,0,1,0,1,-1,2,-1 },
    //S2
    { 0,0,0,1,1,1,1,2 },
    //Z1
    { 0,0,1,0,1,1,2,1 },
    //Z2
    { 0,0,0,1,1,0,1,-1 },
    //T1
    { 0,0,1,0,1,-1,2,0 },
    //T2
    { 0,0,0,1,0,2,1,1 },
    //T3
    { 0,0,1,0,1,1,2,0 },
    //T4
    { 0,0,1,0,1,-1,1,1 }
    };
    
    public Shape()  {
      
    }
    

    public Carpet place (Carpet carpet, int[] start, int[] coord)  {
        Carpet newCarpet = new Carpet(carpet);
        boolean voids = false;
        for (int i = 0; i < 7; i = i + 2) {
            int x = start[0] + coord[i];
            int y = start[1] + coord[i+1];

            if(x < 0 || y < 0 || x > newCarpet.xlen()-1 || y > newCarpet.ylen()-1)  {

                voids = true;
                return null;
            }
            boolean canPut = newCarpet.get(x,y);
            if(canPut)  {
                newCarpet.set(x,y,false); 
            }
            else  { 
                voids = true;
                return null;
            }
        }
        newCarpet.slide();
        if (!voids)  {
            return newCarpet;
        }
        return null;
    }
}