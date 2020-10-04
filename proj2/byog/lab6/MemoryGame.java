package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int seed;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        // Random RANDOM = new Random(seed);
        MemoryGame game = new MemoryGame(seed, 50, 30);
        game.startGame();

        /** // The following codes are for debugging
        String astr = game.generateRandomString(4);
        System.out.println(astr);
        game.drawFrame(astr);
        StdDraw.pause(1000);
        game.flashSequence(astr);
        String Userinput = game.solicitNCharsInput(4);
        System.out.println(Userinput);
         */
    }

    public MemoryGame(int SEED, int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.seed = SEED;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        this.rand = new Random(SEED);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder returnstr = new StringBuilder();
        Random RANDOM = new Random(seed);
        for (int i = 0; i < n; i++) {
            int index = RANDOM.nextInt(26);
            char temp = CHARACTERS[index];
            returnstr.append(temp);
        }
        return returnstr.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        Font myfont = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(myfont);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text((int) this.width/2.0, (int) this.height/2.0, s);

        Font topfont = new Font("Arial", Font.ITALIC, 30);
        StdDraw.setFont(topfont);
        StdDraw.setPenColor(StdDraw.YELLOW);
        String WoT;
        if (playerTurn == false) {
            WoT = "Watch";
        } else {
            WoT = "Type";
        }
        StdDraw.text((int) this.width/2.0 - 2, this.height-1, WoT);
        StdDraw.text(5, this.height-1, "Round: " + String.valueOf(this.round));
        StdDraw.text(this.width-10, this.height-1, ENCOURAGEMENT[this.round % 7]);
        StdDraw.line(0, this.height-2, this.width, this.height-2);

        StdDraw.show();
        // need to implement the display of game information on top
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            String sub = letters.substring(i,i+1);
            drawFrame(sub); // how to adjust the display time?
            StdDraw.pause(1000); // pause for 500 milliseconds
            drawFrame(" ");
            StdDraw.pause(500);
        }
    }

    // StdDraw library cannot handle backspace to remove the last character entered
    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input

        StringBuilder returnStr = new StringBuilder();
        while (returnStr.length() < n) { // cannot use for loop here
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char next = StdDraw.nextKeyTyped(); // nextKeyTyped() returns the key as a char
            returnStr.append(next);
            drawFrame(returnStr.toString());
        }
        return returnStr.toString();


        // The following is taken from the answer key
        /**
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
            drawFrame(input);
        }
        StdDraw.pause(500);
        return input;
         */
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.round = 1;
        this.gameOver = false;
        this.playerTurn = false;

        //TODO: Establish Game loop
        while (this.gameOver == false) {
            this.drawFrame("Round: "+round);
            StdDraw.pause(1000);
            String target = this.generateRandomString(round);
            this.flashSequence(target);

            this.playerTurn = true;
            this.drawFrame(" ");
            String actual = this.solicitNCharsInput(round);
            if (!actual.equals(target)) {
                this.gameOver = true;
                String print = "Game Over! \n You made it to round: " + round;
                this.drawFrame(print);
                continue;
            }
            this.drawFrame(ENCOURAGEMENT[round % 7]);
            StdDraw.pause(1000);
            this.round += 1;
            this.playerTurn = false;
        }
    }
}
