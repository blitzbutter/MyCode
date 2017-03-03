
// Hashtag staircase

#include <stdio.h>

int main(){
    int n; 
    int space;
    
    scanf("%d",&n);
    
    for(int i=1;i<=n;i++)
    {
        if(i<n)
            space = (n-i);
        else
            space = 0; 
        for(int k=0;k<space;k++)
        {
            printf(" ");    
        }
        for(int j=1;j<=(n-space);j++)
        {   
            printf("#");
        }
        printf("\n");
    }
    
    return 0;
}
