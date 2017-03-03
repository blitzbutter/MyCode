// A C++ XML parser I am working on

#pragma GCC diagnostic ignored "-Wunused-result"

#include <cstdio>
#include <vector>
#include <iostream>
#include <string.h>

using namespace std;

#define END '/'
#define BT '<'
#define ET '>'

#define CAT '.'
#define ACC '~'

#define sp(x)       isspace(x)
#define forn(i,n)   for(int i=0;i<n;i++)
#define c(x)        x.c_str()
#define un(x)       if((x!=END)&&(x!=BT)&&(x!=ET)&&(!sp(x)))
#define vchar(x,i)  *(char *)((const char *)x+i)            // Grabs the character from the null pointer

void test(char);
struct Tag parseTag(int);
const char * extract(const char *,int);

struct Tag
{
    const char * name;
    const char * attr;
    const char * value;
    int tag_id;
};

struct Parent
{
    vector<struct Tag> tags;
};

static vector<struct Tag> lines;

int main()
{
    int id=0;
    static int n,q;
    struct Tag read;
    int i;
    string enter;
    
    scanf("%d %d",&n,&q);
    
    getline(cin,enter);
    
    forn(i,n)
    {
        read=parseTag(id);
        lines.push_back(read);
        id+=1;
    }
}

void test(char a)
{
    cout<<"Here "<<a<<endl;
}

struct Tag parseTag(int id)
{
    struct Tag tag;
    
    string temp;
    getline(cin,temp);
    
    tag.name=extract(c(temp),id);
    
    return tag;
}

const char * extract(const char * a,int id_s)
{
    int i;
    char chr[50];
    char bad[50];
    void *b=(void *)a;
    
    forn(i,strlen(a))
    {
        un(vchar(b,i))
        {
            chr[i]=vchar(b,i);
            //cout<<chr[i]<<endl;
        }
        else
        {
            bad[i]=vchar(b,i);
            cout<<bad[i]<<endl;
        }
        
    }
    
    const char * ex;
    return ex;
}
