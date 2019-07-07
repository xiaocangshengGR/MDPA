package mdpa_entrance;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graph.CommunityExtraction;
import graph.CommunityExtraction_O;
import graph.Graph;
import graph.Initialization;
import graph.MembershipDgreePropagation;
import graph.Node;
import graph.Overlapping_Community_Partition;
import graph.Overlapping_Community_Partition_O;
import graph.final_value;

/**
 * ClassName: MDPA.java
 * Description:  MDPA entrance.
 * 
 */
public class MDPA implements Serializable{


	private static final long serialVersionUID = 1L;
	public static void SerializeGraph(Graph g,String path) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(path)));
		oo.writeObject(g);
		oo.flush();
		oo.close();
	}
	public static Graph get_SerializeGraph(String path) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(path));  
		 
	    ObjectInputStream oin = new ObjectInputStream(fis);  

	    Graph g = (Graph) oin.readObject(); 
	    oin.close();
	    return g;
	}  
	
	public static void SerializeCommunitySum(List<String> l,String p) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(p)));
		oo.writeObject(l);
		oo.flush();
		oo.close();
	}
	public static List<String> get_SerializeCommunitySum(String p) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(p));  
		 
	    ObjectInputStream oin = new ObjectInputStream(fis);  

	    List<String> l = (List<String>) oin.readObject(); 
	    oin.close();
	    return l;
	}
	
	public static void Serialize_OCP(Overlapping_Community_Partition OCP,String p) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(p)));
		oo.writeObject(OCP);
		oo.flush();
		oo.close();
	}
	public static Overlapping_Community_Partition get_Serialize_OCP(String p) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(p));  
		 
	    ObjectInputStream oin = new ObjectInputStream(fis);  

	    Overlapping_Community_Partition OCP = (Overlapping_Community_Partition) oin.readObject(); 
	    oin.close();
	    return OCP;
	}
	
	
	public static void Serialize_OCP_O(Overlapping_Community_Partition_O OCP_O,String p) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(p)));
		oo.writeObject(OCP_O);
		oo.flush();
		oo.close();
	}
	public static Overlapping_Community_Partition_O get_Serialize_OCP_O(String p) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(p));  
		 
	    ObjectInputStream oin = new ObjectInputStream(fis);  

	    Overlapping_Community_Partition_O OCP_O = (Overlapping_Community_Partition_O) oin.readObject(); 
	    oin.close();
	    return OCP_O;
	}
	
	
	public static void running_time_int_file(String ss,String p)
	{
		try {
			FileOutputStream out=new FileOutputStream(p);
			out.write(ss.getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int get_number_of_community(Graph g)
	{
		int sum=0;
		List<String> community_id_list=new ArrayList<String>();
		Iterator it=g.NodeSet.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Node> en=(Map.Entry<String, Node>)it.next();
			Node node=en.getValue();
			Map<String,Double> labelbuffer=node.buffer;
			Iterator it1=labelbuffer.entrySet().iterator();
			Double max=0d;
			String max_label="";
			while(it1.hasNext())
			{
				Map.Entry<String, Double> en1=(Map.Entry<String, Double>)it1.next();
				String label_name=en1.getKey();
				Double temp=en1.getValue();
				if(temp>max)
				{
					max_label=label_name;
					max=temp;
				}
			}
			if(!community_id_list.contains(max_label))
			{
				sum++;
				community_id_list.add(max_label);
			}
		}
		return sum;
	}
	
	public static void community_into_file(String ss,String ss_o,String ss_oc,String filepath)
	{
		String path=filepath+"node_community.txt";
		String path_o=filepath+"node_communities.txt";
		String path_oc=filepath+"overlapping_community.txt";
		try {
			FileOutputStream out=new FileOutputStream(path);
			out.write(ss.getBytes());
			out.close();
			FileOutputStream out_o=new FileOutputStream(path_o);
			out_o.write(ss_o.getBytes());
			out_o.close();
			FileOutputStream out_oc=new FileOutputStream(path_oc);
			out_oc.write(ss_oc.getBytes());
			out_oc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Without excellent computing resources, 
	 * we recommend not running multiple test graphs at the same time.
	 */
	public static void main(String[] args) {
		try
		{
			int [] buffersize_list=final_value.buffersize_list;
			String [] filepath_list=final_value.file_path_list;
			boolean [] is_directed_list=final_value.is_directed_list;
			String [] output_path_list=final_value.path;
			for(int i=8;i<9;i++)
			{
				int buffersize=buffersize_list[i];
				String filepath=filepath_list[i];
				boolean is_directed=is_directed_list[i];
				String output_path=".\\"+output_path_list[i]+"\\";
				System.out.print("File:   ");System.out.println(filepath);
				System.out.print("buffersize:    ");System.out.println(buffersize);
				System.out.print("Is directed?  ");System.out.println(is_directed);
				
				//1.Read graph.
				System.out.println("Read graph!");
				Graph g=new Graph(filepath,buffersize,is_directed);
				System.out.println("Serialize Read-in graph!");
				SerializeGraph(g,output_path+"read_graph.txt");
				String running_time="";
				Long program_start_time=System.currentTimeMillis();
				Graph gg = get_SerializeGraph(output_path+"read_graph.txt");
					
				//2.Initialization
				System.out.println("Begin Initialization!");
				Initialization init=new Initialization(gg);
				System.out.println("End of initialization!");
				System.out.println("Serialize initialized graph!");
				SerializeGraph(gg,output_path+"graph_init.txt");
				//3.Membership degree propagation
				System.out.println("Begin membership degree propagation!");
				new MembershipDgreePropagation (gg);
				System.out.println("End of membership degree propagation!");
				System.out.println("Serialize graph after membership degree propagation!");
				SerializeGraph(gg,output_path+"graph_mdp.txt");
				
				//4.Community partition
				Graph g_mdp= get_SerializeGraph(output_path+"graph_mdp.txt");
				int sum=get_number_of_community(g_mdp);
				System.out.println("The number of selected cluster labels: "+sum);
				Double r=(double)1/sum;
				Overlapping_Community_Partition OCP=new Overlapping_Community_Partition();
				CommunityExtraction CE=new CommunityExtraction(r);
				System.out.println("Begin community detection!");
				CE.extract_overlopping_community(g_mdp, OCP);
				List<String> CommunityID_List=new ArrayList<String>();
				Iterator it=OCP.getcommunity().entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry<String, List<String>> en=(Map.Entry<String, List<String>>)it.next();
					String CommunityID=en.getKey();
					CommunityID_List.add(CommunityID);
				}
				SerializeCommunitySum(CommunityID_List,output_path+"CummunitySum.txt");
				System.out.println("The number of community: "+OCP.getcommunity().size()+" "+CommunityID_List.size());
				System.out.println("The number of overlapping nodes: "+OCP.get_overlapping_node().size());
				System.out.println("Serialize community!");
				Serialize_OCP(OCP,output_path+"OCP.txt");
				
				List<String> L=get_SerializeCommunitySum(output_path+"CummunitySum.txt");
				Overlapping_Community_Partition_O OCP_O=new Overlapping_Community_Partition_O();
				CommunityExtraction_O CE_O=new CommunityExtraction_O(r);
				CE_O.extract_overlopping_community(g_mdp, OCP_O,L);
				System.out.println("Serialize overlapping community!");
				Serialize_OCP_O(OCP_O,output_path+"OCP_O.txt");
				
				//5.Output community into file.
				Map<String,String> node_community=OCP.get_node_community();
				Iterator it_OCP=node_community.entrySet().iterator();
				String string_OCP="";
				while(it_OCP.hasNext())
				{
					Map.Entry<String, String> en=(Map.Entry<String, String>)it_OCP.next();
					String label=en.getKey();
					String community=en.getValue();
					string_OCP+=label+"\t"+community+"\n";
				}
			    Map<String,List<String>> node_community_O=OCP_O.get_node_community();
				Iterator it_OCP_O=node_community_O.entrySet().iterator();
				String string_OCP_O="";
				while(it_OCP_O.hasNext())
				{
					Map.Entry<String, List<String>> en=(Map.Entry<String, List<String>>)it_OCP_O.next();
					String label=en.getKey();
					List<String> community=en.getValue();
					string_OCP_O+=label;
					for(int ii=0;ii<community.size();ii++)
					{
						string_OCP_O+="\t"+community.get(ii);
					}
					string_OCP_O+="\n";
				}
				String string_oc="";
				Map<String,List<String>> overlapping_community=OCP_O.getcommunity();
				Iterator it_community=overlapping_community.entrySet().iterator();
				while(it_community.hasNext())
				{
					Map.Entry<String, List<String>> en=(Map.Entry<String, List<String>>)it_community.next();
					String community_ID=en.getKey();
					string_oc=string_oc+community_ID+":";
					List<String> node_list=overlapping_community.get(community_ID);
					for(int j=0;j<node_list.size();j++)
					{
						string_oc=string_oc+"\t"+node_list.get(j);
					}
					string_oc=string_oc+"\n";
				}
				System.out.println("Output community into "+output_path);
				community_into_file(string_OCP,string_OCP_O,string_oc,output_path);
				Long program_end_time=System.currentTimeMillis();
				Long program_running_time =program_end_time-program_start_time;
				running_time=running_time+"Running time: "+program_running_time.toString()+"\n";
				running_time_int_file(running_time,output_path+"running_time.txt");
				System.out.println("Running time: "+(program_end_time-program_start_time));
			}
			
			
		} catch(NullPointerException ie) {
			// TODO Auto-generated catch block
			System.out.println("Input Format Error!");
		} catch(FileNotFoundException ie) {
			// TODO Auto-generated catch block
			System.out.println("File is Not Exist!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
