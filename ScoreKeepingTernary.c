#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>
#include <stdbool.h>

int main()
{
    int a0,a1,a2;
    scanf("%d %d %d",&a0,&a1,&a2);
    int b0,b1,b2;
	scanf("%d %d %d",&b0,&b1,&b2);
    int A[3]={a0,a1,a2};
	  int B[3]={b0,b1,b2};
    for(int i=0;i<3;i++)
	{
        if((A[i]!=B[i])&& !(((A[i]<1)&&(B[i]<1))&&((A[i]>100)&&(B[i]>100))))    // Uses inverse logic to test conditions
	    {
		  if(A[i]>B[i])
            A[0]=((A[0]!=i) ? 1:(A[0]+1));
          else      // Uses ternary operator for score tracking
            B[0]=((B[0]!=i) ? 1:(B[0]+1));
        }
        A[0]=(A[0]==a0) ? 0:A[0];
        B[0]=(B[0]==b0) ? 0:B[0];
    }
    
    if((A[0]!=B[0]))
        printf("%d %d",A[0],B[0]); // Uses A[0] as a way to keep the score
    else
        printf("%d %d",A[0],B[0]);
    
    return 0;
}
