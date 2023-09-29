package Models;

public class Player {

    private int elo;
    private String username;
    private int mmrChange;
    private String rank;
    private String rankImage;
    private String playerCard;
    private String playerCardWide;
    private int rankRating;

    public String getPlayerCard() {
        return playerCard;
    }

    public void setPlayerCard(String playerCard) {
        this.playerCard = playerCard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMmrChange() {
        return mmrChange;
    }

    public void setMmrChange(int mmrChange) {
        this.mmrChange = mmrChange;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankImage() {
        return rankImage;
    }

    public void setRankImage(String rankImage) {
        this.rankImage = rankImage;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getPlayerCardWide() {
        return playerCardWide;
    }

    public void setPlayerCardWide(String playerCardWide) {
        this.playerCardWide = playerCardWide;
    }

    public int getRankRating() {
        return rankRating;
    }

    public void setRankRating(int rankRating) {
        this.rankRating = rankRating;
    }
}
