// Big ass sum problem
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
    int arr[n];
    long long int sum=0;
    for(int arr_i = 0; arr_i < n; arr_i++){
       scanf("%d",&arr[arr_i]);
       sum+=(long long int)arr[arr_i];
    }
    printf("%lld",sum);
    return 0;
}
