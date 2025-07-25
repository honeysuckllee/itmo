def yaml_to_csv(text):
    csv_output = "Day,Class,Title,Type,Weeks,Begin Time,End Time,Teacher,Building,Classroom\n"  # CSV заголовок
    current_day = None
    current_class = None
    details_parts = []

    for i in text:
        cnt_space = len(i) - len(i.lstrip(' '))  # количество пробелов перед текстом (тегом)
        num_tag = cnt_space // 2  # номер тега

        i = i.split(':')
        tag_name = i[0].strip()  # получаем имя тега без пробелов

        if num_tag == 0:  # schedule
            continue  # пропускаем, так как это заголовок

        elif num_tag == 1:  # day
            current_day = tag_name  # сохраняем текущий день

        elif num_tag == 2:  # class
            current_class = tag_name  # сохраняем текущий класс

        elif num_tag == 3:  # details (например, описание или предмет)
            if len(i) > 1:
                details = i[1].strip()  # получаем значение
                if details.find(',') > -1:
                    details_parts.append( '"' + details + '"') # если значение содержит запятые
                else:
                    details_parts.append(details)
                if len(details_parts) == 8:  # ожидаем 8 элементов
                    # добавляем строку в CSV, используя текущий день и класс
                    csv_output += f"{current_day},{current_class}," + ','.join(details_parts) + "\n"
                    details_parts = []
    return csv_output


if __name__ == '__main__':
    in_yaml = ''
    with open('schedule.yml', 'r', encoding='utf-8') as f:
        in_yaml = f.read().splitlines() #делаем список строк

    out_csv = yaml_to_csv(in_yaml)  # Изменение функции на yaml_to_csv
    print(out_csv)

    with open('1.csv', 'w', encoding='utf-8') as f:  # Изменение файла на 1.csv
        f.write(out_csv)
