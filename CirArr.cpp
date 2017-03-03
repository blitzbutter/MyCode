// Circular Array Rotation in C++
// Blocks annoying compiler warnings that are useless to me
#pragma GCC diagnostic ignored "-Wunused-result"   

#include <cstdio>
#include <list>
#include <iterator> 

using namespace std;


int main(){
    long long int n;
    long long int k;
    long long int q;
    
    long long int hold=0;
    
    scanf("%lld %lld %lld",&n,&k,&q); // Scans n, k, and q for the first line

    list<long long int> num;          // Initializes a list of long long int types 
    std::list<long long int>::iterator it=num.begin();  // Initializes an interator to access elements
    for(int a = 0; a < n; a++)
    {
       scanf("%lld",&hold);
       num.push_back(hold);           // Inserts the value into the num lsit
       hold=0;
    }
    
    for(int b = 0; b < k; b++)
    {
        it= --num.end();              // Points the iterator to the last index
        hold=*it;                     // Sets the container value to the value of the it pointer
        num.push_front(hold);         // Pushes the container to the front
        num.pop_back();               // Pops the container off
        hold=0;                       // Initializes to 0 to avoid strange mishappenings later
    }

    for(int c = 0; c < q; c++)
    {
        it=num.begin();               // Sets the list pointer to the beginning of the array
        long long int m; 
        scanf("%lld",&m);
        advance(it,m);                // Points the list pointer to the m index
        printf("%lld\n",*it);         // Prints the value of that index
    } 
    return 0;
}
