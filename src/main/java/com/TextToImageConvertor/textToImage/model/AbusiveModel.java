package com.TextToImageConvertor.textToImage.model;

import java.util.HashSet;

public class AbusiveModel {

    private static final HashSet<String>abusiveMessages = new HashSet<>();

    public static void intialization() {
        abusiveMessages.add("Jerk");
        abusiveMessages.add("Moron");
        abusiveMessages.add("Idiot");
        abusiveMessages.add("Dumb");
        abusiveMessages.add("Dork");
        abusiveMessages.add("Loser");
        abusiveMessages.add("Doofus");
        abusiveMessages.add("Nitwit");
        abusiveMessages.add("Twit");
        abusiveMessages.add("Clown");
        abusiveMessages.add("Chump");
        abusiveMessages.add("Fool");
        abusiveMessages.add("Nerd");
        abusiveMessages.add("Lame");
        abusiveMessages.add("Goofball");
        abusiveMessages.add("Wimp");
        abusiveMessages.add("Weirdo");
        abusiveMessages.add("Silly");
        abusiveMessages.add("Cheapskate");
        abusiveMessages.add("Knucklehead");
    }

    public static boolean checkAbusiveWordOrNot(String word){
        intialization();
        if(abusiveMessages.contains(word)){
            return true;
        }
        else{
            return false;
        }
    }
}
