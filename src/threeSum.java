import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Array;
import java.util.*;

public class threeSum {
        public static int MAX_N = 8000000;
        public static int MAX_TESTS = 20;
        public static long MAX_TEST_TIME = 1000000000000L; //1000 seconds
    public static void main(String[] args) {
//        int[] array = {-10,-8,-5,-3,-1,2,3,5,7,8,9,10,12,-15,33,-37,25,-29,30,33,-31,-41,43,-45,47,55,-53,47,-54,55,-61};
//        int[] array = {1,2,3,5,7,8,9,10};
//        int[] array = {-10,-8,-5,-3,-1,-2,-3,-5,-7,8,-9,-10};
//        int[] array = GenerateTestList(20);
//        ArrayList<int[]> result;
//        result = Brute3Sum(array);
//        if(result.isEmpty()){
//            System.out.println("list does not contain 3 ints that sum to 0");
//        } else {
//            PrintSolutions(result);
//        }
//
//        result = Faster3Sum(array);
//        if(result.isEmpty()){
//            System.out.println("list does not contain 3 ints that sum to 0");
//        } else {
//            PrintSolutions(result);
//        }
//
//        result = Fastest3Sum(array);
//        if(result.isEmpty()){
//            System.out.println("list does not contain 3 ints that sum to 0");
//        } else {
//            PrintSolutions(result);
//        }
        SetupTests();
    }

    public static void PrintSolutions(ArrayList<int[]> solutionList){
        for (int i = 0; i < solutionList.size(); i++) {
            int[] tempTriplet = solutionList.get(i);
            System.out.print("[");
            for (int j = 0; j < 3; j++) {
                if (j == 2)
                    System.out.print(tempTriplet[j] + "] ");
                else
                    System.out.print(tempTriplet[j] + ",");
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
                        int[] tempTriplet = {arr[i], arr[j], arr[k]};
//                        Arrays.sort(tempTriplet);
                        triplets.add(tempTriplet);
                        numSolutions++;
                    }
                }
            }
        }
//        System.out.println("Brute force: " + numSolutions);
        return triplets;
    }

    public static ArrayList<int[]> Faster3Sum (int[] arr){      //N^2logN -total
        Arrays.sort(arr);   //NlogN
        int n = arr.length;
        ArrayList<int[]> triplets = new ArrayList<int[]>();
        int numSolutions = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i+1; j < n; j++) {
                int remainingInt = -(arr[i] + arr[j]);
                int checkIndex = BinarySearch(arr, remainingInt);
                if (checkIndex != -1 && arr[checkIndex] > arr[j]){//find index if val exists and make sure it's not a duplicate permutation
                    int[] tempTriplet = {arr[i], arr[j], arr[checkIndex]};
//                    Arrays.sort(tempTriplet);
                    triplets.add(tempTriplet);
                    numSolutions++;
                }
            }
        }
//        System.out.println("Faster found: " + numSolutions);
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
                        int[] tempTriplet = {arr[i], arr[right], arr[left]};
//                        Arrays.sort(tempTriplet);
                        triplets.add(tempTriplet);
                        numSolutions++;
                        right--;
                        left++;
                    }
                }
        }
