import numpy as np
import matplotlib.pyplot as plt

# ============================================================================
# ЛАБОРАТОРНАЯ РАБОТА №6: ЛИНЕЙНАЯ РЕГРЕССИЯ
# ============================================================================

print("="*80)
print("ЛАБОРАТОРНАЯ РАБОТА №6: ЛИНЕЙНАЯ РЕГРЕССИЯ")
print("="*80)
print()

# Исходные данные из таблицы 1.2
X_values = np.array([210, 340, 470, 600, 730, 860])
Y_values = np.array([2.3, 3.8, 5.3, 6.8, 7.3, 8.8, 10.3, 11.8])

# Частоты из таблицы (частота появления пары (X_i, Y_j))
frequency_table = np.array([
    [0, 4, 3, 5, 0, 0, 0, 0],  # X=210
    [0, 6, 7, 8, 0, 0, 0, 0],  # X=340
    [0, 0, 10, 12, 11, 0, 0, 0],  # X=470
    [0, 0, 0, 0, 5, 4, 3, 0],  # X=600
    [0, 0, 0, 0, 0, 6, 8, 0],  # X=730
    [0, 0, 0, 0, 0, 0, 3, 5]   # X=860
])

# Маргинальные частоты
m_x = np.array([12, 21, 33, 12, 14, 8])  # суммы по строкам
m_y = np.array([0, 10, 20, 25, 16, 10, 14, 5])  # суммы по столбцам
n = 100  # общее количество наблюдений

# Выводим исходную таблицу
print("ТАБЛИЦА 1.2 - Распределение 100 заводов")
print("="*80)
header = "X / Y     "
for y in Y_values:
    header += f"{y:>8}"
header += "     m_x"
print(header)
print("-"*80)

for i, x in enumerate(X_values):
    row = f"{x:<10}"
    for j in range(len(Y_values)):
        if frequency_table[i,j] == 0:
            row += f"{'—':>8}"
        else:
            row += f"{frequency_table[i,j]:>8}"
    row += f"{m_x[i]:>8}"
    print(row)

print("-"*80)
footer = "m_y       "
for my in m_y:
    if my == 0:
        footer += f"{'—':>8}"
    else:
        footer += f"{my:>8}"
footer += f"{n:>8}"
print(footer)
print("="*80)
print()

# ============================================================================
# ШАГ 1: КОНТРОЛЬНЫЕ СУММЫ
# ============================================================================
print("ШАГ 1: КОНТРОЛЬНЫЕ СУММЫ")
print("-"*80)

sum_m_xi = np.sum(m_x)
sum_m_yj = np.sum(m_y)
print(f"Σm_xi = {sum_m_xi}")
print(f"Σm_yj = {sum_m_yj}")
print(f"n = {n}")
print(f"Контроль: все суммы равны {n} ✓")
print()

# ============================================================================
# ШАГ 2: ВЫЧИСЛЕНИЕ НЕОБХОДИМЫХ СУММ (с детальными расчетами)
# ============================================================================
print("ШАГ 2: ВЫЧИСЛЕНИЕ НЕОБХОДИМЫХ СУММ")
print("-"*80)

# 1. Вычисление ΣΣm_ij·x_i = Σm_xi·x_i
print("1) ΣΣm_ij·x_i = Σm_xi·x_i:")
sum_m_xi_xi = 0
symbolic_str = ""
numeric_str = ""
for i, (m, x) in enumerate(zip(m_x, X_values)):
    sum_m_xi_xi += m * x
    if i > 0:
        symbolic_str += " + "
        numeric_str += " + "
    symbolic_str += f"m_x{i+1}·x_{i+1}"
    numeric_str += f"{m}·{x}"
print(f"   = {symbolic_str}")
print(f"   = {numeric_str}")
print(f"   = {sum_m_xi_xi}")
print()

# 2. Вычисление ΣΣm_ij·y_j = Σm_yj·y_j
print("2) ΣΣm_ij·y_j = Σm_yj·y_j:")
sum_m_yj_yj = 0
symbolic_str = ""
numeric_str = ""
for j, (m, y) in enumerate(zip(m_y, Y_values)):
    if m > 0:
        sum_m_yj_yj += m * y
        if symbolic_str:
            symbolic_str += " + "
            numeric_str += " + "
        symbolic_str += f"m_y{j+1}·y_{j+1}"
        numeric_str += f"{m}·{y}"
print(f"   = {symbolic_str}")
print(f"   = {numeric_str}")
print(f"   = {sum_m_yj_yj:.1f}")
print()

# 3. Вычисление Σm_xi·x_i²
print("3) Σm_xi·x_i²:")
sum_m_xi_xi2 = 0
symbolic_str = ""
numeric_str = ""
numeric_values = ""
for i, (m, x) in enumerate(zip(m_x, X_values)):
    sum_m_xi_xi2 += m * x**2
    if i > 0:
        symbolic_str += " + "
        numeric_str += " + "
        numeric_values += " + "
    symbolic_str += f"m_x{i+1}·x_{i+1}²"
    numeric_str += f"{m}·{x}²"
    numeric_values += f"{m * x**2}"
