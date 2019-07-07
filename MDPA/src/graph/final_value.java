package graph;


/**
 * ClassName: final_value.java
 * Description:  
 * buffersize_... : The buffersize of nine test graphs.
 * file_path_... : The file path of nine test graphs.
 * is_directed_... : The nine test graphs is directed or not.
 * normalization_bound : The difference between local distribution 
 * and global distribution for each cluster ID is normalized into scale [0,normalization_bound].
 * 
 */
public class final_value {

	public static final Double normalization_bound=5.0;
	public static final int buffersize_CA_GrQc=(28980/5242)*5;//
	public static final int buffersize_dolphin=(159/62)*5;//
	public static final int buffersize_karate=(78/34)*5;//
	public static final int buffersize_pol_books=(441/105)*5;//
	public static final int buffersize_loc_brightkite_network=(214078/58228)*5;//
	public static final int buffersize_com_amazon_ungraph=(925872/334863)*10;//
	public static final int buffersize_jazz=(2742/198)*5;//
	public static final int buffersize_com_dblp_ungraph=(1049866/317080)*5;//
	public static final int buffersize_football=(613/115)*5;//
	
	public static final int buffersize_list[]= {
													buffersize_CA_GrQc,
													buffersize_dolphin,buffersize_karate,
													buffersize_pol_books,buffersize_loc_brightkite_network,
													buffersize_jazz,buffersize_football,
													buffersize_com_dblp_ungraph,buffersize_com_amazon_ungraph
													};
	
	public static final String file_path_CA_GrQc=".\\result_CA_GrQc\\CA-GrQc.txt";
	public static final String file_path_dolphin=".\\result_dolphin\\dolphin.txt";
	public static final String file_path_karate=".\\result_karate\\network source-target.txt";
	public static final String file_path_pol_books=".\\result_pol_books\\pol.books.txt";
	public static final String file_path_loc_brightkite_network=".\\result_loc_brightkite_network\\Brightkite_edges.txt";
	public static final String file_path_com_amazon_ungraph=".\\result_com_amazon_ungraph\\com-amazon.ungraph.txt";
	public static final String file_path_jazz=".\\result_jazz\\jazz.net";
	public static final String file_path_com_dblp_ungraph=".\\result_com_dblp_ungraph\\com_dblp_ungraph.txt";
	public static final String file_path_football=".\\result_football\\football_edge.txt";
	
	public static final String file_path_list[]= {file_path_CA_GrQc,file_path_dolphin,
									file_path_karate,file_path_pol_books,file_path_loc_brightkite_network,
									file_path_jazz,file_path_football,file_path_com_dblp_ungraph,
									file_path_com_amazon_ungraph};
	
	public static final String path[]= {"result_CA_GrQc","result_dolphin","result_karate",
			"result_pol_books","result_loc_brightkite_network",
			"result_jazz","result_football","result_com_dblp_ungraph","result_com_amazon_ungraph","result_paper_example"};
	

	public static final boolean is_directed_CA_GrQc=true;
	public static final boolean is_directed_dolphin=false;
	public static final boolean is_directed_karate=false;
	public static final boolean is_directed_pol_books=false;
	public static final boolean is_directed_loc_brightkite_network=false;
	public static final boolean is_directed_com_amazon_ungraph=false;
	public static final boolean is_directed_jazz=false;
	public static final boolean is_directed_com_dblp_ungraph=false;
	public static final boolean is_directed_football=false;
	public static final boolean is_directed_paper_example=false;
	
	public static final boolean is_directed_list[]= {is_directed_CA_GrQc,is_directed_dolphin,
									is_directed_karate,is_directed_pol_books,is_directed_loc_brightkite_network,
									is_directed_jazz,is_directed_football,is_directed_com_dblp_ungraph,
									is_directed_com_amazon_ungraph,is_directed_paper_example};
	

	public static final String Source_directory=System.getProperty("user.dir");
}