//        System.out.println("Fastest found: " + numSolutions);
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

    public static void SetupTests(){
        //int[] currentAlgorithmToTest = {0,1,2}; //BruteForce, Faster, Fastest
        long[] currTime = new long[3];
        String[] currTimeStr = new String[3];
        String[] doublingTimeStr = new String[3];
        long[] prevTime = {1,1,1};
        double[] expectedDoubling = new double[3];
        boolean[] stopTesting = {false,false,false};

        expectedDoubling[0] = 8;  //N^3
        expectedDoubling[2] = 4 ; //N^2

        System.out.format("%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n","", "Brute 3 Sum","Doubling", "Exp Doubling", "Faster ", "Doubling", "Exp Doubling", "Fastest ", "Doubling", "Exp Doubling");
        System.out.format("%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n","N",     "Time",     "Ratio",     "Ratio",   "3 Sum Time",  "Ratio",       "Ratio", "3 Sum Time", "Ratio",       "Ratio");

        for (int N = 4; N < MAX_N; N *= 2) {
            for (int i = 0; i < 3; i++) {
                if(stopTesting[i] == false) {
                    currTime[i] = RunTimingTest(N, i);
                    currTimeStr[i] = String.valueOf(currTime[i]);
                    if (currTime[i] > MAX_TEST_TIME)
                        stopTesting[i] = true;
                } else{
                    currTime[i] = -1;
                }
                expectedDoubling[1] = ((N*N) * (Math.log(N) /Math.log(2) )) / ( (N/2)*(N/2) * ( Math.log(N/2) / Math.log(2) ) );//N^2 log N;
            }
            if(N == 4)
                System.out.format("%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n", N, currTime[0], "NA", expectedDoubling[0], currTime[1], "NA", expectedDoubling[1], currTime[2], "NA", expectedDoubling[2]);
            else {
                System.out.format("%15s", N);
                if ( currTime[0] > -1 ) {
                    float doubleTime = (float)currTime[0]/(float)prevTime[0];
                    System.out.format("%15s%15f%15s", currTime[0], doubleTime, expectedDoubling[0]);
                }
                else
                    System.out.format("%15s%15s%15s", "NA", "NA", "NA");
                if ( currTime[1] > -1 ) {
                    float doubleTime = (float)currTime[1]/(float)prevTime[1];
                    System.out.format("%15s%15f%15.5f", currTime[1], doubleTime, expectedDoubling[1]);
                }
                else
                    System.out.format("%15s%15s%15s", "NA", "NA", "NA");
                if ( currTime[2] > -1 ) {
                    float doubleTime = (float)currTime[2]/(float)prevTime[2];
                    System.out.format("%15s%15f%15s\n", currTime[2], doubleTime, expectedDoubling[2]);
                }
                else
                    System.out.format("%15s%15s%15s", "NA", "NA", "NA");
            }
            for (int i = 0; i < 3; i++) {
                prevTime[i] = currTime[i];
            }
        }
    }

    public static long RunTimingTest(int N, int algoToRun){
        int testsRun = 0;
        long cumulativeTime = 0;
        boolean continueTests = true;
        long averageTime;
        if(algoToRun == 0){
            while (testsRun < MAX_TESTS && continueTests){
                int[] testList = GenerateTestList(N);
                long before = getCpuTime();
                ArrayList<int[]> results = Brute3Sum(testList);
                long after = getCpuTime();
                long currTime = after - before;
                cumulativeTime += currTime;
                testsRun++;
                if (currTime > MAX_TEST_TIME)
                    continueTests = false;
            }
            averageTime = cumulativeTime/testsRun;
        } else if (algoToRun == 1){
            while (testsRun < MAX_TESTS && continueTests){
                int[] testList = GenerateTestList(N);
                long before = getCpuTime();
                ArrayList<int[]> results = Faster3Sum(testList);
                long after = getCpuTime();
                long currTime = after - before;
                cumulativeTime += currTime;
                testsRun++;
                if (currTime > MAX_TEST_TIME)
                continueTests = false;
            }
            averageTime = cumulativeTime/testsRun;
        } else {
            while (testsRun < MAX_TESTS && continueTests){
                int[] testList = GenerateTestList(N);
                long before = getCpuTime();
                ArrayList<int[]> results = Fastest3Sum(testList);
                long after = getCpuTime();
                long currTime = after - before;
                cumulativeTime += currTime;
                testsRun++;
                if (currTime > MAX_TEST_TIME)
                continueTests = false;
            }
            averageTime = cumulativeTime/testsRun;
        }
        return averageTime;
    }

    /** Get CPU time in nanoseconds since the program(thread) started. */
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }
}