print(f"   = {symbolic_str}")
print(f"   = {numeric_str}")
print(f"   = {numeric_values}")
print(f"   = {sum_m_xi_xi2}")
print()

# 4. Вычисление Σm_yj·y_j²
print("4) Σm_yj·y_j²:")
sum_m_yj_yj2 = 0
symbolic_str = ""
numeric_str = ""
numeric_values = ""
for j, (m, y) in enumerate(zip(m_y, Y_values)):
    if m > 0:
        sum_m_yj_yj2 += m * y**2
        if symbolic_str:
            symbolic_str += " + "
            numeric_str += " + "
            numeric_values += " + "
        symbolic_str += f"m_y{j+1}·y_{j+1}²"
        numeric_str += f"{m}·{y}²"
        numeric_values += f"{m * y**2:.2f}"
print(f"   = {symbolic_str}")
print(f"   = {numeric_str}")
print(f"   = {numeric_values}")
print(f"   = {sum_m_yj_yj2:.2f}")
print()

# 5. Вычисление ΣΣm_ij·x_i·y_j
print("5) ΣΣm_ij·x_i·y_j:")
print("   Раскрываем по строкам (по x_i):")
sum_mij_xi_yj = 0
total_symbolic = ""
total_numeric = ""

for i, x in enumerate(X_values):
    row_sum = 0
    symbolic_row = f"   x_{i+1}·("
    numeric_row = f"   {x}·("
    symbolic_terms = ""
    numeric_terms = ""
    first_term = True

    for j, y in enumerate(Y_values):
        if frequency_table[i,j] > 0:
            m_ij = frequency_table[i,j]
            row_sum += m_ij * y
            if not first_term:
                symbolic_terms += " + "
                numeric_terms += " + "
            symbolic_terms += f"m_{i+1}{j+1}·y_{j+1}"
            numeric_terms += f"{m_ij}·{y}"
            first_term = False

    if row_sum > 0:
        symbolic_row += symbolic_terms + ")"
        numeric_row += numeric_terms + f") = {x}·{row_sum:.1f}"
        print(symbolic_row)
        print(numeric_row)
        sum_mij_xi_yj += x * row_sum
        if total_symbolic:
            total_symbolic += " + "
            total_numeric += " + "
        total_symbolic += f"x_{i+1}·Σm_{i+1}j·y_j"
        total_numeric += f"{x * row_sum:.1f}"
        print()

print(f"   = {total_symbolic}")
print(f"   = {total_numeric}")
print(f"   = {sum_mij_xi_yj:.1f}")
print()

# ============================================================================
# ШАГ 3: ВЫБОРОЧНЫЕ СРЕДНИЕ
# ============================================================================
print("ШАГ 3: ВЫБОРОЧНЫЕ СРЕДНИЕ")
print("-"*80)

x_bar = sum_m_xi_xi / n
y_bar = sum_m_yj_yj / n

print(f"x̄ = ΣΣm_ij·x_i / n = {sum_m_xi_xi} / {n} = {x_bar:.1f}")
print(f"ȳ = ΣΣm_ij·y_j / n = {sum_m_yj_yj:.1f} / {n} = {y_bar:.2f}")
print()

# ============================================================================
# ШАГ 4: ВЫБОРОЧНЫЕ ДИСПЕРСИИ
# ============================================================================
print("ШАГ 4: ВЫБОРОЧНЫЕ ДИСПЕРСИИ")
print("-"*80)

# Дисперсия X
s_x2 = (1/(n-1)) * (sum_m_xi_xi2 - (1/n) * (sum_m_xi_xi)**2)
s_x = np.sqrt(s_x2)
print(f"s_x² = 1/(n-1) × (Σm_xi·x_i² - 1/n·(Σm_xi·x_i)²)")
print(f"s_x² = 1/{n-1} × ({sum_m_xi_xi2} - {(sum_m_xi_xi)**2/n:.2f}) = {s_x2:.2f}")
print(f"s_x = √{s_x2:.2f} = {s_x:.2f}")
print()

# Дисперсия Y
s_y2 = (1/(n-1)) * (sum_m_yj_yj2 - (1/n) * (sum_m_yj_yj)**2)
s_y = np.sqrt(s_y2)
print(f"s_y² = 1/(n-1) × (Σm_yj·y_j² - 1/n·(Σm_yj·y_j)²)")
print(f"s_y² = 1/{n-1} × ({sum_m_yj_yj2:.2f} - {(sum_m_yj_yj)**2/n:.2f}) = {s_y2:.2f}")
print(f"s_y = √{s_y2:.2f} = {s_y:.2f}")
print()

