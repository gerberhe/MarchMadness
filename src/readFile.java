import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class readFile {
	
	public static ArrayList<String> teamNames = new ArrayList<>();
	public static ArrayList<Double> percentages = new ArrayList<>();
	public static ArrayList<String> regions = new ArrayList<>();
	public static ArrayList<Integer> seeds = new ArrayList<>();
	
	public static void main(String[] args) {
		int numTeams = 64;
		
		try {
			Scanner input = new Scanner(System.in);
			System.out.print("Enter file name with extension: ");
			File file = new File(input.nextLine());
			
			input = new Scanner(file);
			input.nextLine();
			while (input.hasNextLine()) {
				String line = input.nextLine();
				String[] parts = line.split(",");
				teamNames.add(parts[0]);
				percentages.add(Double.parseDouble(parts[1]));
				regions.add(parts[2]);
				seeds.add(Integer.parseInt(parts[3]));
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		NewTeam winner = calculateTotalWinner();
	}
	
	public NewTeam findWinnerofMatchup(NewTeam team1, NewTeam team2) {
		double team1Percentage = team1.getPercentage();
		double team2Percentage = team2.getPercentage();
		
		double chanceTeam1Wins = ((team1Percentage - (team1Percentage * team2Percentage)) / (team1Percentage + team2Percentage - (2 * team1Percentage * team2Percentage)));
		
		double randChance = Math.random();
		if (randChance >= chanceTeam1Wins) {
			return team1;
		} else {
			return team2;
		}
	}
	
	public NewTeam calculateWinnerRegion(String region) {
		ArrayList<NewTeam> teams = new ArrayList<>();
		for (int i = 0; i < teamNames.size(); i++) {
			if (regions.get(i) == region) {
				teams.add(new NewTeam(teamNames.get(i) , percentages.get(i) , regions.get(i) , seeds.get(i)));
			}
		}
		NewTeam winner1vs16 = findWinnerofMatchup(teams.get(0), teams.get(15));
		NewTeam winner8vs9 = findWinnerofMatchup(teams.get(7), teams.get(8));
		NewTeam winner5vs12 = findWinnerofMatchup(teams.get(4), teams.get(11));
		NewTeam winner4vs13 = findWinnerofMatchup(teams.get(3), teams.get(12));
		
		NewTeam winner6vs11 = findWinnerofMatchup(teams.get(5), teams.get(10));
		NewTeam winner3vs14 = findWinnerofMatchup(teams.get(2), teams.get(13));
		NewTeam winner7vs10 = findWinnerofMatchup(teams.get(6), teams.get(9));
		NewTeam winner2vs15 = findWinnerofMatchup(teams.get(1), teams.get(14));
		
		
		NewTeam winner1v16VS8v9 = findWinnerofMatchup(winner1vs16 , winner8vs9);
		NewTeam winner5v12VS4v13  = findWinnerofMatchup(winner5vs12 , winner4vs13);
		
		NewTeam winner6v11VS3v14 = findWinnerofMatchup(winner6vs11 , winner3vs14);
		NewTeam winner7v10VS2v15 = findWinnerofMatchup(winner7vs10 , winner2vs15);
		
		
		NewTeam winnerTop = findWinnerofMatchup(winner1v16VS8v9  , winner5v12VS4v13);
		NewTeam winnerBottom = findWinnerofMatchup(winner6v11VS3v14 , winner7v10VS2v15);
		
		
		NewTeam regionWinner = findWinnerofMatchup(winnerTop, winnerBottom);
		
		return regionWinner;
		
	}
	
	public NewTeam calculateTotalWinner() {
		NewTeam winnerEastvMidwest = findWinnerofMatchup(calculateWinnerRegion("East"), calculateWinnerRegion("Midwest"));
		NewTeam winnerSouthvWest = findWinnerofMatchup(calculateWinnerRegion("South"), calculateWinnerRegion("West"));
		
		return findWinnerofMatchup(winnerEastvMidwest, winnerSouthvWest);
	}
	
}
