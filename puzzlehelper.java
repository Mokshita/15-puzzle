//******************************************************
//*** Purpose: This class helps puzzle class in checking and
//*** defining the moves
//*****************************************************/

public class puzzlehelper{

//******************************************************
//*** Purpose: This method Checks the moves based on index location
//*** Input:Index
//*** Output:Moves as L-left R-Right U-up D-Down
//*****************************************************/

public String checkMoves(int i,int n){
String move=new String();;
if(i==0){ move="RD";}
else if(i==1){move= "LRD";}
else if(i==2){move= "LRD";}
else if(i==3) {move= "LD";}
else if(i==4) {move= "URD";}
else if(i==5) {move= "LRUD";}
else if(i==6) {move= "LRUD";}
else if(i==7) {move= "LUD";}
else if(i==8) {move= "URD";}
else if(i==9) {move= "LRUD";}
else if(i==10) {move= "LRUD";}
else if(i==11) {move= "LUD";}
else if(i==12) {move= "RU";}
else if(i==13) {move= "LRU";}
else if(i==14) {move= "LRU";}
else if(i==15) {move= "LU";}
;
return move;
}


//******************************************************
//*** Purpose: This method makes move to 1 left
//*** Input: Index location of array 
//*** Output:Array after movement
//*****************************************************/
public int[] left(int[] A,int i,int n){
A[i]=A[i-1];
A[i-1]=0;
return A;
}
//******************************************************
//*** Purpose: This method makes move to 1 right
//*** Input: Index location of array 
//*** Output:Array after movement
//*****************************************************/

public int[] right(int[] A,int i,int n){
A[i]=A[i+1];
A[i+1]=0;
return A;
}
//******************************************************
//*** Purpose: This method makes move to 1 up
//*** Input: Index location of array 
//*** Output:Array after movement
//*****************************************************/

public int[] up(int[] A,int i,int n){
A[i]=A[i-n];
A[i-n]=0;
return A;
}
//******************************************************
//*** Purpose: This method makes move to 1 down
//*** Input: Index location of array 
//*** Output:Array after movement
//*****************************************************/

public int[] down(int[] A,int i,int n){
A[i]=A[i+n];
A[i+n]=0;
return A;
}

}