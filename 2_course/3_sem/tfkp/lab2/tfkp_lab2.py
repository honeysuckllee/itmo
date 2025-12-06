import numpy as np

R = 1.0
T = 1.0


def f(t, R=R, T=T):
    """
    Комплексная периодическая функция f(t),
    заданная на одном периоде [-T/8, 7T/8] кусочно,
    затем периодически продолжается.
    """
    t = np.asarray(t, dtype=float)
    base_a = -T/8
    period = T
    tau = base_a + np.mod(t - base_a, period)

    # создаём массив результата
    z = np.zeros_like(tau, dtype=complex)

    m1 = (tau >= -T/8) & (tau <  T/8)
    m2 = (tau >=  T/8) & (tau < 3*T/8)
    m3 = (tau >= 3*T/8) & (tau < 5*T/8)
    m4 = (tau >= 5*T/8) & (tau < 7*T/8)

    # Re = R, Im = 8Rt/T
    z[m1] = R + 1j * (8*R*tau[m1]/T)

    # Re = 2R - 8Rt/T, Im = R
    z[m2] = (2*R - 8*R*tau[m2]/T) + 1j*R

    # Re = -R, Im = 4R - 8Rt/T
    z[m3] = -R + 1j*(4*R - 8*R*tau[m3]/T)

    # Re = -6R + 8Rt/T, Im = -R
    z[m4] = (-6*R + 8*R*tau[m4]/T) - 1j*R

    return z

# Численное вычисление коэффициента c_n


def fourier_coefficient(n, R=R, T=T, M=4000):
    a = -T/8
    b = 7*T/8
    t = np.linspace(a, b, M)
    dt = (b - a) / (M - 1)

    omega_n = 2*np.pi*n / T
    integrand = f(t, R, T) * np.exp(-1j * omega_n * t)

    integral = (integrand[0] + integrand[-1] + 2*np.sum(integrand[1:-1])) * dt / 2

    c_n = integral / T
    return c_n

# Вычисление всех c_n для заданного N


def compute_coefficients(N, R=R, T=T, M=4000):
    """
    Возвращает массив коэффициентов c_n
    для n = -N, ..., N.
    """
    ns = np.arange(-N, N+1)
    cs = np.array([fourier_coefficient(n, R, T, M) for n in ns], dtype=complex)
    return ns, cs

# Тест: результаты при N = 2


if __name__ == "__main__":
    N = 2
    ns, cs = compute_coefficients(N, R=R, T=T)

    print(f"Коэффициенты c_n при N = {N} (R = {R}, T = {T}):")
    for n, c in zip(ns, cs):
        print(f"n = {n:2d} : c_n ≈ {c.real:.6f} + {c.imag:.6f}i")