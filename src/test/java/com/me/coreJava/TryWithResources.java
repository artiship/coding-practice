package com.me.coreJava;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TryWithResources {

    @Test public void
    try_catch_finally() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("test"));
            while(scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) {
                scanner.close();
            }
        }
    }

    @Test public void
    try_with_resource() {
        try (Scanner scanner = new Scanner(new File("test.txt"));
             PrintWriter writer = new PrintWriter(new File("testWrite.txt"))) {
            while (scanner.hasNext()) {
                writer.print(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    class AutoCloseableResourceFirst implements AutoCloseable {

        @Override
        public void close() throws Exception {
            System.out.println("Close auto closable resource first");
        }
    }

}
