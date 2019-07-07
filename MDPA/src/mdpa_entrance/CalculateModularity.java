package mdpa_entrance;

import graph.Graph;
import graph.Node;

import graph.Overlapping_Community_Partition;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Calculate Q
 * Created by Yangwenzhe, on 2019/1/16.
 */
public class CalculateModularity {

	public static double calculateModularity(Graph g, Overlapping_Community_Partition OCP,boolean is_directed){
		double q = 0.0d;
        int m = g.EdgeNum;

        if(!is_directed)
        {
        	m=m*2;
        }

        Map<String,List<String>> communities = OCP.getcommunity();
        Iterator<Map.Entry<String,List<String>>> iterator = communities.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,List<String>> entry = (Map.Entry<String,List<String>>)iterator.next();

            List<String> nodes_in_community = (List<String>) entry.getValue();
            for(int i=0;i < nodes_in_community.size();i++){
                for(int j=0;j <nodes_in_community.size();j++){
                	int Aij = 0;
                    String nodei_name=nodes_in_community.get(i);
                    String nodej_name=nodes_in_community.get(j);
                    Node nodei = g.NodeSet.get(nodei_name);
                    Node nodej = g.NodeSet.get(nodej_name);
                    if(nodei==null)
                    {
                    	System.out.println("nodei:"+nodei_name);
                    }
                    if(nodej==null)
                    {
                    	System.out.println("nodej"+nodej_name);
                    }
                    if(nodei.neighborNode.containsKey(nodej_name) ){
                        Aij=1;//节点i与j之间有边相连
                    }
                    int Ki=nodei.neighborNode.size();//节点i的度
                    int Kj=nodej.neighborNode.size();//节点j的度
                  

                    double temp = Aij - ((double)(Ki*Kj)/(m));
                    q+=temp;
                }
            }
        }
        q= q/(m);
        return q;
    }
}
