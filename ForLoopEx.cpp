// For loops in C++
#include <iostream>
#include <cstdio>
using namespace std;

void ev_od(int);

int main() {
    int a;
    int b;
    scanf("%d",&a);
    scanf("%d",&b);
    
    for(int i=a;i<=b;i++)
    {
        ev_od(i);
        printf("\n");
    }
    // Complete the code.
    return 0;
}

void ev_od(int n)
{
    if(n>9)
    {
        if((n%2)==0)
            printf("even");
        else
            printf("odd");
    }
    else
    {
        if(n==1)
            printf("one");
        else if(n==2)
            printf("two");
        else if(n==3)
            printf("three");
        else if(n==4)
            printf("four");
        else if(n==5)
            printf("five");
        else if(n==6)
            printf("six");
        else if(n==7)
            printf("seven");
        else if(n==8)
            printf("eight");
        else
            printf("nine");
    }
}
