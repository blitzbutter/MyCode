
// Doesn't work, tried to make an XML tag parser
#pragma GCC diagnostic ignored "-Wunused-result"

#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <typeinfo> // useful class
#include <sstream>
#include <algorithm>
#include <array>
#include <string.h>
#define END '/'

using namespace std;


int nf=0;
int np=0;

int nested_t=0;

struct Tag
{
    string name;
    string attr;
    string value;
    vector<string> parent;
    int nested;
};

struct Tag parseTags(string,int);
string parseAttr(string,vector<struct Tag>);
string trans(char [],int,int);
string parseQuery(string,vector<struct Tag>);
void birth(vector<struct Tag>,struct Tag);
string loop_str(int,int,string);
bool cmp(string, struct Tag);
void test(char);

int main() {
    int n,q;
    vector<struct Tag> tags;

    struct Tag tag_t;
    string temp;
    string val;
    string que;
    
    scanf("%d %d",&n,&q);
    getline(cin,temp);
    for(int i=0;i<n;i++)
    {
        getline(cin,temp);
        if(!((temp[1])==END))
        {
            tag_t=parseTags(temp,nested_t);
            if(tag_t.nested>0)
            {
                birth(tags,tag_t);
            }
            tags.push_back(tag_t);
            if(nested_t!=0)
                nested_t+=1;
        }
        else
            nested_t+=1;
    }
    
    for(int k=0;k<q;k++)
    {
      getline(cin,temp);
      cout<<temp<<endl;
      que=parseQuery(temp,tags);
      //cout<<que<<endl;
      if(que=="Bad")
          printf("Not Found!");
      else
      {
        val=parseAttr(que,tags);
        //cout<<val<<endl;
      }
    }
    
    return 0;
}

void birth(vector<struct Tag> a, struct Tag b)
{
    int count=0;
    for(int i=0;i<a.size();i++)
    {
        if((b.nested)>(a[i].nested))
        {
            b.parent.push_back(a[i].name);
            count+=1;
        }
     }
}

string loop_str(int b,int c,string t,vector<struct Tag> tag)
{
    char tmp[50];
    char pull;
    string par;
    pull=t[c];
    par+=pull;
    cout<<par<<endl;
    return par;
}

string parseQuery(string tm,vector<struct Tag> v)
{
    char tmp[50];
    int count=0;
    bool c_n=false,br=false;
    int alfa=0;             // The number of dot movements
    int carr[20];
    int barr[20];
    int parc=0;
    struct Tag tem;
    const char *vp;
    
    string sp;
    string parse;
    string t_parent;
    bool nfind=false;
    int beg=0;

    for(int i=0;i<tm.size();i++)
    {    
        if(tm[i]=='.')
        {
            carr[alfa]=i-1;
            barr[alfa]=beg;
            beg=carr[alfa];
            alfa+=1;
            c_n=true;
        }    
        if(tm[i]=='~')
        {
            carr[alfa]=i-1;
            barr[alfa]=beg;
            beg=carr[alfa];
            //break;
        }
    }
    for(int i=0;i<=alfa;i++)
    {
        test('h');
        parse=loop_str(barr[i],carr[i],tm,v);
        if(i==0)
        {
            for(int k=0;k<v.size();k++)
            {
                tem.name=parse;
                if(cmp(parse,v[k]))
                { 
                    t_parent=v[k].name;
                    parc+=1;
                }
            }
        }
        else
        {
            for(int j=0;j<v.size();j++)
            {
                if(cmp(parse,v[j]))
                {
                    if(t_parent==v[j].parent[parc])
                    {
                        if(v[j].nested!=0)
                        {
                            t_parent=v[j].name;
                            cout<<t_parent<<endl;
                        }
                    }
                    else
                    {
                        br=true;
                    }
                }
            }
        }
        //cout<<parse<<endl;
    }
    if(br)
        parse="Not Found!";
    return parse;
}

string parseAttr(string parse,vector<struct Tag> tag)
{
    string attr;
    char temp[50];
    struct Tag chk;
    int count=0;
    int a_ccount=0;
    bool br=false,match=true;
    int num_v=0;
    string sc;
    string cam;
    for(int i=0;i<parse.size();i++)
    {
        if(parse[i]=='~')
        {
            if(a_ccount==0)
            {
                temp[i]=0;
                string sa(temp);
                sc=sa;
            }
            else
            {
                sc=trans(temp,a_ccount,count+1);
            }
            i+=1;
            for(int j=0;j<tag.size();j++)
            {
                cam=tag[j].name;
                if((sc[sc.size()-1]==cam[cam.size()-1]))
                {
                    if((tag[j].attr[0]==parse[i])&&(num_v==0))
                    {
                        for(int a=0;a<tag[j].attr.size();a++)
                        {
                            if(parse[i+a]==tag[j].attr[a])
                                match=true;
                            else
                                match=false;
                        }
                        attr=tag[j].value;
                        num_v+=1;
                        br=true;
                        break;
                    }
                }
            }
            if((!br)||(match==false))
            {
                attr="Not Found!";
            }
        }
        if(br)
            break;
        else
        {
            temp[i]=parse[i];
            count+=1;
        }
    }
    return attr;
}

struct Tag parseTags(string str,int nes)
{
    char chr[50];
    char hold=' ';
    int count=0;
    stringstream ss(str);
    bool bad=false,next=false,found=false,fin=false,first=true;
    int lcount;
    int arrc=0;
    int n;
    int extra=0;
    struct Tag ref;
    ref.nested=nes;
        
    while(ss)
    {
        ss>>hold;
        bad=false;
        if(hold=='<')
        {
            ss>>hold;
            chr[count]=hold;
            count+=1;
            bad=true;
        }
        else
        {   
            if(hold!='=')
                chr[count]=hold;
        }
        if((isspace(str[count]))&&(next==false)&&(first==true))
        {     
            string sc=trans(chr,count,count-1);
            ref.name=sc;
            lcount=count;
            arrc=0;
            next=true;
            first=false;
        }
        if(next)
        {     
            arrc+=1;
            if((hold=='=')&&(arrc>2))
            {
                //cout<<"here!"<<endl;
                extra=extra+1;
                found=true;
            }
            if(found)
            {
                string sc=trans(chr,arrc,count);
                ref.attr=sc;
                next=false;
                found=false;
                fin=true;
                arrc=0;
            }
        }
        if(fin)
        {
            arrc+=1;
            if((hold=='\"')&&(arrc>2))
                found=true;
            if(found)
            {
                chr[count]=0;
                string sc=trans(chr,arrc-3,count);
                ref.value=sc;
                next=false;
                found=false;
            }
        }
        
        if(!bad)
        {
            count+=1;
        }
    }


    return ref;
}

void test(char a)
{
    cout<<"Here " << a;
}
bool cmp(string a,struct Tag b)
{
    char a_a[50];
    char a_b[50];
    bool eq=false;

    for(int i=0;i<a.size();i++)
    {
        a_a[i]=a[i];
        a_b[i]=b.name[i+1];
        if(a_a[i]==a_b[i])
        {
            if(a.size()==b.name.size()-1)
                eq=true;
        }
        else
            eq=false;
    }
    return eq;
}

string trans(char a[],int ac,int c)
{
    string ret;
    char temp;
    for(int i=0;i<ac;i++)
    {
        temp=a[((c-ac)+i)];
        ret+=temp;
    }
    return ret;
}
