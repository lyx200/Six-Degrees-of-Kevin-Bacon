import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class SymbolGraph //dealing with arbitrary Vertex names
{	//associates names/symbols with integers for use in regular Graph
	
	private HashMap<String, Integer> hm; //String to int mapping
	private String[] mh; //inverse mapping: int to String
	private Graph G;
	
	public SymbolGraph(String fileName, String delim) throws Exception 
	{	//builds from a .txt file 
		hm = new HashMap<>();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line;
		
		//first scan: associate all String names with integer values
		while ( (line=in.readLine()) != null )
		{
			String[] names = line.split(delim);
			for (int x=0;x<names.length;x++)
			{
				String name = names[x];
				if (!hm.containsKey(name)) 
					hm.put(name,hm.size());
			}
		}
		//build inverse mapping
		mh = new String[hm.size()];
		for (String name : hm.keySet())
		{
			int num = hm.get(name);
			mh[num] = name;
		}
		
		//second scan: actually build the Graph
		G = new Graph(hm.size());
		in = new BufferedReader(new FileReader(fileName));
		while ( (line=in.readLine()) != null )
		{
			String[] names = line.split(delim);
			int v = hm.get(names[0]);
			for (int x=1;x<names.length;x++)
			{
				int w = hm.get(names[x]);
				G.addEdge(v, w);
			}
		}
		
	}
	
	public boolean contains(String s) { return hm.containsKey(s); }	
	public int index(String s) { return hm.get(s); }
	public String name(int v) { return mh[v]; } 
	public Graph G() { return G; }
	public HashMap<String, Integer> getHm() { return hm; }
	public String[] getMh() { return mh; }
	
}
