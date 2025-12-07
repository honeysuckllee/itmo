clear; close all; clc;

dt      = 0.001;                
T_total = 10;                   
t       = (-T_total/2 : dt : T_total/2)';   
N       = length(t);

a_amp = 1;                     
t1    = -1;
t2    =  1;

g = zeros(size(t));
g(t >= t1 & t <= t2) = a_amp;

dw = 2*pi / (N*dt);
w  = (-N/2 : N/2-1)' * dw;

G = fftshift( fft(g) );

w0 = 5;                          

a1 = 0;
a2 = w0^2;                       
b1 = 2;                          
b2 = a2;                        

b1_values = [0.5, 2, 5];   
b2 = a2;

figure('Name','Влияние параметра b1 на АЧХ');
hold on;

for b1_test = b1_values
    W2_w_test = (-w.^2 + 1i*a1*w + a2) ./ (-w.^2 + 1i*b1_test*w + b2);

    plot(w, abs(W2_w_test), 'LineWidth', 1.3, ...
        'DisplayName', ['b_1 = ' num2str(b1_test)]);
end

grid on;
xlabel('\omega, рад/с');
ylabel('|W_2(j\omega)|');
title('Влияние параметра b_1 на АЧХ режекторного фильтра');
xlim([0 50]);
legend('Location','best');
hold off;


num = [1 a1 a2];
den = [1 b1 b2];
W2  = tf(num, den);

W2_w = ( -w.^2 + 1i*a1*w + a2 ) ./ ( -w.^2 + 1i*b1*w + b2 );

c = 0.5;                        
d = w0;                         

u = g + c * sin(d * t);
U = fftshift( fft(u) );          

y_lsim = lsim(W2, u, t);

Y_freq = W2_w .* U;              
y_fft  = real( ifft( ifftshift(Y_freq) ) );

Y_lsim = fftshift( fft(y_lsim) );

figure('Name','Задание 1.2, режекторный фильтр');

subplot(2,2,1);
plot(w, abs(W2_w), 'LineWidth', 1.3);
grid on;
xlabel('ω, рад/с');
ylabel('|W_2(jω)|');
title('АЧХ режекторного фильтра');
xlim([0 50]);

subplot(2,2,2);
plot(t, g,      'LineWidth', 1.3); hold on;
plot(t, u);
plot(t, y_lsim, 'LineWidth', 1.3);
grid on;
xlabel('t, с');
ylabel('Амплитуда');
title(['Сигналы g(t), u(t), y(t),  d = ' num2str(d)]);
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
idx = (w >= 0) & (w <= 50);      
plot(w(idx), abs(G(idx)),      'LineWidth', 1.3); hold on;
plot(w(idx), abs(U(idx)));
plot(w(idx), abs(Y_lsim(idx)), 'LineWidth', 1.3);
grid on;
xlabel('ω, рад/с');
ylabel('Модуль спектра');
title('Спектры: |G(ω)|, |U(ω)|, |Y(ω)|');
legend('|G(ω)|', '|U(ω)|', '|Y(ω)|');


figure('Name','Сравнение спектров выходного сигнала');

Y_prod = W2_w .* U;             

plot(w(idx), abs(Y_lsim(idx)), 'LineWidth', 1.3); hold on;
plot(w(idx), abs(Y_prod(idx)), '--', 'LineWidth', 1.3);
grid on;
xlabel('ω, рад/с');
ylabel('Модуль спектра');
title('Сравнение |Y(ω)| и |W_2(jω)·U(ω)|');
legend('|Y(ω)|', '|W_2(jω)·U(ω)|');
