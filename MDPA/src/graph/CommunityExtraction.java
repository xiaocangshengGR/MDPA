package graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * ClassName: CommunityExtraction.java
 * Description:  Etract communities according to the graph after membership degree propagation.
 * Communities are not overlapping here. 
 * An overlapping node is assigned to only one community, but we still store overlapping nodes  
 * The resulting partition can be used to calculate Q.
 * 
 */
public class CommunityExtraction implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	Double r=0d;
	
	public CommunityExtraction(Double rr)
	{
		this.r=rr;
	}
	public CommunityExtraction()
	{
	}
	public void set_r(Double rr)
	{
		this.r=rr;
	}
	public Double get_r()
	{
		return this.r;
	}
	/**
	 * @param g : The graph after membership degree propagation.
	 * @param OCP : The resulting partition
	 */
	public void extract_overlopping_community(Graph g,Overlapping_Community_Partition OCP)
	{
		
		Map<String,Node> all_nodes=g.NodeSet;
		
		
		Iterator<Map.Entry<String, Node>> it=all_nodes.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Node> entry=(Map.Entry<String, Node>) it.next();
			String node_name=entry.getKey();
			Node now_node=entry.getValue();
			Map<String,Double> label_buffer=now_node.buffer;
			Iterator<Map.Entry<String, Double>> iter=label_buffer.entrySet().iterator();
			List<String> temp_community=new ArrayList<String>();  //The temp_community is used to store cluster or community label
			List<String> temp_nodelist=new ArrayList<String>();   //The temp_nodelist is used to store all 
			   													  //labels in the buffer in the case of no m>r
			Double max=0d;
			String max_label="";
			while(iter.hasNext())
			{//To find labels whose m>r and find the label with maximum m.
				Map.Entry<String, Double> en=(Map.Entry<String, Double>)iter.next();
				if(en.getValue()>max)
				{
					max=en.getValue();
					max_label=en.getKey();
				}
				if(en.getValue()>r)
				{
					temp_community.add(en.getKey());
				}
				temp_nodelist.add(en.getKey());
			}
			int len=temp_community.size();
			if(len==0)       //No m>r
			{
				temp_community.add(max_label);
				Map<String,List<String>> community=OCP.getcommunity();
				Map<String,String> node_community=OCP.get_node_community();
				Map<String,List<String>> overlapping_node=OCP.get_overlapping_node();
				//No m>r. The node is an overlapping node
				//We assign it to only one community whose label has maximum m.
				if(community.containsKey(temp_community.get(0)))     
				{
					List<String> temp=community.get(temp_community.get(0));
					temp.add(node_name);
					community.put(temp_community.get(0), temp);
					
				}else
				{
					List<String> temp=new ArrayList<String>();  
					temp.add(node_name);
					community.put(temp_community.get(0), temp);  
				}
				
				node_community.put(node_name, temp_community.get(0));
				overlapping_node.put(node_name, temp_nodelist);
				
				OCP.set_overlapping_node(overlapping_node);
				OCP.setcommunty(community);                
				OCP.set_node_community(node_community);    
				System.out.println("No mc(i) > r,r = "+r+"in the buffer of node "+node_name+"\n");
			}
			else
			{
				if(len>=2)
				{//This node is an overlapping node.
					Map<String,List<String>> community=OCP.getcommunity();
					Map<String,String> node_community=OCP.get_node_community();//
					Map<String,List<String>> overlopping_node=OCP.get_overlapping_node();
					if(community.containsKey(max_label))
					{
						List<String> temp=community.get(max_label);
						temp.add(node_name);
						community.put(max_label, temp);
					}
					else
					{
						List<String> temp=new ArrayList<String>();
						temp.add(node_name);
						community.put(max_label, temp);
					}
					
					temp_community.remove(max_label);
					temp_community.add(max_label);
					
					node_community.put(node_name, max_label);
					overlopping_node.put(node_name, temp_community);
					OCP.setcommunty(community);    
					OCP.set_overlapping_node(overlopping_node);
					OCP.set_node_community(node_community);
				}
				else
				{
					//This node is not an overlapping node
					Map<String,List<String>> community=OCP.getcommunity();
					Map<String,String> node_community=OCP.get_node_community();
					if(community.containsKey(temp_community.get(0)))     
					{
						List<String> temp=community.get(temp_community.get(0));
						temp.add(node_name);
						community.put(temp_community.get(0), temp);
						
					}else
					{
						List<String> temp=new ArrayList<String>();  
						temp.add(node_name);
						community.put(temp_community.get(0), temp);  
					}
					node_community.put(node_name, temp_community.get(0));
					OCP.setcommunty(community);
					OCP.set_node_community(node_community);    
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			
	}
	

}
