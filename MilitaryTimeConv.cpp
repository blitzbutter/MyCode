// Military time conversion challenge

#include <math.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>
#include <stdbool.h>

int main(){
    const char AM = 'A';
    const char PM = 'P';
    
    char* time = (char *)malloc(10 * sizeof(char));
    char* num_s = (char *)malloc(2 * sizeof(char));
    char* s_form = (char *)malloc(10 * sizeof(char));
    int num_i;
    
    scanf("%s",time);
    strncpy(num_s,time,2);
    num_i=atoi(num_s);
    
    if(strchr(time,AM)!=0)
    {
        if(num_i==12)
            num_i=num_i-12;
        strncpy(s_form,time,strlen(time)-2);
        if(num_i<10)
            printf("0%d%s",num_i,s_form+2);
        else
            printf("%d%s",num_i,s_form+2);
    }
    else
    {
        if(num_i!=12)
            num_i+=12;
        strncpy(s_form,time,strlen(time)-2);
        printf("%d%s",num_i,s_form+2);
    }
    
    return 0;
}
