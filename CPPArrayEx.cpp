// C++ Arrays
#include <cmath>m
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;


int main() {
    int n;
    int a;
    scanf("%d",&n);
    int arr[n];
    for(int i=0;i<n;i++)
    {
        scanf("%d",&a);
        arr[i]=a;
    }
    for(int i=n-1;i>=0;i--)
    {
        cout<<arr[i]<<' ';
    }
    /* Enter your code here. Read input from STDIN. Print output to STDOUT */   
    return 0;
}
