package services;

import Models.Player;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

public class DiscordCardBuilder {

    public EmbedCreateSpec createDiscordCard(Player player) {
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
        builder.image(player.getPlayerCardWide());
        builder.author(player.getUsername(), "", player.getPlayerCard());
        builder.title(player.getRank());
        builder.addField("MMR change:", String.valueOf(player.getMmrChange()), true);
        builder.addField("Elo: ", String.valueOf(player.getElo()), true);
        builder.addField("RR: ", String.valueOf(player.getRankRating()) + "/100", true);
//        builder.description(
//                "MMR change: " + player.getMmrChange() + "\n" +
//                        "Rank: " + player.getRank() + "\n"
//        );
        builder.thumbnail(player.getRankImage());
        builder.color(getRankColor(player.getRank()));

        return builder.build();
    }

    private Color getRankColor(String rank) {
        rank = rank.toUpperCase();
        if(rank.contains(" ")) {
            rank = rank.substring(0, rank.indexOf(" "));
        }

        switch (rank) {
            case "IRON" :
                return Color.of(92, 92, 92);

            case "BRONZE":
                return Color.of(125, 91, 13);

            case "SILVER":
                return Color.of(202, 205, 204);

            case "GOLD":
                return Color.of(234,191,68);

            case "PLATINUM":
                return Color.of(85, 209, 224);

            case "DIAMOND":
                return Color.of(239,151,244);

            case "IMMORTAL":
                return Color.of(150,59,102);

            case "RADIANT":
                return Color.of(251,252,189);

            default:
                return Color.WHITE;
        }
    }
}
