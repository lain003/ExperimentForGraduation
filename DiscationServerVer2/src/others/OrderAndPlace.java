package others;

/**
 * Historyを保存するときに、使うクラス。命令と場所を一体化している
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
