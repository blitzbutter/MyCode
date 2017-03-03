// String problem
// Declare second integer, double, and String variables.
int main()
{
	int i = 4;
	double d = 4.0;
	char s[] = "HackerRank ";
	
	int a;
    double b;
    char s1[50]="";
    char s2[50]="";
    strcpy(s2,s);
    // Read and save an integer, double, and String to your variables.
    scanf("%d",&a);
    scanf("%lf\n", &b);
    scanf("%[^\n]",s1);
    // Print the sum of both integer variables on a new line.
    printf("%d\n",a+i);
    // Print the sum of the double variables on a new line.
    printf("%.1lf\n",(b+d));
    printf("%s%s\n",s2,s1);
	return 0;
}
