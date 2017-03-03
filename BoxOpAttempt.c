// Box operations problem
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

int set = 0;

int op_one(int *,int,int *);
int op_two(int *,int,int *);
int op_three(int *,int,int *);
int op_four(int *,int,int *);

int main()
{
    int n; 
    int q; 
    
    scanf("%d %d",&n,&q);
    
    int *box = malloc(sizeof(int) * n);
    int *flag = calloc(4,sizeof(int));
    
    for(int box_i = 0; box_i < n; box_i++)
    {
       scanf("%d",&box[box_i]);
    }
    
    while(q>0)
    {
        for(int i=0;i<=3;i++)
        {
            scanf("%d",&flag[i]);
                 
            if((flag[0]==3)||(flag[0]==4))
            {
                for(int j=0;j<=1;j++)
                {
                    scanf("%d",&flag[j+1]);
                    i=4;
                }    
            }
            
        }
        
        switch(flag[0])
        {
                case 1:
                    op_one(box,n,flag);
                    break;
                case 2:
                    op_two(box,n,flag);
                    break;
                case 3:
                    op_three(box,n,flag);
                    break;
                case 4:
                    op_four(box,n,flag);
                    break;
                default:
                    break;
        }
        q-=1;
    }    
    return 0;
}

int op_one(int *fox, int a, int *blag)
{
    for(int box_i=0;box_i<a;box_i++)
    {
        if((box_i>=blag[1])&&(box_i<=blag[2]))
            fox[box_i]+=blag[3];
    }
    return 0;
}

int op_two(int *fox, int a, int *blag)
{    
    for(int box_i=0;box_i<a;box_i++)
    {    
        if((box_i>=blag[1])&&(box_i<=blag[2]))
        {
            int f;
            double g;
            g=((double)fox[box_i]/(double)blag[3]);
            if(g<0)
                if(g!=(int)g)
                    f=(int)g-1;
                else
                    f=(int)g;
            else
                f=(int)g;
            fox[box_i]=f;
        }
    }
    return 0;
}

int op_three(int *fox, int a, int *blag)
{
    int f;
    
    for(int box_i=0;box_i<a;box_i++)
    {
        if((box_i>=blag[1])&&(box_i<=blag[2]))
        {
            if(set==0)
                f=fox[box_i];
            if(fox[box_i]<f)
                f=fox[box_i];
                set=1;
        }
    }
    printf("%d\n",f);
    
    return 0;
}

int op_four(int *fox, int a, int *blag)    
{
    int sum=0;
    for(int box_i=0;box_i<a;box_i++)
    {
        if((box_i>=blag[1])&&(box_i<=blag[2]))
        {
            sum=fox[box_i]+sum;
        }
    }
    printf("%d\n",sum);
    return 0;
}	
	
