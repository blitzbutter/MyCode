// Breaking the Records #3

#include <stdio.h>
#include <stdlib.h>

int main(){
    int n;
    
    scanf("%d",&n);
    int *score = malloc(sizeof(int) * n);
    
    int high[1],low[1];
    int h=0,l=0;
    
    for(int score_i = 0; score_i < n; score_i++)
    {
       scanf("%d",&score[score_i]);
       if(score_i==0)
       {
           high[0]=score[score_i];
           low[0]=score[score_i]; 
       }
       else
       {
        if(score[score_i]>high[0])
           h+=1;
        if(score[score_i]<low[0])
           l+=1;
       }
       high[0]=(score[score_i]>high[0])?score[score_i]:high[0];
       low[0]=(score[score_i]<low[0])?score[score_i]:low[0];
    }
    printf("%d %d",h,l);
    return 0;
}
