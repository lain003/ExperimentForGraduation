package Header;

/**
 * Headerすべての、元になるクラス
 */
public abstract class Header {
	/**
	 * byteの内容からメンバー変数をセットします。また自身にdata部が存在する場合、そのdata部のsetToMemberを呼ばなければならない。
	 * @param headerData このデータを元に、セットします
	 */
	abstract public void setToMember(byte[] headerData);
	
	/**
	 * MemberからHeader(byte)を作成します
	 */
	abstract public byte[] convertByte();
}
