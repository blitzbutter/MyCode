% Jacob Bryant
% Assignment 2

clc;

% --- Problem 1 --- %

mat_a = [2,4,5; 1,0,-2; 9,-5,3];
mat_b = [-4,1,0; 4,0,2; 0,3,2];
mat_c = [1,2,0;3,7,2;5,6,2];
mat_d = [2,3;-5,-1];

% AB %
disp("AB");
disp(mat_a * mat_b);

% AD %
% disp(mat_a * mat_d);
% A is a 3x3 matrix while D is a 2x2 matrix, thus you cannot multiply them since they
% do not have the same number of rows and colums

% D^2 %
disp("D^2");
disp(mat_d^2);

% A + B %
disp("A+B");
disp(mat_a + mat_b);

% C + D %
% disp(mat_c + mat_d);
% C is a 3x3 matrix while D is a 2x2 matrix, thus you cannot add them since they
% do not have the same number of rows and colums

% ABC %
disp("ABC");
disp(mat_a * mat_b * mat_c);

% BAC %
disp("BAC");
disp(mat_b * mat_a * mat_c);

% (AC)^-1 %
disp("(AC)^-1");
disp((mat_a*mat_c)^-1);

% (C^-1)(A^-1) %
disp("(C^-1)(A^-1)");
disp((mat_c^-1)*(mat_a^-1))
%-------------------%

% --- Problem 2 --- %

% Step a %
mat_a = [1,-1,-1, 9;0,2,5, 29;2,5,-7, 52];
disp("Reduced row echelon form: ");
disp(rref(mat_a));

mat_a = [1,-1,-1;0,2,5;2,5,-7];
mat_b = [9;29;52];

mat_x = inv(mat_a)*mat_b;
disp("Inverse method: ");
disp(mat_x);

% Step b $

%-------------------%


% --- Problem 3 --- %

int_x = [1,2,5];
int_y = [4,1,6];

int_n = 2;

int_p = polyfit(int_x, int_y, int_n);
int_f = polyval(int_p, int_x);
disp("Polynomial interpolation: ");
hold on
plot(int_x,int_f,'-')
plot(1,4);
plot(2,1);
plot(5,6);
hold off
%-------------------%


% --- Problem 4 --- %
pause(2);
clc;
mat_food = [270, 10, 2,400; 51, 5.4, 5.2,30; 70, 15,0,10];
disp("Food amount: ");
disp(rref(mat_food));

%-------------------%

% --- Problem 5 --- %
% #33
% T_1 = (10+20+T_2+T_4)/4 -> 4T_1 - T_2 - T_4 = 30
% T_2 = (T_1 + 20 + 40 + T_3)/4 -> 4T_2 - T_1 - T_3 = 60
% T_3 = (T_4 + T_2 + 40 +30)/4 -> 4T_3 - T_4 - T_2 = 70
% T_4 = (10 + T_1 + T_3 + 30)/4 -> 4T_4 - T_1 - T_3 = 40

% #34
disp("Answer to #34 on problem 5: ");
mat_a = [4, -1, 0, -1, 30; -1, 4, -1, 0, 60; 0, -1, 4, -1, 70; -1, 0, -1, 4, 40];
disp(rref(mat_a));
%-------------------%