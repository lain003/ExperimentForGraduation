package Header;

/**
 * Header���ׂẮA���ɂȂ�N���X
 */
public abstract class Header {
	/**
	 * byte�̓��e���烁���o�[�ϐ����Z�b�g���܂��B�܂����g��data�������݂���ꍇ�A����data����setToMember���Ă΂Ȃ���΂Ȃ�Ȃ��B
	 * @param headerData ���̃f�[�^�����ɁA�Z�b�g���܂�
	 */
	abstract public void setToMember(byte[] headerData);
	
	/**
	 * Member����Header(byte)���쐬���܂�
	 */
	abstract public byte[] convertByte();
}
