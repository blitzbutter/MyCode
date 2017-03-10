#pragma GCC diagnostic ignored "-Wunused-result"

#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

#define mul(x)  (x<((x-x%10)+5))?(x-x%10)+5:(x-x%10)+10
/* If x is less than 5 in the digits place, than make that next multiple of of 5, else make +10 next multiple of 5 */

int main() 
{
    int n, grade, dif=0,m;
    scanf("%d",&n);        
 
    for(int i=0;i<n;i++)
    {
        scanf("%d",&grade);
        if(grade<38)
            printf("%d\n",grade);       // If the grade is less than 38, do not round
        else
        {
            m=mul(grade);
            dif=abs((m-grade));         // Difference between grade and the next multiple of 5
            if(dif<3)                   // If the difference is less than 3
                grade=m;                // Then the grade equals the next multiple of 5
            printf("%d\n",grade);       // Print the grade
        }
    }
    
    return 0;
}
