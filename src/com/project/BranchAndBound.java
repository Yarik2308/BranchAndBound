package com.project;

import com.project.models.Leaf;
import com.project.models.Road;

import java.util.ArrayList;
import java.util.List;

public class BranchAndBound {
    public static void main(int[][] array){

        Leaf optimalLeaf = mainRecursiveFunction(new Leaf(0), array, -1);

        int i = 0;
        if(optimalLeaf!=null) {
            if (optimalLeaf.getUsedRoads() != null) {
                for (Road road : optimalLeaf.getUsedRoads()) {
                    i++;
                    System.out.println("Road" + i + ": " + road.getIn() + " " + road.getOut());
                }
            } else {
                System.out.println("UsedRoads() == null");
            }

            System.out.println("Cost Min = " + optimalLeaf.getCost());
        } else {
            System.out.println("optimalLeaf=null");
        }

    }

    private static Leaf mainRecursiveFunction(Leaf leaf, final int[][] array, int optimalCost){

//        System.out.println(optimalCost);
//        int i = 0;
//        for (Road road : leaf.getUsedRoads()) {
//            i++;
//            System.out.println("Road" + i + ": " + road.getIn() + " " + road.getOut() + " //ZeroScore=" + road.getZeroScore());
//        }

        if(optimalCost!=-1){
            if(leaf.getCost()>=optimalCost){
                return null;
            }
        }

        if(array.length - leaf.getUsedRoads().size()==1){

            Road lastRoad = getLastRoads(array, leaf.getUsedRoads());

            leaf.setCost(leaf.getCost() + array[lastRoad.getIn()][lastRoad.getOut()]);
            leaf.addUsedRoad(lastRoad);

            return leaf;
        }

        leaf.setCost( leaf.getCost() + reduce(array, leaf));

        Road road = findBestRoad(array, leaf);

        Leaf leftLeaf = new Leaf(leaf.getCost());
        leftLeaf.setUsedRoadsList(leaf.getUsedRoads());
        leftLeaf.addUsedRoad(road);
        leftLeaf.setBlockedRoads(leaf.getBlockedRoads());
        leftLeaf.addBlockedRoad(new Road(road.getOut(), road.getIn()));

        Leaf rightLeaf = new Leaf(leaf.getCost() + road.getZeroScore());
        rightLeaf.setBlockedRoads(leaf.getBlockedRoads());
        rightLeaf.addBlockedRoad(road);


        leftLeaf = mainRecursiveFunction(leftLeaf, array.clone(), optimalCost);

        if(leftLeaf!=null){
            if(optimalCost!=-1){
                if(optimalCost>leftLeaf.getCost()){
                    optimalCost = leftLeaf.getCost();
                }
            } else {
                optimalCost = leaf.getCost();
            }
            rightLeaf = mainRecursiveFunction(rightLeaf, array.clone(), optimalCost);

            if(rightLeaf!=null){
                return rightLeaf;
            }

            return leftLeaf;

        } else {
            rightLeaf = mainRecursiveFunction(rightLeaf, array.clone(), optimalCost);

            return rightLeaf;
        }

    }

    private static Road getLastRoads(int[][] array, List<Road> usedRoads){

        if(array.length - usedRoads.size() != 1){
            return new Road(-1, -1);
        }

        int in = -1;
        int out = -1;

        for(int i = 0; i<array.length; i++){
            boolean usedCity = false;
            for(Road road: usedRoads){
                if(road.getIn()==i){
                    usedCity = true;
                }
            }

            if(!usedCity){
                in = i;
                break;
            }
        }

        for(int i = 0; i<array.length; i++){
            boolean usedCity = false;
            for(Road road: usedRoads){
                if(road.getOut()==i){
                    usedCity = true;
                }
            }

            if(!usedCity){
                out = i;
                break;
            }
        }

        return new Road(in, out);
    }

