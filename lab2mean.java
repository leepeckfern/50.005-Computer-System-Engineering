package lab2;


import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MeanThread {	
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO: read data from external file and store it in an array
		       // Note: you should pass the file as a first command line argument at runtime. 
		Scanner scanner = new Scanner(new File("D:\\SCHOOL\\Term 5\\Computer System Engineering\\Week 3\\lab2\\Lab2\\src\\lab2\\input.txt"));
		List<Integer> tall = new ArrayList<>();
		//int [] tall = new int [524288];
		int i = 0;
		
		while(scanner.hasNextInt())
		{
		     tall.add(scanner.nextInt());
		     i++;
		}
		scanner.close();
		//System.out.println(tall);
		// define number of threads
		int NumOfThread =Integer.valueOf( args[2]);
		//int NumOfThread = Integer.valueOf(16);// this way, you can pass number of threads as 
		     // a second command line argument at runtime.
		
		// TODO: partition the array list into N subArrays, where N is the number of threads
		//int totalsubarray = 524288/NumOfThread;
//		System.out.println(totalsubarray);
		int totalinarray = i/ NumOfThread;
		List<List<Integer>> subarray = new ArrayList<>(); //sub arrays
		for (int j=0;j<tall.size();j+=totalinarray){
			subarray.add(tall.subList(j, Math.min(j+totalinarray, tall.size())));
		}
		//System.out.println(subarray.get(0).size());   	 //256
		//subarray.add(tall.subList(0, 3));
		//System.out.println(subarray);
//		System.out.println(subarray.size());				//2048
//		System.out.println(tall.size()/NumOfThread);		//256
		
		// TODO: start recording time
		long startTime = System.currentTimeMillis(); 
		// TODO: create N threads and assign subArrays to the threads so that each thread computes mean of 
		    // its repective subarray. For example,
		double globalmean = 0;
		ArrayList<Double> tempMeanStore = new ArrayList<>(); // new array mentioned above 
		ArrayList<MeanMultiThread> threads = new ArrayList<>();
		for (int k =0;k<subarray.size();k++){
			
				MeanMultiThread thread = new MeanMultiThread(subarray.get(k),k);
				threads.add(thread);
			
		}	
		//to start the threads
		for (MeanMultiThread thread1: threads){
			thread1.start();
		}
		//to wait for the threads to stop so as to get the mean 
		for (MeanMultiThread thread2:threads){
			thread2.join();
		}
//		MeanMultiThread thread1 = new MeanMultiThread(subArray1);
//		MeanMultiThread threadn = new MeanMultiThread(subArrayn);
		//Tip: you can't create big number of threads in the above way. So, create an array list of threads. 
		
		// TODO: start each thread to execute your computeMean() function defined under the run() method
		   //so that the N mean values can be computed. for example, 
//		thread1.start(); //start thread1 on from run() function
//		threadn.start();//start thread2 on from run() function
		for (MeanMultiThread thread3: threads){
			tempMeanStore.add(thread3.getMean());
		}
	
//		thread1.join();//wait until thread1 terminates
//		threadn.join();//wait until threadn terminates
		
		// TODO: show the N mean values
		//System.out.println("Temporal mean value of thread n is ... ");	
		for (Double doubles:tempMeanStore){
			System.out.println("Temporal mean value of thread "+ tempMeanStore.indexOf(doubles) +" is ..." + doubles);
		}
		// TODO: store the temporal mean values in a new array so that you can use that 
		    /// array to compute the global mean.
		tempMeanStore.add(globalmean);
		// TODO: compute the global mean value from N mean values. 
		double globalMean = 0 ;
		double globalSum = 0;
		if (tempMeanStore.size() > 1){
			for (int q = 0 ; q < tempMeanStore.size() ; q++){
				globalSum += tempMeanStore.get(q);
			}
			globalMean = globalSum / NumOfThread; 
		}
		else{
			globalMean = globalmean;
		}
		System.out.println("Global Mean is: " + globalMean);
		// TODO: stop recording time and compute the elapsed time 
		long stopTime = System.currentTimeMillis();
		long totalTime = stopTime - startTime; //will get in ms 
		System.out.println("The total time taken is: " + (totalTime) +"ms");
		System.out.println("The global mean value is: "+ globalMean );
		
				
	}
}
//Extend the Thread class
class MeanMultiThread extends Thread {
	private List<Integer> list;
	private double mean;
	MeanMultiThread(ArrayList<Integer> array) {
		list = array;
	}
	public MeanMultiThread(List<Integer> list2, int k) {
		// TODO Auto-generated constructor stub
		list = list2;
		//System.out.println("Creating Thread " + k);
		
	}
	public double getMean() {
		return mean;
	}
	public void run() {
		// TODO: implement your actions here, e.g., computeMean(...)
		mean = computeMean(list);
	}
	private double computeMean(List<Integer> list2) {
		// TODO Auto-generated method stub
		double temp = 0;
		
//		System.out.println("Calculating...");
		for (int i=0;i<list2.size();i++){
			temp = temp + list2.get(i);
//			System.out.println(temp);
		}
//		System.out.println(temp);
		this.mean = temp / list2.size();
//		System.out.println(this.mean);
		return mean;
	}
}
