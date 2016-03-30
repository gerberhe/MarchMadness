
public class NewTeam {
	
	String teamName;
	double percentage;
	String region;
	int seed;

	public NewTeam(String teamName, double percentage, String region, int seed) {
		this.teamName = teamName;
		this.percentage = percentage;
		this.region = region;
		this.seed = seed;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public double getPercentage() {
		return percentage;
	}

	public String getRegion() {
		return region;
	}

	public int getSeed() {
		return seed;
	}
	
}