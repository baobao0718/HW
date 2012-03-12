package com.study.android.edit;


import java.util.ArrayList;
import java.util.List;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.MyPoint;

/*******************************************************************************
 * Handwritten Edit System Edit �༭��ʶ��-----�﷨ģʽʶ�𷽷� @ ���˳�
 * @version 1.00 2007/08/21
 * 
 * 
 ******************************************************************************/
public class HwOperRecog_SyntaxPat {
	private ArrayList<MyPoint> operatorPoints_smooth; // �洢�ɼ����Ĳ������ĵ���Ϣ
	private ArrayList<Integer> codeVectorOfDegree; // �洢���������Ʊ仯������
	private int sumCountNeeded;

	/***************************************************************************
	 * ���캯������ʼ����Ա����
	 **************************************************************************/
	public HwOperRecog_SyntaxPat() {
		operatorPoints_smooth = new ArrayList<MyPoint>();
		codeVectorOfDegree = new ArrayList<Integer>();
	}

	/***************************************************************************
	 * �ж����ǲ�����ѡ���
	 **************************************************************************/
	private boolean isLeftSelect() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if (codeOfDegree != 2) {
					if (codeOfDegree == 1 || codeOfDegree == 0) {
						if (CurCount >= 2) {
							CurCount = 1;
							sumCount++;
							internalState = 1;
						} else {
							noiseCount++;
							if (noiseCount > 3)// ���������������3�����㲻��ʶ��
							{
								breakFlag = true;
							}
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			case 1:
				if (codeOfDegree != 0) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if ((CurCount >= 2) && (sumCount >= sumCountNeeded)) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ�����ѡ���
	 **************************************************************************/
	private boolean isRightSelect() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if (codeOfDegree != 2) {
					if (codeOfDegree == 4 || codeOfDegree == 3) {
						if (CurCount >= 2) {
							CurCount = 1;
							sumCount++;
							internalState = 1;
						} else {
							noiseCount++;
							if (noiseCount > 3)// ���������������3�����㲻��ʶ��
							{
								breakFlag = true;
							}
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			case 1:
				if (codeOfDegree != 4) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if ((CurCount >= 2) && (sumCount >= sumCountNeeded)) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ��Ǹ��Ʒ�
	 **************************************************************************/
	private boolean isCopyOperator() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		int two_zero, three_one, four_two, one_seven, three_zero, four_one;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		two_zero = 0;
		three_one = 0;
		four_two = 0;
		one_seven = 0;
		three_zero = 0;
		four_one = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if (codeOfDegree != 5) {
					if (codeOfDegree == 4) {
						CurCount = 1;
						sumCount++;
						four_two++;
						four_one++;
						internalState = 1;
					} else if (codeOfDegree == 3) {
						CurCount = 1;
						sumCount++;
						three_one++;
						three_zero++;
						internalState = 2;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			case 1:
				if (codeOfDegree != 4) {
					if (codeOfDegree == 3) {
						CurCount = 1;
						sumCount++;
						three_one++;
						three_zero++;
						internalState = 2;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					four_two++;
					if (four_two >= sumCountNeeded) {
						breakFlag = true;
					}
					four_one++;
					if (four_one >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 2:
				if (codeOfDegree != 3) {
					if (codeOfDegree == 2) {
						CurCount = 1;
						sumCount++;
						two_zero++;
						four_two++;
						internalState = 3;
					} else if (codeOfDegree == 1) {
						CurCount = 1;
						sumCount++;
						three_one++;
						one_seven++;
						four_one++;
						internalState = 4;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					three_one++;
					if (three_one >= sumCountNeeded) {
						breakFlag = true;
					}
					three_zero++;
					if (three_zero >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 3:
				if (codeOfDegree != 2) {
					if (codeOfDegree == 1) {
						CurCount = 1;
						sumCount++;
						three_one++;
						one_seven++;
						four_one++;
						internalState = 4;
					} else if (codeOfDegree == 0) {
						CurCount = 1;
						sumCount++;
						three_zero++;
						internalState = 5;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					two_zero++;
					if (two_zero >= sumCountNeeded) {
						breakFlag = true;
					}
					four_two++;
					if (four_two >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 4:
				if (codeOfDegree != 1) {
					if (codeOfDegree == 0) {
						CurCount = 1;
						sumCount++;
						two_zero++;
						three_zero++;
						internalState = 5;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					three_one++;
					if (three_one >= sumCountNeeded) {
						breakFlag = true;
					}
					one_seven++;
					if (one_seven >= sumCountNeeded) {
						breakFlag = true;
					}
					four_one++;
					if (four_one >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 5:
				if (codeOfDegree != 0) {
					if (codeOfDegree == 7) {
						CurCount = 1;
						sumCount++;
						one_seven++;
						internalState = 6;
					} else if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					two_zero++;
					if (two_zero >= sumCountNeeded) {
						breakFlag = true;
					}
					three_zero++;
					if (three_zero >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 6:
				if (codeOfDegree != 7) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					one_seven++;
					if (one_seven >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ���ճ����
	 **************************************************************************/
	private boolean isPasteOperator() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		int two_four, one_three, zero_two, seven_one, zero_three, one_four;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		two_four = 0;
		one_three = 0;
		zero_two = 0;
		seven_one = 0;
		zero_three = 0;
		one_four = 0;

		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if (codeOfDegree != 7) {
					if (codeOfDegree == 0) {
						CurCount = 1;
						sumCount++;
						zero_two++;
						zero_three++;
						internalState = 1;
					} else if (codeOfDegree == 1) {
						CurCount = 1;
						sumCount++;
						one_three++;
						seven_one++;
						one_four++;
						internalState = 2;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					seven_one++;
					if (seven_one >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 1:
				if (codeOfDegree != 0) {
					if (codeOfDegree == 1) {
						CurCount = 1;
						sumCount++;
						one_three++;
						seven_one++;
						one_four++;
						internalState = 2;
					} else if (codeOfDegree == 2) {
						CurCount = 1;
						sumCount++;
						two_four++;
						zero_two++;
						internalState = 3;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					zero_two++;
					if (zero_two >= sumCountNeeded) {
						breakFlag = true;
					}
					zero_three++;
					if (zero_three >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 2:
				if (codeOfDegree != 1) {
					if (codeOfDegree == 2) {
						CurCount = 1;
						sumCount++;
						two_four++;
						zero_two++;
						internalState = 3;
					} else if (codeOfDegree == 3) {
						CurCount = 1;
						sumCount++;
						one_three++;
						zero_three++;
						internalState = 4;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					one_three++;
					if (one_three >= sumCountNeeded) {
						breakFlag = true;
					}
					seven_one++;
					if (seven_one >= sumCountNeeded) {
						breakFlag = true;
					}
					one_four++;
					if (one_four >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 3:
				if (codeOfDegree != 2) {
					if (codeOfDegree == 3) {
						CurCount = 1;
						sumCount++;
						one_three++;
						zero_three++;
						internalState = 4;
					} else if (codeOfDegree == 4) {
						CurCount = 1;
						sumCount++;
						two_four++;
						one_four++;
						internalState = 5;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					zero_two++;
					if (zero_two >= sumCountNeeded) {
						breakFlag = true;
					}
					two_four++;
					if (two_four >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 4:
				if (codeOfDegree != 3) {
					if (codeOfDegree == 4) {
						CurCount = 1;
						sumCount++;
						two_four++;
						one_four++;
						internalState = 5;
					} else if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					one_three++;
					if (one_three >= sumCountNeeded) {
						breakFlag = true;
					}
					zero_three++;
					if (zero_three >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 5:
				if (codeOfDegree != 4) {
					if (codeOfDegree == 5) {
						CurCount = 1;
						sumCount++;
						internalState = 6;
					} else if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					two_four++;
					if (two_four >= sumCountNeeded) {
						breakFlag = true;
					}
					one_four++;
					if (one_four >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 6:
				if (codeOfDegree != 5) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ��ǳ�����
	 **************************************************************************/
	private boolean isUndoOperator() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		int six_four, seven_five, zero_six, one_seven, seven_four, zero_five;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		six_four = 0;
		seven_five = 0;
		zero_six = 0;
		one_seven = 0;
		seven_four = 0;
		zero_five = 0;

		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:// ״̬��
				if (codeOfDegree != 1) {
					if (codeOfDegree == 0) {
						CurCount = 1;
						sumCount++;
						zero_six++;
						zero_five++;
						internalState = 1;
					} else if (codeOfDegree == 7) {
						CurCount = 1;
						sumCount++;
						seven_five++;
						one_seven++;
						seven_four++;
						internalState = 2;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					one_seven++;
					if (one_seven >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 1:// ״̬��
				if (codeOfDegree != 0) {
					if (codeOfDegree == 7) {
						CurCount = 1;
						sumCount++;
						seven_five++;
						one_seven++;
						seven_four++;
						internalState = 2;
					} else if (codeOfDegree == 6) {
						CurCount = 1;
						sumCount++;
						six_four++;
						zero_six++;
						internalState = 3;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					zero_six++;
					if (zero_six >= sumCountNeeded) {
						breakFlag = true;
					}
					zero_five++;
					if (zero_five >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 2:// ״̬��
				if (codeOfDegree != 7) {
					if (codeOfDegree == 6) {
						CurCount = 1;
						sumCount++;
						six_four++;
						zero_six++;
						internalState = 3;
					} else if (codeOfDegree == 5) {
						CurCount = 1;
						sumCount++;
						seven_five++;
						zero_five++;
						internalState = 4;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					seven_five++;
					if (seven_five >= sumCountNeeded) {
						breakFlag = true;
					}
					one_seven++;
					if (one_seven >= sumCountNeeded) {
						breakFlag = true;
					}
					seven_four++;
					if (seven_four >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 3:// ״̬��
				if (codeOfDegree != 6) {
					if (codeOfDegree == 5) {
						CurCount = 1;
						sumCount++;
						seven_five++;
						zero_five++;
						internalState = 4;
					} else if (codeOfDegree == 4) {
						CurCount = 1;
						sumCount++;
						six_four++;
						seven_four++;
						internalState = 5;
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					six_four++;
					if (six_four >= sumCountNeeded) {
						breakFlag = true;
					}
					zero_six++;
					if (zero_six >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 4:// ״̬��
				if (codeOfDegree != 5) {
					if (codeOfDegree == 4) {
						CurCount = 1;
						sumCount++;
						six_four++;
						seven_four++;
						internalState = 5;
					} else if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					seven_five++;
					if (seven_five >= sumCountNeeded) {
						breakFlag = true;
					}
					zero_five++;
					if (zero_five >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 5:// ״̬��
				if (codeOfDegree != 4) {
					if (codeOfDegree == 3) {
						CurCount = 1;
						sumCount++;
						internalState = 6;
					} else if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
					six_four++;
					if (six_four >= sumCountNeeded) {
						breakFlag = true;
					}
					seven_four++;
					if (seven_four >= sumCountNeeded) {
						breakFlag = true;
					}
				}
				break;
			case 6:// ״̬��
				if (codeOfDegree != 3) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if (sumCount >= sumCountNeeded) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ��ǲ����
	 **************************************************************************/
	private boolean isInsertOperator() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if ((codeOfDegree != 7) && (codeOfDegree != 6)) {
					if (codeOfDegree == 1 || codeOfDegree == 2
						|| codeOfDegree == 0) {
						if (CurCount >= 2) {
							CurCount = 1;
							sumCount++;
							internalState = 1;
						} else {
							noiseCount++;
							if (noiseCount > 3)// ���������������3�����㲻��ʶ��
							{
								breakFlag = true;
							}
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			case 1:
				if ((codeOfDegree != 1) && (codeOfDegree != 2)) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if ((CurCount >= 2) && (sumCount >= sumCountNeeded)) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ������зַ�(1),ͨ�������ĵ����зִ�������ֽ�������
	 **************************************************************************/
	private boolean isReSeg_1Operator() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if ((codeOfDegree != 1) && (codeOfDegree != 2)) {
					if (codeOfDegree == 7 || codeOfDegree == 6
						|| codeOfDegree == 0) {
						if (CurCount >= 2) {
							CurCount = 1;
							sumCount++;
							internalState = 1;
						} else {
							noiseCount++;
							if (noiseCount > 3)// ���������������3�����㲻��ʶ��
							{
								breakFlag = true;
							}
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			case 1:
				if ((codeOfDegree != 7) && (codeOfDegree != 6)) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if ((CurCount >= 2) && (sumCount >= sumCountNeeded)) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ���ɾ������һ��
	 **************************************************************************/
	private boolean isHalfDel() {
		int codeOfDegree;
		int noiseCount;
		int CurCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			if (codeOfDegree != 0) {
				if (codeOfDegree == -1)// ��ʾ���Ľ���
				{
					if (CurCount >= sumCountNeeded) {
						admitFlag = true;
					} else {
						breakFlag = true;
					}
				} else {
					noiseCount++;
					if (noiseCount > 3)// ���������������3�����㲻��ʶ��
					{
						breakFlag = true;
					}
				}
			} else {
				CurCount++;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ�������з�
	 **************************************************************************/
	private boolean isLeftCut() {
		int codeOfDegree;
		int noiseCount;
		int CurCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			if (codeOfDegree != 1) {
				if (codeOfDegree == -1)// ��ʾ���Ľ���
				{
					if (CurCount >= sumCountNeeded) {
						admitFlag = true;
					} else {
						breakFlag = true;
					}
				} else {
					noiseCount++;
					if (noiseCount > 3)// ���������������3�����㲻��ʶ��
					{
						breakFlag = true;
					}
				}
			} else {
				CurCount++;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �жϵ�ǰ�켣�Ƿ����������������(firstCode,secondCode)��������ı༭��
	 **************************************************************************/
	private boolean isConstructedOperator(int firstCode, int secondCode) {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if (codeOfDegree != firstCode) {
					if (codeOfDegree == secondCode) {
						if (CurCount >= 2) {
							CurCount = 1;
							sumCount++;
							internalState = 1;
						} else {
							noiseCount++;
							if (noiseCount > 3)// ���������������3�����㲻��ʶ��
							{
								breakFlag = true;
							}
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			case 1:
				if (codeOfDegree != secondCode) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if ((CurCount >= 2) && (sumCount >= sumCountNeeded)) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ��ǿո��
	 **************************************************************************/
	private boolean isSpaceOperator() {
		boolean admitFlag;
		admitFlag = false;
		if (isConstructedOperator(1, 3) == true) {
			admitFlag = true;
		} else if (isConstructedOperator(0, 3) == true) {
			admitFlag = true;
		} else if (isConstructedOperator(1, 4) == true) {
			admitFlag = true;
		} else if (isConstructedOperator(0, 4) == true) {
			admitFlag = true;
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ����Ҽ��з�
	 **************************************************************************/
	private boolean isRightCut() {
		int codeOfDegree;
		int noiseCount;
		int CurCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			if (codeOfDegree != 3) {
				if (codeOfDegree == -1)// ��ʾ���Ľ���
				{
					if (CurCount >= sumCountNeeded) {
						admitFlag = true;
					} else {
						breakFlag = true;
					}
				} else {
					noiseCount++;
					if (noiseCount > 3)// ���������������3�����㲻��ʶ��
					{
						breakFlag = true;
					}
				}
			} else {
				CurCount++;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ��ǻظ��
	 **************************************************************************/
	private boolean isBackSpaceOperator() {
		boolean admitFlag;
		admitFlag = false;
		if (isConstructedOperator(3, 1) == true) {
			admitFlag = true;
		} else if (isConstructedOperator(3, 0) == true) {
			admitFlag = true;
		} else if (isConstructedOperator(4, 1) == true) {
			admitFlag = true;
		} else if (isConstructedOperator(4, 0) == true) {
			admitFlag = true;
		}
		return admitFlag;
	}

	/***************************************************************************
	 * �ж����ǲ��ǻ��з�
	 **************************************************************************/
	private boolean isEnterOp() {
		int internalState;
		int codeOfDegree;
		int noiseCount;
		int CurCount, sumCount;
		boolean admitFlag, breakFlag;

		noiseCount = 0;
		CurCount = 0;
		sumCount = 0;
		internalState = 0;
		admitFlag = false;
		breakFlag = false;
		for (int index = 0; index < codeVectorOfDegree.size(); index++) {
			codeOfDegree = codeVectorOfDegree.get(index);
			switch (internalState) {
			case 0:
				if (codeOfDegree != 0) {
					if (codeOfDegree == 2 || codeOfDegree == 1) {
						if (CurCount >= 2) {
							CurCount = 1;
							sumCount++;
							internalState = 1;
						} else {
							noiseCount++;
							if (noiseCount > 3)// ���������������3�����㲻��ʶ��
							{
								breakFlag = true;
							}
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			case 1:
				if (codeOfDegree != 2) {
					if (codeOfDegree == -1)// ��ʾ���Ľ���
					{
						if ((CurCount >= 2) && (sumCount >= sumCountNeeded)) {
							admitFlag = true;
						} else {
							breakFlag = true;
						}
					} else {
						noiseCount++;
						if (noiseCount > 3)// ���������������3�����㲻��ʶ��
						{
							breakFlag = true;
						}
					}
				} else {
					CurCount++;
					sumCount++;
				}
				break;
			default:
				break;
			}
			if (true == breakFlag) {
				break;
			}
		}
		return admitFlag;
	}

	/***************************************************************************
	 * ���ݱ༭���켣�仯�����ĽǶ�������䷽λ����
	 **************************************************************************/
	private int judgeScopeOfDegree(int degree) throws Exception {
		if ((degree <= 20 && degree >= 0) || (degree <= 360 && degree >= 340)) {
			return 0;
		} else if (degree < 70 && degree > 20) {
			return 1;
		} else if (degree <= 110 && degree >= 70) {
			return 2;
		} else if (degree < 160 && degree > 110) {
			return 3;
		} else if (degree <= 200 && degree >= 160) {
			return 4;
		} else if (degree < 250 && degree > 200) {
			return 5;
		} else if (degree <= 290 && degree >= 250) {
			return 6;
		} else if (degree < 340 && degree > 290) {
			return 7;
		} else {
			throw new Exception("error :wrong value of degree!");
		}
	}

	/***************************************************************************
	 * ����༭���켣�ı仯����
	 **************************************************************************/
	private void computeCodeVector(List<MyPoint> points) {
		int index;
		float collectInterval, intervalSum;
		boolean smoothFlag;
		MyPoint firstPoint, secondPoint, directionVector, point_smooth;
		List<MyPoint> operatorPtsProcess;
		int codeOfDegree;

		if (points.size() >= 12) {
			smoothFlag = true;
			collectInterval = ((float) points.size()) / 12;
			// System.out.println("operatorPoints.size:"+operatorPoints.size()+"collectInterval:"+collectInterval);
			index = 0;
			intervalSum = index + collectInterval;
			while (Math.round(intervalSum) < points.size()) {
				firstPoint = (MyPoint) points.get(index);
				secondPoint = (MyPoint) points.get(Math.round(intervalSum));
				index = Math.round(intervalSum);
				intervalSum += collectInterval;
				point_smooth = new MyPoint(
					(short)((secondPoint.getX() + firstPoint.getX()) / 2), (short)((secondPoint
						.getY() + firstPoint.getY()) / 2), false);
				operatorPoints_smooth.add(point_smooth);
			}
			// System.out.println("operatorPoints_smooth.size:" +
			// operatorPoints_smooth.size());
			operatorPtsProcess = operatorPoints_smooth;
		} else {
			smoothFlag = false;
			operatorPtsProcess =  points;
			// System.out.println("operatorPoints.size:" +
			// operatorPoints.size());
		}

		for (index = 0; index + 1 < operatorPtsProcess.size(); index++) {
			firstPoint = (MyPoint) operatorPtsProcess.get(index);
			secondPoint = (MyPoint) operatorPtsProcess.get(index + 1);
			short dx = (short) (secondPoint.getX() - firstPoint.getX());
			short dy = (short) (secondPoint.getY() - firstPoint.getY());
			boolean negative = dx < 0 ? true:false;
			directionVector = new MyPoint(dx, dy, negative);
			if ((directionVector.getX() == 0) && (directionVector.getY() == 0))
				continue;
			double cosValue = directionVector.getX()
				/ Math.hypot(directionVector.getX(), directionVector.getY());
			if(directionVector.isEnd()) {
				cosValue *= -1;
			}
			double acosValue = Math.acos(cosValue);
			int degree = (int) (180 * (acosValue / 3.14159));// ������ü���������Ӧ�ĽǵĶ���
			if (directionVector.getY() < 0) {
				degree = 360 - degree;
			}
			// System.out.println("�ñ��Ʊ仯�����ĽǶ�Ϊ��" + degree);
			try {
				codeOfDegree = judgeScopeOfDegree(degree);
				codeVectorOfDegree.add(codeOfDegree);// ��Ӳ������ı�����Ϣ
			} catch (Exception e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
		// System.out.println("vector code value after 1 times of smoothing:");
		// for (index = 0; index < codeVectorOfDegree.size(); index++) {
		// System.out.println(codeVectorOfDegree.get(index));
		// }
		if (true == smoothFlag) {
			operatorPoints_smooth.clear();
		}
		/* ��Ҫ������������ƽ������ */
		for (index = 1; index < codeVectorOfDegree.size() - 1; index++) {
			if ((codeVectorOfDegree.get(index - 1) == codeVectorOfDegree
				.get(index + 1))
				&& (codeVectorOfDegree.get(index - 1) != codeVectorOfDegree
					.get(index))) {
				codeVectorOfDegree
					.set(index, codeVectorOfDegree.get(index - 1));
			}
		}
		codeVectorOfDegree.add(-1);
	}

	/***************************************************************************
	 * ���ݶԱ༭���켣�ķ�λ���봮������жϸñ༭����ʲô
	 **************************************************************************/
	public OperatorType recognize(List<MyPoint> points) {
		OperatorType operatorId = OperatorType.OP_ID_INVALID;

		/* �ȼ���仯�������봮 */
		computeCodeVector(points);
		/* ����ʶ��ɹ�����Ҫ����ֵ */
		sumCountNeeded = (int) ((codeVectorOfDegree.size() * (7.0 / 10.0)));
		// �Ƿ�ΪhalfDel
		if (isHalfDel() == true) {
			operatorId = OperatorType.OP_ID_HALFDEL;
		} else if (isLeftSelect() == true) {
			operatorId = OperatorType.OP_ID_LEFTSELECT;
		} else if (isRightSelect() == true) {
			operatorId = OperatorType.OP_ID_RIGHTSELECT;
		} else if (isCopyOperator() == true) {
			operatorId = OperatorType.OP_ID_COPY;
		} else if (isPasteOperator() == true) {
			operatorId = OperatorType.OP_ID_PASTE;
		} else if (isInsertOperator() == true) {
			operatorId = OperatorType.OP_ID_INSERT;
		} else if (isSpaceOperator() == true) {
			operatorId = OperatorType.OP_ID_SPACE;
		} else if (isLeftCut() == true) {
			operatorId = OperatorType.OP_ID_LEFTCUT;
		} else if (isBackSpaceOperator() == true) {
			operatorId = OperatorType.OP_ID_BACKSPACE;
		} else if (isRightCut() == true) {
			operatorId = OperatorType.OP_ID_RIGHTCUT;
		} else if (isEnterOp() == true) {
			operatorId = OperatorType.OP_ID_ENTER;
		} else if (isUndoOperator() == true) {
			operatorId = OperatorType.OP_ID_UNDO;
		} else if (isReSeg_1Operator() == true)// һ�����зַ�
		{
			operatorId = OperatorType.OP_ID_RESEG_1;
		}
		// ������Ʊ仯����
		codeVectorOfDegree.clear();
		return operatorId;
	}
}