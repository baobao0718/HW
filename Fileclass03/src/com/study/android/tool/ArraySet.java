
package com.study.android.tool;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 *@author: fuxinggang
 *@create-time: 2009-3-13 下午08:56:12
 */
/**
 * 满数组实现二分二叉树集合，算法从1开始，存储从0开始
 * @author 小强
 *
 */
public class ArraySet {

	private List<Object> list;
	private Comparator comp;
	
	public ArraySet(Comparator comp){
		this.list = new ArrayList<Object>();
		this.comp = comp;
	}
	
	/**
	 * 添加 x
	 * @param x
	 */
	public Object add(Object x){
		int index = find(x);
		if(index != -1){
			return get(index);
		}
		add(x, 1, getSize()+1);
		return x;
	}

	public boolean contains(Object x){
		return find(x) != -1;
	}
	
	public int getSize() {
		return list.size();
	}

	public void remove(Object x){
		int index = find(x);
		if(index == -1)return ;
		int maxIndex = getLeftMaxIndex(index);
		if(maxIndex != -1){
			set(get(maxIndex), index);
		}else {
			maxIndex = index;
		}
		
		if(maxIndex != (getSize() - 1)){
			add(list.get(list.size() - 1), 1, maxIndex);
		}
		list.remove(list.size() - 1);
	}
	
	/**
	 * 查找
	 * @param x
	 * @return 对应的下标，如果没找到则返回 -1
	 */
	private int find(Object x){
		int size = getSize();
		for(int i=1;i<=size;){
			Object y = get(i);
			int ret = comp.compare(x, y);
			if(ret < 0)i=i*2;
			else if(ret > 0)i=i*2+1;
			else return i;
		}
		return -1;
	}
	
	private void add(Object x, int begin, int end){
		while(end != begin){
			Object y = get(begin);
			// 果然存在相同对象，则直接返回
			if(x == y)return ;
			
			int ret = comp.compare(x, y);
			if(isLeft(end,begin)){
				// 新对象下标在左子树中
				if(ret > 0){
					// x取代y的位置，y继续迭代插入左子树
					int index = getRightMinIndex(begin);
					if(index != -1 && comp.compare(x, get(index)) > 0){
						set(get(index), begin);
						add(x, begin*2 + 1, index);
					}else {
						set(x, begin);
					}
					x = y;
				}
				// 继续插入左子树中
				begin = begin*2;
			}else {
				// 新对象下标在右子树中
				if(ret < 0){
					// x取代y的位置，y继续迭代插入右子树
					int index = getLeftMaxIndex(begin);
					if(index != -1 && comp.compare(x, get(index)) < 0){
						set(get(index), begin);
						add(x, begin*2, index);
					}else {
						set(x, begin);
					}
					x = y;
				}
				// 继续插入右子树中
				begin = begin*2 + 1;
			}
		}
		if(end <= getSize())set(x, end);
		else list.add(x);
	}

	private void set(Object x, int index) {
		list.set(index-1, x);
	}

	private Object get(int index) {
		return list.get(index-1);
	}

	/**
	 * 获取左子树中最大值对象的下标
	 * @param begin
	 * @return
	 */
	private int getLeftMaxIndex(int begin){
		int size = getSize();
		if(begin > size)return -1;
		int index = begin*2;
		if(index > size)return -1;
		int next = index*2 + 1;
		while(next <= size){
			index = next;
			next = index*2 + 1;
		}
		return index;
	}
	
	/**
	 * 获取右子树中最小值对象的下标
	 * @param begin
	 * @return
	 */
	private int getRightMinIndex(int begin){
		int size = getSize();
		if(begin > size)return -1;
		int index = begin*2 + 1;
		if(index > size)return -1;
		int next = index*2;
		while(next <= size){
			index = next;
			next = index*2;
		}
		return index;
	}

	/**
	 * 判断childc 是否在 ancestor 的左边，保证child>ancestor
	 * @param child
	 * @param ancestor
	 * @return
	 */
	private boolean isLeft(int child, int ancestor) {
		int a = ancestor * 2;
		while(child >= a){
			if(child == a)return true;
			if(child == (a+1))return false;
			child /= 2;
		}
		return false;
	}
	/*
	public void print(int i){
		int size = getSize();
		if(i*2<=size)print(i*2);
		System.out.println(get(i));
		if(i*2+1 <= size)print(i*2+1);
	}
	*/
}
