package obje;

import others.Coordinate;

/**
 * タッチパッドで方向性を示すためのクラス
 */
public class DirectionAction extends Obje{
	
	/**
	 * タッチパッドで触った方角を示す
	 */
	int direction;
	
	public void setDirection(int direc)
	{
		direction = direc;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	@Override
	public int getLength() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void setToMember(byte[] originData) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public byte[] convertByte() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Coordinate[] getScope() {
		//未自走//////////////////////////////
		Coordinate[] returnCoordinate = new Coordinate[0];
		return returnCoordinate;
	}
}
