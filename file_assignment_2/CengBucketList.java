import java.util.ArrayList;

public class CengBucketList {

	public static ArrayList<CengBucket> bucket_list;

	public CengBucketList()
	{
		bucket_list = new ArrayList<CengBucket>(1);

		// TODO: Empty Implementation
	}

	public void deletePoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}

	public void addPoke(CengPoke poke)
	{
		// TODO: Empty Implementation
	}
	
	public void searchPoke(Integer pokeKey)
	{
		// TODO: Empty Implementation
	}
	
	public void print()
	{
		// TODO: Empty Implementation
	}
	
	// GUI-Based Methods
	// These methods are required by GUI to work properly.
	
	public int bucketCount()
	{
		return this.bucket_list.size();
		// TODO: Return all bucket count.
	}
	
	public CengBucket bucketAtIndex(int index)
	{
		if(index>bucket_list.size()){
			return null;
		}
		return bucket_list.get(index);
		// TODO: Return corresponding bucket at index.
	}

	public void add(CengBucket cengBucket) {
		bucket_list.add(cengBucket);
	}


	// Own Methods
	public void delete_bucket(int index){
		bucket_list.remove(index);
	}
	public ArrayList<CengBucket> get_the_bucket(){
		return bucket_list;
	}
	public ArrayList<CengBucket> getBucket_list(){
		return bucket_list;
	}


}
