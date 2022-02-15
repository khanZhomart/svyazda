package com.svayzda.websocket_client;

import com.svayzda.controllers.PostController;
import com.svayzda.data.IDB;
import com.svayzda.data.PostgresDB;
import com.svayzda.entities.Post;
import com.svayzda.repositories.PostRepository;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;


//    IUserRepository userRepository = new UserRepository(db);
//    UserController userController = new UserController(userRepository);

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }


    public static void sendUpdatedPosts(BufferedWriter bufferedWriter, PostController postController) throws IOException {
        Collection<Post> posts = postController.getAllPosts();

        for (Post post: posts) {
            bufferedWriter.write(post.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public void listenForMessage() throws IOException {

        bufferedWriter.write(username);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        messageFromGroupChat = bufferedReader.readLine();
                        System.out.println(messageFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public void sendMessage() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        IDB db = new PostgresDB();
        PostRepository postRepository = new PostRepository(db);
        PostController postController = new PostController(postRepository);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);
        client.listenForMessage();
        //client.sendMessage();
        System.out.println("Press '1' for posting");
        while (true) {
            int choice = scanner.nextInt();
            if (choice == 1) {
                createPost(username, postController);
                sendUpdatedPosts(client.bufferedWriter, postController);
            }
        }


    }



    public static void createPost(String username, PostController postController) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Title: ");
        String title = sc.nextLine();
        System.out.println("Text: ");
        String text = sc.nextLine();
        System.out.println("disable comments: 1) yes 2) no ");
        int dcChoice = sc.nextInt();
        boolean disabledComments = dcChoice == 1 ? true : false;
        System.out.println("visibility: 0) All 1) Authorized 3) Friends");
        int visibility = sc.nextInt();
        System.out.println(postController.createPost(title, text, visibility, disabledComments, username));
    }
}
