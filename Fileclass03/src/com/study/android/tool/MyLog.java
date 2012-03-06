package com.study.android.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class MyLog {
	/*
	 * desc: output log message
	 * 
	 * @param iLogFile log file path @param iMsg output message @return none
	 */
	protected static String path = "log.txt";
	
	private MyLog() {
	}

	public static void logPrint(String iMsg) {
		FileWriter out = null;

		try {
			out = new FileWriter(path, true);
			// print message
			out.write(new Date() + ": " + iMsg + "\n");
			// close
			out.close();
		} catch (IOException e) {
//			System.out.println("file I/O error");
//			System.out.println(e);
		}
	}

}
