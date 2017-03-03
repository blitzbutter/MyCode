#pragma GCC diagnostic ignored "-Wunused-result"  
#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

int main() {
    long long int a[5];
    long long int sum=0;
    scanf("%lld %lld %lld %lld %lld",&a[0],&a[1],&a[2],&a[3],&a[4]);
    sum=a[0]+a[1]+a[2]+a[3]+a[4];
    printf("%lld %lld",sum-max(max(max(a[0],a[1]),max(a[2],a[3])),a[4]),sum-min(min(min(a[0],a[1]),min(a[2],a[3])),a[4]));
    return 0;
}
