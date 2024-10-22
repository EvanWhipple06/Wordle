import java.util.*;

public class Wordle {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";	//ansi stuff for changing colors
    public static String[] keyboard = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "\n", "A", "S", "D", "F", "G", "H", "J", "K", "L", "\n", " ", "Z", "X", "C", "V", "B", "N", "M"};    //keyboard array

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();					//standard object stuff
        System.out.println("Welcome to Wordle!");
        String[] words = {"crane", "slate", "stare", "flair", "trace", "grasp", "proud", "blink", "swipe", "charm", "flock", "piano", "mirth", "tread", "quilt"};  //array of possible words
        String word = words[rand.nextInt(words.length)];   //select a random word
        //System.out.println(word);			//print that word to console, FOR DEBUGGING
        String[] answer = word.split("");					//turns word into an array
        String input;
        String[] inputSplit;
        String[] print = new String[5];
        String correct = "";
        int flag = 0;
        boolean[] answerChecked = new boolean[5];
        boolean[] inputChecked = new boolean[5];			//initializing empty arrays for use later

        for(int i = 0; i < 6; i++) {  //looping system for rounds
            for(int j = 0; j < 5; j++) {
                answerChecked[j] = false;
                inputChecked[j] = false;
                correct = "";
                print[j] = null;  //resetting the arrays for if the answer/input is checked, and the print array
            }
            System.out.println("Your guess here:");
            input = sc.next();
            input = input.substring(0, 5).toLowerCase();
            inputSplit = input.split("");			//take and format the user input

            if(input.equals(word)) {
                System.out.println("Congratulations, you won in " + (i + 1) + " rounds!");
                flag = 1;
                break;
            }						//win condition

            for(int j = 0; j < answer.length; j++) {		//check if the right character is in the right spot, if so add the character to the relevant place in the print array as green
                if(answer[j].equals(inputSplit[j])) {
                    print[j] = ANSI_GREEN + inputSplit[j] + ANSI_RESET;
                    answerChecked[j] = true;
                    inputChecked[j] = true;
                    correct += inputSplit[j];
                }
            }

            for(int j = 0; j < answer.length; j++) {
                if (!inputChecked[j]) {						//check the remaining elements of the array to see if theyre just in the wrong spot of completely wrong
                    for(int k = 0; k < answer.length; k++) {
                        if(inputSplit[j].equals(answer[k]) && !answerChecked[k]) {
                            print[j] = ANSI_YELLOW + inputSplit[j] + ANSI_RESET;
                            answerChecked[k] = true;
                            inputChecked[j] = true;
                            correct += inputSplit[j];
                            break;

                        }
                    }
                }											//returns the character as yellow or red respectively/appropriately
                if (print[j] == null) {
                    print[j] = ANSI_RED + inputSplit[j] + ANSI_RESET;
                }
            }

            String[] keyboard = updateKeyboard(inputSplit, correct);

            for(String f: print) {
                System.out.print(f + " ");
            }									//print out the checked array with relevant colors

            System.out.println();

            for(String f: keyboard) {
                System.out.print(f + " ");
            }

            System.out.println(ANSI_RESET);		//reset the text color
        }
        if(flag == 0) {
            System.out.println("Womp womp, you lost, the word was " + word + ".");
        }
    }

    public static String[] updateKeyboard(String[] guess, String correct) {
        for(int i = 0; i < guess.length; i++) {
            for(int j = 0; j < keyboard.length; j++) {
                if(guess[i].toUpperCase().equals(keyboard[j]) && !correct.contains(guess[i])) { //check if the guess equals the current keyboard key and that key is not in guessed
                    keyboard[j] = keyboard[j].toLowerCase();                                    //change key to lowercase to signal it has been used
                }
            }
        }
        return keyboard;
    }
}