    /*
        Zero score function
     */
    // int[] closedCityForOut - Road.In , int[] closedCityForIn - Road.Out
    public static Road findBestRoad(int[][] array, Leaf leaf){

        List<Road> roadsWithZeroScore = new ArrayList<>();

        for(int i=0; i<array.length; i++) {
            boolean cityCheck = false;

            for (int j = 0; j < leaf.getUsedRoads().size(); j++) {
                if (leaf.getUsedRoads().get(j).getIn() == i) {
                    cityCheck = true;
                    break;
                }
            }
            if(cityCheck) {
                continue;
            }

            for(int j = 0; j < array.length; j++) {
                if (j == i) {
                    continue;
                }

                cityCheck = false;

                for (Road blockedRoad : leaf.getBlockedRoads()) {
                    if ((i == blockedRoad.getIn()) && (j == blockedRoad.getOut())) {
                        cityCheck = true;
                        break;
                    }
                }

                if (cityCheck) {
                    continue;
                }

                for (int n = 0; n < leaf.getUsedRoads().size(); n++) {
                    if (leaf.getUsedRoads().get(n).getOut() == j) {
                        cityCheck = true;
                        break;
                    }
                }
                if (cityCheck) {
                    continue;
                }

                // Zero score

                if(array[i][j]==0){
                    int minRow = 9999;
                    int minCol = 9999;
                    // get min zero score by rows
                    for(int i1 = 0; i1<array.length; i1++){
                        if(i1!=i & i1!=j){
                            boolean cityCheck2 = false;
                            for (int n = 0; n < leaf.getUsedRoads().size(); n++) {
                                if (leaf.getUsedRoads().get(n).getIn() == i1) {
                                    cityCheck2 = true;
                                    break;
                                }
                            }
                            for (int n = 0; n < leaf.getBlockedRoads().size(); n++) {
                                if (leaf.getBlockedRoads().get(n).getIn() == i1 &&
                                        leaf.getBlockedRoads().get(n).getOut()==j) {
                                    cityCheck2 = true;
                                    break;
                                }
                            }
                            if(cityCheck2) {
                                continue;
                            }

                            if(minRow>array[i1][j]){
                                minRow = array[i1][j];
                            }
                        }
                    }
                    // get zero score by columns
                    for(int j1 = 0; j1<array.length; j1++){
                        // get zero score by rows
                        if(j1!=i & j1!=j){
                            boolean cityCheck2 = false;
                            for (int n = 0; n < leaf.getUsedRoads().size(); n++) {
                                if (leaf.getUsedRoads().get(n).getOut() == j1) {
                                    cityCheck2 = true;
                                    break;
                                }
                            }
                            for (int n = 0; n < leaf.getBlockedRoads().size(); n++) {
                                if (leaf.getBlockedRoads().get(n).getIn() == j1 &&
                                        leaf.getBlockedRoads().get(n).getOut()==i) {
                                    cityCheck2 = true;
                                    break;
                                }
                            }
                            if(cityCheck2) {
                                continue;
                            }

                            if(minCol>array[i][j1]){
                                minCol = array[i][j1];
                            }
                        }
                    }

                    Road road = new Road(i, j);
                    road.setZeroScore(minCol + minRow);
                    roadsWithZeroScore.add(road);
                }
            }

        }

        int maxZeroScore = 0;
        for(Road road: roadsWithZeroScore){
            if(road.getZeroScore()>maxZeroScore){
                maxZeroScore = road.getZeroScore();
            }
        }

        for(Road road: roadsWithZeroScore){
            if(road.getZeroScore()==maxZeroScore){
                return road;
            }
        }

        return new Road(-1, -1);
    }

    /*
        Reduce - Reduces the passed Matrix with minimum value possible
        Input - 2D Array to be reduced, closed rows, closed columns, used roads
        Return - Cost of Reduction
	*/
    public static int reduce(int[][] array, Leaf leaf)
    {
        // Variables
        int new_cost = 0;

        int[] array_of_min = findRowsMin(array, leaf);
        new_cost = new_cost + costSum(array_of_min);

//        System.out.print("Rows mins: ");
//        for(int i=0; i<array_of_min.length; i++){
//            System.out.print(array_of_min[i] + " ");
//        }
//
//        System.out.println();

        reduceRows(array, leaf, array_of_min);

//        System.out.println("Array after Rows reduce");
//
//        for(int i=0; i<array.length; i++){
//            for(int j=0; j<array.length; j++){
//                System.out.print(array[i][j] + " ");
//            }
//            System.out.println();
//        }

        array_of_min = findColumnsMin(array, leaf);
        new_cost += costSum(array_of_min);

//        System.out.print("Columns mins: ");
//
//        for(int i=0; i<array_of_min.length; i++){
//            System.out.print(array_of_min[i] + " ");
//        }
//        System.out.println();

        reduceColumns(array, leaf, array_of_min);

//        System.out.println("Array after Columns reduce");
//
//        for(int i=0; i<array.length; i++){
//            for(int j=0; j<array.length; j++){
//                System.out.print(array[i][j] + " ");
//            }
//            System.out.println();
//        }

        // Reduction done, return the new cost
        return new_cost;
    }

