package others;

/**
 * 座標を表現するためのクラス
 */
public class Coordinate {
	public int x;
	public int y;
	
	public Coordinate()
	{
		x = 0;
		y = 0;
	}
	
	public Coordinate(int ax,int ay)
	{
		x = ax;
		y = ay;
	}
	
	/**
	 * 引数に指定されたCoordinateの中身が、この中身と一緒かどうかを比較します
	 * @param compareCoordinate 比較したいCoordinate
	 * @return 結果。
	 */
	public boolean compare(Coordinate compareCoordinate)
	{
		if(x == compareCoordinate.x)
		{
			if(y == compareCoordinate.y)
			{
				return true;
			}
		}
		
		return false;
	}
}
