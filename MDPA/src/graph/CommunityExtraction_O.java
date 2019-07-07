package graph;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CommunityExtraction_O.java
 * Description:  Etract communities according to the graph after membership degree propagation.
 * Communities are overlapping here and overlapping nodes are added into different communities they belong to.
 * The resulting partition can be used to calculate EQ.
 */
public class CommunityExtraction_O implements Serializable {

	private static final long serialVersionUID = 1L;


	//r is a predefined threshold
	Double r=0d;
	
	public CommunityExtraction_O(Double rr)
	{
		this.r=rr;
	}
	public CommunityExtraction_O()
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
	 * @param OCP_O : The resulting partition
	 * @param L : The community ID list
	 * The ith node vi is assigned to cluster c, if mc(i) > r, where r is a predefined threshold.
	 */
	public void extract_overlopping_community(Graph g,Overlapping_Community_Partition_O OCP_O
			,List<String> L)
	
	{
		Map<String,Node> all_nodes=g.NodeSet;
		
		
		Iterator<Map.Entry<String, Node>> it=all_nodes.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Node> entry=(Map.Entry<String, Node>) it.next();
			String node_name=entry.getKey();
			Node label_node=entry.getValue();
			Map<String,Double> label_buffer=label_node.buffer;
			Iterator<Map.Entry<String, Double>> iter=label_buffer.entrySet().iterator();
			List<String> temp_community=new ArrayList<String>();//The temp_community is used to store cluster or community label 
			List<String> temp_nodelist=new ArrayList<String>();//The temp_nodelist is used to store all 
															   //labels in the buffer in the case of no m>r
			Double max=0d;
			String max_label="";
			//To find labels whose m>r and find the label with largest m.
			while(iter.hasNext())
			{
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
			if(len==0)        //No m>r
			{
				temp_community.add(max_label);
				Map<String,List<String>> community=OCP_O.getcommunity();
				Map<String,List<String>> node_community=OCP_O.get_node_community();
				Map<String,List<String>> overlapping_node=OCP_O.get_overlapping_node();
				
				int i=0;
				for(i=0;i<temp_nodelist.size();i++)   //No m>r. All communities that labels correspond to in buffer should be overlapping communities.
				{
					String nn=temp_nodelist.get(i);
					if(L.contains(nn))
					{
						if(community.containsKey(nn))     
						{
							List<String> temp=community.get(nn);
							temp.add(node_name);
							community.put(nn, temp);
							
						}else
						{
							
								List<String> temp=new ArrayList<String>();  
								temp.add(node_name);
								community.put(nn, temp);  
							
						}
					}
				}
				
				node_community.put(node_name, temp_nodelist);
				overlapping_node.put(node_name, temp_nodelist);
				
				OCP_O.set_overlapping_node(overlapping_node);
				OCP_O.setcommunty(community);                
				OCP_O.set_node_community(node_community);    
				System.out.println("No mc(i) > r,r = "+r+" in the buffer of node "+node_name+"\n");
			}
			else
			{
				if(len>=2)
				{//This node is an overlapping node.
					Map<String,List<String>> community=OCP_O.getcommunity();
					Map<String,List<String>> node_community=OCP_O.get_node_community();//
					Map<String,List<String>> overlopping_node=OCP_O.get_overlapping_node();
		
					int i=0;
					for(i=0;i<temp_community.size();i++)
					{
						if(L.contains(temp_community.get(i)))
						{
							if(community.containsKey(temp_community.get(i)))
							{
								List<String> temp=community.get(temp_community.get(i));
								temp.add(node_name);
								community.put(temp_community.get(i), temp);
							}
							else
							{
								
									List<String> temp=new ArrayList<String>();
									temp.add(node_name);
									community.put(temp_community.get(i), temp);
								
							}
						}
					}
				
					temp_community.remove(max_label);
					temp_community.add(max_label);
					
					node_community.put(node_name, temp_community);
					overlopping_node.put(node_name, temp_community);
					OCP_O.setcommunty(community);    
					OCP_O.set_overlapping_node(overlopping_node);
					OCP_O.set_node_community(node_community);
				}
				else
				{   //This node is not an overlapping node
					Map<String,List<String>> community=OCP_O.getcommunity();
					Map<String,List<String>> node_community=OCP_O.get_node_community();
					if(community.containsKey(temp_community.get(0)))     
					{
						List<String> temp=community.get(temp_community.get(0));
						temp.add(node_name);
						community.put(temp_community.get(0), temp);
						
					}else
					{
						if(L.contains(temp_nodelist.get(0)))
						{
						List<String> temp=new ArrayList<String>();  
						temp.add(node_name);
						community.put(temp_community.get(0), temp);  
					
						}
					}
					node_community.put(node_name, temp_community);
					OCP_O.setcommunty(community);
					OCP_O.set_node_community(node_community);    
				}
			}
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}
}
