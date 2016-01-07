
import java.io.*;
import java.util.*;

public class printBoard {

	public void printFinalBoard(ArrayList<Integer> finalBoardData)
	{
		String player2Data="",player1Data="",mancala1Data="",mancala2Data="";
		for(int i=0;i<finalBoardData.size()/2-1;i++){
			player1Data+=finalBoardData.get(i)+" ";
		}
		for(int i=finalBoardData.size()-2;i>finalBoardData.size()/2-1;i--){
			player2Data+=finalBoardData.get(i)+" ";
		}
		mancala1Data+=finalBoardData.get(finalBoardData.size()/2-1);
		mancala2Data+=finalBoardData.get(finalBoardData.size()-1);
		
		
		try{
    		
    		File file =new File("next_state.txt");
    		file.createNewFile();
    		FileWriter fileWritter = new FileWriter(file.getName(),false);
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	    bufferWritter.write(player2Data+"\n"+player1Data+"\n"+mancala2Data+"\n"+mancala1Data);
    	    bufferWritter.close();
    		
		}catch(IOException e){
    		e.printStackTrace();
		}		
		
	}
	
	
	public void PrintBoardMiniMaxTraverseLog(int node, int depth, int value,int player) {
		
		try{
			String nodeName;
			if(node == 0 && depth == 0){
				nodeName="root";
			}
			else{
				nodeName=findNodeNameMiniMax(node, player);
			}
    		
    		File file =new File("traverse_log.txt");
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    	        bufferWritter.newLine();
	    	        if(value == Integer.MIN_VALUE){
	    	        	bufferWritter.write(nodeName+","+depth+","+"-Infinity");
	    	        }
	    	        else if(value == Integer.MAX_VALUE){
		        		bufferWritter.write(nodeName+","+depth+","+"Infinity");
	    	        }
	    	        else{
	    	        	bufferWritter.write(nodeName+","+depth+","+value);
	    	        }
	    	        bufferWritter.close();

    		
		}catch(IOException e){
    		e.printStackTrace();
		}		
		
	}



	private String findNodeNameAlphaBeta(int node,int player) {
		String finalNode="";
		
		if(player == 1){
			finalNode="B";
			finalNode+=(int)(node+2);

		}
		else{
			finalNode="A";
			int across = Math.abs(node-AlphaBetaAlgo.PLAY_PITS);
			finalNode+=(int)(across+2);

			
		}
		
		return finalNode;
	}

	private String findNodeNameMiniMax(int node,int player) {
		String finalNode="";
		
		if(player == 1){
			finalNode="B";
			finalNode+=(int)(node+2);

		}
		else{
			finalNode="A";
			int across = Math.abs(node-miniMaxAlgo.PLAY_PITS);
			finalNode+=(int)(across+2);

			
		}
		
		return finalNode;
	}
	public void PrintBoardMiniMaxTraverseLogStart(String string, String string2, String string3) {
		try{
    		
    		File file =new File("traverse_log.txt");
    		
    		//if file doesnt exists, then create it
    		file.createNewFile();
    		FileWriter fileWritter = new FileWriter(file.getName(),false);
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	    bufferWritter.write("Node,Depth,Value");
    	    bufferWritter.close();
    		
    		
		}catch(IOException e){
    		e.printStackTrace();
		}		
		
	}
	
	
	public void PrintBoardAlphaBetaTraverseLog(int node, int depth, int value,int player,String alpha, String beta) {
		
		try{
			String nodeName;
			if(node == 0 && depth == 0){
				nodeName="root";
			}
			else{
				nodeName=findNodeNameAlphaBeta(node, player);
			}
    		
    		File file =new File("traverse_log.txt");
    		
    		FileWriter fileWritter = new FileWriter(file.getName(),true);
    			 BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    	        bufferWritter.newLine();
	    	        if(value == Integer.MIN_VALUE){
	    	        	bufferWritter.write(nodeName+","+depth+","+"-Infinity"+","+alpha+","+beta);
	    	        }
	    	        else if(value == Integer.MAX_VALUE){
		        		bufferWritter.write(nodeName+","+depth+","+"Infinity"+","+alpha+","+beta);
	    	        }
	    	        else{
	    	        	bufferWritter.write(nodeName+","+depth+","+value+","+alpha+","+beta);
	    	        }
	    	        bufferWritter.close();

    	
    		
		}catch(IOException e){
    		e.printStackTrace();
		}		
		
	}


	public void PrintBoardAlphaBetaTraverseLogStart(String string, String string2, String string3, String string4,
			String string5) {
		
		try{
    		
    		File file =new File("traverse_log.txt");
    		file.createNewFile();
			FileWriter fileWritter = new FileWriter(file.getName(),false);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
 	        bufferWritter.write("Node,Depth,Value,Alpha,Beta");
 	        bufferWritter.close();

		}catch(IOException e){
    		e.printStackTrace();
		}		
		
	}
}
