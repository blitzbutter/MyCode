% === Jacob Paul Bryant === %

% === Assignment 3 === %
%{ 
	Ask the user to input 3 points, then calculate Lagrange polynomials to find a basis
	for the polynomial space. Finally, it will plot the user's points, and the Lagrange polynomial interpolation.
	***NOTE*** So far, I've got this to work up to degree six.
%}

% ---------------------------- %
% --- Some things to note  --- %
% ---------------------------- %

% ***
% matLabFunction() can turn a condensed function such as f(x)=x^2, where the input would be an x, to a function that can take input and produce output.
% Example: function ret = f(x) / ret = x.^2; / endfunction / syms x; /new_func = f(x) % new_func = x^2
% In other words, it will take the symbolic output of one function and turn it into its own function.
% ***

% ***
% Plotting in octave-online.net is a pain in the ass and I had to work around it using very basic idea such as for-loops and such. I learned that if you condense more than what you need in the hold on and hold off statements, it'll graph twice which sucks. So you have to just put all your graphing stuff in the hold on and hold off statements
% ***

% ***
% I relearned to always work on code as seperately as possible or else it will start to clutter
% *** 

% ***
% Always reset your loop variables that need to be reset!!!!!!!!!
% ***

% ---------------------------- %
% --- Some things to note  --- %
% ---------------------------- %

function lagrange_poly_int() % Name the main function after the name of the script
    clc;
    
    disp("Please click add more time in octave-online to make sure the program finishes");
    pause(1);
    cont = clc_in("Press enter to continue");
    
    % First, find and graph the polynomial interpolation for the user points
    graph_int_poly();
    % Then find and graph the polynomial interpolation for the Runge-Kutta function as well as graph the Runge-Kutta function %
    graph_int_runge();
    close all;
endfunction

% === Ask the user for input and then clears the screen === % 
function ret = clc_in(var)
    ret = input(var);
    clc;
endfunction

function graph_int_poly()
    % === Ask the user for two vectors of x and y coordinates === %
    while(true)
        % Vectors of x and y coordinates
        x_vals = clc_in("Please enter x-values of points in form [1,...,n]: ");
        y_vals = clc_in("Please enter y-values of points in form [1,...,n]: ");
        if(!isvector(x_vals) || !isvector(y_vals) || (length(x_vals)!=length(y_vals)))		% If the input is not vectorized and the lengths do not equal one another
            disp("Please enter the values in the form [1,...,n]");
            pause(1); % Pause so there is a new line
            disp("Please make sure both vectors are of the same length");
            pause(1);
            continue; % Repeat the loop until the input is a vector
        endif
        break;
    endwhile
    
    % === Plot the user's points and Lagrange polynomial interpolation === %
    pause(2);
    clc;
    
    syms x; % Symbolic x
    clc; % Just in case the Python info for the symbolic package pops up
    
    % Calculates the interpolated polynomial through the user points
    int_handle = int_la(x, x_vals, y_vals); % Returns the simplified interpolated polynomial function
    int_poly = matlabFunction(int_handle); % Converts the symbolic function into a usable function (Faster computation)
    
    hold on;
    title("Lagrange Interpolation", "fontsize", 16, "color", "blue");
    xlabel('X', "color", "blue");
    ylabel('Y', "color", "blue");
    
    % Plots the function in dots 
    
    for i=min(x_vals):.1:max(x_vals)
        plot(i, int_poly(i));
    endfor
    
    
    % Plot points and highlights user points with a triangle
    plot(0,0, '+k'); % Plot the origin
    
    for i = 1:length(x_vals) % Plot each point from index 1 to the nth degree.
        plot(x_vals(i), y_vals(i), "^r");
    endfor
    hold off;
    % !!! Always encapsulate graph only functions in the hold on and off, setting it after line 81 created two graphs which was annoying !!! %
    % Set titles and labels
    % Display information about the graph
    pause(1);
    disp("----------");
    disp("Points");
    disp("----------");
    print_points(x_vals, y_vals);
    pause(1);
    disp("----------");
    disp("Polynomial");
    disp("----------");
    disp(pretty(int_handle)); % Prints a prettier version of the polynomial
    cont = clc_in("Press enter to continue...");
    close all; % Closes any remaining graph
    clc;
endfunction

