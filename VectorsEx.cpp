
#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>

using namespace std;

int main() 
{    
    int n, q, s, value, which, index;
    scanf("%d %d",&n,&q);   // Gets the n and q terms
    vector<vector<int>> vec; // Vector of int vectors
    for(int i=0;i<n;i++)
    {
        scanf("%d",&s);
        vector<int> vec_i;  // Creates the second vector that the first vector will encapsulate
        for(int j=0;j<s;j++)    // Goes through the values of s
        {
            cin >> value;   // Grabs the value that is returned from the size 
            vec_i.push_back(value); // Pushes the value read to the index in the second vector
        }
        vec.push_back(vec_i);   // Pushes the new vector array into the first vector array
    }
    for(int k=0;k<q;k++)
    {
        scanf("%d %d",&which,&index);
        printf("%d\n",vec[which][index]);   // Takes the element of the index of the array pointed to by which in the two dimensional variable
    }
    return 0;
}
