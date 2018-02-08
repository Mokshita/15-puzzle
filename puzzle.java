/* ******************************************************************************
Purpose: To replicate 15 puzzle using 1 D array
Input:Initial State of puzzle as 1 D
Output:Goal State of puzzle as 1 D
Help:Worked on puzzle based on BFS n DFS concept
*******************************************************************************/

import java.util.*;
import java.io.*;
public class puzzle {
//========declaring depth of the tree=============================
private static final int depth=30;
private static String function=null;
private static boolean found=false;

public static void main(String[] args) {

//====args=====================================
 String fileName="";
 
 if(args.length<2)
 {
 System.out.println("Invalid number of Args");
 System.exit(0);
 }
 
 if (args.length > 0) {
     try {
               if(!(args[0].equals("DFS") || args[0].equals("BFS")))
               {
                  System.out.println("Wrong function defined");
                  System.exit(0);
               }
               else
               {
                  function = args[0];
               }
               
               fileName=args[1];
         } 
     catch (NumberFormatException e) {
         System.err.println("Number of Args insuffiecient");
         System.exit(0);
     }
 }
//======take input from cla=============================================
try
{
    InputStream is = new FileInputStream(fileName);
    if (is.read() == -1) {
    // The file is empty!
    
         System.err.println("No data found , Please check input");
         System.exit(0);

} 
    Scanner input = new Scanner(new FileInputStream(fileName));// the file name will be in args[0].

    String textFile_puzzle =input.nextLine();
    String textFile_Goal=input.nextLine();
    
    
    int[] initial_state=  extractIntegersFromText(textFile_puzzle);
    int[] goal_state =  extractIntegersFromText(textFile_Goal);  
  
//test File
//Test Puzzle
if(initial_state.length!=16)
{
   System.out.println("Invalid Input for 15 puzzle please enter again");
   System.exit(0);
}   
//Test Goal
   if(goal_state.length!=16)
   {
      System.out.println("Invalid Goal  for 15 puzzle please enter again");
      System.exit(0);
   }   

//============================================================================  
      solvePuzzle(initial_state,goal_state);    
   }
   catch(Exception e)
   {
      System.out.println(e.toString());
      System.exit(0);
   }

}
//Main Ends=====================================================================
//******************************************************
//*** Purpose: This method calls DFS or BFS based on Input
//*** Input:Initial State and Goal State
//*** Output:Void
//*****************************************************/

public static void solvePuzzle(int[] initial_state,int[] goal_state)
{
   Node initial=new Node(initial_state,"S",0);      
         //start at depth 0
         int d=0;

   if(function.equals("DFS"))
     {     
         //===Initialize Frontier and Explored for visited nodes
         Stack<Node> frontier = new Stack<Node>();
         Stack<Node> explored = new Stack<Node>();
   
        //============Add initial to frontier
        frontier.add(initial);
        
        //============calling DFS  
        DFS(frontier,goal_state,d,explored); 
           if(found==false)
    System.out.println("Move not possible for specified depth");
       
   }
 else
   {
      //===Initialize Frontier and Explored for visited nodes
      LinkedList<Node> frontier = new LinkedList<Node>();
      LinkedList<Node> explored = new LinkedList<Node>();

      //============Add initial to frontier 
      frontier.add(initial);
      
      //calling BFS
      BFS(frontier,goal_state,explored);
   }     

}
//=============================================================
//******************************************************
//*** Purpose: This class creates a node as Data Structure to 
//*** storestate,moves and MD
//****************************************************** 
public static class Node<T> 
    {        
      private int[] state;      
      private String moves;
      private int md;  
                 
      public Node(int[] state,String moves,int md) {
                this.state = state; 
                if(this.moves==null){if(moves=="S"){this.moves="";}else{this.moves=moves;}}           
                else this.moves = this.moves.concat(moves); 
                this.md=md;
                }
                             
