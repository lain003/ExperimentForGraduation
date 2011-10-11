package others;

/**
 * ‘½”Œˆ‚ÉŠÖ‚·‚éƒNƒ‰ƒX
 */
public class Majority{
	/**
	 * ^¬•[‚Ì‘”
	 */
	private int ayeVote;
	/**
	 * ”½‘Î•[‚Ì‘”
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
			System.out.println("–³Œø‚È•[‚Å‚·");
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
	 * •[‚Ì‘”‚ğ‘S‚ÄƒŠƒZƒbƒg‚µ‚Ü‚·
	 */
	public void reset(){
		ayeVote = 0;
		nayVote = 0;
	}
}
