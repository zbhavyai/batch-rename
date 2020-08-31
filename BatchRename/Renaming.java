package BatchRename;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Renaming
{
	File config;
	boolean recursive;
	boolean skipFolder;
	ArrayList<String> skipList;


	Renaming()
	{
		//first populate the configuration with default values
		//and later over-write them when read from config file
		recursive = true;
		skipFolder = false;
		skipList = new ArrayList<>();
		skipList.add(".mp3");
		skipList.add(".mp4");

		//line for netbeans
		//config = new File("src/netbeans_practice" + File.separator + "config.ini");
		//final line is below
		config = new File("BatchRename" + File.separator + "config.ini");

		PrintWriter pw = new PrintWriter(System.out, true);
		PrintWriter pe = new PrintWriter(System.err, true);

		try
		{
			//if there is a directory by this name, remove it
			if(config.isDirectory())
			{
				pw.println("[INFO] Removing directory " + config.getAbsolutePath() + File.separator);
				RemoveDirectory rd = new RemoveDirectory();

				if(rd.removeDirectory(config.toString()))
				{
					pw.println("[PASS] " + config.getAbsolutePath() + " removed");
				}
			}


			//it will create new file only if it does not exists
			if(config.createNewFile())
			{
				pw.println("[INFO] Config file created at " + config.getAbsolutePath());
				FileWriter configNewFile = new FileWriter(config);

				//store the default values in the config file
				configNewFile.write("RECURSIVE:" + recursive + "\n");				//whether descend to directories
				configNewFile.write("SKIP_FOLD:" + skipFolder + "\n");				//whether rename folder names
				configNewFile.write("SKIP_EXTS:" + skipList.toString() + "\n");		//extensions like mp3 not to be renamed

				configNewFile.flush();
				configNewFile.close();
			}

			//if configuration file exists, read the configurations
			else
			{
				pw.println("[INFO] Reading the settings");
				FileReader configOldFile = new FileReader(config);
				Scanner sc = new Scanner(configOldFile);

				recursive = Boolean.valueOf(sc.nextLine().substring(10));
				skipFolder = Boolean.valueOf(sc.nextLine().substring(10));

				String skipListString = sc.nextLine();

				skipList = new ArrayList<>();
				skipList = new ArrayList<String>(Arrays.asList(skipListString.substring(11,skipListString.length()-1).split(", ")));

				sc.close();
				configOldFile.close();
			}
		}

		catch(IOException e)
		{
			pe.println("[FAIL] Config file creation failed at " + config.getAbsolutePath());
			e.printStackTrace(pe);
		}
	}

	void spacesToUnderscore(String directoryPath)
	{
		File startDirectory = new File(directoryPath);
		directoryPath = startDirectory.getAbsolutePath();	//reassigning to prevent possible errors

		PrintWriter pw = new PrintWriter(System.out, true);
		PrintWriter pe = new PrintWriter(System.err, true);

		pw.println("[INFO] Working Directory - " + directoryPath);

		if(!startDirectory.exists())
		{
			pe.println("[FAIL] Cannot find directory - " + directoryPath);
			return;
		}

		File fileList[] = startDirectory.listFiles();

		for(File f : fileList)
		{
			//temp variables
			int i;
			boolean flag=false;

			//handling renaming of files
			if(f.isFile())
			{
				//skip complete iteration if file name has no spaces
				if(!f.getName().contains(" "))
				{
					pw.println("[INFO] Nothing to rename - " + f.getName());
					continue;
				}


				//check to avoid renaming of files as per skip extension list
				for(i=0; i<skipList.size(); i++)
				{
					if(f.getName().endsWith(skipList.get(i)))
					{
						pw.println("[INFO] File extension is in exclusion list - " + f.getName());
						flag=true;
						break;
					}
				}

				if(flag == true)
					continue;


				//code here means file name is not to be skipped
				String old_name = f.getName();
				String new_name = old_name.replace(" ", "_");


				//if(f.renameTo(new File(directoryPath + File.separator + new_name)))
				//below line is better than above line, although both works similarly
				if(f.renameTo(new File(directoryPath, new_name)))
				{
					pw.println("[PASS] File " + old_name + " renamed to " + new_name);
				}

				else
				{
					pe.println("[FAIL] " + old_name + " could not be renamed");
				}
			}

			//handling renaming of directories
			else if(f.isDirectory())
			{
				//whether to go inside the directory
				if(recursive)
				{
					this.spacesToUnderscore(f.getAbsolutePath());
				}

				else
				{
					pw.println("[INFO] Skipping contents of directory - " + f.getName());
				}

				//whether directory itself should be renamed, iff it contain spaces
				if(!skipFolder && f.getName().contains(" "))
				{
					String old_name = f.getName();
					String new_name = old_name.replace(" ", "_");


					//if(f.renameTo(new File(directoryPath + File.separator + new_name)))
					//below line is better than above line, although both works similarly
					if(f.renameTo(new File(directoryPath, new_name)))
					{
						pw.println("[PASS] Folder " + old_name + " renamed to " + new_name);
					}

					else
					{
						pe.println("[FAIL] " + old_name + " could not be renamed");
					}
				}

				else if(skipFolder)
				{
					pw.println("[INFO] Skipping directory " + f.getName());
				}

				else
				{
					pw.println("[INFO] Nothing to rename - " + f.getName());
				}
			}
		}
	}
}
