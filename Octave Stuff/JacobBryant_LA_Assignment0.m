% Jacob Bryant
% Assignment 1
%{ 
	Plots a quadratic function based on user input and then
	evaluates a definite integral over limits of integrations
	given by user input.
%}

% Main function, this removes a lot of errors
% Acts as an entry point for the program
function main()
    clc;
    disp("NOTE: My script only works right now on octave-online.net, not the software IDE. In order for it to work, you'd have to install this package called symbolic and the animation for the load screen would not work as well. I might come back to this and port it to the software version.");
    pause(.01);
    disp("");
    cont = input("Press enter to continue...");
    clc;
    disp("Please enter values for the variables a,b,c, for the quadratic equation f(x) = ax^2 + bx + c: ");
    pause(.01);
    cont = input("Press enter to continue...");
    pause(2);
    spin_load; % Calls the loading screen
    
    % === Ask user to input values for a,b,c to represent their quadratic function === %
    quad_a = clc_in("Enter value for a: ");
    quad_b = clc_in("Enter value for b: ");
    quad_c = clc_in("Enter value for c: ");
    
    % === Graphs the quadratic function from the user input given === %
    quad_x = -10:0.1:10; % Sets up the x-axis
    
    quad_f = @q_func; % Passes a function handle of the quadratic function to the variable quad_f
    plot(quad_x, quad_f(quad_x, quad_a, quad_b, quad_c)); % Plots the quadratic function
    disp("");
    cont = input("Press enter to continue...");
    pause(.2);
    clc;
    disp("Now enter values for the lower and upper limit of a definite integral of the quadratic function: ");
    pause(.1);
    cont = input("Press enter to continue...");
    pause(2);
    clc;
    spin_load;
    % === Ask user for lower and upper limits of integration === %
    lower_a = clc_in("Enter the lower limit: ");
    upper_a = clc_in("Enter the upper limit: ");
    % === Calculate the definite integral of the quadratic function with the given limits === %
    syms x; % This declares a symbolic variable x to represent an arbitrary variable x in the inputted equation
    int_ans = int(quad_f(x, quad_a, quad_b, quad_c), lower_a, upper_a); % Passes the function handle of the quadratic function to the integrating function quad
    spin_load;
    disp("The definite integral is: ");
    disp(int_ans);
    
    return;
endfunction
% --- Functions here ---%

% === Shows a fun little loading sreen === %
function spin_load
    spin_i = 0;
    spin_pause = .2;
	for i = 1:10
		spin_i += 1;
		pause(spin_pause);
		clc;
		if(mod(i,2)==0)
			disp("----Loading...----");
			pause(spin_pause);
			clc;
			disp("////Loading...////");
		else
			disp("----Loading...----");
			pause(spin_pause);
			clc;
			disp("\\\\\\\\Loading...\\\\\\\\");
		endif
	endfor
	pause(spin_pause);
	clc;
endfunction

% === Ask the user for input and then clears the screen === % 
function ret = clc_in(var)
    ret = input(var);
    clc;
endfunction

% === The placeholder of the quadratic function, I pass function handles to this instead of rewriting the function every time === % 
function var = q_func(q_x, q_a, q_b, q_c)
    var = ((q_a .* (q_x.^2))+(q_b .* q_x)+(q_c));
endfunction
%-----------------------%