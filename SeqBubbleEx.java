// Sequential search and bubble sort in Java
public int sequentialSearch(int key)
{
	for(int i=0;i<arr.length;i++)
	{
		if(arr[i]==key)
		{
			animate(i,0);
			return i;
		}
	}
	return -1;
}

public void bubbleSort()
{
  int temp=0;
	for(int i=0;i<arr.length-1;i++)
	{
		for(int j=0;j<arr.length-1-i;j++)
		{
			if(arr[j]>arr[j+1])
			{
				temp=arr[j];
				arr[j]=arr[j+1];
				arr[j+1]=temp;
				animate(i,j);
			}
		}
	}
