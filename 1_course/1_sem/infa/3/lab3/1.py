import re


def sym(string):
    return len(re.findall(r'8-{P', string))


print(sym('kPak8-{P:zhe88:-{Ya<>%%lo!VVe8-{P::aNnOTacPP-{ii8-'))  # 2
print(sym('![Zachem??8!P-{tyt|O|chota8-{iskat?8-P)'))  # 0
print(sym('O8-{Pa<m9-{Pam8-{Pam8-{P![SZH8-}P|-{}S|G'))  # 3
print(sym('XO|9-{CHY8-{P!DOMOI!![!]'))  # 1
print(sym('8-Pi8-{P|>a;<Pop8-{PiIKt3<!'))  # 2