# ============================================================================
# ШАГ 5: КОРРЕЛЯЦИОННЫЙ МОМЕНТ
# ============================================================================
print("ШАГ 5: КОРРЕЛЯЦИОННЫЙ МОМЕНТ")
print("-"*80)

s_xy = (1/(n-1)) * (sum_mij_xi_yj - (1/n) * sum_m_xi_xi * sum_m_yj_yj)
print(f"s_xy = 1/(n-1) × (ΣΣm_ij·x_i·y_j - 1/n·(Σm_xi·x_i)·(Σm_yj·y_j))")
print(f"s_xy = 1/{n-1} × ({sum_mij_xi_yj:.1f} - 1/{n}·{sum_m_xi_xi}·{sum_m_yj_yj:.1f})")
print(f"s_xy = 1/{n-1} × ({sum_mij_xi_yj:.1f} - {sum_m_xi_xi * sum_m_yj_yj / n:.2f})")
print(f"s_xy = {s_xy:.2f}")
print()

# ============================================================================
# ШАГ 6: КОЭФФИЦИЕНТ КОРРЕЛЯЦИИ
# ============================================================================
print("ШАГ 6: КОЭФФИЦИЕНТ КОРРЕЛЯЦИИ")
print("-"*80)

r_xy = s_xy / (s_x * s_y)
print(f"r_xy = s_xy / (s_x × s_y)")
print(f"r_xy = {s_xy:.2f} / ({s_x:.2f} × {s_y:.2f})")
print(f"r_xy = {s_xy:.2f} / {s_x * s_y:.2f} = {r_xy:.3f}")
print()

# ============================================================================
# ШАГ 7: УРАВНЕНИЕ РЕГРЕССИИ
# ============================================================================
print("ШАГ 7: УРАВНЕНИЕ ЭМПИРИЧЕСКОЙ ЛИНИИ РЕГРЕССИИ")
print("-"*80)

b = r_xy * (s_y / s_x)
a = y_bar - b * x_bar

print(f"Формула: y = ȳ + r_xy × (s_y/s_x) × (x - x̄)")
print(f"y = {y_bar:.2f} + {r_xy:.3f} × ({s_y:.2f}/{s_x:.2f}) × (x - {x_bar:.1f})")
print()
print(f"Коэффициенты:")
print(f"b = r_xy × (s_y/s_x) = {r_xy:.3f} × {s_y/s_x:.6f} = {b:.6f}")
print(f"a = ȳ - b·x̄ = {y_bar:.2f} - {b:.6f}·{x_bar:.1f} = {a:.6f}")
print()
# Округляем a до 2 знаков с правильным округлением
a_rounded = round(a, 2)
print(f"С округлением до сотых:")
print(f"b ≈ {b:.4f}")
print(f"a ≈ {a_rounded:.2f}")
print()
print(f"УРАВНЕНИЕ РЕГРЕССИИ: y = {b:.4f}x + {a_rounded:.2f}")
print("="*80)
print()

# ============================================================================
# ШАГ 8: ПОСТРОЕНИЕ ГРАФИКА
# ============================================================================
print("ШАГ 8: ПОСТРОЕНИЕ ГРАФИКА")
print("-"*80)

# Создаем массивы всех точек (x_i, y_j) с их частотами
points_x = []
points_y = []

for i, x in enumerate(X_values):
    for j, y in enumerate(Y_values):
        freq = frequency_table[i, j]
        if freq > 0:
            for _ in range(int(freq)):
                points_x.append(x)
                points_y.append(y)

points_x = np.array(points_x)
points_y = np.array(points_y)

print(f"Всего точек для графика: {len(points_x)}")

# Создаем линию регрессии
x_line = np.linspace(min(X_values) - 50, max(X_values) + 50, 100)
y_line = b * x_line + a_rounded

# Построение графика
plt.figure(figsize=(10, 7))
plt.scatter(points_x, points_y, alpha=0.5, s=50, c='black', label='Данные выборки')
plt.plot(x_line, y_line, 'k-', linewidth=2, label=f'y = {b:.4f}x + {a_rounded:.2f}')

plt.xlabel('X (тыс. ден. ед.)', fontsize=12)
plt.ylabel('Y (т)', fontsize=12)
plt.title('Эмпирическая линия регрессии и случайные точки выборки', fontsize=14)
plt.grid(True, alpha=0.3)
plt.legend(fontsize=10)

# Добавляем текст с коэффициентом корреляции
plt.text(0.05, 0.95, f'r = {r_xy:.3f}', 
         transform=plt.gca().transAxes,
         fontsize=11, verticalalignment='top',
         bbox=dict(boxstyle='round', facecolor='wheat', alpha=0.5))

plt.tight_layout()
plt.show()

print("График построен!")
print("="*80)
