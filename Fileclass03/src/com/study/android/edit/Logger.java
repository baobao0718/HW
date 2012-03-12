package com.study.android.edit;

/**
 * ��־��¼
 * 
 * @author: tanqiang
 * @create-time: 2008-7-21
 */
public final class Logger {

	public static final Logger logger = new Logger();

	private Logger() {
	}

	/**
	 * ��¼��Ϣ����
	 * 
	 * @param e
	 * @param info���ɱ������Ϊnull����û����Ĭ��Ϊ����ļ������к�
	 *            info[0]:�Ƿ�����ļ��� info[1]:�Ƿ�������� inf0[2]:�Ƿ���������� info[3]:�Ƿ�����к�
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
		else// ������С��4ά
			this.log(e, info[0], info[1], info[2], info[3]);
	}

	/**
	 * ��¼��Ϣ����
	 * 
	 * @param e��
	 * @param file���Ƿ���ʾ�쳣���ڵ��ļ�
	 * @param clas���Ƿ���ʾ�쳣���ڵ���
	 * @param method���Ƿ���ʾ�쳣���ڵķ���
	 * @param line���Ƿ���ʾ�쳣���ڵ��к�
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
