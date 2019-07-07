package mdpa_entrance;

import graph.Graph;
import graph.Node;
import graph.Overlapping_Community_Partition_O;

import java.util.*;

/**
 * Modularity EQ considers the impact of overlapping nodes and is more 
 * suitable for overlapping community detection than modularity Q.
 * Created by Yangwenzhe, on 2019/1/16.
 */
public class CalculateEQ {
	public static double calculateEQOfShen(Graph g, Overlapping_Community_Partition_O overlapPartition,boolean is_direact){
        //
        double q = 0.0d;
        int m = g.EdgeNum;
        if(!is_direact)
        {
        	m=m*2;
        }
        Map<String, List<String>> node_Communities = overlapPartition.get_node_community();

        Map<String,List<String>> communities = overlapPartition.getcommunity();
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
                    if(nodei.neighborNode.containsKey(nodej_name) ){
                        Aij=1;//节点i与j之间有边相连
                    }
                    int Ki=nodei.neighborNode.size();//节点i的度
                    int Kj=nodej.neighborNode.size();//节点j的度
                    
                    int oi=1;
                    int oj=1;
                    if(node_Communities.containsKey(nodei_name)) 
                    {
                        oi = overlapPartition.get_node_community().get(nodei_name).size();
                        if(oi == 0){
                            System.out.println(nodei_name+"****非法值****");
                        }
                    }
                    if(node_Communities.containsKey(nodej_name)) 
                    {
                        oj = overlapPartition.get_node_community().get(nodej_name).size();
                        if(oj == 0){
                            System.out.println(nodej_name+"****非法值****");
                        }
                    }
                    double temp = ((double)1/(oi*oj))*(Aij - (double)(Ki*Kj)/(m));
                    q+=temp;
                }
            }
        }
        q= q/(m);

        return q;
    }
    public static void main(String[] args){
		// TODO Auto-generated method stub
    } 
}
