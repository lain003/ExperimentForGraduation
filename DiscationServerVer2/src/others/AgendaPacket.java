package others;

/**
 * Agenda��ID�������������́B��Ƀf�[�^�̑��M�̍ۂɁAAgenda�ɏꏊ��t���������Ƃ��Ɏg��
 */
public class AgendaPacket extends AgendaNode{
	private ID id = new ID();
	public ID getID()
	{
		return id;
	}
	
	public String getAgenda()
	{
		return agenda;
	}
	
	public void setAgenda(String s)
	{
		agenda = s;
	}
}
