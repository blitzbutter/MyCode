// String C++ fundamentals
#include <iostream>
#include <string>
using namespace std;

int main() {
    string a;
    string b;
    char temp;
    
    cin >> a;
    cin >> b;
    
    printf("%d %d\n",a.size(),b.size());
    cout<<a+b<<'\n';
    
    temp=a[0];
    a[0]=b[0];
    b[0]=temp;
    
    cout<<a<<" "<<b;
   // Complete the program
  
    return 0;
}
