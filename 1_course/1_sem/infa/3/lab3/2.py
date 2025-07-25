# 467922 % 6 = 0
import re


def check(text):
    if len(re.findall('/', text)) != 2: #считаем кол-во /
        return 'Не хайку. Должно быть 3 строки.'
    text = text.split('/')
    key = [5, 7, 5]
    weHave = []
    for i in text:
        weHave.append(len(re.findall(r'[аеёиоуыэюяАЕЁИОУЫЭЮЯ]', i))) #добавляем в наш массив количество гласных
    if key == weHave:
        return 'Хайку!'
    else:
        return 'Не хайку.'


case = []
case.append('ура1/ ура2/ ура3') #Не хайку.
case.append('я хочу домой!/ пока пока прощайте/ всем добра добра.') #Хайку!
case.append('мяу/ миу') #Не хайку. Должно быть 3 строки.
case.append('псж/ или / эм псж') #Не хайку.
case.append('я поднимаю/ руки хочу тебее/ сдаться ведь ты же..') #Хайку!
for i in case:
    print(check(i))

