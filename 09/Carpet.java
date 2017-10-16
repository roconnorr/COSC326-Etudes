public class Carpet  {
 private boolean[][] carpet;
 int x;
 int y;

 public int xlen ()  {
  return this.x;
 }
 public int ylen()  {
  return this.y;
 }
 public Carpet (int x, int y)   {
  this.x = x;
  this.y = y;
  carpet = new boolean[x][y];
  for(int i = 0; i < x; i++) {
   for (int j = 0; j < y; j++)  {
    carpet[i][j] = true;
   }
  }
 }

 public Carpet (Carpet old)  {
  x = old.xlen();
  y = old.ylen();
  this.carpet = new boolean[x][y];
  for(int i = 0; i < x; i++) {
   for (int j = 0; j < y; j++) {
    this.carpet[i][j] = old.get(i,j);
   }
  }
 }

 public int[] getEmpty()  {
  int[] result = new int[2];
  for(int i = 0; i < x; i++)  {
   for (int j = 0; j < y; j++)  {
    if (this.get(i, j))  {
     result[0] = i;
     result[1] = j;
     return result;
    }
   }
  }
  return result;
 }

 
 
 public boolean get(int x, int y)  {
  return carpet[x][y];
 }

 public void slide()  {
  boolean finished = false;
  int count = 0;
  while (!finished)  {
   for (int i = 0; i < this.y; i++)  {
    if (!this.get(0,i))  {
     count++;
    }
   }
   if (count == this.y)  {
    count = 0;
    for(int i = 0; i < x-1; i++)  {
     for (int j = 0; j < y; j++)  {
      this.carpet[i][j] = this.carpet[i+1][j];
     }
    }
    for (int i = 0; i < this.y; i++)  {
     this.set(this.x-1, i, true);
    }
   }
   else  {
    finished = true;
   }
  }
 }

 public void set(int x, int y, boolean value)  {
  this.carpet[x][y] = value;
 }
 
 public String strVal ()   {
  String result = "";
  for(int i = 0; i < y; i++) {
   for (int j = 0; j < x; j++) {
    if(carpet[j][i]){
     result+= '0';
    }else {
     result += 'X';
    }
   }
  }
  return result;
 }

 public boolean equals(Object other) {
  if (other instanceof Carpet) {
   Carpet that = (Carpet) other;
   for(int i = 0; i < x; i++) {
    for (int j = 0; j < y; j++) {
     if (this.get(i,j) != that.get(i,j)) {
      return false;
     }
    }
   }
   return true;
  }
  return false;
 }

}