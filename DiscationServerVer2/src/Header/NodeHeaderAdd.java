package Header;

import others.*;

public class NodeHeaderAdd extends NodeHeader{
	/**
	 * ‚±‚Ìƒwƒbƒ_‘S‘Ì‚Ì’·‚³
	 */
	int length;
	/**
	 *
	 */
	Node node;

	public NodeHeaderAdd() {
		length = 0;
		node = new Node();
	}
	
	@Override
	public byte[] convertByte() {
		byte[] headerByte = new byte[length];
		
		headerByte[0] = (byte) (length % 255);
		headerByte[1] = (byte) (length / 255 % 255);
		headerByte[2] = (byte) (length / 255 / 255 % 255);
		headerByte[3] = (byte) (length / 255 / 255 / 255);
		
		byte[] nodeByte = node.convertByte();
		for(int i = 0;i < nodeByte.length;i++)
		{
			headerByte[4 + i] = nodeByte[i];
		}
		
		return headerByte;
	}

	@Override
	public void setToMember(byte[] dataOfHeader) {
		length = (dataOfHeader[0] & 0xFF)+ (dataOfHeader[1] & 0xFF)* 255 + (dataOfHeader[2] & 0xFF)* 255 * 255+ (dataOfHeader[3] & 0xFF)* 255 * 255 * 255;
		byte[] nodeData = new byte[length - 4];
		for(int i = 0;i < nodeData.length;i++)
		{
			nodeData[i] = dataOfHeader[4 + i];
		}
		node.setToMember(nodeData);
	}
	
	public void setToMember(Node nodeArgument)
	{
		node = nodeArgument;
		length = node.getLength() + 4;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public Node getNode()
	{
		return node;
	}

}
