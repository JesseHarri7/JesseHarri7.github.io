package services;

import Models.Player;
import Players.PlayerIDs;
import com.google.gson.JsonObject;
import net.socketconnection.jva.ValorantAPI;
import net.socketconnection.jva.enums.Rank;
import net.socketconnection.jva.player.ValorantPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValorantLeaderboard {

    private final String valToken;
    final static private String UNRATED_ICON_SMALL = "https://media.valorant-api.com/competitivetiers/edb72a72-7e6d-6010-9591-7c053bbdbf48/0/smallicon.png";

    public List<Player> getLeaderBoard() throws IOException, InterruptedException {
        //TODO: Try make use of /valorant/v2/mmr/{affinity}/{name}/{tag} to filter out people who don't have ranks in the current act
        ValorantAPI valorantAPI = new ValorantAPI();
        List<Player> playerList = new ArrayList<>();
        Map<String,String> nameTags = getNameTags();

        for(String nameTag : nameTags.values()) {
            try {
                ValorantPlayer valorantPlayer = new ValorantPlayer(valorantAPI).fetchData(nameTag);
                Rank rank = valorantPlayer.getRank();

                //            System.out.println("Player data: \n" +
                //                    valorantPlayer.getPlayerId() + "; \n" +
                //                    valorantPlayer.getMmrChange() + "; \n" +
                //                    valorantPlayer.getPlayerCard().getSmall() + "; \n" +
                //                    valorantPlayer.getRankImage().getSmall() + "; \n");
                //            System.out.println("Rank name: " + rank.getName());

                Player player = new Player();
                player.setPlayerCard(valorantPlayer.getPlayerCard().getSmall());
                player.setPlayerCardWide(valorantPlayer.getPlayerCard().getWide());
                player.setUsername(valorantPlayer.getUsername());
                player.setMmrChange(valorantPlayer.getMmrChange());
                player.setRank(rank.getName());
                player.setRankImage(valorantPlayer.getRankImage().getSmall());

                if(nameTags.get(PlayerIDs.AZRAA.getPlayerUUID()).equalsIgnoreCase(nameTag)
                    && rank.getId() < 9) {
                    player.setRank(Rank.RADIANT.getName());
                    player.setRankImage("https://media.valorant-api.com/competitivetiers/564d8e28-c226-3180-6285-e48a390db8b1/24/smallicon.png");
                }

                player.setElo(valorantPlayer.getElo());
                player.setRankRating(valorantPlayer.getRankRating());

                //Try get this to work for unranked people
                /*if(valorantPlayer.getRankRating() == 0) {
                    player.setRank(Rank.UNRANKED.getName());
                    player.setRankImage(UNRATED_ICON_SMALL);
                }*/

                playerList.add(player);
                System.out.println("Current player size: " + playerList.size());
//                Thread.sleep(6000);
            }catch (Exception e) {
                System.out.println("This person is a problem: " + nameTag);
            }
        }

        playerList = playerList.stream()
                .sorted((p1, p2) -> p2.getElo() - p1.getElo())
                .collect(Collectors.toList());

        /*playerList = playerList.stream()
                .sorted((p1, p2) -> p2.getRank().compareTo(p1.getRank()))
                .collect(Collectors.toList());*/

        return playerList;
    }

    private Map<String,String> getNameTags() throws IOException {
        Map<String,String> nameTags = new HashMap<>();
        ValorantAPI api = new ValorantAPI(valToken);

        for(PlayerIDs playerID : PlayerIDs.values()) {
            JsonObject accountData = api.sendRestRequest("/v1/by-puuid/account/" + playerID.getPlayerUUID()).getAsJsonObject().getAsJsonObject("data");

            String name = accountData.get("name").getAsString();
            String tag = accountData.get("tag").getAsString();

            nameTags.put(playerID.getPlayerUUID(), name + "#" + tag);
        }

        return nameTags;
    }

    public ValorantLeaderboard(String valToken) {
        this.valToken = valToken;
    }

}