    /*
        findRowsMin - function for finding array of rows minimums.
        Input - 2D Array,
        Return - array of minimums
     */
    private static int[] findRowsMin(int[][] array, Leaf leaf){
        int[] array_of_min = new int[array.length];

        ////// Loop for finding minimums of rows
        // i - rows; j - columns
        for(int i=0; i<array.length; i++) {
            boolean cityCheck = false;

            for (int j = 0; j < leaf.getUsedRoads().size(); j++) {
                if (leaf.getUsedRoads().get(j).getIn() == i) {
                    cityCheck = true;
                    break;
                }
            }
            if(cityCheck) {
                array_of_min[i] = 0;
                continue;
            }

            int min = 9999;

            for(int j = 0; j < array.length; j++) {
                if (j == i) {
                    continue;
                }

                cityCheck = false;

                for (Road blockedRoad : leaf.getBlockedRoads()) {
                    if ((i == blockedRoad.getIn()) && (j == blockedRoad.getOut())) {
                        cityCheck = true;
                        break;
                    }
                }

                if (cityCheck) {
                    continue;
                }

                for (int n = 0; n < leaf.getUsedRoads().size(); n++) {
                    if (leaf.getUsedRoads().get(n).getOut() == j) {
                        cityCheck = true;
                        break;
                    }
                }
                if (cityCheck) {
                    continue;
                }

                if (array[i][j] < min) {
                    min = array[i][j];
                }
            }

            if(min == 9999){
                array_of_min[i] = 0;
            }else {
                array_of_min[i] = min;
            }
        }

        return array_of_min;
    }

    /*
        findRowsMin - function for finding array of rows minimums.
        Input - 2D Array,
        Return - array of minimums
     */
    private static int[] findColumnsMin(int[][] array, Leaf leaf){
        int[] array_of_min = new int[array.length];

        ////// Loop for finding minimums of columns
        // j - rows; i - columns
        for(int i=0; i<array.length; i++) {
            boolean cityCheck = false;

            for(int j=0; j<leaf.getUsedRoads().size(); j++){
                if(leaf.getUsedRoads().get(j).getOut()==i){
                    cityCheck = true;
                    break;
                }
            }
            if(cityCheck){
                array_of_min[i]=0;
                continue;
            }

            int min = 9999;

            for(int j=0; j<array.length; j++){
                if(j == i){
                    continue;
                }

                cityCheck = false;

                for(Road blockedRoad: leaf.getBlockedRoads()){
                    if( (j == blockedRoad.getIn()) && (i == blockedRoad.getOut())){
                        cityCheck = true;
                        break;
                    }
                }

                if(cityCheck){
                    continue;
                }

                for(int n=0; n<leaf.getUsedRoads().size(); n++){
                    if(leaf.getUsedRoads().get(n).getIn()==j){
                        cityCheck = true;
                        break;
                    }
                }
                if(cityCheck){
                    continue;
                }

                if(array[j][i]<min){
                    min = array[j][i];
                }
            }

            if(min == 9999){
                array_of_min[i] = 0;
            }else {
                array_of_min[i] = min;
            }
        }

        return array_of_min;
    }

    private static void reduceRows(int[][] array, Leaf leaf, int[] array_of_min){
        // Loop for reducing rows
        for(int i=0; i<array.length;i++){
            boolean road = false;
            for(int n=0; n<leaf.getUsedRoads().size(); n++){
                if(i==leaf.getUsedRoads().get(n).getIn()){
                    road=true;
                    break;
                }
            }

            if(road){
                continue;
            }

            if (array_of_min[i]!=0){
                for(int j=0; j<array.length; j++){
                    if(i!=j) {
                        road = false;
                        for(Road blockedRoad: leaf.getBlockedRoads()){
                            if( (i == blockedRoad.getIn()) && (j == blockedRoad.getOut())){
                                road = true;
                                break;
                            }
                        }
                        for(int n=0; n<leaf.getUsedRoads().size(); n++){
                            if(j==leaf.getUsedRoads().get(n).getOut()){
                                road=true;
                                break;
                            }
                        }

                        if(!road) {
                            array[i][j] = array[i][j] - array_of_min[i];
                        }
                    }
                }
            }
        }
    }

    private static void reduceColumns(int[][] array, Leaf leaf, int[] array_of_min){
        // Loop for reducing columns and cost sum
        for(int i=0; i<array.length;i++){
            boolean road = false;

            for(int n=0; n<leaf.getUsedRoads().size(); n++){
                if(i==leaf.getUsedRoads().get(n).getOut()){
                    road=true;
                    break;
                }
            }

            if(road){
                continue;
            }
            if (array_of_min[i]!=0){
                for(int j=0; j<array.length; j++){
                    if(i!=j) {
                        road = false;
                        for(Road blockedRoad: leaf.getBlockedRoads()){
                            if( (j == blockedRoad.getIn()) && (i == blockedRoad.getOut())){
                                road = true;
                                break;
                            }
                        }
                        for(int n=0; n<leaf.getUsedRoads().size(); n++){
                            if(j==leaf.getUsedRoads().get(n).getIn()){
                                road=true;
                                break;
                            }
                        }

                        if(!road) {
                            array[j][i] = array[j][i] - array_of_min[i];
                        }
                    }
                }
            }
        }
    }

    private static int costSum(int[] array_of_min){
        int cost = 0;
        for(int min: array_of_min){
            cost += min;
        }
        return cost;
    }
}
