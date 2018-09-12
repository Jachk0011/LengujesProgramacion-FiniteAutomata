import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class List {
	private Node head = null;
	
	
	
	//return the i-th  node gave a index
	public Node getNode(int index)
	{
		if(index < this.sizeList())
		{
			Node temp = this.getHead();
			for(int i=0; i<index; i++)
				temp = temp.next;
			
			return temp;
		}
		return null;
	}
	
	//get Tail
	public Node getTail()
	{
		Node temp = this.getHead();
		while(temp.next != null)
			temp = temp.next;
		
		return temp;
	}
	
	//return head
	public Node getHead()
	{
		return this.head;
	}
	
	// Tell us if the list is empty
	public boolean emptyList(){
		return (head!=null) ? false : true;
	}
	
	// return the size of he list as a integer
	public int sizeList()
	{
		int size = 0;
		if(emptyList())
			return 0;
		else
		{
			Node temp = head;
			while(temp.next != null)
			{
				temp = temp.next;
				size++;
			}				
			return size;
		}
	}
	
	// Let print all nodes in the List using Buffered
	public void printList() throws IOException
	{
		if(this.emptyList())
			System.out.println("The list is empty");
		else
		{
			Node temp = head;
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(System.out));
			
			while(temp != null)
			{
				br.write(temp.toString());
				temp = temp.next;
			} 
			br.flush();
			//br.close();
		}
		
	}

	
	
	// add at end of the list the new node get as parameter. Also check if the list is Empty 
	public void addEnd(Node n)
	{
		if(emptyList())
			this.head = n;
		else
		{
			Node temp = getHead();
			while(temp.next != null)		
				temp = temp.next;
			temp.next = n;
			n.next = null;
		}				
	}
	
	//add at begin of the list the new node get as parameter.
	public void addBegin(Node n)
	{
		if(emptyList())
			head = n;
		else
		{
			Node temp = head;
			head = n;
			n.next = temp;
		}
	}

	//add at any position of the list the new node get as parameter.  
	public void addAnyPosition(Node n, int position)
	{				
		if(this.emptyList())
			head = n;
		else if(position>sizeList()+1)		
			System.out.println("The position exceed the size of the list");
		else if(position == 1 )
			this.addBegin(n);		
		else
		{
			Node temp = head;
			
			for(int i=0; i<position-2; i++)
				temp = temp.next;
			n.next = temp.next;
			temp.next = n;
		}			
	}

	
	
	//delete head node
	public void deleteBeginList()
	{
		@SuppressWarnings("unused")
		Node temp = head;
		head = head.next;
		temp.next = null;
		System.gc();		
	}

	//delete tail node
	public void deleteEndList(){
		Node temp = head;
		while(temp.next.next != null)
			temp = temp.next;		
		temp.next = null;		
		System.gc();
	}

	//delete the node got for parameter
	public void deleteAnyPosition(int position)
	{
		if(position == 1)
			this.deleteBeginList();		
		else if(position == this.sizeList())
			this.deleteEndList();
		else if(position>this.sizeList())
			System.out.println("The position of item to delete is superior to size list");
		else{
			Node temp = head, garbage;
			for(int i=1; i<position-1; i++)
				temp = temp.next;
			garbage = temp.next;
			temp.next = temp.next.next;
			garbage.next = null;
			System.gc();
		}
		
	}


}
