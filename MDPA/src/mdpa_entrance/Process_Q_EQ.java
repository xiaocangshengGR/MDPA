package mdpa_entrance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import graph.Graph;
import graph.Overlapping_Community_Partition;
import graph.Overlapping_Community_Partition_O;
import graph.final_value;

/**
 * ClassName: Process_Q_EQ.java
 * Description:  Calculate Q and EQ for the results of test graphs.
 * 
 */
public class Process_Q_EQ {

	
	public static void SerializeGraph_lab(Graph g,String p) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(".\\"+p+"\\graph_mdp.txt")));
		oo.writeObject(g);
		oo.flush();
		oo.close();
	}
	public static Graph get_SerializeGraph_lab(String p) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(".\\"+p+"\\graph_mdp.txt"));  
		 
	    ObjectInputStream oin = new ObjectInputStream(fis);  

	    Graph g = (Graph) oin.readObject(); 
	    oin.close();
	    return g;
	} 
	public static void Serialize_OCP_O(Overlapping_Community_Partition_O OCP,String p) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(".\\"+p+"\\OCP_O.txt")));
		oo.writeObject(OCP);
		oo.flush();
		oo.close();
	}
	public static Overlapping_Community_Partition_O get_Serialize_OCP_O(String p) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(".\\"+p+"\\OCP_O.txt"));  
		 
	    ObjectInputStream oin = new ObjectInputStream(fis);  

	    Overlapping_Community_Partition_O OCP = (Overlapping_Community_Partition_O) oin.readObject(); 
	    oin.close();
	    return OCP;
	}
	public static void Serialize_OCP(Overlapping_Community_Partition OCP,String p) throws FileNotFoundException, IOException
	{
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File(".\\"+p+"\\OCP.txt")));
		oo.writeObject(OCP);
		oo.flush();
		oo.close();
	}
	public static Overlapping_Community_Partition get_Serialize_OCP(String p) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(new File(".\\"+p+"\\OCP.txt"));  
		 
	    ObjectInputStream oin = new ObjectInputStream(fis);  

	    Overlapping_Community_Partition OCP = (Overlapping_Community_Partition) oin.readObject(); 
	    oin.close();
	    return OCP;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			int i=0;
			boolean is_directed[]=final_value.is_directed_list;
			String path[]= final_value.path;
			//6
			for(i=0;i<8;i++)
			{
				System.out.print(path[i]+":\t");
				Graph g= get_SerializeGraph_lab(path[i]);
				Overlapping_Community_Partition OCP_SpeakEasy=get_Serialize_OCP(path[i]);
				double q1=CalculateModularity.calculateModularity(g, OCP_SpeakEasy,is_directed[i]);
				System.out.print("Q£º"+q1+"\t");
				
				Overlapping_Community_Partition_O OCP_O_spe=get_Serialize_OCP_O(path[i]);
				double eq1=CalculateEQ.calculateEQOfShen(g, OCP_O_spe, is_directed[i]);
				System.out.println("EQ£º"+eq1);
					
			}//for i
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
