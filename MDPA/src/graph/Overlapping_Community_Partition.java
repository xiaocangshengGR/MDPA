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
import java.util.Random;

/**
 * ClassName: Overlapping_Community_Partition.java
 * Description:  The result of community Extraction.
 * 
 * community : HashMap,<Community_ID,node_list>.
 * Communities are not overlapped here and one 
 * node only belongs to one community.
 * 
 * node_community : HashMap,<node_ID,community_ID>.
 * Node_ID belongs to the community that community_ID corresponds to.
 * 
 * overlapping_node : HashMap,<overlapingnode,community_list>.
 * The nodes are all overlapped here and we store all overlapping nodes
 * by this way.
 * 
 */
public class Overlapping_Community_Partition extends BasicPartition implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	Map<String,String> node_community=new HashMap<String,String>(); 
	Map<String,List<String>> overlopping_node=new HashMap<String,List<String>>();

	public void setcommunty(Map<String,List<String>> com)
	{
		this.community=com;
	}

	public Map<String,List<String>> getcommunity()
	{
		return this.community;
	}

	public void set_node_community(Map<String,String> com)
	{
		this.node_community=com;
	}

	public Map<String,String> get_node_community()
	{
		return this.node_community;
	}

	public void set_overlapping_node(Map<String,List<String>> over)
	{
		this.overlopping_node=over;
	}

	public Map<String,List<String>> get_overlapping_node()
	{
		return this.overlopping_node;
	}
	
	/**
	 * @param path
	 * Get standard community partition from path.
	 */
	public void getcommunity_from_file(String path)
	{
		File file=new File(path);
		if(file.exists())
		{
			try {
				InputStreamReader in=new InputStreamReader(new FileInputStream(file));
				BufferedReader read=new BufferedReader(in);
				String line="";
				int sum=0;
				while((line=read.readLine())!=null)
				{
					
					String [] str=line.split("\t| ");
					sum++;
					int len=str.length;
					if(len<=1)
					{
						System.out.println(str[0]+"输入错误\n");
						System.out.println(sum+"\n");
					}
					else
					{
						String nodename=str[0];
						if(len>2)
						{
							List<String> overlopping_list=new ArrayList<String>();
							Random randomnum=new Random();
							int rn=randomnum.nextInt(len-1)+1; 
							if(this.community.containsKey(str[rn]))
							{
								List<String> list=this.community.get(str[rn]);
								list.add(nodename);		
								this.community.put(str[rn], list);
							}
							else
							{
								List<String> list=new ArrayList<String>();
								list.add(nodename);
								this.community.put(str[rn], list);
							}
							int ii=0;
							for(ii=1;ii<=len-1;ii++)
							{
								overlopping_list.add(str[ii]);
							}
							
							overlopping_node.put(nodename, overlopping_list);
							
							this.node_community.put(nodename, str[rn]);
						}
						else
						{
							if(this.community.containsKey(str[1]))
							{
								List<String> list=this.community.get(str[1]);
								list.add(nodename);
								this.community.put(str[1], list);
							}
							else
							{
								List<String> list=new ArrayList<String>();
								list.add(nodename);
								this.community.put(str[1], list);
							}
							node_community.put(nodename, str[1]);
						}
					}
				}
				read.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			System.out.println("文件不存在！\n");
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
}
