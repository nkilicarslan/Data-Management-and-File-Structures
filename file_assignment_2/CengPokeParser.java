import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CengPokeParser {

	public static ArrayList<CengPoke> parsePokeFile(String filename)
	{
		ArrayList<CengPoke> pokeList = new ArrayList<CengPoke>();
		try{
			FileReader file = new FileReader(filename);
			BufferedReader reader = new BufferedReader(file);

			String str = reader.readLine();

			while(str !=null){
				String[] list_to_be_added = str.split("\t");
				CengPoke new_poke = new CengPoke(Integer.parseInt(list_to_be_added[1]),list_to_be_added[2],list_to_be_added[3],list_to_be_added[4] );

				pokeList.add(new_poke);
				str = reader.readLine();
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}


		// You need to parse the input file in order to use GUI tables.

		// TODO: Parse the input file, and convert them into CengPokes

		return pokeList;
	}
	
	public static void startParsingCommandLine() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(isr);
	 	String str = reader.readLine();
		 String[] new_str_list = str.split("\t");
		while(!new_str_list[0].equals("quit")){
			String first_elem = new_str_list[0];
			if(first_elem.equals("add")){
				CengPoke new_poke = new CengPoke(Integer.parseInt(new_str_list[1]),new_str_list[2],new_str_list[3],new_str_list[4] );
				CengPokeKeeper.addPoke(new_poke);
				str = reader.readLine();
				new_str_list = str.split("\t");
				continue;
			}
			if(first_elem.equals("search")){
				CengPokeKeeper.searchPoke(Integer.parseInt(new_str_list[1]));
				str = reader.readLine();
				new_str_list = str.split("\t");
				continue;

			}
			if(first_elem.equals("delete")){
//				CengHashTable hs = new CengHashTable();
//				//CengHashRow row = new CengHashRow(1);
//				//CengHashRow row1 = new CengHashRow(9);
//
//				CengBucket buc = new CengBucket(1);
//				CengPoke pok = new CengPoke(16,"poknam","süüüüü","asdasd");
//				CengPoke pok2 = new CengPoke(17,"poknamasdasdasd","süüüüüüüüüüüüüüüüüüü","asdasdflkhdsfkjdfhsd");
//				buc.add_to_bucket(pok);
//				buc.add_to_bucket(pok2);
//				row.setCeng_bucket(buc);
//				row1.setCeng_bucket(buc);
//				hs.set_row_arr(row);
//				hs.set_row_arr(row1);


				CengPokeKeeper.deletePoke(Integer.parseInt(new_str_list[1]));
				str = reader.readLine();
				new_str_list = str.split("\t");
				continue;
			}
			if(first_elem.equals("print")){


				CengPokeKeeper.printEverything();
				str = reader.readLine();
				new_str_list = str.split("\t");
				continue;
			}

		}

		// TODO: Start listening and parsing command line -System.in-.
		// There are 5 commands:
		// 1) quit : End the app. Print nothing, call nothing.
		// 2) add : Parse and create the poke, and call CengPokeKeeper.addPoke(newlyCreatedPoke).
		// 3) search : Parse the pokeKey, and call CengPokeKeeper.searchPoke(parsedKey).
		// 4) delete: Parse the pokeKey, and call CengPokeKeeper.removePoke(parsedKey).
		// 5) print : Print the whole hash table with the corresponding buckets, call CengPokeKeeper.printEverything().

		// Commands (quit, add, search, print) are case-insensitive.
	}
}
