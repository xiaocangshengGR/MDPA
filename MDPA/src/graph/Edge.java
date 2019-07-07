package graph;

import java.io.Serializable;

/**
 * ClassName: Edge.java
 * Description:  The definition of edge in graph.
 * StartNode : The start of an edge.
 * EndNode : The end of an edge.
 * value : The weight of an edge.
 */
public class Edge implements Serializable {


	private static final long serialVersionUID = 1L;
	
	public Node StartNode;     //边的起点
	public Node EndNode;    //边终点
	public double value;       //边权值
	
	public Edge(Node start,Node end,double va)//构造函数
	{
		StartNode=start;
		EndNode=end;
		value=va;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
