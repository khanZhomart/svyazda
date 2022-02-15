package com.svayzda;

import com.svayzda.controllers.PostController;
import com.svayzda.data.PostgresDB;
import com.svayzda.data.IDB;
import com.svayzda.repositories.PostRepository;


public class Main {
    public static void main(String[] args) {

        IDB db = new PostgresDB();
        PostRepository postRepository = new PostRepository(db);
        PostController postController = new PostController(postRepository);

        MyApplication app = new MyApplication(postController);

        app.start();
    }
}