      public int[] getState(){return this.state;}
      public String getMove(){return this.moves;}
      
      }   
      
//========================================================================
//DFS Starts
//******************************************************
//*** Purpose: This method Recursively call immediate child of front node
//*** Input:   frontier(to stored unvisited nodes),explored(visited nodes)
//***          Goal as goal_state,d as depth 
//*** Output:  void
//******************************************************
//========================================================================

public static void DFS(Stack<Node> frontier,int[] Goal,int d,Stack<Node> explored)
{
   //=====class where moves are defined===================
     puzzlehelper help = new puzzlehelper();
     
   //====Iterate while all childs are not traced==========
     while(!frontier.isEmpty())
      {
         Node top=frontier.pop();
         
    
  //======check if explored has same node already=========
  boolean check1=false;
  for (Iterator itr1 = explored.iterator(); itr1.hasNext();) {
      Node test =(Node) itr1.next();
      check1=compareArrays(test.getState(),top.state);
      if(check1==true){break;}
   }
      if(check1==false)explored.add(top);
  
 //=========get state of top node 
    int[] parent =  top.getState();
    int n=parent.length;
      
      if(compareArrays(parent,Goal))
      {
         System.out.println("Hurrah");
         System.out.println("Moves= "+top.moves);
         System.out.println("Manhatten Distance"+top.md);
         found=true;
         System.exit(0);
       }
   //========Search for 0 location
         int index=indexOfZero(parent);
         
   //=========Check number of moves
          String s=help.checkMoves(index,parent.length);

   //============Iterate for Ever Child============================
for (int i = 0; i <s.length(); i++)
{
      if(d>depth) return;

        char ch = s.charAt(i); 
      //  System.out.println("index=="+index+"  "+"S=  "+s+"   ch="+ch+"   "+s.length()+"  i=  "+i);  
               
        int[] child=new int[n];
        for (int j= 0; j < parent.length; j++)
          {
            child[j]=parent[j];
          } 
           
  //============================================================================  
  //Moves       
      if(ch=='L')
      {     
         child=help.left(child,index,4); 
      }
      else if(ch=='R')
      {
         child=help.right(child,index,4);
      }
      else if (ch=='U')
      {
         child=help.up(child,index,4);
      }      
      else if(ch=='D')
      {
         child=help.down(child,index,4);
      }
     

//=====flag to check if child is in frontier 
//=====first check in frontier
      boolean flag=true;    
     
      for (Iterator itr =frontier.iterator(); itr.hasNext();) {
      Node test =(Node) itr.next();
      boolean check=compareArrays(test.getState(),child);
      if(check==true){flag=false;break;}
      }
      
//======second if not in frontier then check in explored
      if(flag)
      {
      for (Iterator itr = explored.iterator(); itr.hasNext();) {
      Node test =(Node) itr.next();
      boolean check=compareArrays(test.getState(),child);
      if(check==true){flag=false;break;}
      }
      }
     
 //========if not in frontier and explored then add to frontier          
      if(flag)
      {  
         frontier.push(new Node(child,top.moves+Character.toString(ch),top.md+1));         
      }
           
   //recursively call DFS for child nodes
   DFS(frontier,Goal,++d,explored);
   }
  }
  }
//========================================================================
//DFS Ends
//========================================================================

//========================================================================
//BFS Starts
//******************************************************
//*** Purpose: This class works for puzzle using BFS
//*** Input:   frontier(to stored unvisited nodes),explored(visited nodes)
//***          Goal as goal_state
//*** Output:  void
//******************************************************
//========================================================================

public static void BFS(LinkedList<Node> frontier,int[] Goal,LinkedList<Node> explored)
{
   puzzlehelper help = new puzzlehelper();
      long startTime = System.currentTimeMillis();

      while(!frontier.isEmpty())
      {
         Node top=frontier.poll();
         explored.add(top);
         
         if(System.currentTimeMillis()-startTime>30000)
         {
            System.out.println("Move not possible for specified depth");
            System.exit(0);
         }
            
         int[] parent =top.getState();
         int n=parent.length;
            
            if(compareArrays(parent,Goal))
            {
               System.out.println("Hurrah");
               System.out.println("Moves"+top.moves);
               System.out.println("MD ="+top.md);
               System.exit(0);
             }
      //=======Search for 0 location========================================================  
               int index=indexOfZero(parent);    
       //===========Check number of moves
                String s=help.checkMoves(index,parent.length);
      //=====For Every neigbour============================================================
       
      for (int i = 0; i <s.length(); i++)
      {
              char ch = s.charAt(i); 
      //        System.out.println("index=="+index+"  "+"S=  "+s+"   ch="+ch+"   "+s.length()+"  i=  "+i);  
              
              int[] neigbour=new int[n];
               
              for (int j= 0; j < parent.length; j++)
                {
                  neigbour[j]=parent[j];
                } 
                 
        //====Moves ========================================================================     
            if(ch=='L')
               {
                    neigbour=help.left(neigbour,index,4); 
               }      
            else if(ch=='R')
               {
                  neigbour=help.right(neigbour,index,4); 
               }
            else if (ch=='U')
               {
                  neigbour=help.up(neigbour,index,4);
               }
            else if(ch=='D')
               {
                  neigbour=help.down(neigbour,index,4);
               } 
            
            boolean flag=true;
      
      //==check neigbour in frontier    
            for (Iterator itr = frontier.iterator(); itr.hasNext();) {
            Node test =(Node) itr.next();
            boolean check=compareArrays(test.getState(),neigbour);
            if(check==true){flag=false;break;}
            }
      //==check neigbour in explored     
            if(flag)
            {
              for (Iterator itr = explored.iterator(); itr.hasNext();) 
              {
               Node test =(Node) itr.next();
               boolean check=compareArrays(test.getState(),neigbour);
               if(check==true){flag=false;break;}
               }
            }
      
      //==If neigbour not in frontier U explored       
            if(flag)
            {  
               frontier.addLast(new Node(neigbour,top.moves+Character.toString(ch),top.md+1));
            }
      }
      
      }
      }
//==================================================================================
//BFS Ends
//==================================================================================
//******************************************************
//*** Purpose: This method checks index of 0
//*** Input:    Array
//*** Output:   Index of Zero
//******************************************************
public static int indexOfZero(int[] array) {
   for (int i = 0; i < array.length; i++)
    {
        if (array[i] == 0) 
        {
        return i;                
        }
     }
  return -1;
}  

////******************************************************
//*** Purpose: compare 2 arrays
//*** Input:    Array1, Array2
//*** Output:   Boolean based on comparision
//******************************************************
public static boolean compareArrays(int[] array1, int[] array2) {
    boolean b = true;
    for (int i = 0; i < array2.length; i++) {
        if (array2[i] == array1[i]) {
        } else {
            b = false;
        }
    } 
    return b;
}

////******************************************************
//*** Purpose: extract Integers From Text File received
//*** Input:    Initial state/Explored state of source file
//*** Output:   Integer array
//******************************************************
private static int[] extractIntegersFromText(final String source ) { 
 
  String[] integersAsText = source.split(" ");
 
  int[] results = new int[integersAsText.length];
 
  int i = 0; 
 
  for ( String textValue : integersAsText ) 
  {
    results[i] = Integer.parseInt( textValue ); 
    i++; 
  } 
 
  return results ; 
} 

}