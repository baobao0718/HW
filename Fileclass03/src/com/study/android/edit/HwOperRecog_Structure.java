package com.study.android.edit;


import java.util.ArrayList;
import java.util.List;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.MyPoint;

/*******************************************************************************
 * Handwritten Edit System Edit �༭��ʶ��------��Ԫ�ṹʶ�𷽷� @ ���˳�
 * @version 1.00 07/08/21
 * 
 * 
 ******************************************************************************/
public class HwOperRecog_Structure {
	/***************************************************************************
	 * ���캯������ʼ����Ա����
	 **************************************************************************/
	public HwOperRecog_Structure() {
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
			throw new Exception(
				"error :wrong value of degree in HwOperRecog_Structure.judgeScopeOfDegree()");
		}
	}

	/***************************************************************************
	 * �ж�ĳһ�켣������
	 **************************************************************************/
	private int getTypeIdOfTrack(List<MyPoint> points,
		int firstIndex, int secondIndex) {
		int index;
		MyPoint firstPoint, secondPoint, directionVector, point_Loop;
		int typeIdOfTrack = -1;
		float lengthOfLine;
		float MaxDistanceToLine = 0;
		float distanceToLine;
		float ratio;
		int para_A, para_B, para_C;
		int valueofFuncOfLine;
		int countOfBigThanZero = 0;
		int countOfSmallThanZero = 0;
		// ȡ���ù켣�������˵�
		firstPoint = (MyPoint) points.get(firstIndex);
		secondPoint = (MyPoint) points.get(secondIndex);
		short dx = (short) (secondPoint.getX() - firstPoint.getX());
		short dy = (short) (secondPoint.getY() - firstPoint.getY());
		boolean negative = dx < 0 ? true:false;
		directionVector = new MyPoint(dx, dy, negative);
		lengthOfLine = (int) Math.hypot(directionVector.getX(),directionVector.getY());
		// ����ֱ����ͨ���̵���������
		para_A = secondPoint.getY() - firstPoint.getY();
		para_B = firstPoint.getX() - secondPoint.getX();
		para_C = secondPoint.getX() * firstPoint.getY() - firstPoint.getX()
			* secondPoint.getY();
		// ����ļ��㱣ֱ֤�ߵķ�������"��"��
		if (para_B == 0) {
			if (para_A < 0) {
				para_A = para_A * (-1);
				para_C = para_C * (-1);
			}
		} else if (para_B < 0) {
			para_A = para_A * (-1);
			para_B = para_B * (-1);
			para_C = para_C * (-1);
		}

		// ����켣�������˵����ɵ�ֱ�߾������ĵ㼰�þ���
		for (index = firstIndex + 1; index < secondIndex; index++) {
			point_Loop = (MyPoint) points.get(index);
			valueofFuncOfLine = para_A * point_Loop.getX() + para_B
				* point_Loop.getY() + para_C;
			distanceToLine = (int) (Math.abs(valueofFuncOfLine) / Math
				.sqrt(para_A * para_A + para_B * para_B));
			if (distanceToLine > MaxDistanceToLine) {
				MaxDistanceToLine = distanceToLine;
				// index_MaxDistance = index;
			}
			if (valueofFuncOfLine > 0) {
				countOfBigThanZero++;
			} else if (valueofFuncOfLine < 0) {
				countOfSmallThanZero++;
			}
		}
		// ratioС��һ����ֵ�����Ǿ���Ϊ��ֱ��
		ratio = MaxDistanceToLine / lengthOfLine;
		if (ratio <= 0.2) {
			double cosValue = directionVector.getX()
				/ Math.sqrt(directionVector.getX() * directionVector.getX()
					+ directionVector.getY() * directionVector.getY());
			if(directionVector.isEnd()) {// ע��Ϊ����
				cosValue *= -1;
			}
			double acosValue = Math.acos(cosValue);
			int degree = (int) (180 * (acosValue / 3.14159));// ������ü���������Ӧ�ĽǵĶ���
			if (directionVector.getY() < 0) {
				degree = 360 - degree;
			}
			try {
				typeIdOfTrack = judgeScopeOfDegree(degree);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else // ����ֱ�ߣ����ж��ǲ��ǻ�������
		{
			if (countOfBigThanZero <= 2)// ������͹
			{
				if (firstPoint.getX() >= secondPoint.getX()) {
					if (firstPoint.getY() >= secondPoint.getY()) {
						typeIdOfTrack = 15;
					} else {
						typeIdOfTrack = 10;
					}
				} else {
					if (firstPoint.getY() >= secondPoint.getY()) {
						typeIdOfTrack = 14;
					} else {
						typeIdOfTrack = 11;
					}
				}
			} else if (countOfSmallThanZero <= 2)// �����°�
			{
				if (firstPoint.getX() >= secondPoint.getX()) {
					if (firstPoint.getY() >= secondPoint.getY()) {
						typeIdOfTrack = 13;
					} else {
						typeIdOfTrack = 8;
					}
				} else {
					if (firstPoint.getY() >= secondPoint.getY()) {
						typeIdOfTrack = 12;
					} else {
						typeIdOfTrack = 9;
					}
				}
			} else {
				typeIdOfTrack = -1;
			}
		}
		return typeIdOfTrack;
	}

	/***************************************************************************
	 * ʵ��ʶ��ͬ�ı༭����������ID
	 **************************************************************************/
	public OperatorType recognize(List<MyPoint> points) {
		MyPoint firstPoint, secondPoint, point_Loop;
		ArrayList<Integer> typeIdsOfTrack;
		int stateOfDFA;
		boolean errFlag;
		int index_loop, index_Seperate = -1;
		int typeIdOfTrack;
		int typeIdOfTrack_0;
		int typeIdOfTrack_1;
		OperatorType opreratorId = OperatorType.OP_ID_INVALID;
		float MaxDistanceToLine = 0;
		float distanceToLine;
		float para_A, para_B, para_C;

		// �жϸù켣�Ƿ���ֱ��
		typeIdOfTrack = getTypeIdOfTrack(points, 0,
			points.size() - 1);
		if (typeIdOfTrack >= 0 && typeIdOfTrack <= 7)// ��ֱ��
		{
			if (0 == typeIdOfTrack) {
				opreratorId = OperatorType.OP_ID_HALFDEL;// �ñ༭����ɾ������һ��
			} else if (1 == typeIdOfTrack) {
				opreratorId = OperatorType.OP_ID_LEFTCUT;
			} else if (3 == typeIdOfTrack) {
				opreratorId = OperatorType.OP_ID_RIGHTCUT;
			}
		} else // �ù켣����ֱ��
		{
			firstPoint = (MyPoint) points.get(0);
			secondPoint = (MyPoint) points.get(points.size() - 1);
			// ����ֱ����ͨ���̵���������
			para_A = secondPoint.getY() - firstPoint.getY();
			para_B = firstPoint.getX() - secondPoint.getX();
			para_C = secondPoint.getX() * firstPoint.getY() - firstPoint.getX()
				* secondPoint.getY();
			// ����켣�������˵����ɵ�ֱ�߾������ĵ�
			for (index_loop = 1; index_loop < (points.size() - 1); index_loop++) {
				point_Loop = (MyPoint) points.get(index_loop);
				distanceToLine = (int) (Math.abs(para_A * point_Loop.getX()
					+ para_B * point_Loop.getY() + para_C) / Math.sqrt(para_A
					* para_A + para_B * para_B));
				if (distanceToLine > MaxDistanceToLine) {
					MaxDistanceToLine = distanceToLine;
					index_Seperate = index_loop;
				}
			}
			// ��ԭ�ȹ켣�ֳ����Σ�Ȼ��ֱ���������ε�����
			typeIdOfTrack_0 = getTypeIdOfTrack(points, 0,
				index_Seperate);
			typeIdOfTrack_1 = getTypeIdOfTrack(points,
				index_Seperate, points.size() - 1);
			typeIdsOfTrack = new ArrayList<Integer>();
			typeIdsOfTrack.add(typeIdOfTrack_0);
			typeIdsOfTrack.add(typeIdOfTrack_1);
			typeIdsOfTrack.add(-1);
			stateOfDFA = 0;
			errFlag = false;
			for (index_loop = 0; index_loop < typeIdsOfTrack.size(); index_loop++) {
				typeIdOfTrack = typeIdsOfTrack.get(index_loop);
				switch (stateOfDFA) {
				case 0:
					switch (typeIdOfTrack) {
					case 0:
						stateOfDFA = 12;
						break;
					case 1:
						stateOfDFA = 10;
						break;
					case 2:
						stateOfDFA = 1;
						break;
					case 3:
					case 4:
						stateOfDFA = 16;
						break;
					case 6:
					case 7:
						stateOfDFA = 8;
						break;
					case 10:
						stateOfDFA = 4;
						break;
					case 11:
					case 14:
						stateOfDFA = 6;
						break;
					case 12:
						stateOfDFA = 14;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recognize()");
						errFlag = true;
					}
					break;
				case 1:
					switch (typeIdOfTrack) {
					case 0:
						stateOfDFA = 2;
						break;
					case 4:
						stateOfDFA = 3;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ "in HwOperRecog_Structure.recognize()");
						errFlag = true;
					}
					break;
				case 2:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 18;
						opreratorId = OperatorType.OP_ID_LEFTSELECT;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 3:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 19;
						opreratorId = OperatorType.OP_ID_RIGHTSELECT;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 4:
					switch (typeIdOfTrack) {
					case 9:
					case 12:
						stateOfDFA = 5;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 5:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 20;
						opreratorId = OperatorType.OP_ID_COPY;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 6:
					switch (typeIdOfTrack) {
					case 8:
					case 13:
						stateOfDFA = 7;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 7:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 21;
						opreratorId = OperatorType.OP_ID_PASTE;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 8:
					switch (typeIdOfTrack) {
					case 1:
					case 2:
						stateOfDFA = 9;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 9:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 22;
						opreratorId = OperatorType.OP_ID_INSERT;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 10:
					switch (typeIdOfTrack) {
					case 3:
					case 4:
						stateOfDFA = 11;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 11:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 23;
						opreratorId = OperatorType.OP_ID_SPACE;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 12:
					switch (typeIdOfTrack) {
					case 2:
						stateOfDFA = 13;
						break;
					case 3:
					case 4:
						stateOfDFA = 11;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 13:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 24;
						opreratorId = OperatorType.OP_ID_ENTER;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 14:
					switch (typeIdOfTrack) {
					case 15:
						stateOfDFA = 15;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 15:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 25;
						opreratorId = OperatorType.OP_ID_UNDO;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 16:
					switch (typeIdOfTrack) {
					case 0:
					case 1:
						stateOfDFA = 17;
						break;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 17:
					switch (typeIdOfTrack) {
					case -1:
						stateOfDFA = 26;
						opreratorId = OperatorType.OP_ID_BACKSPACE;
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				case 18:
				case 19:
				case 20:
				case 21:
				case 22:
				case 23:
				case 24:
				case 25:
				case 26:
					switch (typeIdOfTrack) {
					default:
						System.err.println("error:typeIdOfTrack "
							+ typeIdOfTrack + " in stateOfDFA " + stateOfDFA
							+ " in HwOperRecog_Structure.recoginze()");
						errFlag = true;
					}
					break;
				default:
					System.err.println("wrong stateOfDFA " + stateOfDFA
						+ " in HwOperRecog_Structure.recoginze()");
					errFlag = true;
				}
				if (true == errFlag) {
					break;
				}
			}
		}
		return opreratorId;
	}
}