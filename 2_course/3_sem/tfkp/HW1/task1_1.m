clear; close all; clc;
rng('default');                

T_const = 1;                    
a       = 1;                    

dt      = 0.001;               
T_total = 10;                  
t       = (-T_total/2 : dt : T_total/2)';   
N       = length(t);

t1 = -1;                        
t2 =  1;                        

g = zeros(size(t));
g(t >= t1 & t <= t2) = a;


b  = 0.5;                       
xi = 2 * rand(size(t)) - 1;    
u  = g + b * xi;


dw = 2 * pi / (N * dt);         
w  = (-N/2 : N/2-1)' * dw;     

G = fftshift( fft(g) );         
U = fftshift( fft(u) );         


num = 1;
den = [T_const 1];
W1  = tf(num, den);             

W1_w = 1 ./ (1 + 1i * w * T_const);


y_lsim = lsim(W1, u, t);       

Y_freq = W1_w .* U;            
y_fft  = ifft( ifftshift(Y_freq) );
y_fft  = real(y_fft);           


Y_lsim = fftshift( fft(y_lsim) );



figure('Name','Задание 1.1, T = 1');


subplot(2,2,1);
plot(w, abs(W1_w), 'LineWidth', 1.3);
grid on;
xlabel('ω, рад/с');
ylabel('|W_1(jω)|');
title(['АЧХ фильтра первого порядка, T = ', num2str(T_const)]);
xlim([-50 50]);


subplot(2,2,2);
plot(t, g,      'LineWidth', 1.3); hold on;
plot(t, u);
plot(t, y_lsim, 'LineWidth', 1.3);
grid on;
xlabel('t, с');
ylabel('Амплитуда');
title('Сигналы: исходный g(t), зашумлённый u(t), фильтрованный y(t)');
legend('g(t)', 'u(t)', 'y(t)');


subplot(2,2,3);
plot(t, y_lsim, 'LineWidth', 1.3); hold on;
plot(t, y_fft,  '--', 'LineWidth', 1.3);
grid on;
xlabel('t, с');
ylabel('Амплитуда');
title('Сравнение во времени: y_{lsim}(t) и y_{FFT}(t)');
legend('y_{lsim}(t)', 'y_{FFT}(t)');


subplot(2,2,4);
idx = abs(w) < 50;             
plot(w(idx), abs(G(idx)),      'LineWidth', 1.3); hold on;
plot(w(idx), abs(U(idx)));
plot(w(idx), abs(Y_lsim(idx)), 'LineWidth', 1.3);
grid on;
xlabel('ω, рад/с');
ylabel('Модуль спектра');
title('Модули спектров исходного, зашумлённого и фильтрованного сигналов');
legend('|G(ω)|', '|U(ω)|', '|Y(ω)|');



figure('Name','Сравнение спектров выходного сигнала');

Y_prod = W1_w .* U;             

plot(w(idx), abs(Y_lsim(idx)), 'LineWidth', 1.3); hold on;
plot(w(idx), abs(Y_prod(idx)), '--', 'LineWidth', 1.3);
grid on;
xlabel('ω, рад/с');
ylabel('Модуль спектра');
title('Сравнение |Y(ω)| и |W_1(jω)·U(ω)|');
legend('|Y(ω)|', '|W_1(jω)·U(ω)|');
