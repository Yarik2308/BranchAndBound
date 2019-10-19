package test.com.project;

import com.project.models.Leaf;
import com.project.models.Road;
import com.project.BranchAndBound;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBoundTest {

    public static void main(String[] args){
//        System.out.println(testReduce());
//        System.out.println("=======================");
//        System.out.println(testReduceWithClosedCity());
//        System.out.println("=======================");
//        System.out.println(testReduceWithClosedCity2());
//        System.out.println("=======================");
//
//
        System.out.println(testZeroScore());

    }

    private static boolean testReduce(){
        int[][] array = {
                {9999, 6, 4, 8, 7, 14},
                {6, 9999, 7, 11, 7, 10},
                {4, 7, 9999, 4, 3, 10},
                {8, 11, 4, 9999, 5, 11,},
                {7, 7, 3, 5, 9999, 7},
                {14, 10, 10, 11, 7, 9999}};
        int[][] array_after_reduce= {
                {9999, 0, 0, 3, 3, 6},
                {0, 9999, 1, 4, 1, 0},
                {1, 2, 9999, 0, 0, 3},
                {4, 5, 0, 9999, 1, 3},
                {4, 2, 0, 1, 9999, 0},
                {7, 1, 3, 3, 0, 9999}};

        Leaf leaf = new Leaf(0);



        List<Road> closedRoadsList = new ArrayList<>();

        int cost = BranchAndBound.reduce(array, leaf);

        if(cost != 34){
            System.out.println("Cost is: " + cost);
            return false;
        }

        for(int i=0; i<array.length; i++){
            for(int j=0; j<array.length; j++){
                if(array[i][j]!=array_after_reduce[i][j]){
                    System.out.println("Elements Are not Equal: " + i +" " + j);
                    System.out.println(array[i][j] + " " + array_after_reduce[i][j]);
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean testReduceWithClosedCity(){
        int[][] array = {
                {9999, 0, 0, 3, 3, 6},
                {9999, 9999, 1, 4, 1, 0},
                {1, 2, 9999, 0, 0, 3},
                {4, 5, 0, 9999, 1, 3},
                {4, 2, 0, 1, 9999, 0},
                {7, 1, 3, 3, 0, 9999}};
        int[][] array_after_reduce= {
                {9999, 0, 0, 3, 3, 6},
                {9999, 9999, 1, 4, 1, 0},
                {0, 2, 9999, 0, 0, 3},
                {3, 5, 0, 9999, 1, 3},
                {3, 2, 0, 1, 9999, 0},
                {6, 1, 3, 3, 0, 9999}};

        Leaf leaf = new Leaf(0);

        Road usedRoad = new Road(0, 1);
        Road closedRoad = new Road(1, 0);

        leaf.addUsedRoad(usedRoad);
        leaf.addBlockedRoad(closedRoad);

        int cost = BranchAndBound.reduce(array, leaf);

        if(cost != 1){
            System.out.println("Cost is: " + cost);
            return false;
        }

        for(int i=0; i<array.length; i++){
            for(int j=0; j<array.length; j++){
                if(array[i][j]!=array_after_reduce[i][j]){
                    System.out.println("Elements Are not Equal: " + i +" " + j);
                    System.out.println(array[i][j] + " " + array_after_reduce[i][j]);
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean testReduceWithClosedCity2(){
        int[][] array = {
                {9999, 0, 0, 3, 3, 6},
                {9999, 9999, 1, 4, 1, 0},
                {9999, 2, 9999, 0, 0, 3},
                {3, 5, 0, 9999, 1, 3},
                {3, 2, 0, 1, 9999, 0},
                {6, 1, 3, 3, 0, 9999}};
        int[][] array_after_reduce= {
                //1    2  3  4  5  6
                {9999, 0, 0, 3, 3, 6}, // 1
                {9999, 9999, 1, 3, 1, 0}, // 2
                {9999, 2, 9999, 0, 0, 3}, // 3
                {3, 5, 0, 9999, 1, 3}, // 4
                {3, 2, 0, 0, 9999, 0}, // 5
                {6, 1, 3, 2, 0, 9999}}; // 6

        Leaf leaf = new Leaf(0);

        Road closedRoad = new Road(1, 0);
        Road closedRoad2 = new Road(2, 0);

        Road usedRoad = new Road(0,1);
        Road usedRoad2 = new Road(2,0);

        leaf.addUsedRoad(usedRoad);
        leaf.addUsedRoad(usedRoad2);
        leaf.addBlockedRoad(closedRoad);
        leaf.addBlockedRoad(closedRoad2);

        int cost = BranchAndBound.reduce(array, leaf);

        if(cost != 1){
            System.out.println("Cost is: " + cost);
            return false;
        }

        for(int i=0; i<array.length; i++){
            for(int j=0; j<array.length; j++){
                if(array[i][j]!=array_after_reduce[i][j]){
                    System.out.println("Elements Are not Equal: " + i +" " + j);
                    System.out.println(array[i][j] + " " + array_after_reduce[i][j]);
                    return false;
                }
            }
        }


        return true;
    }

    public static boolean testZeroScore(){
        int[][] array = {
                {9999, 6, 4, 8, 7, 14},
                {6, 9999, 7, 11, 7, 10},
                {4, 7, 9999, 4, 3, 10},
                {8, 11, 4, 9999, 5, 11,},
                {7, 7, 3, 5, 9999, 7},
                {14, 10, 10, 11, 7, 9999}};

        Leaf leaf = new Leaf(0);

        int cost = BranchAndBound.reduce(array, leaf);

        Road road = BranchAndBound.findBestRoad(array, leaf);

        System.out.println("Road info: " + road.getIn() + " " + road.getOut() + " " + road.getZeroScore());

        if(road.getIn()==0 && road.getOut()==1 && road.getZeroScore()==1){
            return true;
        }
        return false;
    }

}
