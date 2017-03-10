#include <math.h>
#include <stdio.h>
#include <stdlib.h>

#define mul(x)  (x<((x-x%10)+5))?(x-x%10)+5:(x-x%10)+10
/////////*
/* If x is less than 5 in the digits place, than make that next multiple of of 5, else make +10 next multiple of 5 */
// 1::: x <
// 2::: x    -   x % 10
// 3::: + 5
// 4::: ?                Ternary operator, if the condition of 1-2-3 is true,
/////////                Then return 5-6-7
// 5::: x    -   x % 10
// 6::: + 5
// 7::: :                Ternary operator, else
/////////                Then return 
// 8::: x    -   x % 10
// 9::: + 10
/////////*

int main() 
{
    int n, grade, dif=0, m;
    scanf("%d",&n);        
 
    for(int i=0;i<n;i++)
    {
        scanf("%d",&grade);
        if(grade<38)
            printf("%d\n",grade);       // If the grade is less than 38, do not round
        else
        {
            m=mul(grade);               // Store the next multiple of 5 in the variable m
            dif=abs(m-grade);           // Difference between grade and the next multiple of 5
            if(dif<3)                   // If the difference is less than 3
                grade=m;                // Then the grade equals the next multiple of 5
            printf("%d\n",grade);       // Print the grade
        }
    }
    
    return 0;
}
