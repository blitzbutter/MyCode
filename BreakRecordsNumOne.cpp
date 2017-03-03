
// Breaking the Records
#include <stdio.h>
#include <stdlib.h>

int main(){
    int n; 
    int low,l=0;
    int high,h=0;
    
    scanf("%d",&n);
    int *score = malloc(sizeof(int) * n);
    
    for(int score_i = 0; score_i < n; score_i++){
       scanf("%d",&score[score_i]);
    }
    
    low=high=score[0];

    for(int i=1;i<n;i++)    
    {
        if(score[i]>high)
        {
            high=score[i];
            h+=1;
        }
        if(score[i]<low)
        {
            low=score[i];
            l+=1;
        }
    }
    printf("%d %d",h,l);
    return 0;
}
