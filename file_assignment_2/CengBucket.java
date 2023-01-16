public class CengBucket {

	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	private int poke_count;   // this is the total poke number. First initialize it to 0
	private int local_depth;       // This is the local depth.
	private CengPoke [] poke_array;       // This is the poke array which holds the hole poke for that bucket.
	private int [] deleted_or_not;
	private int poke_array_size;

	public int pokeCount()
	{
		return poke_count;             // Simply return the total poke number
		// TODO: Return the pokemon count in the bucket.
	}
	
	public CengPoke pokeAtIndex(int index)
	{
		return poke_array[index];       //return that poke in that index
		// TODO: Return the corresponding pokemon at the index.
	}
	
	public int getHashPrefix()
	{
		return local_depth;     // Return the global depth
		// TODO: Return hash prefix length.
	}
	
	public Boolean isVisited()
	{
		// TODO: Return whether the bucket is found while searching.
		return false;		
	}
	
	// Own Methods

	//This is the constructor for bucket
	public CengBucket(int local_depth){
		this.poke_count = 0;   // First initialize it to 0
		this.local_depth = local_depth;  // Initialize it to given output
		this.poke_array_size = CengPokeKeeper.getBucketSize();
		poke_array = new CengPoke[CengPokeKeeper.getBucketSize()]; //
		deleted_or_not = new int [CengPokeKeeper.getBucketSize()];
		int deleted_array_size = CengPokeKeeper.getBucketSize();
		for(int i=0;i< deleted_array_size;i++){
			deleted_or_not[i] = Integer.MAX_VALUE;
		}
		CengPokeKeeper.getBucketList().add(this);

	}
	public void add_to_bucket(CengPoke poke_obj){
		int deleted_array_size = CengPokeKeeper.getBucketSize();
		int min_val = Integer.MAX_VALUE;
		int min_index = -1;
		for(int i=0;i<deleted_array_size;i++){
			if(min_val>deleted_or_not[i]){
				min_val = deleted_or_not[i];
				min_index = i;
				break;
			}

		}


		if(min_val != Integer.MAX_VALUE){
			deleted_or_not[min_index] = Integer.MAX_VALUE;
			this.poke_array[min_index] = poke_obj;

		}
		else{
			this.poke_array[this.poke_count] = poke_obj;

		}
		poke_count++;

	}
	public boolean is_in_or_not(int key){
		int array_size = CengPokeKeeper.getBucketSize();
		for(int i=0;i<array_size;i++){
			if(deleted_or_not[i] == key){
				return true;
			}
		}
		return false;
	}
	public int getLocal_depth(){
		return this.local_depth;
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



	public void delete_the_bucket(int poke_key){
		int bucket_size = CengPokeKeeper.getBucketSize();
		for(int i=0;i<bucket_size;i++){
			if(poke_key == poke_array[i].pokeKey()){
				deleted_or_not[i] = poke_key;
//				for(int j=0;j<bucket_size;j++){
//					if(deleted_or_not[j] == Integer.MAX_VALUE){
//						deleted_or_not[j] = poke_key;
//						break;
//					}
//				}
				break;
			}
		}
		poke_count--;

	}

	public void print_bucket(){
		int flag_first = 0;
		System.out.println("\t\t\"bucket\": {");
		System.out.printf("\t\t\t\"hashLength\": %d,\n",local_depth);
		System.out.printf("\t\t\t\"pokes\": [");

		for(int i=0;i<poke_array_size;i++){
			if(poke_array[i] == null){
				continue;
			}
			if(is_in_or_not(poke_array[i].pokeKey())){
				continue;
			}
			if(flag_first == 0){
				System.out.printf("\n");
				System.out.println("\t\t\t\t\"poke\": {");
				String hash = get_the_hash_value(poke_array[i].pokeKey());
				System.out.printf("\t\t\t\t\t\"hash\": %s,\n",hash);
				System.out.printf("\t\t\t\t\t\"pokeKey\": %d,\n",poke_array[i].pokeKey());
				System.out.printf("\t\t\t\t\t\"pokeName\": %s,\n",poke_array[i].pokeName());
				System.out.printf("\t\t\t\t\t\"pokePower\": %s,\n",poke_array[i].pokePower());
				System.out.printf("\t\t\t\t\t\"pokeType\": %s\n",poke_array[i].pokeType());
				flag_first = 1;
				System.out.printf("\t\t\t\t}");
			}
			else{
				System.out.println(",");
				System.out.println("\t\t\t\t\"poke\": {");
				String hash = get_the_hash_value(poke_array[i].pokeKey());
				System.out.printf("\t\t\t\t\t\"hash\": %s,\n",hash);
				System.out.printf("\t\t\t\t\t\"pokeKey\": %d,\n",poke_array[i].pokeKey());
				System.out.printf("\t\t\t\t\t\"pokeName\": %s,\n",poke_array[i].pokeName());
				System.out.printf("\t\t\t\t\t\"pokePower\": %s,\n",poke_array[i].pokePower());
				System.out.printf("\t\t\t\t\t\"pokeType\": %s\n",poke_array[i].pokeType());
				System.out.printf("\t\t\t\t}");
			}

		}
		System.out.printf("\n");
		System.out.println("\t\t\t]");
		System.out.println("\t\t}");
	}
	public void print_searched_key(int pokeKey){
		for(int i=0;i<poke_array_size;i++){
			if(poke_array[i] == null){
				continue;
			}
			if(is_in_or_not(poke_array[i].pokeKey())){
				continue;
			}
			if(poke_array[i].pokeKey() == pokeKey){
				System.out.println("\t\t\"bucket\": {");
				System.out.printf("\t\t\t\"hashLength\": %d,\n",local_depth);
				System.out.println("\t\t\t\"pokes\": [");
				System.out.println("\t\t\t\t\"poke\": {");
				String hash = get_the_hash_value(poke_array[i].pokeKey());
				System.out.printf("\t\t\t\t\t\"hash\": %s,\n",hash);
				System.out.printf("\t\t\t\t\t\"pokeKey\": %d,\n",poke_array[i].pokeKey());
				System.out.printf("\t\t\t\t\t\"pokeName\": %s,\n",poke_array[i].pokeName());
				System.out.printf("\t\t\t\t\t\"pokePower\": %s,\n",poke_array[i].pokePower());
				System.out.printf("\t\t\t\t\t\"pokeType\": %s\n",poke_array[i].pokeType());
				System.out.printf("\t\t\t\t}");
				System.out.printf("\n");
				System.out.println("\t\t\t]");
				System.out.println("\t\t}");

			}


		}
	}

}
