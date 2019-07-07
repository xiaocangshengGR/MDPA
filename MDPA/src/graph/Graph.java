package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Iterator;

/**
 * ClassName: Graph.java
 * Description:  The definition of graph.
 * BufferSize : The size of buffer.
 * NodeNum : The number of nodes in graph.
 * EdgeNum : The number of Edges in graph.
 * NodeSet : A HashMap. <Node_ID,Node>
 * EdgeList : A list. <Edge>
 * Is_directed : The graph is directed or not.
 * GlobalProbability : A HashMap,<label,probability>. The global probability of labels in graph.
 */

public class Graph implements Serializable {

	private static final long serialVersionUID = 1L;
	public int BufferSize;            
	public int NodeNum;                   
	public int EdgeNum;                    
	
	public Map<String,Node> NodeSet;  
	public List<Edge> EdgeList;        
	public boolean Is_directed;
	
	public Map<String,Double> GlobalProbability;       
	public Graph(String filepath,int bufferSize,boolean is_directed)
	{
		this.NodeSet=new HashMap<String,Node>();
		this.GlobalProbability=new HashMap<String,Double>();
		this.EdgeList=new ArrayList<Edge>();

		this.Is_directed=is_directed;
		this.BufferSize=bufferSize;     //定义buffer大小
		this.CreateGraph(filepath);               //读取文件，构造图
		this.NodeNum=NodeSet.size();
		this.EdgeNum=EdgeList.size();
	}
	
