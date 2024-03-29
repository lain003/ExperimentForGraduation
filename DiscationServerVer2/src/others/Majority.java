package others;

/**
 * 多数決に関するクラス
 */
public class Majority{
	/**
	 * 賛成票の総数
	 */
	private int ayeVote;
	/**
	 * 反対票の総数
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
			System.out.println("無効な票です");
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
	 * 票の総数を全てリセットします
	 */
	public void reset(){
		ayeVote = 0;
		nayVote = 0;
	}
}
