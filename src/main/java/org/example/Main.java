package org.example;

import com.github.javafaker.Faker;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
//        String desc=new Faker().company().buzzword();
        String desc=new Faker().company().catchPhrase();
        String title=new Faker().name().title();
        System.out.println(title);
        System.out.println();

    }
}