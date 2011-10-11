package obje;

import others.Coordinate;

/**
 * Objectの属性を持つクラス(Round,Char,JPGなど)はこれを継承しなくてはならない
 */
public abstract class Obje {
	public abstract int getLength();
	
	/**
	 * メンバ変数をセットする
	 * @param originData このデータを元にセットする。
	 */
	public abstract void setToMember(byte[] originData);
	
	/**
	 * 自身をbyteに変換した値を返します
	 * @return 変換したbyte配列
	 */
	public abstract byte[] convertByte();
	
	/**
	 * このオブジェクトが持っている、ボジションの範囲を会得します(主にRemoveするときに使用します)(JPGは0,0を返します)
	 * @return 
	 */
	public abstract Coordinate[] getScope();
}
