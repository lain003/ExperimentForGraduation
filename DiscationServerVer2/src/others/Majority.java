package others;

/**
 * �������Ɋւ���N���X
 */
public class Majority{
	/**
	 * �^���[�̑���
	 */
	private int ayeVote;
	/**
	 * ���Ε[�̑���
	 */
	private int nayVote;
	
	public Majority()
	{
		ayeVote = 0;
		nayVote = 0;
	}
	
	public void addVote(char vote)
	{
		if(vote == 'Y')
		{
			ayeVote++;
		}
		else if(vote == 'N')
		{
			nayVote++;
		}
		else
		{
			System.out.println("�����ȕ[�ł�");
		}
	}
	
	public int getAyeote()
	{
		return ayeVote;
	}
	
	public int getNayVote()
	{
		return nayVote;
	}
	
	/**
	 * �[�̑�����S�ă��Z�b�g���܂�
	 */
	public void reset(){
		ayeVote = 0;
		nayVote = 0;
	}
}
