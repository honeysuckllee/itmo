clear; close all; clc;

filename = 'SBER_data.csv';  

opts = detectImportOptions(filename, 'Delimiter',';');
T = readtable(filename, opts);

if any(strcmpi('CLOSE', T.Properties.VariableNames))
    price = T.CLOSE;
else
    price = T{:, end};
end

price = price(:);             
N = length(price);

t = (0:N-1)';                   

price_mean = mean(price);
x = price - price_mean;         
T_list = [1, 7, 30, 90, 365];

for k = 1:length(T_list)
    Tconst = T_list(k);
    num = 1;
    den = [Tconst 1];
    W1  = tf(num, den);

    x0 = x(1);                  
    y  = lsim(W1, x, t, x0);

    y_plot = y + price_mean;
    x_plot = x + price_mean;

    figure('Name',['Сглаживание, T = ' num2str(Tconst) ' дней']);

    plot(t, x_plot, 'Color',[0.6 0.6 0.6]); hold on;
    plot(t, y_plot, 'LineWidth', 1.5);
    grid on;
    xlabel('Номер дня');
    ylabel('Цена');
    title(['Сравнение исходных котировок и сглаженных данных, T = ' ...
           num2str(Tconst) ' дней']);
    legend('Исходные данные','Сглаженные данные','Location','best');

end


figure('Name','Общее сравнение сглаживания для разных T');
plot(t, x_plot, 'Color',[0.7 0.7 0.7]); hold on;

colors = lines(length(T_list));

for k = 1:length(T_list)
    Tconst = T_list(k);

    num = 1;
    den = [Tconst 1];
    W1  = tf(num, den);
    x0  = x(1);
    y   = lsim(W1, x, t, x0);
    y_plot = y + price_mean;

    plot(t, y_plot, 'Color', colors(k,:), 'LineWidth', 1.2, ...
        'DisplayName', ['T = ' num2str(Tconst) ' дн.']);
end

grid on;
xlabel('Номер дня');
ylabel('Цена');
title('Сглаживание котировок для разных значений T');
legend('Исходные данные', 'T = 1', 'T = 7', ...
       'T = 30', 'T = 90', 'T = 365', 'Location','best');
