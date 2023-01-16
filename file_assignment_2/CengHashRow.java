public class CengHashRow {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	private String hash_index;
	private CengBucket ceng_bucket;



	public String hashPrefix()
	{

		// TODO: Return row's hash prefix (such as 0, 01, 010, ...)
		return hash_index;
	}
	
	public CengBucket getBucket()
	{
		// TODO: Return the bucket that the row points at.
		return ceng_bucket;
	}
	
	public boolean isVisited()
	{
		// TODO: Return whether the row is used while searching.
		return false;		
	}
	
	// Own Methods
	public void print_one_row(){
		System.out.printf("\t\"row\": {\n");
		System.out.printf("\t\t\"hashPref\": %s,\n",hash_index);
		ceng_bucket.print_bucket();
		System.out.printf("\t}");
	}
	public void print_search_row(int pokeKey){
		System.out.printf("\t\"row\": {\n");
		System.out.printf("\t\t\"hashPref\": %s,\n",hash_index);
		ceng_bucket.print_searched_key(pokeKey);
		System.out.printf("\t}");
	}
	public CengHashRow(int index_at_row_arr,boolean first_or_not,int global_depth,int local_depth){
		if(first_or_not == true){
			this.hash_index = Integer.toBinaryString(index_at_row_arr);
			this.ceng_bucket = new CengBucket(index_at_row_arr);
		}
		else{
			this.hash_index = calculate_prefix(global_depth,index_at_row_arr);
			this.ceng_bucket = new CengBucket(local_depth);
		}
	}
	public String calculate_prefix(int global_depth, int location_at_row_array){
		String location_to_str = Integer.toBinaryString(location_at_row_array);
		if(location_to_str.length() < global_depth){
			int diff = global_depth - location_to_str.length();
			String new_str = "";
			for(int i=0;i<diff;i++){
				new_str = "0" + new_str;
			}
			location_to_str = new_str + location_to_str;
		}
		return location_to_str;
	}


	public void setCeng_bucket(CengBucket ceng_bucket) {
		this.ceng_bucket = ceng_bucket;
	}

	public CengBucket get_ceng_bucket(){
		return this.ceng_bucket;
	}


}
