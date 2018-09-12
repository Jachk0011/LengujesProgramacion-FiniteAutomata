
public class Node {
	public int state;
	public char transition;
	public boolean isFinal;
	public String inputs;
	public Node next;
	
	//CONSTRUCTORS 
	public Node (){}
	
	public Node (int state)
	{
		this.state = state;
	}
	public Node (String input)
	{
		this.inputs = input;
	}
	
	// NICE VIEW DATA
	public String toString()
	{
		return "input: " + this.inputs + "\n";
		//return "state: " + this.state + "\n";
	}
	
	
	
	
}