	/**
	 * Breadth-first search
	 * @param VisitedNode : A set stores visited nodes.
	 * @param que : Auxiliary Queue.
	 * @return The next node.
	 */
	public String BFS(Set<String> VisitedNode,Queue<String> que)
	{
		String return_node="end";
		if(!(que.isEmpty()))
		{
			String now_node=que.poll();
			if(!VisitedNode.contains(now_node))
			{
				VisitedNode.add(now_node);
				return_node=now_node;
				Map<String,Double> nei_node=NodeSet.get(now_node).neighborNode;
				Iterator<Map.Entry<String, Double>> it=nei_node.entrySet().iterator();
				while(it.hasNext()) 
				{
					Map.Entry<String, Double> en=(Map.Entry<String, Double>)it.next();
					String nei=en.getKey();
					if(!VisitedNode.contains(nei))
					{
						que.offer(nei);
					}
					
				}
			}
			else
			{
				return_node="visited";
			}
		}
		return return_node;
	}
	/**
	 * whether the edge repeat or not
	 */
	public boolean is_directed_edge_repeating(Edge edge,List<Edge> EdgeList)
	{
		boolean flag=false;
		Node start=edge.StartNode;
		Node end=edge.EndNode;
		Double value=edge.value;
		int i=0;
		for(i=0;i<EdgeList.size();i++)
		{
			Node s=EdgeList.get(i).StartNode;
			Node e=EdgeList.get(i).EndNode;
			Double v=EdgeList.get(i).value;
			if((s.NodeName.equals(start.NodeName)
					&&e.NodeName.equals(end.NodeName)
					&&v.equals(value)))
			{
				flag=true;
				break;
			}
			
		}
		return flag;
	}
	/**
	 * whether the edge repeat or not
	 */
	public boolean is_undirected_edge_repeating(Edge edge,List<Edge> EdgeList)
	{
		boolean flag=false;
		Node start=edge.StartNode;
		Node end=edge.EndNode;
		Double value=edge.value;
		int i=0;
		for(i=0;i<EdgeList.size();i++)
		{
			Node s=EdgeList.get(i).StartNode;
			Node e=EdgeList.get(i).EndNode;
			Double v=EdgeList.get(i).value;
			if((s.NodeName.equals(start.NodeName)
					&&e.NodeName.equals(end.NodeName)
					&&v.equals(value))
				||(s.NodeName.equals(end.NodeName)
						&&e.NodeName.equals(start.NodeName)
						&&v.equals(value)))
			{
				flag=true;
				break;
			}
			
		}
		return flag;
	}
	/**
	 * @param filepath : file path
	 * Create graph according to read file.
	 */
	public void CreateGraph(String filepath)
	{
		try {
			File file=new File(filepath);
			if (!(file.exists()))
			{
				System.out.println("File is Not Exist!");  
			}else
			{
				int sum=0;
				int sum_edge=0;
				int sum_node=0;
				InputStreamReader in = new InputStreamReader(new FileInputStream(file));
				BufferedReader bf=new BufferedReader(in);  
				String rowline="";
				while((rowline=bf.readLine())!=null)       
				{
					sum++;
					double value=1;
					String [] str=rowline.split("\t| ");     
					Node start=new Node("");
					Node end=new Node("");
					if(str.length==1)                     
					{//Acnode
						value=1;
						start=end=new Node(str[0]);				
						if(!NodeSet.containsKey(str[0]))   
						{
							NodeSet.put(str[0], start);
							sum_node++;
						}
					} else
					if(str.length==2)                    
					{//unweighted graph
						value=1;
						start=new Node(str[0]);
						end=new Node(str[1]);
						Edge tempedge=new Edge(start,end,value);
						
						if(!this.Is_directed)//undirected
						{
							if(!is_undirected_edge_repeating(tempedge,this.EdgeList))
							{//The edge is not repeated
								sum_edge++;
								this.EdgeList.add(tempedge);       
								if(!NodeSet.containsKey(str[0]))   
								{
									start.neighborNode.put(str[1], value);
									NodeSet.put(str[0], start);	
									sum_node++;
								}else                              
								{
									start=NodeSet.get(str[0]);     
									if(!start.neighborNode.containsKey(str[1]))
									{
										start.neighborNode.put(str[1], value);
									}
									NodeSet.put(str[0], start);
								}
								if(!NodeSet.containsKey(str[1]))  //同上
								{
									end.neighborNode.put(str[0], value);
									NodeSet.put(str[1], end);	
									sum_node++;
								}else
								{
									start=NodeSet.get(str[1]);
									if(!start.neighborNode.containsKey(str[0]))
									{
										start.neighborNode.put(str[0], value);
									}
									NodeSet.put(str[1], start);
								}
							}//!is_undirected_edge_repeating
						}else 
						{//directed
							if(!is_directed_edge_repeating(tempedge,this.EdgeList))
							{//The edge is not repeated
								sum_edge++;
								this.EdgeList.add(tempedge);       
								if(!NodeSet.containsKey(str[0]))   
								{
									start.neighborNode.put(str[1], value);
									NodeSet.put(str[0], start);	
									sum_node++;
								}else                              
								{
									start=NodeSet.get(str[0]);     
									if(!start.neighborNode.containsKey(str[1]))
									{
										start.neighborNode.put(str[1], value);
									}
									NodeSet.put(str[0], start);
								}
								if(!NodeSet.containsKey(str[1]))  
								{
									NodeSet.put(str[1], end);
									sum_node++;
								}
							}//!is_directed_edge_repeating
						}
					} else
					if(str.length==3)     //weighted graph
					{
						value=Double.parseDouble(str[2]);   
						start=new Node(str[0]);
						end=new Node(str[1]);
						Edge tempedge=new Edge(start,end,value);
						if(!this.Is_directed)
						{//undirected
							if(!is_undirected_edge_repeating(tempedge,this.EdgeList))
							{//The edge is not repeated
								sum_edge++;
								this.EdgeList.add(tempedge);       
								if(!NodeSet.containsKey(str[0]))   
								{
									start.neighborNode.put(str[1], value);
									NodeSet.put(str[0], start);	
									sum_node++;
								}else                              
								{
									start=NodeSet.get(str[0]);     
									if(!start.neighborNode.containsKey(str[1]))
									{
										start.neighborNode.put(str[1], value);
									}
									NodeSet.put(str[0], start);
								}
								if(!NodeSet.containsKey(str[1]))  
								{
									end.neighborNode.put(str[0], value);
									NodeSet.put(str[1], end);	
									sum_node++;
								}else
								{
									start=NodeSet.get(str[1]);
									if(!start.neighborNode.containsKey(str[0]))
									{
										start.neighborNode.put(str[0], value);
									}
									NodeSet.put(str[1], start);
								}
							}//!is_undirected_edge_repeating
						}else 
						{//directed
							if(!is_directed_edge_repeating(tempedge,this.EdgeList))
							{//The edge is not repeated
								sum_edge++;
								this.EdgeList.add(tempedge);       
								if(!NodeSet.containsKey(str[0]))   
								{
									start.neighborNode.put(str[1], value);
									NodeSet.put(str[0], start);	
									sum_node++;
								}else                             
								{
									start=NodeSet.get(str[0]);    
									if(!start.neighborNode.containsKey(str[1]))
									{
										start.neighborNode.put(str[1], value);
									}
									NodeSet.put(str[0], start);
								}
								if(!NodeSet.containsKey(str[1]))  
								{
									NodeSet.put(str[1], end);	
									sum_node++;
								}
							}//!is_directed_edge_repeating
						}
					}
				}//while
				System.out.println("The number of lines: "+sum+".\tThe number of edges: "+sum_edge+".\tThe number of nodes: "+sum_node);
				bf.close();
				in.close();
			} 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

}
