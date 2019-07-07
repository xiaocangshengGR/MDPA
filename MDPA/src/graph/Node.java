package graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ClassName: Node.java
 * Description:  The definition of edge in graph.
 * NodeName : Node ID.
 * neighborNode : A HashMap,<neighborNode,weight>
 * buffer : The buffer.
 */
public class Node implements Serializable {

	private static final long serialVersionUID = 1L;

	public String NodeName;   

	public Map<String,Double> neighborNode;   
	
	public Map<String,Double> buffer;        
	public Node (String name)
	{
		neighborNode=new HashMap<String,Double>();
		buffer=new HashMap<String,Double>();
		NodeName=name;
	}
	public String FindMaxProbability()
	{
		Iterator<Map.Entry<String,Double>> iter=buffer.entrySet().iterator();
		Double max=0d;
		String name="no";
		while(iter.hasNext())
		{
			Map.Entry<String,Double> temp=(Map.Entry<String,Double>)iter.next();
			Double value=temp.getValue();
			if(value>max)
			{
				name=temp.getKey();
				max=value;
			}
		}
		return name;
		
	}
	public String FindMinProbability()
	{
		Iterator<Map.Entry<String,Double>> iter=buffer.entrySet().iterator();
		Double min=2d;
		String name="no";
		while(iter.hasNext())
		{
			Map.Entry<String,Double> temp=(Map.Entry<String,Double>)iter.next();
			Double value=temp.getValue();
			if(value<min)
			{
				name=temp.getKey();
				min=value;
			}
		}
		return name;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
