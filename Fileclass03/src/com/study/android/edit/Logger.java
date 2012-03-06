package com.study.android.edit;

/**
 * 日志记录
 * 
 * @author: tanqiang
 * @create-time: 2008-7-21
 */
public final class Logger {

	public static final Logger logger = new Logger();

	private Logger() {
	}

	/**
	 * 记录信息方法
	 * 
	 * @param e
	 * @param info：可变参数，为null或者没有则默认为输出文件名和行号
	 *            info[0]:是否输出文件名 info[1]:是否输出类名 inf0[2]:是否输出方法名 info[3]:是否输出行号
	 */
	public void log(Exception e, boolean... info) {
		if (info == null || info.length == 0)
			this.log(e, true, false, false, true);
		else if (info.length == 1)
			this.log(e, info[0], false, false, false);
		else if (info.length == 2)
			this.log(e, info[0], info[1], true, false);
		else if (info.length == 3)
			this.log(e, info[0], info[1], info[2], false);
		else// 参数不小于4维
			this.log(e, info[0], info[1], info[2], info[3]);
	}

	/**
	 * 记录信息方法
	 * 
	 * @param e：
	 * @param file：是否显示异常所在的文件
	 * @param clas：是否显示异常所在的类
	 * @param method：是否显示异常所在的方法
	 * @param line：是否显示异常所在的行号
	 */
	private void log(Exception e, boolean file, boolean clas, boolean method,
		boolean line) {
		StackTraceElement[] trace = e.getStackTrace();
		if (trace == null || trace.length == 0)
			return;
		StackTraceElement ste = trace[0];
		StringBuffer sb = new StringBuffer();
		if (file == true)
			sb.append(ste.getFileName());
		if (clas == true)
			sb.append(", class: " + ste.getClassName());
		if (method == true)
			sb.append(", method: " + ste.getMethodName());
		if (line == true)
			sb.append("(" + ste.getLineNumber() + ") ");
		sb.append(e.toString());
		System.err.println(sb.toString());
	}
}
