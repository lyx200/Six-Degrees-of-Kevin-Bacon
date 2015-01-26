import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class KevinBacon
{
	private static final String URL = "jdbc:mysql://localhost:3306/imdb";
	private static final String USER = "root";
	private static final String PASSWORD = "OMITTED";
	private static HashMap<String, String> hm_identifierTitle = new HashMap<>();
	
	public static void main(String[] args) throws Exception
	{
		//retrieve data from MySQL and build identifier -> movie title hashmap
		Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM movies; ");
		
		while ( rs.next() ) 
		{
            String identifier = rs.getString("identifier");
            String title = rs.getString("title");
            hm_identifierTitle.put(identifier, title);
        }
		st.close();
		
		//Build the graph
		SymbolGraph sg = new SymbolGraph(con);
		Graph G = sg.G();
		con.close();
		
		String sourceVertex = "Kevin Bacon"; //(or any other actor as source vertex)
		if (!sg.contains(sourceVertex)) 
		{
			System.out.println(sourceVertex + " is not in the database."); 
			return;
		}
		
		int sourceVertexNum = sg.getHm().get(sourceVertex); //finds the integer vertex representation
		BfsPaths BFS = new BfsPaths(G, sourceVertexNum); //creates a shortest paths tree
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String destinationVertex;
		while ( (destinationVertex=in.readLine()) != null)
		{
			if (sg.getHm().containsKey(destinationVertex))
			{
				int destinationNum = sg.getHm().get(destinationVertex);
				Iterable<Integer> path = BFS.pathTo(destinationNum);
				for (int v : path)
				{
					if (hm_identifierTitle.containsKey(sg.getMh()[v]))
						System.out.println("   " + hm_identifierTitle.get(sg.getMh()[v]));
					else 
						System.out.println("   " + sg.getMh()[v]);
				}	
			}
			else 
				System.out.println(destinationVertex + " is not in database.");
		}
			
	}
}
