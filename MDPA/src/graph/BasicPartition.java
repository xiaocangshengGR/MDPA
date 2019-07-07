package graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ClassName: BasicPartition.java
 * Description:  BasicPartition
 * 
 */
public class BasicPartition implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	Map<String,List<String>> community=new HashMap<String,List<String>>();
	public BasicPartition()
	{
		
	}
	public void setcommunty(Map<String,List<String>> com)
	{
		community=com;
	}
	public Map<String,List<String>> getcommunity()
	{
		return community;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
        
	}

}
