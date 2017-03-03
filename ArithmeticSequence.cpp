#pragma GCC diagnostic ignored "-Wunused-result"

#include <cmath>
#include <cstdio>
#include <iostream>

using namespace std;

int main() 
{
    long long int t,n,sum=0;
    long long int a[3];
    cin>>t;
    for(int i=0;i<t;i++)
    {
        cin>>n;
        n-=1;   // Subtracts 1 so that the range 0-n is exclusive
        
        // Calculates n by taking the floor of the number by the multiple
        a[0]=floor((n)/3);
        a[1]=floor((n)/5);
        a[2]=floor((n)/15); // Multiply 3 and 5 to find the multiple that is both a multiple of 3 and 9
        
        sum=(((a[0]*(2*3+(a[0]-1)*3))/2) + ((a[1]*(2*5+(a[1]-1)*5))/2)) - (((a[2]*(2*15+(a[2]-1)*15))/2));
              /* Adds the arithmetic sequence formula calculated multiples 3 and 5, then subtracts this sum by the arithmetic sequence forumla calculated 15 */
       
        // Uses the arithmetic sequence (n/2)(2(a)+(n-1)d)
        // Subtracts the set of integers that are both are a multiple of 5 and 3
        // The difference inbetween is just the multiple as 3 would be 3,6,9,...,n*3 d=6-3=3.
        // The first case is just the multiple as 3,6,9,...,n*3 and 5,10,15,...,n*5 and 15,30,45,...,n*15
        
        cout<<sum<<endl;   
    }
    return 0;
}
