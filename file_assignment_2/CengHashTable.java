import java.util.ArrayList;

public class CengHashTable {

	private static int global_depth;
	private static ArrayList<CengHashRow> row_arr;
	private static int row_count;


	public CengHashTable()
	{
		row_count = 1; global_depth=0;
		row_arr = new ArrayList<CengHashRow>(1);
		CengHashRow row = new CengHashRow(0,true,0,0);
		row_arr.add(row);
		// TODO: Create a hash table with only 1 row.


	}

	public void deletePoke(Integer pokeKey)
	{
		String poke_key_to_str = get_the_hash_value(pokeKey % CengPokeKeeper.getHashMod());
		//String poke_key_to_str = Integer.toBinaryString(calculated_val);
		if(poke_key_to_str.length() < global_depth){
			int diff = global_depth - poke_key_to_str.length();
			String new_str = "";
			for(int i=0;i<diff;i++){
				new_str = "0" + new_str;
			}
			poke_key_to_str = new_str + poke_key_to_str;
		}
		if(poke_key_to_str.length() > global_depth){
			poke_key_to_str = poke_key_to_str.substring(0,global_depth);
			if(global_depth == 0){
				poke_key_to_str = "0";
			}

		}
		int vec_size = row_arr.size();
		for(int i=0;i<vec_size;i++){
			if(row_arr.get(i).hashPrefix().equals(poke_key_to_str)){
				CengBucket b = row_arr.get(i).get_ceng_bucket();
				b.delete_the_bucket(pokeKey);


			}
		}
		int num = 0;
		int bucket_list_size = CengBucketList.bucket_list.size();
		int row_arr_sizes = row_arr.size();
		for (int i = 0; i < CengBucketList.bucket_list.size(); i++) {
			if(CengBucketList.bucket_list.get(i).pokeCount() == 0){
				num++;
			}

		}
		System.out.printf("\"delete\": {\n\t");
		System.out.printf("\"emptyBucketNum\": %d\n", num);
		System.out.printf("}\n");
		// TODO: Empty Implementation
	}

