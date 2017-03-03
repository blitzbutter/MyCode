// C++ Virtual class example
// Created by Jacob Bryant
static long long int ccount_s;  // The count for students
static long long int ccount_p;  // The count for professors
long long int hold;             // A temp value for the arry

class Person
{    
    public:
        string name;            // The name for each person
        long long int age;      // Their age
        
        /* The virtual functions are declared here, to be overloaded in the later classes */
        virtual void getdata()  
        {
            cin>>this->name;            // Scans for name and age
            scanf("%lld",&this->age);
        }
        virtual void putdata()
        {
           cout<<this->name<<" ";       // Prints name and age
           printf("%lld ",this->age);
        }
};

class Professor : public Person         // Inherits Person class
{
    Person a;                           // Creates a Person object
    public:
        long long int cur_id;           
        long int publications;
        
        void getdata()
        {
            ccount_p+=1;
            cur_id=ccount_p;
            a.getdata();                // Uses the getdata() function from the Person class, not the Professor class
            scanf("%ld",&this->publications);
        }
        void putdata()
        {
           a.putdata();
           printf("%ld ",this->publications); 
           printf("%lld ",this->cur_id);
           printf("\n");
        }
};

class Student  : public Person          // Inherits Person class
{
    Person b;
    public:
        vector<long long int> vec;       
        long long int cur_id;
        int sum=0;                      // Make sure to initialize sum to 0 or you gets errors. I learned this the hard way.
        
        void getdata()
        {
            ccount_s+=1;
            cur_id=ccount_s;
            b.getdata();                // Uses the getdata() function from the Person class, not the Student class
            for(int i=0;i<6;i++)
            {
                scanf("%lld",&hold);
                this->vec.push_back(hold);  
                hold=0;
            }
        }
        void putdata()
        {
           b.putdata();
           for(int i=0;i<6;i++)
           {
            this->sum+=this->vec[i];
           }
           printf("%lld ",this->sum);
           printf("%lld ",this->cur_id);
           printf("\n");
        }
};

int main(){

    int n, val;
    cin>>n; //The number of objects that is going to be created.
    Person *per[n];

    for(int i = 0;i < n;i++){

        cin>>val;
        if(val == 1){
            // If val is 1 current object is of type Professor
            per[i] = new Professor;

        }
        else per[i] = new Student; // Else the current object is of type Student

        per[i]->getdata(); // Get the data from the user.

    }

    for(int i=0;i<n;i++)
        per[i]->putdata(); // Print the required output for each object.

    return 0;

}
