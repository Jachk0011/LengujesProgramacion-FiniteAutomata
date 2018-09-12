import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Run {
	
	static char[] alphabet = null;
	static int[][] functionLambda;
	static List automata = new List(), inputs = new List();
	static int states;
	static boolean flag = true;
	
	
	public void creatingStates(int states, char[] finalStates)
	{		
		for(int i=0; i < states; i++)
			automata.addEnd(new Node(i));
		
		
		for(int i=0; i < finalStates.length; i++)
		{
			Node tmp = automata.getHead();
			while(tmp != null && tmp.state != Integer.parseInt(String.valueOf(finalStates[i])))
				tmp = tmp.next;
			if(tmp != null)
				tmp.isFinal = true;
		}
	}
	
	//
	public int knowPositionMatrix(char input, char[] alphabet)
	{
		int counter = 0;
		while(input != alphabet[counter])
			counter++;
		
		return counter;
	}
	
	//using when we've finished to read the inputs and want to know
	// if the actual state is terminal or not
	public boolean verifyingFinalStates(int actualState)
	{
		Node tmp = automata.getHead();
		while(tmp != null)
			if(tmp.state != actualState)
				tmp = tmp.next;
			else
				break; // we got it
		if(tmp != null && tmp.isFinal)
			return true;
		else
			return false;
		
	}
	

	
	public void readFile(String nameFile)
	{
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;

		try 
		{			
			file = new File (nameFile);
			fr = new FileReader (file);
			br = new BufferedReader(fr);
			String line = null;
			
			//int numStates = 0;
			
			
			if(nameFile.equals("alfabetos.txt"))
			{
				line = br.readLine();				
				alphabet = line.toCharArray();									
			}
			
			if(nameFile.equals("estados.txt"))
			{
				states = Integer.parseInt(br.readLine()); 
			}
			
			if(nameFile.equals("cadenas.txt"))
			{				
				while((line = br.readLine()) != null)
					inputs.addEnd(new Node(line));								
			}
			
			if(nameFile.equals("lambda.txt"))
			{
				line = br.readLine();
				int counter = 0;
				
				char[] segments = line.toCharArray();
				functionLambda = new int[states][line.length()];
				
				while(line  != null)
				{					
					segments = line.toCharArray();
					for(int i=0; i < line.length(); i++)
						functionLambda[counter][i] = Integer.parseInt(String.valueOf(segments[i]));
					
					counter++;
					line = br.readLine();
				}	
				
				/* to print the lambda matrix
				for(int i=0; i<states; i++)
				{					
					for(int j=0; j<2; j++)
						System.out.print(functionLambda[i][j] + "\n");
				}*/
				
			}
			
			if(nameFile.equals("aceptacion.txt"))
			{
				line = br.readLine();
				char[] finalStates = line.toCharArray();
				
				this.creatingStates(states, finalStates);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{	         
			try{                    
				if( fr != null ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
	}
	
	public boolean checkInputExistInAlphabet(char[] input)
	{			
		for(int i=0, j=0; i < input.length; i++)
		{
			if(input[i] == alphabet[j])
				continue;//because character exists
			else
			{
				while(j < alphabet.length && input[i] != alphabet[j])
				{
					flag = false;//the character is not passed the test yet
					j++;
				}
				if(j < alphabet.length)
				{
					flag = true; // because j is minor so it's ok
					j = 0; // reset the value to can repeat the for cycle
				}
				if(!flag)//mean that the input character is different of the alphabets options
					return false;
			}
		}		
		return true;
	}
	
	public void runAutomata() throws IOException
	{
		this.readFile("alfabetos.txt");
		this.readFile("estados.txt");
		this.readFile("cadenas.txt");
		this.readFile("lambda.txt");
		this.readFile("aceptacion.txt");
		
		Node tmp = inputs.getHead();
		int c = 1;
		while(tmp != null)
		{
			char[] input = tmp.inputs.toCharArray();			
			
			if(this.checkInputExistInAlphabet(input))
			{
				int currentState = 0;
				for(int i=0; i < input.length; i++)
				{
					int col =  this.knowPositionMatrix(input[i], alphabet);
					int row = currentState;
					currentState = functionLambda[row][col];
				}
				if(this.verifyingFinalStates(currentState))
				{
					System.out.println("line number: " + c++);
					System.out.println("ACCEPT string\n");
				}
				else
				{
					System.out.println("line number: " + c++);
					System.out.println("DENIED string\n");
				}
			}
			else
			{
				System.out.println("line number: " + c++ );
				System.out.println("FAIL: input doesn't exist in the alphabet\n");
			}
			
			
			tmp = tmp.next; //to prove all inputs inside the file
		}		
	}
	
	public static void main(String[] args) throws IOException {
		Run run = new Run();
		run.runAutomata();
	}
}
