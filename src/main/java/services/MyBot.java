package services;

import Models.Player;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyBot {

    private final String discordToken;
    private final String valToken;

    public void botListen() {
        DiscordClient client = DiscordClient.create(discordToken);

        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
                gateway.on(ReadyEvent.class)
                        .subscribe(ready -> System.out.println("Logged in as " + ready.getSelf().getUsername()));

                gateway.on(MessageCreateEvent.class)
                        .subscribe(event -> {
                        Message message = event.getMessage();
                        if (message.getContent().equals("!getRanks")) {
                            try {
                                getValoRanks(message);
                            }catch (Exception e) {
                                System.out.println("Something went wrong: " + e.getMessage());
                                message.getChannel().block().createMessage(
                                        "Something failed")
                                        .block();
                            }
                        }
                    });

            return gateway.onDisconnect();
            });

        login.block();
    }

    private void getValoRanks(Message message) throws IOException, InterruptedException {
        List<EmbedCreateSpec> playerCardList = new ArrayList<>();
        List<Player> playerList = getPlayerList();
        DiscordCardBuilder cardBuilder = new DiscordCardBuilder();

        for(Player player : playerList) {
            playerCardList.add(cardBuilder.createDiscordCard(player));
        }

//      List<Message> messagesToDelete = new ArrayList<>();
//        message.getChannel().block().createMessage(Calendar.getInstance().getTime().toString())
//                .block();
        if(playerCardList.size() > 10) {
            for(EmbedCreateSpec discordCard : playerCardList) {
                message.getChannel().block().createMessage(discordCard)
                        .block();

//              messagesToDelete.add(delete);
            }
        }else{
            message.getChannel().block().createMessage(
                            playerCardList.toArray(new EmbedCreateSpec[0]))
                    .block();

//          messagesToDelete.add(delete);
        }

        /*try {
            Thread.sleep(Duration.ofMinutes(5).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(Message delete : messagesToDelete) {
            delete.delete().block();
        }*/

//      message.delete().block();
    }

    private List<Player> getPlayerList() throws IOException, InterruptedException {
        ValorantLeaderboard board =  new ValorantLeaderboard(valToken);
        return board.getLeaderBoard();
    }

    public MyBot(String discordToken, String valToken) {
        this.discordToken = discordToken;
        this.valToken = valToken;
    }
}