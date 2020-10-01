import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class threeSum {
    public static void main(String[] args) {
//        int[] array = {-10,-8,-5,-3,-1,2,3,5,7,8,9,10,12,-15,33,-37,25,-29,30,33,-31,-41,43,-45,47,55,-53,47,-54,55,-61};
//        int[] array = {1,2,3,5,7,8,9,10};
//        int[] array = {-10,-8,-5,-3,-1,-2,-3,-5,-7,8,-9,-10};
        int[] array = GenerateTestList(100);


        ArrayList<int[]> result;
        result = Brute3Sum(array);
        if(result.isEmpty()){
            System.out.println("list does not contain 3 ints that sum to 0");
        } else {
            PrintSolutions(result);
        }

        result = Faster3Sum(array);
        if(result.isEmpty()){
            System.out.println("list does not contain 3 ints that sum to 0");
        } else {
            PrintSolutions(result);
        }

        result = Fastest3Sum(array);
        if(result.isEmpty()){
            System.out.println("list does not contain 3 ints that sum to 0");
        } else {
            PrintSolutions(result);
        }
    }

    public static void PrintSolutions(ArrayList<int[]> solutionList){
        for (int i = 0; i < solutionList.size(); i++) {
            int[] tmp = solutionList.get(i);
            System.out.print("[");
            for (int j = 0; j < 3; j++) {
                if (j == 2)
                    System.out.print(tmp[j] + "] ");
                else
                    System.out.print(tmp[j] + ",");
            }
        }
        System.out.println();
    }
    
    public static ArrayList<int[]> Brute3Sum (int[] arr){       //N^3
        int n = arr.length;
        ArrayList<int[]> triplets = new ArrayList<int[]>();
        int[] solution = new int[3];
        int numSolutions = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    if (arr[i] + arr[j] + arr[k] == 0) {
                        int[] tmp = {arr[i], arr[j], arr[k]};
                        triplets.add(tmp);
                        numSolutions++;
                    }
                }
            }
        }
        System.out.println("Brute force: " + numSolutions);
        return triplets;
    }

    public static ArrayList<int[]> Faster3Sum (int[] arr){      //N^2logN -total
        Arrays.sort(arr);   //NlogN
        int n = arr.length;
        ArrayList<int[]> triplets = new ArrayList<int[]>();
        int numSolutions = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int remainingInt = -(arr[i] + arr[j]);
                int checkIndex = BinarySearch(arr, remainingInt);
                if (checkIndex != -1 && arr[checkIndex] > arr[j]){//find index if val exists and make sure it's not a duplicate permutation
                    int[] tmp = {arr[i], arr[j], arr[checkIndex]};
                    triplets.add(tmp);
                    numSolutions++;
                }
            }
        }
        System.out.println("Faster found: " + numSolutions);
        return triplets;
    }

    public static ArrayList<int[]> Fastest3Sum (int[] arr){     //N^2
        Arrays.sort(arr);
        int n = arr.length;
        ArrayList<int[]> triplets = new ArrayList<int[]>();
        int numSolutions = 0;
        for (int i = 0; i < n; i++) {
                int left = i + 1;
                int right = n - 1;
                while (left < right) {
                    if (arr[i] + arr[left] + arr[right] > 0)
                        right--;
                    else if (arr[i] + arr[left] + arr[right] < 0)
                        left++;
                    else {
                        int[] tmp = {arr[i], arr[right], arr[left]};
                        triplets.add(tmp);
                        numSolutions++;
                        right--;
                        left++;
                    }
                }
        }
        System.out.println("Fastest found: " + numSolutions);
        return triplets;
    }

    public static int BinarySearch ( int [] arr, int key ){
        int left = 0;
        int middle;
        int right = arr.length - 1;

        while (left <= right){
            middle =  left + ((right - left) / 2);
            if (arr[middle] < key) {
                left = middle + 1;
            }
            else if (arr[middle] > key){
                right = middle - 1;              }
            else {
                return middle;
            }
        }
        return -1;
    }

    public static int[] GenerateTestList(int N){
        int[] list = new int[N];
        int min = -3 * N;
        int max = 3 * N;
        for (int i = 0; i < N; i++) {
            list[i] = (int) (Math.random() * (max - min)) + min;
        }
        return list;
    }
    
}
