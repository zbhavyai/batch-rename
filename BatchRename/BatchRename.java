package BatchRename;

import java.io.PrintWriter;
import java.util.Scanner;


public class BatchRename
{
	public static void main(String args[])
	{
		PrintWriter pw = new PrintWriter(System.out, true);
		PrintWriter pe = new PrintWriter(System.err, true);

		Renaming r = new Renaming();

		if(args.length == 0)
		{
			Scanner sc = new Scanner(System.in);
			pw.print("[INFO] Enter the target directory : ");
			pw.flush();

			String target = sc.nextLine();

			pw.println("\n");

			r.spacesToUnderscore(target);
			pw.println("[INFO] Operation successful");
		}

		else
		{
			pw.println("Argument is indeed passed - " + args[0] );
			r.spacesToUnderscore(args[0]);
			pw.println("[INFO] Operation successful");
		}
	}
}

