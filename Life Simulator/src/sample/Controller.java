package sample;



import com.sun.prism.paint.Color;
import java.awt.Transparency;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
public class Controller {
    Button[][] btn = new Button[32][32];
    int[][] gameGrid = new int[32][32];
    @FXML
    private AnchorPane aPane;

    @FXML
    private GridPane gPane;

    private ArrayList<Person> p = new ArrayList<>();
    private Tree[] t = new Tree[40];
    private Food[] f = new Food[40];
    private Animal[] a = new Animal[40];
    private ArrayList<Water> w = new ArrayList<>();


    @FXML
    private void handleStart(ActionEvent event) {
        for (int i = 0; i < 15; i++) {
            makeNewPersonStart();
        }

        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn.length; j++) {

                //Initializing 2D buttons with values i,j
                btn[i][j] = new Button();
                btn[i][j].setStyle("-fx-background-color:#FFFFFF");
                btn[i][j].setMinSize(25, 25);
                btn[i][j].setMaxSize(25, 25);
                //Paramters:  object, columns, rows
                gPane.add(btn[i][j], j, i);
                gameGrid[i][j] = 0;


            }
        }
        makeMap();

        gPane.setGridLinesVisible(true);
        gPane.setVisible(true);
        start();
    }


    public void updateScreen() {
        for (int j = 0; j < btn.length; j++) {
            for (int i = 0; i < btn.length; i++) {
                if (gameGrid[j][i] == 0) {
                    btn[i][j].setStyle("-fx-background-color:#FFFFFF");
                } else if (gameGrid[j][i] == 1) {
                    btn[i][j].setStyle("-fx-background-color:#ff0000");
                } else if (gameGrid[j][i] == 2) {
                    btn[i][j].setStyle("-fx-background-color:#804000");
                } else if (gameGrid[j][i] == 3) {
                    btn[i][j].setStyle("-fx-background-color:#1161E5");
                }
            }
        }
    }


    public void start() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                for (int i =0;i <p.size();i++) {
                    if (now - p.get(i).getStartTime() > 1999999999 / p.get(i).getSpeed()) {
                        p.get(i).setStartTime();

                        if (makeNewPerson(p.get(i))){

                        } else if (!cutTree(p.get(i))) {
                            p.get(i).move(gameGrid);
                        }

                    }
                }
                // for planting trees
                for (Tree t : t) {
                    if (now - t.getStartTime() > 1000000000) {
                        makeNewTrees(t);
                        t.setStartTime();
                    }
                }

                updateScreen();


            }
        }.start();
    }

    //pre has person cuting tree, has array t fully initialised
    //post returns if person has cut a tree and if they have removes it
    private boolean cutTree(Person pt) {
        boolean temp = false;
        for (Tree t : this.t) {
            if (t.isPlanted()) {
                if (pt.getX() == t.getX() && pt.getY() == t.getY()) {
                    t.setPlanted();
                    gameGrid[t.getX()][t.getY()] = 0;
                    temp = true;
                }
            }
        }
        return temp;
    }


    // pre has array t and game grid
    //post inishalised all tree objects in t and plants all but 7 of the trees
    private void makeTreesStart() {
        Random r = new Random();
        for (int i = 0; i < t.length - 7; i++) {
            boolean canNotPlace = true;
            while (canNotPlace) {
                int temp1 = r.nextInt(16) + 10;
                int temp2 = r.nextInt(gameGrid.length);
                int temp3 = r.nextInt(gameGrid.length);
                if (gameGrid[temp2][temp3] == 0) {
                    t[i] = new Tree(temp1, temp2, temp3);
                    gameGrid[temp2][temp3] = 2;
                    canNotPlace = false;
                }
            }
        }
        for (int i = 7; i < t.length; i++) {
            t[i] = new Tree(0, 100, 100);
            t[i].setPlanted();
        }
    }

    // pre array t with all objects initialised, array game grid with 0 for sum index's
    // post has a 1 out of 20 change to place the tree if the tree is not already on the board with a random amount to wood
    private void makeNewTrees(Tree t) {
        Random r = new Random();
        boolean canPlace = false;
        int temp1;
        int temp2;
        int temp3;
        if (!t.isPlanted()) {
            if (r.nextInt(100) < 5) {
                while (!canPlace) {
                     temp1 = r.nextInt(16) + 10;
                     temp2 = r.nextInt(gameGrid.length);
                     temp3 = r.nextInt(gameGrid.length);
                     if (gameGrid[temp2][temp3] ==0){
                         gameGrid[temp2][temp3] = 2;
                         t.setLumberAmountLeft(temp1);
                         t.setX(temp2);
                         t.setY(temp3);
                         t.setPlanted();
                         canPlace = true;
                     }
                }
            }
        }


    }

    // pre array p
    //post make a group of people for the start if the sim with varying stats such as speed moves, is solitary, and strength
    private void makeNewPersonStart() {
        Random r = new Random();
        int tempx = r.nextInt(gameGrid.length);
        int tempy = r.nextInt(gameGrid.length);
        boolean canBeThere = false;
        while (!canBeThere) {
            if (gameGrid[tempx][tempy] == 0) {
                canBeThere = true;
            } else {
                tempx = r.nextInt(gameGrid.length);
                tempy = r.nextInt(gameGrid.length);
            }
            p.add(new Person(r.nextInt(10), tempx, tempy, r.nextInt(2) == 1));
        }
    }


    private void makeMap(){
        placeRiver();
        makeTreesStart();
    }


    private void placeRiver(){
        Random r = new Random();
        int xval = r.nextInt(7) + 7;
        for (int i = 0;i<gameGrid.length/2;i++){
            for (int w = -1;w<=2;w++){
                gameGrid[w+xval][i] = 3;
            }
        }
    }


    private boolean makeNewPerson(Person q) {
        for (Person w : p) {
            if (q.getX()+1 == w.getX()&&((q.isMale() && !w.isMale()) || (!q.isMale() && w.isMale()))) {
                makeAPerson(q.getX(),q.getY());

                return true;
            } else if (q.getX() -1 == w.getX()&&((q.isMale() && !w.isMale()) || (!q.isMale() && w.isMale()))) {
                makeAPerson(q.getX(),q.getY());

                return true;
            } else if (q.getY()+1 == w.getY()&&((q.isMale() && !w.isMale()) || (!q.isMale() && w.isMale()))) {
                makeAPerson(q.getX(),q.getY());

                return true;
            } else if (q.getY()-1 == w.getY()&&((q.isMale() && !w.isMale()) || (!q.isMale() && w.isMale()))) {
                makeAPerson(q.getX(),q.getY());

                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    private void makeAPerson(int x,int y){
        Random r= new Random();
        if (gameGrid[x+1][y] == 0){
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));
        }else if (gameGrid[x-1][y] == 0) {
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));

        }else if (gameGrid[x][y+1] == 0) {
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));

        }else if (gameGrid[x][y-1] == 0) {
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));

        }else if (gameGrid[x+1][y+1] == 0) {
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));

        }else if (gameGrid[x+1][y-1] == 0) {
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));

        }else if (gameGrid[x-1][y+1] == 0) {
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));

        }else if (gameGrid[x-1][y-1] == 0) {
            p.add(new Person(r.nextInt(10),x+1,y,r.nextInt(2) == 1));

        }


    }





// TODO make it so you cant walk on water
//    TODO add need of resources or you die,  they will slowly deplet
//    TODO get back to work and have a good project after






}