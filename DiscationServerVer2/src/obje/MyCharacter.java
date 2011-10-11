package obje;

import others.Coordinate;


public class MyCharacter extends Obje{
	protected String chars;
	/**
	 * 2byte
	 */
	protected int x;
	/**
	 * 2byte
	 */
	protected int y;
	
	public void setChars(String ArgumentChars,int argumentx,int argumenty)
	{
		chars = ArgumentChars;
		x = argumentx;
		y = argumenty;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public String getChars()
	{
		return chars;
	}
	
	@Override
	public int getLength() {
		return chars.length() + 2 + 2;
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
		Coordinate[] returnCoordinate = new Coordinate[1];
		returnCoordinate[0] = new Coordinate(x,y);
		return returnCoordinate;
	}
}
