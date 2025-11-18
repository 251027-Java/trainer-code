package org.example;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String sampleInput;
        sampleInput = scanner.nextLine();
        boolean result = true;
        for (int i = 0; i < sampleInput.length(); i++) {
            if (sampleInput.charAt() != sampleInput.charAt()) {
                result = false;
                break;
            }
        }
        System.out.println(result);
    }
}