// Prime numbers [Not 100% accurate, coded on Wolfram], needs to take in account mersenne primes and perfect numbers

a=6999999
b=DigitCount[a^(1/((2.29454652))), 10, 2]
d=Floor[N[a/2^b]]
e=N[a/2^b]
f=e-d
g=f;While[Mod[g,10]==g||Element[g,Integers]==False,
 g*=10
 ]
Which[Mod[g,10]==0,g/=10]
h=Floor[g*d]
i=Mod[h,a]
j=a-i
Which[((  !PrimeQ[j]&&j≠1)||j==a||(PrimeQ[i]&&j==1))&&a≠6&&Mod[a,10]≠0,Print["This is a prime"]]
