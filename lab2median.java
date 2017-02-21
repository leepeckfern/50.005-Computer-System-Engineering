package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class MedianThread {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException  {
		// TODO: read data from external file and store it in an array
	       // Note: you should pass the file as a first command line argument at runtime. 
		Scanner scanner = new Scanner(new File("D:\\SCHOOL\\Term 5\\Computer System Engineering\\Week 3\\lab2\\Lab2\\src\\lab2\\input.txt"));
		ArrayList<Integer> tall = new ArrayList<>();
		MedianMultiThread medd = new MedianMultiThread(tall);
		int i = 0;
		ArrayList<Integer> sortedArray = new ArrayList<>();
		while(scanner.hasNextInt())
		{
		     tall.add(scanner.nextInt());
		     i++;
		}
		scanner.close();
		// define number of threads
		int NumOfThread = Integer.valueOf(args[2]);// this way, you can pass number of threads as 
		     // a second command line argument at runtime.
		//int NumOfThread = Integer.valueOf(1);
		// TODO: partition the array list into N subArrays, where N is the number of threads
		int totalinarray = i/ NumOfThread;
		ArrayList<ArrayList<Integer>> subarray = new ArrayList<>(); //sub arrays
		for (int j=0;j<tall.size();j+=totalinarray){
			subarray.add(new ArrayList<Integer>(tall.subList(j, Math.min(j+totalinarray, tall.size()))));
			//System.out.println(subarray.get(j));
		}
		// TODO: start recording time
		long startTime = System.currentTimeMillis(); 
		// TODO: create N threads and assign subArrays to the threads so that each thread sorts
		    // its repective subarray. For example,
		ArrayList<MedianMultiThread> threads = new ArrayList<>();
		for (int k =0;k<subarray.size();k++){
			
				MedianMultiThread thread = new MedianMultiThread(subarray.get(k));
				threads.add(thread);
				//System.out.println(thread.getInternal());
			
		}	
		//to start the threads
		for (MedianMultiThread thread1: threads){
			thread1.start();
		}
		for (MedianMultiThread thread2:threads){
			thread2.join();
		}
		//MeanMultiThread thread1 = new MeanMultiThread(subArray1);
		//MeanMultiThread threadn = new MeanMultiThread(subArrayn);
		//Tip: you can't create big number of threads in the above way. So, create an array list of threads. 
		
		// TODO: start each thread to execute your sorting algorithm defined under the run() method, for example, 
//		thread1.start(); //start thread1 on from run() function
//		threadn.start();//start thread2 on from run() function
//		
//		thread1.join();//wait until thread1 terminates
//		threadn.join();//wait until threadn terminates
		
		// TODO: use any merge algorithm to merge the sorted subarrays and store it to another array, e.g., sortedFullArray. 
		
		MedianMultiThread threadFrom = null;
		for (int p = 0 ; p < i ; p++){
			int temp = 99999999;
			for (MedianMultiThread thread3 : threads){
				if (thread3.getInternal().get(0) < temp){
					temp = thread3.getInternal().get(0);
					threadFrom = thread3;
				}
			}
			sortedArray.add(temp);
			
			if (threadFrom.getInternal().size() == 0){
				threads.remove(threadFrom);
			}
			else {
				threadFrom.getInternal().remove(0);
				if (threadFrom.getInternal().size() == 0){
					threads.remove(threadFrom);
				}
			}
			
		}
		//TODO: get median from sortedFullArray
		
		    //e.g, computeMedian(sortedFullArray);
		
		// TODO: stop recording time and compute the elapsed time 
		long stopTime = System.currentTimeMillis();
		long runningTime = stopTime - startTime; //will get in ms
		// TODO: printout the final sorted array
		//System.out.println(sortedArray);
		// TODO: printout median
		//System.out.println(computeMedian(sortedArray));
		System.out.println("The Median value is ..." + computeMedian(sortedArray));
		System.out.println("Running time is " + runningTime + " milliseconds\n");
		
		}
	
		public static double computeMedian(ArrayList<Integer> inputArray) {
		  //TODO: implement your function that computes median of values of an array 
			int medianLeft = inputArray.get(inputArray.size()/2);
			int medianRight = inputArray.get((inputArray.size()/2)+1);
			int median = (medianLeft + medianRight)/2;
			return median;
		}
	
}

// extend Thread
class MedianMultiThread extends Thread {
	private ArrayList<Integer> list;

	public ArrayList<Integer> getInternal() {
		return list;
	}

	MedianMultiThread(ArrayList<Integer> array) {
		list = array;
	}

	public void run() {
		// called by object.start()
		mergeSort(list);
		
	}
	public void mergeSort(ArrayList<Integer> array) {
		list = recursemergeSort(array);
		}

	// TODO: implement merge sort here, recursive algorithm
	public ArrayList<Integer> recursemergeSort(ArrayList<Integer> array) {
		if (array.size() < 2){
			return array;
		}
		else if (array.size() == 2){
			int lelbig;
			int lelsmall;
			if (array.get(1) < array.get(0)){
				lelsmall = array.get(1);
				lelbig = array.get(0);
				array.set(0, lelsmall);
				array.set(1, lelbig);
				return array;
			}
			else {
				return array;
			}
		}
		else{
			int mid= array.size()/2;
            ArrayList<Integer> left= new ArrayList<Integer>(mid);
            ArrayList<Integer> right=new ArrayList<Integer>(array.size()-mid);
            for (int i = 0; i < array.size(); i++) {
            	if (i <= mid){
            		left.add(array.get(i));
            	} 
            	else{
            		right.add(array.get(i));
            	}
            }
            
            left = recursemergeSort(left); 
            right = recursemergeSort(right);
            ArrayList<Integer> finalsort = new ArrayList<>();
            for(int p = 0; p < array.size() ; p++){
            	if (left.size() > 0 && right.size() >0){
            		if (left.get(0) < right.get(0)){
            			finalsort.add(left.get(0));
            			left.remove(0);
            		}
            		else{
            			finalsort.add(right.get(0));
            			right.remove(0);
            		}
            	}
            	else if (left.size() > 0){
            		finalsort.add(left.get(0));
            		left.remove(0);
            	}
            	else if( right.size() > 0){
            		finalsort.add(right.get(0));
            		right.remove(0);
            	}
            }
            	//System.out.println(finalsort.size());
            	
            
            return finalsort;
        }
//		return array;
	}
}

