
// Diagonals
#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>
#include <stdbool.h>

int main(){
    int n; 
    scanf("%d",&n);
    int a[n][n];
    for(int a_i = 0; a_i < n; a_i++){
       for(int a_j = 0; a_j < n; a_j++){
          scanf("%d",&a[a_i][a_j]);
       }
    }
    
    int pi=0;
    int ip=0;
    
    for(int b_j=n;b_j>=1;b_j--)
    {
        pi+=a[n-b_j][n-b_j];
    }
    
    for(int b_i=1;b_i<=n;b_i++)
    {
        ip+=a[b_i-1][n-b_i];
    }
    
    printf("%d\n",abs(ip-pi));
    return 0;
}
