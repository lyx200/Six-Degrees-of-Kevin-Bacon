import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class SymbolGraph //dealing with arbitrary Vertex names
{	//associates names/symbols with integers for use in regular Graph
	
	private HashMap<String, Integer> hm; //String to int mapping
	private String[] mh; //inverse mapping: int to String
	private Graph G;
	
	//builds from MySQL
	public SymbolGraph(Connection con) throws Exception 
	{
		hm = new HashMap<>();
		Statement st = con.createStatement();
		
		//first scan: associate all String names with integer values
		ResultSet rs = st.executeQuery("SELECT * FROM actors; ");
		while ( rs.next() ) 
		{
			String name = rs.getString("name");
			String movie_id = rs.getString("movie_id");
			if (!hm.containsKey(name)) hm.put(name,hm.size());
			if (!hm.containsKey(movie_id)) hm.put(movie_id,hm.size());
		}
		//build inverse mapping
		mh = new String[hm.size()];
		for (String name : hm.keySet())
		{
			int num = hm.get(name);
			mh[num] = name;
		}
		
		//second scan: build the Graph
		G = new Graph(hm.size());
		rs = st.executeQuery("SELECT * FROM actors; ");
		while ( rs.next() ) 
		{
			String name = rs.getString("name");
			String movie_id = rs.getString("movie_id");
			int v = hm.get(name);
			int w = hm.get(movie_id);
			G.addEdge(v, w);
		}
		
		st.close();
	}
	
	public boolean contains(String s) { return hm.containsKey(s); }	
	public int index(String s) { return hm.get(s); }
	public String name(int v) { return mh[v]; } 
	public Graph G() { return G; }
	public HashMap<String, Integer> getHm() { return hm; }
	public String[] getMh() { return mh; }
	
}
