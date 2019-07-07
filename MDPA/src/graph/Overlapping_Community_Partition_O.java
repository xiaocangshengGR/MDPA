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

/**
 * ClassName: Overlapping_Community_Partition_O.java
 * Description:  The result of community Extraction.
 * 
 * community : HashMap,<Community_ID,node_list>.Communities 
 * are overlapped here and overlapping nodes are added into 
 * different communities they belong to.
 * 
 * node_community : HashMap,<node_ID,community_list>.
 * Node_ID belongs to the communities in community_list.
 * 
 * overlapping_node : HashMap,<overlapingnode,community_list>.
 * The nodes are all overlapped here.
 */
public class Overlapping_Community_Partition_O extends BasicPartition implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	Map<String,List<String>> node_community=new HashMap<String,List<String>>(); 
	Map<String,List<String>> overlopping_node=new HashMap<String,List<String>>();

	public void setcommunty(Map<String,List<String>> com)
	{
		this.community=com;
	}

	public Map<String,List<String>> getcommunity()
	{
		return this.community;
	}

	public void set_node_community(Map<String,List<String>> com)
	{
		this.node_community=com;
	}

	public Map<String,List<String>> get_node_community()
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
						System.out.println(str[0]+"Input Error!\n");
						System.out.println(sum+"\n");
					}
					else
					{
						String nodename=str[0];
						if(len>2)
						{
							List<String> overlopping_list=new ArrayList<String>();

							int ii=0;
							for(ii=1;ii<=len-1;ii++)
							{
								if(this.community.containsKey(str[ii]))
								{
									List<String> temp=this.community.get(str[ii]);
									temp.add(nodename);
									this.community.put(str[ii], temp);
								}
								else
								{
									List<String> temp=new ArrayList<String>();
									temp.add(nodename);
									this.community.put(str[ii], temp);
								}
								overlopping_list.add(str[ii]);
							}
							this.node_community.put(nodename, overlopping_list);
							this.overlopping_node.put(nodename, overlopping_list);
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
							List<String> temp_list=new ArrayList<String>();
							temp_list.add(str[1]);
							node_community.put(nodename, temp_list);
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
			System.out.println("File not exist!\n");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}
}
