package graph;

import java.util.*;

class specifity_pro 
{
	public String label_name; 
	public Double spec;
	public Double spec_min;
	public Double location;
	public Double normalize;
	public specifity_pro(String l,Double a,Double b,Double c,Double d)
	{
		label_name=l;
		spec=a;
		spec_min=b;
		location=c;
		normalize=d;
	}
	public specifity_pro(){};
}

/**
 * ClassName: MembershipDgreePropagation.java
 * Description:  
 * step1() : Calculate the probability distribution of all labels in the 
 * current network(global distribution).
 * step2() : Calculate the probability distribution of all labels in the
 *  local subgraph of the current node (local distribution).
 * step3() : Get the maximum difference label L according to he difference 
 * between local distribution and global distribution.
 * 
 * ajustProbability() : Increase the membership degree corresponding to 
 * label L, update the buffer of current node and update the global distribution.
 */
public class MembershipDgreePropagation {

	public MembershipDgreePropagation()
	{
		
	}
	public MembershipDgreePropagation (Graph g)
	{
		int i;
		List<String> templist=new ArrayList<String>();
		for(Map.Entry<String, Node> entry : g.NodeSet.entrySet())
		{
			templist.add(entry.getKey());
		}
		Random randomnum=new Random();
		int rn=randomnum.nextInt(templist.size());
		String node_name=templist.get(rn);
		//1.Calculating globalProbability here can improve the efficiency of the program.
		//The adjustment of globalProbability occurs in ajustProbability().
		step1(g);
		for(i=0;i<800;i++)
		{
			System.out.println(" Iteration "+(i+1)+"\n");
			Queue<String> que=new LinkedList<String>();
			Set<String> VisitedNode=new HashSet<String>();
			
			que.add(node_name);
			while(!que.isEmpty())
			{
				String now_node=g.BFS(VisitedNode, que);
				if(!now_node.equals("visited"))
				{
					Node v=g.NodeSet.get(now_node);
					Map<String,Double> localProbability=new HashMap<String,Double>();
					//2.Calculate localProbability.
					step2(g,v,localProbability);
					
					Map<String,Double> specificity =new HashMap<String,Double>();
					List<specifity_pro> spe_pro=new ArrayList<specifity_pro>();
					//3.Calculate differences between localProbability and globalProbability.
					//And then get the label that needs to ajust buffer.
					String change_label=step3(g,localProbability,specificity,
							spe_pro,now_node);
					
					//4.Ajust buffer and globalprobability.
					int buffersize=g.BufferSize;
					if(!change_label.equals(""))
					{
						ajustProbability(change_label,v,buffersize,g);
						g.NodeSet.put(now_node, v);
					}
					
				}
			}
			
		}
	}
	public void step1(Graph g)
	{
		Double a=(double)1/g.NodeNum;
		g.GlobalProbability=new HashMap<String,Double>();
		Iterator<Map.Entry<String, Node>> it=g.NodeSet.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Node> temp=(Map.Entry<String, Node>)it.next();
			Node node=temp.getValue();
			Iterator<Map.Entry<String, Double>> iter=node.buffer.entrySet().iterator();
			while(iter.hasNext())
			{
				Map.Entry<String, Double> templabel=(Map.Entry<String, Double>)iter.next();
				String labelname =templabel.getKey();
				if(g.GlobalProbability.containsKey(labelname))
				{
					Double t=g.GlobalProbability.get(labelname)+templabel.getValue()*a;
					g.GlobalProbability.put(labelname, t);
				}
				else
				{
					g.GlobalProbability.put(labelname, templabel.getValue()*a);
				}
			}
		}
	}
	public void step2(Graph g,Node v,Map<String,Double> localProbability)
	{                        
		
		Double a=(double)1/v.neighborNode.size();
		Iterator<Map.Entry<String, Double>> it=v.neighborNode.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Double> entry=(Map.Entry<String, Double>) it.next();
			String now_neighbor_name=entry.getKey();
			if(g.NodeSet.containsKey(now_neighbor_name))
			{
				Node now_neighbor_node=g.NodeSet.get(now_neighbor_name);
				Map<String,Double> neighbor_buffer= now_neighbor_node.buffer;
				Iterator<Map.Entry<String, Double>> iter=neighbor_buffer.entrySet().iterator();
				while(iter.hasNext())
				{
					Map.Entry<String, Double> temp=(Map.Entry<String, Double>)iter.next();
					Double labelprobability=temp.getValue();
					String labelname=temp.getKey();
					Double num=labelprobability*a;
					if(localProbability.containsKey(labelname))
					{
						num=localProbability.get(labelname)+num;
					}
					localProbability.put(labelname, num);
				}
			}else
			{
				System.out.println("The node is not in this \n");
			}	
		}
	}
	public String step3(Graph g,Map<String,Double> localProbability,Map<String,Double> specificity,
			List<specifity_pro> spe_pro ,String now_node)
	{                        
		
		Iterator<Map.Entry<String, Double>> it=localProbability.entrySet().iterator();
		Double max=-999999999999d;
		String change_label="";
		Map<String,Double> globalProbability=g.GlobalProbability;
		Double min=99999999999d;
		String temp_min_label="";
		String temp_max_label="";
		Double spect_sum=0d;
		if(localProbability.size()==0)
		{
			System.out.println("localProbability.size()==0");
			return change_label;
		}
		String local_label_num_equal_1="";
		int sum_local=0;
		//1.Find the maximum label and minimum label.
		while(it.hasNext())
		{
			Map.Entry<String, Double> entry=(Map.Entry<String, Double>)it.next();
			String label_name=entry.getKey();
			Double local=entry.getValue();
			Double global=0d;
			if(globalProbability.containsKey(label_name))
			{
				sum_local++;
				local_label_num_equal_1=label_name;
				global=globalProbability.get(label_name);		
				Double spect=local-global;   
				if(spect>max)
				{
					max=spect;
					temp_max_label=label_name;
				}
				if(spect<min)
				{
					min=spect;
					temp_min_label=label_name;
				}
			}else	
			{
				System.out.println("GlobalProbability do not have label:"+label_name);
				change_label="";
				return change_label;
			}
		}
		if(sum_local==1)
		{
			change_label=local_label_num_equal_1;
			return change_label;
		}
		//2.Calculate differences between localprobability and globalprobability and normalize them to scale [0,normalization_bound]
		Iterator<Map.Entry<String, Double>> it0=localProbability.entrySet().iterator();
		while(it0.hasNext())
		{
			Map.Entry<String, Double> entry=(Map.Entry<String, Double>)it0.next();
			String label_name=entry.getKey();
			Double local=entry.getValue();
			Double global=0d;
			
			if(globalProbability.containsKey(label_name))
			{
				global=globalProbability.get(label_name);		
				Double spect=local-global;   
				if(max.equals(min))
				{
					specificity.put(label_name, spect);
					Double temp_x=(spect/max)*(final_value.normalization_bound);
					Double temp_e=Math.exp(temp_x);
					spect_sum+=temp_e;
					if(Double.isNaN(spect_sum))
					{
						System.out.println("spect_sum is NaN temp_e:"+temp_e+" temp_x:"+temp_x+" max:"+max+" min:"+min);
						System.out.println("localProbability.size():"+localProbability.size());
					}
					specifity_pro sp=new specifity_pro(label_name,spect,temp_e
							,spect_sum,temp_x);
					spe_pro.add(sp);
				}
				else
				{
					specificity.put(label_name, spect);
					Double temp_x=((spect-min)/(max-min))*(final_value.normalization_bound);
					Double temp_e=Math.exp(temp_x);
					spect_sum+=temp_e;
					if(Double.isNaN(spect_sum))
					{
						System.out.println("spect_sum is NaN temp_e:"+temp_e+" temp_x:"+temp_x+" max:"+max+" min:"+min);
						System.out.println("localProbability.size():"+localProbability.size());
					}
					specifity_pro sp=new specifity_pro(label_name,spect,temp_e
							,spect_sum,temp_x);
					spe_pro.add(sp);
				}
			}else
			{
				System.out.println("globalProbability do not have label:"+label_name);
				change_label="";
				return change_label;
			}
		}

		//3.Random selection 
		Double rand=Math.random();
		Double rr=rand;
		if(rand.equals(0d))
		{
			change_label=spe_pro.get(spe_pro.size()-1).label_name;
		}
		else
		{
			rand=rand*spect_sum;
			int i=0;
			for(i=0;i<spe_pro.size();i++)
			{
				specifity_pro temp_sp=spe_pro.get(i);
				String label_name=temp_sp.label_name;
				Double local=temp_sp.location;
				if(rand<=local)
				{
					change_label=label_name;
					break;
				}
			}
			if(i>=spe_pro.size())   
			{
				int dd=spe_pro.size()-1;
				System.out.println("i:"+i+"  spe_pro.size():"+spe_pro.size());
				System.out.println("rand:"+rand+"\tlocation:"+spe_pro.get(dd).location);
				change_label=spe_pro.get(dd).label_name;
			}
		}
		
		return change_label;
		
	}
	
	
	public void remove_negative_number(Map<String,Double> labelbuffer,Graph g)
	{
		boolean flag=true;
		int nodenum=g.NodeNum;
		while(flag)
		{
			flag=false;
			Double sum=0d;
			Iterator<Map.Entry<String, Double>> it=labelbuffer.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry<String, Double> entry=(Map.Entry<String, Double>)it.next();
				Double tempprobability=entry.getValue();
				String tempname=entry.getKey();
				if(tempprobability<0)
				{
					Double temp_global=g.GlobalProbability.get(tempname)-tempprobability/nodenum;
					g.GlobalProbability.put(tempname, temp_global);
					
					sum=sum+tempprobability;
					flag=true;
					it.remove();
				}
			}
			if(flag)
			{
				int len=labelbuffer.size();
				Double t=sum/len;
				Iterator<Map.Entry<String, Double>> iter=labelbuffer.entrySet().iterator();
				while(iter.hasNext())
				{
					Map.Entry<String, Double> entry=(Map.Entry<String, Double>)iter.next();
					String tempname=entry.getKey();
					Double temp_global=g.GlobalProbability.get(tempname)+t/nodenum;
					g.GlobalProbability.put(tempname, temp_global);
					
					Double temp=entry.getValue()+t;
					labelbuffer.put(entry.getKey(), temp);
				}
			}
		}
	}
	/**
	 * @param labelname : The label ID 
	 * @param v : The node whose buffer needs to be ajusted.
	 * @param buffersize
	 * @param g
	 * Add the membership degree that labelname corresponds to to 1/buffersize.
	 * And then make sure the sum of membership degrees is 1.
	 */ 
	public void ajustProbability(String labelname,Node v,int buffersize,Graph g) 
	{
		
		int nodenum=g.NodeNum;
		if(v.buffer.containsKey(labelname))
		{
			
			if(v.buffer.size()-1==0)
			{
				return;
			}
			Double additem=(double)1/buffersize;
			Double subtractitem=additem/(v.buffer.size()-1);
			Double temp=v.buffer.get(labelname)+additem;  
			
			if(temp>=1)
			{
				temp=1d;
				Double global=g.GlobalProbability.get(labelname)+(temp-v.buffer.get(labelname))/nodenum;
				g.GlobalProbability.put(labelname, global);
				
				v.buffer.put(labelname, temp);
				Iterator<Map.Entry<String,Double>> it=v.buffer.entrySet().iterator();
				while(it.hasNext())              
				{
					Map.Entry<String,Double> entry=(Map.Entry<String, Double>)it.next();
					String templabelname=entry.getKey();
					if(!templabelname.equals(labelname))                
					{
						Double temp_global=g.GlobalProbability.get(templabelname)-entry.getValue()/nodenum;
						g.GlobalProbability.put(templabelname, temp_global);
						it.remove();
					}
				}
			}else
			{
				Double global=g.GlobalProbability.get(labelname)+additem/nodenum;
				g.GlobalProbability.put(labelname, global);
				
				v.buffer.put(labelname, temp);
				Iterator<Map.Entry<String,Double>> it=v.buffer.entrySet().iterator();
				while(it.hasNext())              
				{
					Map.Entry<String,Double> entry=(Map.Entry<String, Double>)it.next();
					String templabelname=entry.getKey();
					Double tempprobability=entry.getValue();
					if(!templabelname.equals(labelname))                
					{
						Double temp_global=g.GlobalProbability.get(templabelname)-subtractitem/nodenum;
						g.GlobalProbability.put(templabelname, temp_global);
						
						temp=tempprobability-subtractitem;            
						v.buffer.put(templabelname, temp);
					}
				}
				remove_negative_number(v.buffer,g);               
			}
		}else
		{
			Double additem=(double)1/buffersize;
			Double subtractitem=additem/(v.buffer.size());
			
			Double global=g.GlobalProbability.get(labelname)+additem/nodenum;
			g.GlobalProbability.put(labelname, global);        
			
			v.buffer.put(labelname, additem);            
			Iterator<Map.Entry<String,Double>> it=v.buffer.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry<String,Double> entry=(Map.Entry<String, Double>)it.next();
				String templabelname=entry.getKey();
				Double tempprobability=entry.getValue();
				if(!templabelname.equals(labelname))    
				{
					Double temp_global=g.GlobalProbability.get(templabelname)-subtractitem/nodenum;
					g.GlobalProbability.put(templabelname, temp_global);
					
					Double temp=tempprobability-subtractitem;        
					v.buffer.put(templabelname, temp);
				}
			}
			remove_negative_number(v.buffer,g);             
			if(v.buffer.size()>buffersize)            
			{                                                 
				Double min=2d;
				String min_label="";
				Iterator<Map.Entry<String,Double>> iter=v.buffer.entrySet().iterator();
				boolean flag=true;
				while(iter.hasNext())
				{
					Map.Entry<String, Double> en=(Map.Entry<String, Double>)iter.next();
					if(en.getValue()<min)                        
					{
						min=en.getValue();
						min_label=en.getKey();
						flag=false;
					}
				}
				if(!flag)
				{
					global=g.GlobalProbability.get(min_label)-min/nodenum;
					g.GlobalProbability.put(min_label, global);        
					
					v.buffer.remove(min_label);             
					iter=v.buffer.entrySet().iterator();
					Double a=(double)min/v.buffer.size();
					while(iter.hasNext())                       
					{
						Map.Entry<String, Double> en=(Map.Entry<String, Double>)iter.next();
						String temp_name=en.getKey();
						Double temp_global=g.GlobalProbability.get(temp_name)+a/nodenum;
						g.GlobalProbability.put(temp_name, temp_global);
						
						Double temp=en.getValue()+a;
						v.buffer.put(en.getKey(), temp);
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
