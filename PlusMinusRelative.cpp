// Plus minus, relative fractions to postive, zeroes, and negatives

#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>
#include <stdbool.h>

int main(){
    int n;
    int count[3]={0,0,0};
    scanf("%d",&n);
    int arr[n];
    for(int arr_i = 0; arr_i < n; arr_i++){
       scanf("%d",&arr[arr_i]);
       if(arr[arr_i]>0)
        count[0]+=1;
       if(arr[arr_i]<0)
        count[1]+=1;
       if(arr[arr_i]==0)
        count[2]+=1;
    }
    
    printf("%lf\n%lf\n%lf",count[0]/(double)n,count[1]/(double)n,count[2]/(double)n);
    return 0;
}
