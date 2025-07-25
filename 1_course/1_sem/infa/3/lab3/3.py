# 467922 % 8 = 2
import re


def check(text):
    match = re.search(r"<meta name=\"daily_price\" content=\".*?Bitcoin.*?₽([0-9,.]+)RUB>", text) #.*? - любой текст
    if match:
        price = match.group(1)
        return price


case = []
case.append('<meta name="daily_price" content=" в реальном времени цена на Ethereum ставляет ₽5,797,806.66RUB><meta name="daily_price" content=" в реальном времени цена на Bitcoin ставляет ₽5,900,806.66RUB>')
case.append('<meta name="daily_price" content=" в реальном времени цена на Bitcoin ставляет ₽5.555RUB><meta name="daily_price" content=" в реальном времени цена на Ripple ставляет ₽300,806.66RUB">')
case.append('<meta name="daily_price" content=" в реальном времени цена на Bitcoin ставляет ₽5,912,888.66RUB><meta name="daily_price" content=" в реальном времени цена на Cardano ставляет ₽850,806.66RUB">')
case.append('<meta name="daily_price" content=" в реальном времени цена на Bitcoin ставляет ₽5,780,806.303RUB><meta name="daily_price" content=" в реальном времени цена на Binance Coin ставляет ₽2,500,806.66RUB">')
case.append('<meta name="daily_price" content=" в реальном времени цена на Bitcoin ставляет ₽5,6RUB><meta name="daily_price" content=" в реальном времени цена на Solana ставляет ₽1,000,806.66RUB">')
for i in case:
    print(check(i))