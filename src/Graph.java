import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Graph //Graph data structure to work solely with integer Vertices
{   
	private final int V; 				//number of Vertices
	private int E; 						//number of edges (undirected, unweighted)
	private LinkedList<Integer>[] adj; 	//adjacency lists
	private static StringTokenizer st = new StringTokenizer("");  //used in nextInt()
	
	private static int nextInt(BufferedReader in) throws Exception
	{
		while (!st.hasMoreTokens())
			st = new StringTokenizer(in.readLine());
		return Integer.parseInt(st.nextToken());
	}

	@SuppressWarnings("unchecked")
	public Graph(int V)
	{
		this.V=V; 
		this.E=0;
		adj = (LinkedList<Integer>[]) new LinkedList[V];
		for (int v=0;v<V;v++)
			adj[v]=new LinkedList<Integer>();
	}
	
	public Graph(BufferedReader in) throws Exception
	{
		this(nextInt(in));	
		int numEdges = nextInt(in);
		for (int e=0;e<numEdges;e++)
		{
			int v=nextInt(in); 
			int w=nextInt(in);
			addEdge(v,w);
		}
	}
	
	public int V() { return V; }
	public int E() { return E; }
	
	public void addEdge(int v, int w)
	{
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}
	
	public Iterable<Integer> adj(int v)
	{ return adj[v]; }
	
}