% === Functions to graph the Runge-Kutta function and the interpolated Lagrange polynomial of the Runge-Kutta function === %
function graph_int_runge()
    % Remember to always work on things in seperate functions for cleaner and organized code
    % Working with this code in a seperate file saved me a lot of pain
    clc; % Clears screen to continue
    disp("Enter x-values which will go through the Runge Kutta function and be interpolated");
    pause(1);
    cont = clc_in("Press enter to continue...");
    while(true)
        % Vectors of x and y coordinates
        x_r_vals = clc_in("Please enter x-values of points in form [1,...,n]: ");
        if(!isvector(x_r_vals))		% If the input is not vectorized
            disp("Please enter the values in the form [1,...,n]");
            pause(1); % Pause so there is a new line
            continue; % Repeat the loop until the input is a vector
        endif
        break;
    endwhile
    
    % Fill an array of y-values with the Runge-Kutta function output
    for i=1:length(x_r_vals)
        y_r_vals(i) = runge_kutta(x_r_vals(i));
    endfor
    
    % Entered [MSG, MSGID] = lastwarn(), the copy pasted that here to turn off annoying warnings
    % I've learned to seperate everything into different functions
    warning("off", "OctSymPy:sym:rationalapprox");
    
    syms k; % Creates a symbolic k to use in the function
    int_handle = int_la(k, x_r_vals,y_r_vals);
    int_poly = matlabFunction(int_handle);
    
    runge_handle = @runge_kutta; % Again, since we are using a function inside a function (In this case plot), we will need to create a function handle. :)
    runge_disp = runge_kutta(k);
    % Plots both the Runge-Kutta function and the interpolated functions from the minimum of x values to the maximum
    hold on
    title("Lagrange Interpolation of Runge-Kutta Points", "fontsize", 16, "color", "blue");
    xlabel('X', "color", "blue");
    ylabel('Y', "color", "blue");

    for i=min(x_r_vals):.1:max(x_r_vals)
        plot(i, int_poly(i), "g");
        plot(i, runge_handle(i), "b");
    endfor
    
    % Plot points and highlights user points with a triangle
    plot(0,0, '+k'); % Plot the origin
    
    for i = 1:length(x_r_vals) % Plot each point from index 1 to the nth degree.
        plot(x_r_vals(i), y_r_vals(i), "^r");
    endfor
    hold off
    disp("----------");
    disp("Points");
    disp("----------");
    print_points(x_r_vals, y_r_vals);
    disp("----------");
    disp("Polynomial");
    disp("----------");
    disp(pretty(int_handle));
    disp("----------");
    disp("Key");
    disp("----------");
    disp("Runge-Kutta Function ---> BLUE");
    disp("Interpolated Runge-Kutta Function ---> GREEN");
    cont = input("Press enter to continue...");
    close all;
    clc;
    %{
        There are points that will better approximates the function. The points
        (2,0.2)
        (1,0.5)
        (3,0.1)
        (12,0.00689655)
        (4,0.0588235)
        strayed greatly from the original function while points with x-values closer
        like 
        (1,0.5)
        (2,0.2)
        (3,0.1)
        (4,0.0588235)
        (5,0.0384615)
        stayed closer to the graph at the points interpolated, likely due to the
        max jump difference of 9 of the first set of points in comparison to the
        second set.
        (1,0.5)
        (-2,0.2)
        (3,0.1)
        (-4,0.0588235)
        (5,0.0384615)
        seems to take on the curve's general form, but is far more spead out in
        the center than the Runge-Kutta function
    %}
endfunction

% === Interpolation function happens here === % 
function ret = int_la(x, x_vals, y_vals)
    degree_n = length(x_vals); % Degree of the polynomial
    polynomial = 0; % Will contain the final polynomial interpolation
    par_ans = 1; % The parameter expression of the jth index
    
    % --- We will calculate the Lagrange polynomials here --- % 
    
    % Uses a nested for loop to iterate through each j and kth degree
    for j=1:degree_n
        for k=1:degree_n
            if(k!=j) % Satisfies condition of Lagrange polynomial
                par_ans *= ((x-x_vals(k))/(x_vals(j)-x_vals(k))); % jth step of multiplying values in paranthesis
            endif
        endfor
        polynomial += par_ans*y_vals(j); % Adds the product of the jth step
        par_ans = 1; % Reset pars_ans
    endfor
    ret = simplify(polynomial); % Simplifies the busy looking polynomial to print for later
endfunction

% === Runge-Kutta function === %
function ret = runge_kutta(x_a)
    ret = 1./(1+(x_a.^2));
endfunction

% === A function that prints formatted points === %
function print_points(x_points, y_points)
    for i=1:1:length(x_points)
        printf("(%d,%d)\n",x_points(i),y_points(i));
    endfor
endfunction