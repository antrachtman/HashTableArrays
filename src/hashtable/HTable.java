/*
 * 
 */
package hashtable;

public class HTable {
    //This is typically called M (the rows) in a hash table. Should be a power of 2.
    private int tableSize;
    private String[][] hashTable;
    //xDim is like M and yDim is like N
    private int xDim;
    private int yDim;
    
    //constructor
    public HTable(int xDimension, int yDimension){
        hashTable = new String[xDimension][yDimension];
        for(int x = 0; x < xDimension; x++){
            for(int y = 0; y < yDimension; y++){
                hashTable[x][y] = null;
            }
        }
        setDim(xDimension, yDimension);
    }
    
    private int hashString(String string, int M){
        //The hash is where the info goes into the table.
        int hash = 0;
        //R must be a small prime number.
        int R = 31;
        //This is probably technically constant speed for smaller inputs. O(string.length())
        //If the string is too long, limit the number of characters used.
        //Collapse all excess whitespace using a regular expression.
        string = string.replaceAll("\\s+", " ");
        if(string.length() < 20){
            if(string.length() == 0){
                System.out.println("Null/empty string should not be added to hash table.");
            }
            for(int i = 0; i < string.length(); i++){
                hash = (R * hash + string.charAt(i)) % M;
            }
            return hash;
        }
        else 
            for(int i = 0; i < 20; i++){
                hash = (R * hash + string.charAt(i)) % M;
            }
        return hash;
    }
    
    private void rebuildTable(int xDimNew, int yDimNew){
        String [][] hashTableTemp = new String[xDimNew][yDimNew];
        for(int x = 0; x < xDim; x++){
            for(int y = 0; y < yDim; y++){
                if(hashTable[x][y] != null)
                    hashTableTemp[x][y] = hashTable[x][y];
                else
                    hashTableTemp[x][y] = null;
            }
        }
        xDim = xDimNew;
        yDim = yDimNew;
        hashTable = hashTableTemp;
        //System.out.println("Table doubled!");
    }
    
    private void setDim(int xDimension, int yDimension){
        xDim = xDimension;
        yDim = yDimension;
    }
    
    public void getDimensions(){
        System.out.println("X: "+xDim+" Y: "+yDim);
    }
    
    public int getXDim(){
        return xDim;
    }
    
    public int getYDim(){
        return yDim;
    }
    
    public String getElement(int x, int y){
        return hashTable[x][y];
    }
    
    public void printHashValue(String string){
        System.out.println(hashString(string, xDim));
    }
    
    public void addEntry(String string){
        boolean inserted = false;
        int hashValue = hashString(string, xDim);
        for(int i = 0; inserted == false; i++){
            if(hashTable[hashValue][i] == null){
                hashTable[hashValue][i] = string;
                //System.out.println("Inserted at: "+hashValue+" "+i);
                inserted = true;
            }
            if(i == yDim-1){
                //System.out.println("Table needs to be doubled.");
                if(hashTable[hashValue][i] == null)
                    hashTable[hashValue][i] = string;
                else{
                    rebuildTable(xDim*2, yDim*2);
                    hashTable[hashValue][i+1] = string;
                }
                //System.out.println("Inserted at: "+hashValue+" "+i);
                inserted = true;
            }
        }
    }
}
