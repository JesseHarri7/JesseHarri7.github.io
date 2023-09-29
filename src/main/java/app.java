import services.MyBot;

public class app {
    public static void main(String[] args) {
        String discordToken = args[0];
        String valToken = args[1];

        MyBot bot = new MyBot(discordToken, valToken);
        bot.botListen();
    }
}
