import java.util.LinkedList;
import java.util.Stack;

//class to build the shortest paths tree from a source vertex
public class BfsPaths
{
	private final int s; //source vertex
	private Graph G;
	private boolean[] marked; //has been visited
	private int[] edgeTo; //parent vertex in the shortest paths tree
	private LinkedList<Integer> queue;
	
	//creates the shortest paths tree from s, stores in edgeTo[]
	public BfsPaths(Graph G, int s)  
	{
		this.G=G;
		this.s=s;
		marked = new boolean[G.V()]; 
		edgeTo = new int[G.V()];
		queue = new LinkedList<>();
	
		BFS();	
	}
	
	//traverses entire graph. sets edgeTo[] array for path retrieval
	private void BFS()
	{
		queue.addLast(s);
		marked[s] = true;
		edgeTo[s] = s;
		
		while (!queue.isEmpty())
		{
			int v = queue.pollFirst();
			for (int w : G.adj(v))
				if (!marked[w])
				{
					queue.addLast(w);
					marked[w] = true;
					edgeTo[w] = v;
				}
		}
	}
	
	public Iterable<Integer> pathTo(int t)
	{
		if (!hasPathTo(t)) return null;
		Stack<Integer> path = new Stack<>();
		for (int temp=t; temp!=s; temp=edgeTo[temp])
			path.push(temp);
		path.push(s);
		//note: Java library's Stack Iterator is flawed. Doesn't preserve stack LIFO order. pop() & use linked list
		queue = new LinkedList<>();
		while ( !path.isEmpty() )
			queue.add(path.pop());
		return queue;
	}
	
	public boolean hasPathTo(int t)
	{ return marked[t]; }
	
}
