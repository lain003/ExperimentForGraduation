package Header;

import java.net.IDN;
import java.util.ArrayList;

import others.*;
import obje.*;


public class RequestHeader extends Header{
	/**
	 * ���̃w�b�_�S�̂̒���������
	 */
	protected int headerLength = 0;
	/**
	 * data�̎�ނ�����
	 */
	protected int identNumber;
	/**
	 * ���߂̎�ނ�\��
	 */
	protected int order;
	/**
	 * ���܂��Ă��Ȃ��̈�
	 */
	protected byte[] unDecided = new byte[4];
	/**
	 * data�{��
	 */
	protected DataHeader data;

	/**
	 * ��������Header���쐬���܂�
	 * @param node ���ɂȂ�Node�ł�
	 * @param identnumOrder add.deleate,update�̎O��ނ̖��߂̒�����w�肵�܂�
	 * @return ������Header�̃o�C�g��Ԃ��܂�
	 */
	public byte[] createHeader(Node node,int identOrder)
	{
		setToMember(node, identOrder);

		return convertByte();
	}

	/**
	 * Client���瑗���Ă����f�[�^����͂��āA�����o�ɃZ�b�g���鎞�ɌĂ΂��
	 */
	@Override
	public void setToMember(byte[] headerData) {

//		setToMyMember(headerData);
//
//		byte[] dataHeaderByte = new byte[headerLength - 10];
//		for(int i = 0;i < dataHeaderByte.length;i++)
//		{
//			dataHeaderByte[i] = headerData[4 + 1 + 1 + 4 + i];
//		}
//
//		if(identNumber == Define.obje.NODE)
//		{
//			if(order == Define.order.CREATE)
//			{
//				data = new NodeHeaderAdd();
//			}
//		}
//		data.setToMember(dataHeaderByte);
	}

	/**
	 * �������烁���o���Z�b�g���܂��B����͌��݂̏�,Node�Ɩ��߂����Ƀ��N�G�X�g�w�b�_���쐬���邽�߂Ɏg���邱�Ƃ�z�肵�Ă��܂�
	 * @param node ���ɂ�����Node���w�肷��
	 * @param orderArgument ���߂̎�ނ�\��
	 */
	public void setToMember(Node node,int orderArgument)
	{
//		identNumber = Define.obje.NODE;
//		order = orderArgument;
//		headerLength += 4 + 1 + 1+ 4;
//		if(order == Define.CREATE)
//		{
//			data = new NodeHeaderAdd();
//			((NodeHeaderAdd)data).setToMember(node);
//			headerLength += ((NodeHeaderAdd)data).getLength();
//		}
	}


	/**
	 * �w�b�_�[�f�[�^���玩���̃����o�[���Z�b�g���܂� data�͊܂܂�܂���
	 * @param headerData
	 */
	protected void setToMyMember(byte[] headerData)
	{
		headerLength = (headerData[0] & 0xFF)+ (headerData[1] & 0xFF)*255+ (headerData[2] & 0xFF)*255*255+ (headerData[3] & 0xFF)* 255 * 255 * 255;
		identNumber = (headerData[4] & 0xFF);
		order = (headerData[5] & 0xFF);
	}

	@Override
	public byte[] convertByte() {
		byte[] headerByte = new byte[headerLength];

		headerByte[0] = (byte) (headerLength % 255);
		headerByte[1] = (byte) (headerLength / 255 % 255);
		headerByte[2] = (byte) (headerLength / 255 / 255 % 255);
		headerByte[3] = (byte) (headerLength / 255 / 255 / 255);
		headerByte[4] = (byte) (identNumber);
		headerByte[5] = (byte) (order);
		byte[] dataByte = data.convertByte();
		for(int i = 0;i < dataByte.length;i++)
		{
			headerByte[10 + i] = dataByte[i];
		}

		return headerByte;
	}

	public int getIdentNumber() {
		return identNumber;
	}

	public int getOrder() {
		return order;
	}

	public int getHeaderLength() {
		return headerLength;
	}

	public DataHeader getData() {
		return data;
	}
}