	public void addPoke(CengPoke poke)
	{
		if(global_depth == 0) {
			if (row_arr.size() == 0) {
				CengHashRow first_row = new CengHashRow(0, true,global_depth,0);
				first_row.getBucket().add_to_bucket(poke);
				row_arr.add(first_row);
			}
			else if(row_arr.get(0).get_ceng_bucket().pokeCount() == CengPokeKeeper.getBucketSize()){
				int flag = 0;
				global_depth++;
				int new_local_depth = row_arr.get(0).getBucket().getLocal_depth() +1;
				CengHashRow row = new CengHashRow(0,false,global_depth,new_local_depth);
				CengHashRow row1 = new CengHashRow(1,false,global_depth,new_local_depth);
				ArrayList<CengHashRow> new_array = new ArrayList<CengHashRow>(1);
				new_array.add(row);
				new_array.add(row1);
				for(int i=0;i<CengPokeKeeper.getBucketSize();i++){
					String str1= get_the_hash_value(row_arr.get(0).getBucket().pokeAtIndex(i).pokeKey() % CengPokeKeeper.getHashMod());
					String str = find_which_row_to_add(str1);
					for (int j = 0; j < new_array.size(); j++) {
						if(new_array.get(j).hashPrefix().equals(str)){
							new_array.get(j).getBucket().add_to_bucket(row_arr.get(0).getBucket().pokeAtIndex(i));

						}
					}
				}
				String hashh_to_str1 = get_the_hash_value(poke.pokeKey() % CengPokeKeeper.getHashMod());
				String hashh_to_str = find_which_row_to_add(hashh_to_str1);
				for (int i = 0; i < new_array.size(); i++) {
					if(new_array.get(i).hashPrefix().equals(hashh_to_str)){
						if(new_array.get(i).getBucket().pokeCount() == CengPokeKeeper.getBucketSize()){
							flag = 1;
							row_arr = new_array;
							addPoke(poke);
							break;

						}
						else{
							new_array.get(i).getBucket().add_to_bucket(poke);
						}

					}
				}
				CengPokeKeeper.getBucketList().delete_bucket(0);
				if(flag == 0) {
					row_arr = new_array;

				}

			}
			else{
				row_arr.get(0).getBucket().add_to_bucket(poke);
			}
		}
		else{
			String hash_value= get_the_hash_value(poke.pokeKey() % CengPokeKeeper.getHashMod());

			String str = find_which_row_to_add(hash_value);
			int row_sizeee = row_arr.size();
			for (int i = 0; i < row_sizeee; i++) {
				if(row_arr.get(i).hashPrefix().equals(str)){
					if(row_arr.get(i).getBucket().pokeCount() < CengPokeKeeper.getBucketSize()){
						row_arr.get(i).getBucket().add_to_bucket(poke);
					}
					else if(row_arr.get(i).getBucket().getLocal_depth() == global_depth){
						global_depth++;
						CengBucket bucket_to_be_splitted = row_arr.get(i).getBucket();
						ArrayList<CengHashRow> new_array = new ArrayList<CengHashRow>(1);
						int new_row_size = row_arr.size() * 2;
						// create new rows and add to arr
						for (int j = 0; j < new_row_size; j+=2) {
							if(2*i== j){
								int new_local_depth = row_arr.get(j / 2).getBucket().getLocal_depth() + 1;
								CengHashRow row = new CengHashRow(j, false, global_depth, new_local_depth);
								CengHashRow row1 = new CengHashRow(j+1, false, global_depth, new_local_depth);
								new_array.add(row);
								new_array.add(row1);
							}
							else {
								int new_local_depth = row_arr.get(j / 2).getBucket().getLocal_depth() + 1;
								CengHashRow row = new CengHashRow(j, false, global_depth, new_local_depth);
								CengHashRow row1 = new CengHashRow(j+1, false, global_depth, new_local_depth);
								row.setCeng_bucket(row_arr.get(j/2).getBucket());
								row1.setCeng_bucket(row_arr.get(j/2).getBucket());
								new_array.add(row);
								new_array.add(row1);
							}
						}
						ArrayList<CengHashRow> tmp = new ArrayList<CengHashRow>(1);
						for (int j = 0; j < row_arr.size(); j++) {
							tmp.add(row_arr.get(j));
						}
						row_arr = new_array;
						for (int j = 0; j < CengPokeKeeper.getBucketSize(); j++) {
							addPoke(bucket_to_be_splitted.pokeAtIndex(j));
						}
						addPoke(poke);
						break;

					}
					else{   //global depth > local_depth
						CengBucket bucket_to_be_splitted = row_arr.get(i).getBucket();
						int new_local_depth = row_arr.get(i).getBucket().getLocal_depth() + 1;
						int old_local_depth = new_local_depth -1;
						CengBucket cb1 = new CengBucket(new_local_depth);
						CengBucket cb2 = new CengBucket(new_local_depth);
						int number_of_rows_to_be_splitted = (int)Math.pow(2,(global_depth-old_local_depth));
						int for_index = number_of_rows_to_be_splitted/2;
						// Find appropriate location
						String str1 = row_arr.get(i).hashPrefix().substring(0,old_local_depth);
						int i_yedek = i;
						while(true){
							if(i_yedek == 0){
								break;
							}
							else if(row_arr.get(i_yedek-1).hashPrefix().substring(0,old_local_depth).equals(str1)){
								i_yedek--;
							}
							else{
								break;
							}
						}

						for (int j = i_yedek; j <i_yedek+for_index; j++) {
							row_arr.get(j).setCeng_bucket(cb1);
						}
						for (int j = i_yedek+for_index; j <i_yedek+number_of_rows_to_be_splitted ; j++){
							row_arr.get(j).setCeng_bucket(cb2);

						}
						for (int j = 0; j < bucket_to_be_splitted.pokeCount(); j++) {
							addPoke(bucket_to_be_splitted.pokeAtIndex(j));
						}
						addPoke(poke);

					}

				}
			}

		}

		int flag = 0;
		for (int i = 0; i < CengBucketList.bucket_list.size(); i++) {
			CengBucket bucket = CengBucketList.bucket_list.get(i);
			flag = 0;
			for (int j = 0; j < row_arr.size(); j++) {
				if(bucket == row_arr.get(j).getBucket()){
					flag = 1;
					break;
				}
			}
			if(flag == 0){
				CengBucketList.bucket_list.remove(i);
			}
		}


		// TODO: Empty Implementation
	}
	
