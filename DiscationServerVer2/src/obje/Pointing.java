package obje;

import java.util.ArrayList;

import others.Coordinate;

/**
 * ポインティングに関するクラスです。
 */
public class Pointing extends Obje{
	protected ArrayList<Coordinate> coordinatesList = new ArrayList<Coordinate>();
	protected int r;//半径

	public void setR(int R)
	{
		r = R;
	}

	public int getR()
	{
		return r;
	}

	public ArrayList<Coordinate> getCoordinateList()
	{
		return coordinatesList;
	}

	public void setCoordinate(int x,int y)
	{
		Coordinate addCoordinate = new Coordinate();
		addCoordinate.x = x;
		addCoordinate.y = y;

		coordinatesList.add(addCoordinate);
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
		Coordinate[] coordinate = new Coordinate[coordinatesList.size()];
		for(int i = 0;i < coordinate.length;i++)
		{
			coordinate[i] = coordinatesList.get(i);
		}
		
		return coordinate;
	}
}
