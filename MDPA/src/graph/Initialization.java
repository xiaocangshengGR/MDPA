
package graph;

import java.io.Serializable;

import java.util.*;
/**
 * ClassName: Initialization.java
 * Description:   Initialization Process
 * step1() : Node's own ID number is pushed into the label part of buffer at 
 * the beginning, and the membership degree is set as 1/B where B is buffersize.
 * 
 * step2() : Node's neighbor ID numbers will be randomly selected for B-1 times, 
 * with the membership degree adding 1/B correspondingly.
 */
public class Initialization implements Serializable {

	private static final long serialVersionUID = 1L;
	public int bool;
	public Initialization(Graph g)
	{
		bool=0;
		step1(g);
		step2(g);
	}
	public boolean step1(Graph g)
	{
		boolean flag=false;
		
		Iterator<Map.Entry<String, Node>> iter=g.NodeSet.entrySet().iterator();
		Map<String,Node> tempnodeset=new HashMap<String,Node>();
		Double value=1/(double)(g.BufferSize);
		while(iter.hasNext())
		{
			Map.Entry<String, Node> temp=(Map.Entry<String, Node>)iter.next();
			String tempnodename=temp.getKey();
			Node tempnode=temp.getValue();
			tempnode.buffer.put(tempnodename, value);
			tempnodeset.put(tempnodename, tempnode);
		}
		g.NodeSet=tempnodeset;
		flag=true;
		return flag;
	}
	public boolean step2(Graph g)
	{
		Double value=1/(double)(g.BufferSize);
		boolean flag=false;
		Map<String,Node> now=(Map<String,Node>)g.NodeSet;
		Iterator<Map.Entry<String, Node>> iter=now.entrySet().iterator();
		int buffersize=g.BufferSize;
		Map<String,Node> tempnodeset=new HashMap<String,Node>();
		while(iter.hasNext())
		{
			Map.Entry<String, Node> temp=(Map.Entry<String, Node>)iter.next();
			String tempnodename=temp.getKey();
			Node tempnode=temp.getValue();
			List<String> templist = new ArrayList<String>();
			int neighnodenum=tempnode.neighborNode.size();
			if(neighnodenum>0)
			{
				for(Map.Entry<String, Double> entry : tempnode.neighborNode.entrySet())
				{
					templist.add(entry.getKey());
				}
				for(int i=1;i<buffersize;i++)    
				{
					Random randomnum=new Random();
					int rn=randomnum.nextInt(neighnodenum); 
					String key=templist.get(rn);
					if(tempnode.buffer.containsKey(key))
					{
						Double tempvalue=tempnode.buffer.get(key)+value;
						tempnode.buffer.put(key, tempvalue);
					}
					else
					{
						tempnode.buffer.put(key, value);
					}
					
				}
			}
			else
			{
				if(neighnodenum==0)   
				{
					tempnode.buffer.put(tempnodename, 1d);
				}
			}
			tempnodeset.put(tempnodename, tempnode);
		}
		g.NodeSet=tempnodeset;
		flag=true;
		return flag;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
