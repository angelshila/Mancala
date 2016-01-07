
import java.io.*;
import java.util.*;

public class mancala {

	public static void main(String[] args) throws IOException {
			
////		    File inFile = null;
////			if (0 < args.length) {
////			   inFile = new File(args[1]);
////			} else {
////			   System.err.println("Invalid arguments count:" + args.length);
////			   System.exit(0);
////		}
			System.out.println("Anu");
			long startTime = System.currentTimeMillis();
			File dir = new File("/Users/Anushila/Dropbox/Fall 2015/AI/Homeworks/Homework 2_Mancala");
			File fin = new File(dir.getCanonicalPath() + File.separator + "input.txt");

//			readFile(inFile);
			
			readFile(fin);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime/1000+" sec");
			System.out.println("Anu");
		}

	@SuppressWarnings("resource")
	private static void readFile(File fin) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(fin));
		
		//Reading Task
		int task=Integer.parseInt(br.readLine().trim());
		
		//Read Player
		int player=Integer.parseInt(br.readLine().trim());
		
		//Read Cut-Off Depth
		int cutOff=Integer.parseInt(br.readLine().trim());
		
		//Board States for Players 2 and 1
		ArrayList<Integer> board=new ArrayList<Integer>(); 
		String state_player2[]=br.readLine().split(" ");
		String state_player1[]=br.readLine().split(" ");
		int mancala2_stones=Integer.parseInt(br.readLine().trim());
		int mancala1_stones=Integer.parseInt(br.readLine().trim());
		
		//Filling the Board
		for(String p:state_player1)
			board.add(Integer.parseInt(p));
		board.add(mancala1_stones);
		for(int i=state_player2.length-1;i>=0;i--)
			board.add(Integer.parseInt(state_player2[i]));
		board.add(mancala2_stones);
		
		//Board Data
		int Total_Pits=board.size();
		int Play_Pits=board.size()-2;
		int mancala1=board.size()/2-1;
		int mancala2=board.size()-1;

		if(task == 1){
			//Creating object of Game Class and adding data
			GreedyAlgo g=new GreedyAlgo(player,board, Total_Pits, Play_Pits, mancala1, mancala2,cutOff);
			g.Greedy();

		}
		else if(task == 2){
			miniMaxAlgo m=new miniMaxAlgo(player,board, Total_Pits, Play_Pits, mancala1, mancala2,cutOff);
			m.miniMax();
		}
		
		else {
			AlphaBetaAlgo ab=new AlphaBetaAlgo(player,board, Total_Pits, Play_Pits, mancala1, mancala2,cutOff);
			ab.alphaBeta();
		}


		
	}
		

	

}
