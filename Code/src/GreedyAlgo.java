
import java.util.*;

public class GreedyAlgo {
	
	@SuppressWarnings("unused")
	private int MAX_SCORE_PIT, MIN_SCORE_PIT,TOTAL_PITS,PLAY_PITS,PLAYER,DEPTH;
	ArrayList<Integer>board =new ArrayList<Integer>();
	
	//Constructor
	public GreedyAlgo(int player, ArrayList<Integer> b,int totPits,int playPits, int mancala1, int mancala2, int cutOff){
		this.board=b;
		this.PLAYER=player;
		if(PLAYER==1){
			
			this.MAX_SCORE_PIT = mancala1;
			this.MIN_SCORE_PIT = mancala2;
		}
		else if(PLAYER==2){
			this.MAX_SCORE_PIT = mancala2;
			this.MIN_SCORE_PIT = mancala1;
		}
		this.TOTAL_PITS=totPits;
		this.PLAY_PITS=playPits;
		this.DEPTH=cutOff;		
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void Greedy(){
		
		//If PLAYER can't move, flush the stones on the other side
		if(cannotMove(PLAYER,board)){
			board=flushStones(otherPlayer(PLAYER),board);
		}
		//If opponent can't move, flush the stones on the PLAYER's side
		else if(cannotMove(otherPlayer(PLAYER),board)){
			board=flushStones(PLAYER,board);
		}
		
		//Start Greedy
		else{
			
			//Cloning the original board and keeping track of best move
			ArrayList<Integer> boardTemp;
			ArrayList<Integer> boardBest = null;
			int pitBest = 0, maxValue, evalValue;
			int Mancala_Max=Integer.MIN_VALUE;
			ArrayList<Integer> FinalScores =new ArrayList<Integer>();
			
			if(PLAYER ==1){
				for(int i=0;i<MAX_SCORE_PIT;i++){	
					boardTemp=(ArrayList<Integer>) board.clone();	
//					System.out.println("Parent"+" "+boardTemp);
					
					//Choice contains 0 stones
					if(boardTemp.get(i)==0)
						continue;
					
					//Start Greedy
					ArrayList<Integer> maxScore = takemove(boardTemp,i);
//					System.out.println("MaxScoreChild"+" "+maxScore);
					
				//Board returned has no moves for PLAYER. Hence, flush stones on the other side.
					if(maxScore!=null && cannotMove(1,maxScore)){
						maxScore=flushStones(otherPlayer(1),maxScore);
					}
				//Board returned has no moves for opponent. Hence, flush stones on PLAYER's side.
					else if(maxScore!=null && cannotMove(otherPlayer(1),maxScore)){
						maxScore=flushStones(1,maxScore);
					}
					
				//Evaluate Mancala Score and find Max value
					evalValue=Eval(maxScore,MAX_SCORE_PIT,MIN_SCORE_PIT);
					FinalScores.add(evalValue);	
					maxValue=Collections.max(FinalScores);
//					System.out.println(evalValue);
					
					if(Mancala_Max<maxValue){
						Mancala_Max=maxValue;
						boardBest=(ArrayList<Integer>) maxScore.clone();
						pitBest=i;
					}

				}
				
//				System.out.println("Best"+boardBest);

			}
			
			//PLAYER == 2
			else{
				
				for(int i=board.size()-2;i>=board.size()/2;i--){
					boardTemp=(ArrayList<Integer>) board.clone();	
//					System.out.println("Parent"+" "+boardTemp);
					
					//Choice contains 0 stones
					if(boardTemp.get(i)==0)
						continue;
					
					//Start Greedy
					ArrayList<Integer> maxScore=takemove(boardTemp,i);	
//					System.out.println("MaxScoreChild"+" "+maxScore);
				
				//Board returned has no moves for PLAYER. Hence, flush stones on the other side.
					if(maxScore!=null && cannotMove(2,maxScore)){
						maxScore=flushStones(otherPlayer(2),maxScore);
					}
				//Board returned has no moves for opponent. Hence, flush stones on PLAYER's side.
					else if(maxScore!=null && cannotMove(otherPlayer(2),maxScore)){
						maxScore=flushStones(2,maxScore);
					}
					
				//Evaluate Mancala Score and find Max value
					evalValue=Eval(maxScore,MAX_SCORE_PIT,MIN_SCORE_PIT);
					FinalScores.add(evalValue);
					maxValue=Collections.max(FinalScores);
//					System.out.println(evalValue);
					
					if(Mancala_Max<maxValue){
						Mancala_Max=maxValue;
						boardBest=(ArrayList<Integer>) maxScore.clone();
						pitBest=i;
					}

					
				}
		
//				System.out.println("Best"+boardBest);


			}
			
			board=(ArrayList<Integer>) boardBest.clone();
		}
		
		//Print Board in file
		printBoard pB=new printBoard();
		pB.printFinalBoard(board);
	
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Integer> takemove(ArrayList<Integer> b, int choice) {
		int avoidMancala;
		int stones=b.get(choice);
		int nextPit=choice+1;
		
		//Find Mancala to avoid
		if(PLAYER == 1){
			avoidMancala=b.size()-1;
		}
		else{
			avoidMancala=b.size()/2-1;	
		}
		
		//Set nextPit to point to play pits
		if(nextPit==avoidMancala) {
			if (avoidMancala == b.size()-1){
				nextPit = 0; //avoid comp i.e. player 2 mancala for human
			}
			else if( avoidMancala == b.size()/2-1){
				nextPit = b.size()/2;//avoid human i.e. player 1 mancala for comp
			}
		}
		
		//Empty selected pit
		b.set(choice, 0);
		
		while(stones>0){
			
			//function to steal stones
			//function to avoid mancala of opponent 
			//function to change player
			
			if(nextPit==avoidMancala) {
				if (avoidMancala == b.size()-1){
					nextPit = 0; //avoid comp i.e. player 2 mancala for human
				}
				else if( avoidMancala == b.size()/2-1){
					nextPit = b.size()/2;//avoid human i.e. player 1 mancala for comp
				}
			}
			else if(nextPit == board.size()){
				nextPit = 0;
			}
			
			if(stones==1 && PLAYER ==1 && b.get(nextPit) == 0 && nextPit!=MAX_SCORE_PIT && (nextPit>-1 && nextPit<board.size()/2-1)){
//				b.set(MAX_SCORE_PIT, b.get(MAX_SCORE_PIT)+1);
				stones--;
				stealStones(nextPit, b);
				
			}
			
			else if(stones==1 && PLAYER ==2 && b.get(nextPit) == 0 && nextPit!=MAX_SCORE_PIT && (nextPit>board.size()/2-1 && nextPit<board.size()-1)){
//				b.set(MAX_SCORE_PIT, b.get(MAX_SCORE_PIT)+1);
				stones--;
				stealStones(nextPit, b);
			}
			
			
			//Landed in one's own Mancala. Return something  so that turns are not changed 
			else if(stones == 1 && PLAYER == 1 &&  nextPit == b.size()/2-1){
				
				ArrayList<Integer> Temp = null;
				ArrayList<Integer> Scores = new ArrayList<Integer>();
				ArrayList<Integer> TempboardBest = null;
				stones--;
				b.set(MAX_SCORE_PIT, b.get(MAX_SCORE_PIT)+1);
				
				int TempMax=Integer.MIN_VALUE;

				if(cannotMove(PLAYER,b)){
					Temp=flushStones(otherPlayer(PLAYER),b);
					return Temp;
				}
				
				else if(cannotMove(otherPlayer(PLAYER),b)){
					Temp=flushStones(PLAYER,b);
					return Temp;
				}
				
				else {
						for(int ch=0;ch<MAX_SCORE_PIT;ch++){
							ArrayList<Integer> bClone = (ArrayList<Integer>) b.clone();
							if(bClone.get(ch)==0)
								continue;

							Temp = takemove(bClone,ch);
							
							if(Temp!=null && (cannotMove(PLAYER,Temp))){
								Temp=flushStones(otherPlayer(PLAYER),Temp);
							}
							
							else if(Temp!=null && (cannotMove(otherPlayer(PLAYER),Temp))){
								Temp=flushStones(PLAYER,Temp);
							}
//							System.out.println("Temp"+Temp);	
							
							int evalValue=Eval(Temp,MAX_SCORE_PIT,MIN_SCORE_PIT);
							Scores.add(evalValue);
							int maxValue=Collections.max(Scores);
							if(TempMax<maxValue){
								TempMax=maxValue;
								TempboardBest = new ArrayList<Integer>(Temp);
							}
							
						}
						
						return TempboardBest;
					}

			}
			
			//Landed in one's own Mancala. Return something  so that turns are not changed 
			else if(stones == 1 && PLAYER==2 && nextPit == b.size()-1){
				stones--;
				ArrayList<Integer> Scores = new ArrayList<Integer>();
				ArrayList<Integer> Temp=null;
				
				b.set(MAX_SCORE_PIT, b.get(MAX_SCORE_PIT)+1);
				int TempMax = Integer.MIN_VALUE;
				ArrayList<Integer> TempboardBest = null;
				
				if(cannotMove(PLAYER,b)){
					Temp=flushStones(otherPlayer(PLAYER),b);
					return Temp;
				}
				//return something so that turns are not changed
				
				else {
						for(int ch=board.size()-2;ch>=board.size()/2;ch--){
							ArrayList<Integer> bClone = (ArrayList<Integer>) b.clone();
							if(bClone.get(ch)==0)
								continue;
							
							Temp = takemove(bClone,ch);
//							System.out.println("Temp"+Temp);
							
							if(Temp!=null && (cannotMove(PLAYER,Temp))){
								Temp=flushStones(otherPlayer(PLAYER),Temp);
							}
							
							int evalValue=Eval(Temp,MAX_SCORE_PIT,MIN_SCORE_PIT);
							Scores.add(evalValue);
							int maxValue=Collections.max(Scores);
							if(TempMax<maxValue){
								TempMax=maxValue;
								TempboardBest = new ArrayList<Integer>(Temp);
							}
							
						}

					return TempboardBest;

				}
			}
			
			//Normal Move
			else{
				int currentInNext=b.get(nextPit);
				b.set(nextPit, currentInNext+1); //Incrementing value in next pits
				stones--; //Decrementing Stones
				nextPit++;
			}
		}	
	
		return b;
	}

	//Steal Stones rule implementation
	private void stealStones(int nextPit,ArrayList<Integer> boardState) {
		int across=findacross(nextPit);
		if (boardState.get(across) > 0){
            boardState.set(MAX_SCORE_PIT, boardState.get(MAX_SCORE_PIT)+1+boardState.get(across)); // take the piece just landed
            boardState.set(nextPit,0);//empty bin on human side
            boardState.set(across, 0) ;
		}
		else{
			boardState.set(MAX_SCORE_PIT, boardState.get(MAX_SCORE_PIT)+1);
			boardState.set(nextPit,0);
		}
		
	}

	//Flush Stones
	public ArrayList<Integer> flushStones (int player, ArrayList<Integer> b) {
		
		if(player == 1){
			for (int i = 0; i <board.size()/2-1; i++) {
				b.set(board.size()/2-1, b.get(board.size()/2-1)+b.get(i));
				b.set(i, 0);
				
			}
			
		}
		else{
			
			for (int i = board.size()/2; i <board.size()-1; i++) {
				b.set(board.size()-1, b.get(board.size()-1)+b.get(i));
				b.set(i, 0);
				
			}
		}
		return b;
		
	}

	//Find the other player
	private int otherPlayer(int pLAYERturn) {
		if(pLAYERturn==1){
			return 2;
		}
		else{
			return 1;
		}	
	}

	//Check if player can move or not
	public boolean cannotMove(int player,ArrayList<Integer> b) {
		// Returns true if the currentPlayer cannot move.
		if(player == 1){
			for (int i = 0; i < board.size()/2-1; i++) {
				if (b.get(i) > 0) {  
				// System.out.println("CannotMove: player = " + currentPlayer + "; result = " + false);
				return false;
				}
			}
		}
		
		else{//player == 2
			for (int i = board.size()/2; i < board.size()-1; i++) {
				if (b.get(i) > 0) {  
				// System.out.println("CannotMove: player = " + currentPlayer + "; result = " + false);
				return false;
				}
			}
		}
		
		// System.out.println("CannotMove: player = " + currentPlayer + "; result = " + true);
		return true;
	}
	
	//Eval Function
		private int Eval(ArrayList<Integer> maxScore, int mAX_SCORE_PIT2, int mIN_SCORE_PIT2) {
			
			return (maxScore.get(mAX_SCORE_PIT2)-maxScore.get(mIN_SCORE_PIT2));
		}

	//find pit across
	private int findacross(int ending) {
		int across = -555;
	    across = Math.abs(ending-PLAY_PITS);   
	    return across;
	}

}
