#include <cstdio>
#include <iostream>
#include <math.h>
using namespace std;

int main()
{
    float tip,tax,totalCost;
    double mealCost;
    int tipPercent,taxPercent;
    cin>>mealCost;
    cin>>tipPercent;
    cin>>taxPercent;
    tip=mealCost*((double)tipPercent/100);
    tax=mealCost*((double)taxPercent/100);
    totalCost=mealCost+tip+tax;
    printf("The total meal cost is %d dollars.",(int)round(totalCost));
}