	public void searchPoke(Integer pokeKey)
	{
		ArrayList<CengHashRow> arr_hash = new ArrayList<>(1);
		int found_or_not = 0;
		int calculated_val = pokeKey % CengPokeKeeper.getHashMod();
		int local_dept = 0;
		String poke_key_to_str = Integer.toBinaryString(calculated_val);
		if(poke_key_to_str.length() < global_depth){
			int diff = global_depth - poke_key_to_str.length();
			String new_str = "";
			for(int i=0;i<diff;i++){
				new_str = "0" + new_str;
			}
			poke_key_to_str = new_str + poke_key_to_str;
		}
		if(poke_key_to_str.length() > global_depth){
			poke_key_to_str = poke_key_to_str.substring(0,global_depth);

		}
		int vec_size = row_arr.size();
		for(int i=0;i<vec_size;i++){
			if(row_arr.get(i) == null){
				continue;
			}
			if(row_arr.get(i).hashPrefix().equals(poke_key_to_str) && !(row_arr.get(i).get_ceng_bucket().is_in_or_not(pokeKey))){
				CengBucket b = row_arr.get(i).get_ceng_bucket();
				for (int j = 0; j < b.pokeCount(); j++) {
					if(b.pokeAtIndex(j).pokeKey() == pokeKey){
						local_dept = b.getHashPrefix();
						found_or_not = 1;
						break;
					}
				}
				break;
				

			}
		}
		if(found_or_not == 1) {
			String str = poke_key_to_str.substring(0, local_dept);
			int vec_sizee = row_arr.size();
			for(int i = 0; i < vec_sizee; i++) {
				if(row_arr.get(i).hashPrefix().substring(0,local_dept).equals(str)){
					arr_hash.add(row_arr.get(i));
				}
			}
			System.out.println("\"search\": {");
			int vec_sizeee = arr_hash.size();
			for(int i=0;i<vec_sizeee;i++){
				arr_hash.get(i).print_one_row();
				if(i == vec_sizeee-1){
					break;
				}
				System.out.printf(",\n");
			}
			System.out.println("\n}");
		}
		else{
			System.out.println("\"search\": {");
			System.out.println("}");
		}

		
		// TODO: Empty Implementation
	}
	
	public void print()
	{
		System.out.println("\"table\": {");
		int flag_first = 0;
		int vec_size = row_arr.size();
		for(int i=0;i<vec_size;i++){
			if(flag_first == 0){
				flag_first = 1;
				row_arr.get(i).print_one_row();
			}
			else{
				System.out.println(",");
				row_arr.get(i).print_one_row();
			}
		}
		System.out.printf("\n");
		System.out.printf("}\n");
		// TODO: Empty Implementation
	}

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int prefixBitCount()
	{
		// TODO: Return table's hash prefix length.
		return global_depth;
	}
	
	public int rowCount()
	{
		return row_arr.size();
		// TODO: Return the count of HashRows in table.
	}
	
	public CengHashRow rowAtIndex(int index)
	{
		// TODO: Return corresponding hashRow at index.
		return row_arr.get(index);
	}
	
	// Own Methods

	public void set_row_arr(CengHashRow row){
		row_arr.add(row);

	}
	public String find_which_row_to_add(String hash_value_to_str){
		if(hash_value_to_str.length() < global_depth){
			int diff = global_depth - hash_value_to_str.length();
			String new_str = "";
			for(int i=0;i<diff;i++){
				new_str = "0" + new_str;
			}
			hash_value_to_str = new_str + hash_value_to_str;
		}
		if(hash_value_to_str.length() > global_depth){
			hash_value_to_str = hash_value_to_str.substring(0,global_depth);

		}
		return hash_value_to_str;
	}
	public String get_the_hash_value(int pokeKey){
		int moded_pokeKey = pokeKey % CengPokeKeeper.getHashMod();
		String moded_key_to_str = Integer.toBinaryString(moded_pokeKey);
		String global_depth = Integer.toBinaryString(CengPokeKeeper.getHashMod());
		int global_depth_int = global_depth.length()-1;
		if(moded_key_to_str.length() < global_depth_int){
			int diff = global_depth_int - moded_key_to_str.length();
			String new_str = "";
			for(int i=0;i<diff;i++){
				new_str = "0" + new_str;
			}
			moded_key_to_str = new_str + moded_key_to_str;
		}
		if(moded_key_to_str.length() > global_depth_int){
			moded_key_to_str = moded_key_to_str.substring(0,global_depth_int);
		}
		return moded_key_to_str;
	}
}

