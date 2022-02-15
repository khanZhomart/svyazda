package com.svayzda;

import com.svayzda.controllers.PostController;

import java.util.InputMismatchException;
import java.util.Scanner;


public class MyApplication {

    private final PostController postController;

    private final Scanner sc;

    public MyApplication(PostController postController) {
        this.postController = postController;
        sc = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println();
            System.out.println("Welcome to Hotel Management Application");
            System.out.println("Select option:");
            System.out.println("1. Create a post");

            try {
                System.out.print("Enter option: ");
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        createPost();
                        break;
                }
            } catch (InputMismatchException _) {
                System.out.println("Input must be integer");
                sc.nextLine(); // to ignore incorrect input
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("*************************");

        }
    }


    public void createPost() {
        System.out.println("Title: ");
        String title = sc.nextLine();
        System.out.println("Text: ");
        String text = sc.nextLine();
        System.out.println("disable comments: 1) yes 2) no ");
        int dcChoice = sc.nextInt();
        boolean disabledComments = dcChoice == 1 ? true : false;
        System.out.println("visibility: 0) All 1) Authorized 3) Friends");
        int visibility = sc.nextInt();
        System.out.println(postController.createPost(title, text, visibility, disabledComments, "rakha"));
    }

}



/*

(about repositories)
https://gist.github.com/maestrow/594fd9aee859c809b043


 */