import java.io.BufferedReader;
import java.io.InputStreamReader;


public class KevinBacon
{
	public static void main(String[] args) throws Exception
	{
		String fileName = args[0];
		String delim = args[1];
		String sourceVertex = args[2]; //"Bacon, Kevin"
		
		SymbolGraph sg = new SymbolGraph(fileName, delim);
		Graph G = sg.G();
		
		if (!sg.contains(sourceVertex)) 
		{
			System.out.println(sourceVertex + " is not in database."); 
			return;
		}
		
		int sourceNum = sg.getHm().get(sourceVertex); //finds the integer vertex representation
		BfsPaths BFS = new BfsPaths(G, sourceNum); //creates a shortest paths tree
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String destinationVertex;
		while ( (destinationVertex=in.readLine()) != null)
		{
			if (sg.getHm().containsKey(destinationVertex))
			{
				int destinationNum = sg.getHm().get(destinationVertex);
				Iterable<Integer> path = BFS.pathTo(destinationNum);
				for (int v : path)
					System.out.println("   " + sg.getMh()[v]);
			}
			else 
				System.out.println(destinationVertex + " is not in database.");
		}
		
	}
}
