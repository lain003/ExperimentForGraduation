package others;

/**
 * ���W��\�����邽�߂̃N���X
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
	 * �����Ɏw�肳�ꂽCoordinate�̒��g���A���̒��g�ƈꏏ���ǂ������r���܂�
	 * @param compareCoordinate ��r������Coordinate
	 * @return ���ʁB
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
