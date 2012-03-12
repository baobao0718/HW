package com.study.android.retrieval;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Transform {
	
	public static byte[] InttoBytes(int num)
	{
		byte[] targets=new byte[4];
        targets[0] = (byte)(num & 0xff);//最低位
        targets[1] = (byte)((num >> 8) & 0xff);//次低位
        targets[2]= (byte)((num >> 16) & 0xff);//次高位
        targets[3]= (byte)(num >>> 24);//最高位,无符号右移。
        return targets;
	}
	
	public static int BytestoInt(byte[] b)
	{
		int targets= (b[0] & 0xff)
        | ((b[1]<<8) & 0xff00)   // | 表示按位或
        | ((b[2]<<24)>>>8)
        | (b[3]<<24);
		return targets;
	}
	
	public static byte[] floattoBytes(float num)
	{
		ByteBuffer bb = ByteBuffer.allocate(4);
        byte[] ret = new byte [4];
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(num);
        bb.get(ret);
        return ret;

	}
	
	
	public static float Bytestofloat(byte[] b)
	{
		 ByteBuffer bb = ByteBuffer.wrap(b);
	        FloatBuffer fb = bb.asFloatBuffer();
	        return fb.get();

	}
}
