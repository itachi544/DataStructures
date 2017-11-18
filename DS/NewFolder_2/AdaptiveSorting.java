import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class AdaptiveSorting {
//Global Variables
public static AdaptiveSorting objAdaptiveSorting = new AdaptiveSorting(); 
	


//######### Merge Sort #######################################

public  void mergeSort(int[] inputData) {
	
    if (inputData.length > 1) {
      
      //call recursively until the inputData is sorted.
     
      //Split the first first half
      int[] firstHalf = new int[inputData.length / 2];
      System.arraycopy(inputData, 0, firstHalf, 0, inputData.length / 2);  
      mergeSort(firstHalf);  
      
      // Split the second half
      int secondHalfLength = inputData.length - inputData.length / 2;  
      int[] secondHalf = new int[secondHalfLength];  
      System.arraycopy(inputData, inputData.length / 2,
        secondHalf, 0, secondHalfLength);  
      mergeSort(secondHalf);  

      // Merge firstHalf with secondHalf
      //Now that the list has been split to a smaller size
      //then we can try to merge it 
      int[] temp = merge(firstHalf, secondHalf);  
      System.arraycopy(temp, 0, inputData, 0, temp.length);  
    }
  }
	
//merge operation O(n)
 public  int[] merge(int[] left, int[] right) {
	    int[] temp = new int[left.length + right.length];

	    int leftIndex = 0; 
	    int rightIndex = 0; 
	    int tempIndex = 0; 
	    
	    
	    while (leftIndex < left.length && rightIndex < right.length) {  
	    //Comparing the elements in each list and add to the merge list
	      if (left[leftIndex] < right[rightIndex])
	        temp[tempIndex++] = left[leftIndex++];
	      else
	        temp[tempIndex++] = right[rightIndex++];
	    }
	    //Copying the rest of elements into the temp
	    while (leftIndex < left.length)
	      temp[tempIndex++] = left[leftIndex++];
	  //Copying the rest of elements into the temp
	    while (rightIndex < right.length)
	      temp[tempIndex++] = right[rightIndex++];

	    return temp;
	  }
	  
//######### Merge Sort Ends###################################



//######### Quick Sort #######################################

public void swap(int[] array, int i, int j) {
	int tmp = array[i];
	array[i] = array[j];
	array[j] = tmp;
}
public int partition(int arr[], int left, int right) {
	
	
//different way of selecting pivot
	
	//int pivot = arr[1];
	
	int pivot = arr[(left + right) / 2]; // Pick a pivot point. Can be an element
	
	//Random rand = new Random();
	//int  num = rand.nextInt(arr.length-1);
	//System.out.println(num);
	
	//objAdaptiveSorting.swap(arr, num, right);
	
	//int pivot = arr[right];
	
	
	while (left <= right) { // Until we've gone through the whole array
		// Find element on left that should be on right
		while (arr[left] < pivot) { 
			left++;
		}
		
		// Find element on right that should be on left
		while (arr[right] > pivot) {
			right--;
		}
		
		// Swap elements, and move left and right indices
		if (left <= right) {
			swap(arr, left, right);
			left++;
			right--;
		}
	}
	return left; 
}
public int [] quickSort(int arr[], int left, int right) {
	int index = partition(arr, left, right); 
	
	if (left < index - 1) { // Sort left half
		quickSort(arr, left, index - 1);
	}
	return arr;
}

//######### Quick Sort Ends  #################################



//######### Bucket Sort ####################################
public  int [] bucketSort(int array[]){
	 
	 int lenghtofArray = array.length;
	 //base case if an array is empty 
   if( lenghtofArray <= 0 )
     return array;                                   
   
   //initilaizing min and max as first element of array
   int min = array[0];
   int max = min;
   
   for( int i = 1; i < lenghtofArray; i++ )                
     if( array[i] > max )
       max = array[i];
     else if( array[i] < min )
       min = array[i];
   
   //initializing the buckets equal to number of elements in array
   int bucket[] = new int[max-min+1];          
   //filling the bucket
   for( int i = 0; i < lenghtofArray; i++ )                
     bucket[array[i]-min]++;                   

   int i = 0;
   
   for( int b = 0; b < bucket.length; b++ )    
     for( int j = 0; j < bucket[b]; j++ )     
       array[i++] = b+min;                    
   
   return array;
   }

//######### Bucket Sort Ends################################



//######## Adaptive Sort ############################################
long toalTime = 0;
int [] algorithmRanking = new int[3];
long [] algorithmSpeed = new long[3];
int algorithmSelected ;

public int [] adaptiveSort(int semiSortedData[]){
	
	int algorithm = 0;
	long startTime=0, endTime=0;
	long mTime, qTime, bTime;
	
	int firstPosition = (int)(0.1 * semiSortedData.length);
	
	int[] input10 = new int[firstPosition];
	int[] input20 = new int[firstPosition];
	int[] input30 = new int[firstPosition];
	
	//random number for chosing randomly the first three working set of array 
	Random rand = new Random();
    int randomNum = rand.nextInt((9 - 0) + 1) + 0;
    
	
	System.arraycopy(semiSortedData, 0, input10, 0, firstPosition);
	System.arraycopy(semiSortedData, firstPosition, input20, 0, firstPosition);
	System.arraycopy(semiSortedData, (2*firstPosition), input30, 0, firstPosition);
	
	//first 10 percent
	startTime = System.nanoTime();
	objAdaptiveSorting.mergeSort(input10);
	endTime = System.nanoTime();
	mTime = endTime - startTime;
	
	//second 10 percent
	startTime = System.nanoTime();
	objAdaptiveSorting.quickSort(input20, 0, input20.length-1);
	endTime = System.nanoTime();
	qTime = endTime - startTime;
	
	//third 10 percent
	startTime = System.nanoTime();
	objAdaptiveSorting.bucketSort(input30);
	endTime = System.nanoTime();
	bTime = endTime - startTime;
	
	//updating speed of each algorithm
	objAdaptiveSorting.algorithmSpeed[0]= mTime;
	objAdaptiveSorting.algorithmSpeed[1]= qTime;
	objAdaptiveSorting.algorithmSpeed[2]= bTime;
	
	//System.out.println("Base ===>"+Arrays.toString(objAdaptiveSorting.algorithmSpeed));
	
	//ranking algorithm based on their speed
	objAdaptiveSorting.rankAlgorithm(mTime, qTime, bTime);
	
	//System.out.println("Base ====>"+Arrays.toString(objAdaptiveSorting.algorithmRanking));
	
	//merge operation runs in linear time
	// merged data is in input20
	
	startTime = System.nanoTime();
	objAdaptiveSorting.merge(input10, input20);
	objAdaptiveSorting.merge(input20, input30);
	endTime = System.nanoTime();
//	System.out.println(Arrays.toString(input30));
	
	objAdaptiveSorting.toalTime += (endTime - startTime);
	
	//recursively sorting remaining 70 percentage of data based on algorithm ranking
	
	 recurisveAssociativeSort(semiSortedData);
	
	
	 algorithm = objAdaptiveSorting.rankAlgorithm(mTime, qTime, bTime);
	 
	 
   return semiSortedData;
}

public void recurisveAssociativeSort(int [] data){
	
	for(int i = 3; i<10; i++){
		float start = (float) ((i*0.1)*data.length);
		float end = (float) (((i+1)*0.1)*data.length);
		objAdaptiveSorting.sortArray(data,(int)start ,(int)end );
		objAdaptiveSorting.manageRanking();
	}
	
}

//takes whole array and first sorts part of it and sorts from zero to end of original array
public int[] sortArray(int[] array, int start,int end){
	
	int[] tempArray = new int[(end-start)]; 
	//int[] tempArray1 = new int[end]; 
	
	//slicling only the part of array that needs to be sorted
	System.arraycopy(array, start, tempArray, 0, (end-start));
	
	//System.out.println("All data"+Arrays.toString(array));
	//System.out.println("working set unsorted data"+Arrays.toString(tempArray));
	
	//sorting sliced array with current best algorithm
	//current working set gets sorted here
	objAdaptiveSorting.switchAlgorithmAndSort(tempArray);
	
	//System.out.println("working set Sorted data"+Arrays.toString(tempArray));
	
	
	//copying from array[0] to array[start]
	int [] tempArray2 = new int[start];
	System.arraycopy(array, 0, tempArray2, 0, start);
	
	//System.out.println("Already sorted data +working set Sorted data "+Arrays.toString(tempArray2));
	
	//using merge procedure to merge the currently sorted 10% of data with already sorted data
	long startTime = System.nanoTime();
	objAdaptiveSorting.merge(tempArray, tempArray2);
	
	long endTime = System.nanoTime();
	objAdaptiveSorting.toalTime += endTime - startTime;
	
	//System.out.println(Arrays.toString(tempArray2));
	

	
	objAdaptiveSorting.insertionSort(tempArray2);
	
//	System.out.println(Arrays.toString(tempArray2));
	System.arraycopy(tempArray2, 0, array, 0, tempArray2.length);
	
	//System.out.println(Arrays.toString(array));
	return array;
}

public int[] switchAlgorithmAndSort(int [] semiSortedData){
	long startTime = 0;
	long endTime = 0;
	
	objAdaptiveSorting.algorithmSelected = objAdaptiveSorting.algorithmRanking[0];
	
	//System.out.println("Selected Algorithm ===>"+ objAdaptiveSorting.algorithmSelected);
	
	switch (objAdaptiveSorting.algorithmSelected){
	 case 1:{
		 startTime = System.nanoTime();
		 objAdaptiveSorting.mergeSort(semiSortedData);
		 endTime = System.nanoTime();
	 }
	 case 2:{
		 startTime = System.nanoTime();
		 objAdaptiveSorting.quickSort(semiSortedData,0,semiSortedData.length-1);
		 endTime = System.nanoTime();
	 }
	 case 3:{
		 startTime = System.nanoTime();
		 objAdaptiveSorting.bucketSort(semiSortedData);
		 endTime = System.nanoTime();
	 }
	 
	}
	
 objAdaptiveSorting.toalTime += (endTime - startTime);
 return semiSortedData;
}

public void manageRanking(){
	int temp0,temp1, temp2;
	
	temp0 = objAdaptiveSorting.algorithmRanking[0];
	temp1 = objAdaptiveSorting.algorithmRanking[1];
	temp2 = objAdaptiveSorting.algorithmRanking[2];
	//System.out.println(" ==> "+temp0);
	//System.out.println(objAdaptiveSorting.toalTime);
	if(objAdaptiveSorting.toalTime > objAdaptiveSorting.algorithmSpeed[temp0-1]){   //-1 bcoz im starting ranking form 1 to 3 instead of 0 to 2
		objAdaptiveSorting.algorithmSpeed[objAdaptiveSorting.algorithmRanking[0]-1]= objAdaptiveSorting.toalTime;
		objAdaptiveSorting.rankAlgorithm(objAdaptiveSorting.algorithmSpeed[0], objAdaptiveSorting.algorithmSpeed[1], objAdaptiveSorting.algorithmSpeed[2]);
		
		/*
		//changing ranking
		if(objAdaptiveSorting.toalTime > objAdaptiveSorting.algorithmSpeed[2]){	
			objAdaptiveSorting.algorithmRanking[2]=temp0;
			objAdaptiveSorting.algorithmRanking[1]=temp2;
			objAdaptiveSorting.algorithmRanking[0]=temp1;
		}
		
		objAdaptiveSorting.algorithmRanking[1]=temp0;
		objAdaptiveSorting.algorithmRanking[0]=temp1;
	*/
	//System.out.println(Arrays.toString(objAdaptiveSorting.algorithmRanking));
	//System.out.println(Arrays.toString(objAdaptiveSorting.algorithmSpeed));
	}
}

public int rankAlgorithm(long mTime, long qTime, long bTime){
	int algorithm = 0;
	
	//first position 
	if ( mTime < qTime && mTime < bTime ){
        objAdaptiveSorting.algorithmRanking[0]=1;
	}
     else if ( qTime < mTime && qTime < bTime ){
   	  
	 objAdaptiveSorting.algorithmRanking[0]=2;
     }
     else if ( bTime < mTime && bTime < qTime ){
   	 objAdaptiveSorting.algorithmRanking[0]=3;
     }
	//second position
	if ( (mTime < qTime && mTime > bTime)|| (mTime > qTime && mTime < bTime) ){
        
        objAdaptiveSorting.algorithmRanking[1]=1;
	}
     else if ( (qTime < mTime && qTime > bTime)||(qTime > mTime && qTime < bTime) ){
   	  
	  objAdaptiveSorting.algorithmRanking[1]=2;
     }
      else if((bTime < mTime && bTime > qTime)||(bTime > mTime && bTime > qTime)){
   	   objAdaptiveSorting.algorithmRanking[1]=3;
     }
	
	//third position
	if ( (mTime > qTime && mTime > bTime)){
        
        objAdaptiveSorting.algorithmRanking[2]=1;
	}
     else if ( (qTime > mTime && qTime > bTime)){
   	  
	 objAdaptiveSorting.algorithmRanking[2]=2;
     }
     else if((bTime > mTime && bTime > qTime)){
   	 objAdaptiveSorting.algorithmRanking[2]=3;
     }
	
	  return algorithm;
}

//select algorithm
public int chooseAlgorithm(long mTime, long qTime, long bTime){
	int algorithm = 0;
	//first position 
	if ( mTime > qTime && mTime > bTime ){
        algorithm =1;
	}
     else if ( qTime > mTime && qTime > bTime ){
   	  algorithm =2;
	
     }
     else{
   	  algorithm =3;
     }
	  return algorithm;
}

public int [] insertionSort(int [] array){
	for (int i=1; i < array.length; i++)
	   {
	      int index = array[i]; int j = i;
	      while (j > 0 && array[j-1] > index)
	      {
	    	  array[j] = array[j-1];
	           j--;
	      }
	      array[j] = index;
	}
	return array;
	
}

//######## Adaptive Sort Ends ##########################################



//########### Main ########################################################

public static void main(String [] args){
	
	//int numberOfInputs = (100);
	
	//start and end values for random number generator for better control in input values
	//int start = 10;
	//int end =   20;
	

	long startTime =0;
	long endTime =0;
	
	
	
	//variables for storting times for analysis
	//ArrayList<Long> mTimes = new ArrayList<Long>();
	//ArrayList<Long> qTimes = new ArrayList<Long>();
	//ArrayList<Long> bTimes = new ArrayList<Long>();
	//ArrayList<Long> adaptTimes = new ArrayList<Long>();
	
	
	
	int [] inputArray = objAdaptiveSorting.getInputs();
	
	
	
	
	//This for loop is for analysis part
	//for(int st= 10; st<=10000000; st=st*10){
	
		//System.out.println("===>"+st);
	
	//int [] inputArray = objAdaptiveSorting.getRandomInputs(st);
	
	//int [] inputArray = objAdaptiveSorting.getRandomInputs(st, 1, 200);
	  //int [] inputArray = objAdaptiveSorting.getSortedArray(st);
	//int [] inputArray = objAdaptiveSorting.getReverseSortedArray(st);
	
	
	//copying  inputs just for safety
	int [] inp1 = new int [inputArray.length];
	int [] inp2 = new int [inputArray.length];
	int [] inp3 = new int [inputArray.length];
	int [] inp4 = new int [inputArray.length];
			
			
	for(int i = 0 ;i< inputArray.length; i++){
		inp1[i]= inputArray[i];
		inp2[i]= inputArray[i];
		inp3[i]= inputArray[i];
		inp4[i]= inputArray[i];
			 
	}	
			
	//merge Sort
	startTime = System.nanoTime();
	objAdaptiveSorting.mergeSort(inp1);
	endTime = System.nanoTime();
	long mTime = endTime - startTime;
	
	objAdaptiveSorting.WritetoFile(inputArray, inp1, "Merge Sort", startTime, endTime,1);
			
	//Qucik Sort
	startTime = System.nanoTime();
	objAdaptiveSorting.quickSort(inp2,0, inp2.length-1);
	endTime = System.nanoTime();
	long qTime = endTime - startTime;
	
	objAdaptiveSorting.WritetoFile(inputArray, inp2, "Quick Sort", startTime, endTime,1);

	//Bucket Sort
	startTime = System.nanoTime();
	objAdaptiveSorting.bucketSort(inp3);
	endTime = System.nanoTime();
	long bTime = endTime - startTime;
	
	objAdaptiveSorting.WritetoFile(inputArray, inp3, "Bucket Sort", startTime, endTime,1);

	//Adaptive Sort
	
	startTime = System.nanoTime();
	objAdaptiveSorting.adaptiveSort(inp4);
	endTime = System.nanoTime();
	long aTime = endTime - startTime;
	
	objAdaptiveSorting.WritetoFile(inputArray, inp4, "Adaptive Sorting", startTime, endTime,1);
	
	System.out.println("Merge-Sort :         "+mTime +" nano-sec");
	System.out.println("Quick-Sort :         "+qTime +" nano-sec");
	System.out.println("Bucket-Sort:         "+bTime+" nano-sec");
	System.out.println("Adaptive Sorting:    "+objAdaptiveSorting.toalTime +" nano-sec");
	
	System.out.println();
	
	
	
	// For analysis part
	/*
	mTimes.add(mTime);
	qTimes.add(qTime);
	bTimes.add(bTime);
	adaptTimes.add(objAdaptiveSorting.toalTime);
	
	
	
	//}
	objAdaptiveSorting.printTime(mTimes,qTimes,bTimes,adaptTimes);
	
	*/
	
	
}

//########### Main Ends ###################################################


//########### ALL About Getting Inputs ####################################
public int[] getInputs(){
	
	
	//Reading input
	
	int [] inputArray = null;
	
	 System.out.println("##### Programming Assignment 1   #####");
	 System.out.println("##### Adaptive Sorting Algortihm #####");
	 System.out.println();
	 System.out.println("Choose how do you want to provide Input (press 1 or 2)");
	 System.out.println("1. Read From File");
	 System.out.println("2. Use Number Generator");
	 Scanner in = new Scanner(System.in);
	 String fileName;
	 int choice = in.nextInt();
	 switch(choice){
	 case 1:
	 {
		 System.out.println("Enter File Name: ");
		 fileName = in.next();
		 //read input from file
		 inputArray = ReadAFile("integers");
			
		 break;
	 }
	 case 2:
	 {
		 System.out.println("Choose What type of input you want to generate");
		 System.out.println("1. Random Numbers");
		 System.out.println("2. Random Numbers of Specific range");
		 System.out.println("3. Already Sorted Numbers");
		 System.out.println("4. Reverse Sorted Numbers");
		 int ch = in.nextInt();
		 switch (ch){
		 
		 case 1:
		 	{	
			 System.out.println("Enter number of random number you want to generate: ");
			 int numberOfInputs = in.nextInt();
			 //call random number generator function
			
			 
			 inputArray = objAdaptiveSorting.getRandomInputs(numberOfInputs);
	
			 
			 break;
		 	}
		 case 2:{
			 System.out.println("Enter the begining: ");
			 int start = in.nextInt();
			 
			 System.out.println("Enter the end: ");
			 int end = in.nextInt();
			 
			 
			 System.out.println("Enter number of random number you want to generate: ");
			 int numberOfInputs = in.nextInt();
			 
			 
			inputArray = objAdaptiveSorting.getRandomInputs(numberOfInputs, start, end);
			   
			 
			 //call random number generator of fixed range
			 
			 break;
		 	}
		 case 3:{
			 
			 System.out.println("Enter number of sorted numbers you want to generate: ");
			 int numberOfInputs = in.nextInt();
			 //call sorted array generator
			inputArray = objAdaptiveSorting.getSortedArray(numberOfInputs);
				
			 
			 break; 
		 }
		 case 4:{
			 System.out.println("Enter number of reverse sorted you want to generate: ");
			 int numberOfInputs = in.nextInt();
			 //call reverse sorted array generator
			inputArray = objAdaptiveSorting.getReverseSortedArray(numberOfInputs);
			 
			 break;
		 }
		  default:{
			  System.out.println("Invalid choice");
			  getInputs();	
			  
		  }
		 }
		 break;
		 
	 }
	 default:{
		 System.out.println("Invalid choice");
		 getInputs();	
		 
	 }
	 }
	 
	 return inputArray;
	 
}

//random number number generator
public int[] getRandomInputs(int size){
	
	Random random = new Random();
	int [] array = new int[size];

	for(int i = 0; i<size; i++){
		array[i] = random.nextInt(100);
	}
	return array;
	
}

//random number generator of given range
public  int[] getRandomInputs(int size, int start, int end){
	
	Random random = new Random();
	int [] array = new int[size];
	if(start > end){
		throw new IllegalArgumentException("Invalid start and end");
	}
	for(int i = 0; i<size; i++){
		
		//castig to avoid overflow problems
		long range  = (long)end - (long)start +1;
		//compute numbers
		long fraction = (long)(range * random.nextDouble());
		array[i]= (int) (fraction + start);
		
		//array[i] = (int)(Math.random()*100);		
}
	return array;	
}

//creates sorted input
public int [] getSortedArray(int size){
	int [] sortedArray = new int[size];
	for(int i =0; i< size; i++){
		sortedArray[i]=i;
	}
	return sortedArray;
}

//create reverse sortedArray (input)
public int [] getReverseSortedArray(int size){
	int [] reverseSortedArray = new int[size+1];
	
	for(int i = 0; i< size; i++){
		reverseSortedArray[i]= size;
		size--;
	}
	return reverseSortedArray;
}


//Read Data from a File
public static int[] ReadAFile(String fileName){	
	ArrayList<Integer> fileContent = new ArrayList<Integer>();
	try {
	File file = new File(fileName);
	if(!file.exists()){
		System.err.println("Error: File Not Found");
		System.exit(-1);
	}
	else{
		FileReader in = new FileReader(fileName);
		Scanner scanner = new Scanner(in);
		while ((scanner.hasNextInt())) {
		fileContent.add(scanner.nextInt());
		 }
	in.close();
	scanner.close();
		   
	}
	}
	catch (Exception ex){
		System.out.println(ex);
	}
		
	int[] inputData = new int[fileContent.size()];
		 
	 for(int i = 0; i< fileContent.size();i++){
			 inputData[i] =fileContent.get(i); 
		 }
		
		return inputData;
	}


//########### ALL About Getting Inputs Ends ####################################



//######## writing input and output to file ##############
public void WritetoFile(int [] inputArray,int [] sortedArray,String algorithmName, long startTime , long endTime, int flag){
	String fileName ="output.txt";
	try {
	File file = new File(fileName);
	FileWriter fileWriter = new FileWriter(fileName,true);
	BufferedWriter brWriter = new BufferedWriter(fileWriter);
	PrintWriter printWriter = new PrintWriter(brWriter);
				
	//create file if it does not exist
	if(!file.exists()){
	 file.createNewFile();
	}
	//using flag to print input and out only at the begining 
	if(flag == 1){
	printWriter.println();
	printWriter.println();
	printWriter.println("Unsorted Input:");
	for(int i =0;i<inputArray.length;i++){
	 printWriter.print(inputArray[i] +" ");				
	}
					
	printWriter.println();
	printWriter.println("Sorted Output:");
	for(int i =0;i<sortedArray.length;i++){
	 printWriter.print(sortedArray[i] +" ");
						
	}
	printWriter.println();
	printWriter.println();
					
	}
				
	printWriter.println("Algorithm's Name: "+algorithmName );
	if(algorithmName.equals("Adaptive Sorting")){
		printWriter.println("Runtime: "+ (objAdaptiveSorting.toalTime ) +" nano-sec");
	}
	else{
	printWriter.println("Runtime: "+ (endTime - startTime) + " nano-sec");
	}		
	printWriter.println();
			
	printWriter.flush();
brWriter.close();
	printWriter.close();
				
	} catch (IOException e) {
				// TODO Auto-generated catch block
	e.printStackTrace();
	}
			
}

//#######  Writing input and output to file ends ##########




//############ Printing data for analysis ######
public void printTime(ArrayList<Long> mTimes, ArrayList<Long> qTimes, ArrayList<Long> bTimes, ArrayList<Long> adaptTimes){
	
	System.out.println("Merge Sort");
	for (long value : mTimes){
		System.out.println(value);
		
	}
	System.out.println("Quick Sort");
	for (long value : qTimes){
		System.out.println(value);
		
	}
	System.out.println("Bucket Sort");
	for (long value : bTimes){
		System.out.println(value);
		
	}
	System.out.println("Adaptive Sort");
	for (long value : adaptTimes){
		System.out.println(value);
		
	}
	
}

//########### Printing data for analysis ends ####







}
