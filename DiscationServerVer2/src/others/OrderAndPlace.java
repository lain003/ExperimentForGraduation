package others;

/**
 * History��ۑ�����Ƃ��ɁA�g���N���X�B���߂Əꏊ����̉����Ă���
 */
public class OrderAndPlace {
	protected int order;
	protected ID id;
	
	public void setMember(int Aorder,ID aId)
	{
		order = Aorder;
		id = aId;
	}
	
	public int getOrder()
	{
		return order;
	}
	
	public ID getId()
	{
		return id;
	}
}
