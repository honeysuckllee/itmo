import re


def find_teg(line):#так можно найти теги и запомнить их
    match = re.search(r'\b\w+\b', line)
    if match:
        return match.group(0)


def cnt_spase(text): #функция возвращает кол-во 0 в начале строки
    match = re.match(r'^\s*', text)
    return (len(match.group(0))) if match else 0


def replace_line(line): # -> <title>Мат. анализ</title>
    line = re.sub(r'^(\s*)(\w+):\s*(.*)', r'\1<\2>\3</\2>', line) + '\n'
    return line


def replace_teg(line): # tuesday: -> <tuesday>
    line = re.sub(r'(\s*)(\w+):', r'\1<\2>', line) + '\n'
    return line


def close_teg(cnt_space, teg): # -> </tuesday>
    return ' ' * cnt_space + '</' + teg + '>' + '\n'


def yaml_to_xml_2(yaml_text):
    xml_text = '<?xml version="1.0" encoding="UTF-8"?>\n'
    tegs = [0, 0, 0, 0]
    for i in range(len(yaml_text) - 1):
        line = yaml_text[i]
        next_line = yaml_text[i + 1]
        teg = find_teg(line)
        first_space = cnt_spase(line) #кол-во нулей в начале строки
        num_teg = first_space // 2
        if num_teg == 0:
            tegs[0] = teg
            xml_text += replace_teg(line)
        if num_teg == 1:  # день недели
            xml_text += replace_teg(line)
            tegs[1] = find_teg(line)
        if num_teg == 2:
            xml_text += replace_teg(line)
            tegs[2] = find_teg(line)
        if num_teg == 3: #информация
            xml_text += replace_line(line)
            if cnt_spase(next_line) == 4:
                xml_text += close_teg(4, tegs[2]) #закрываем старый класс
                tegs[2] = find_teg(next_line) #добавляем новый класс
            elif cnt_spase(next_line) == 2:
                xml_text += close_teg(4, tegs[2]) #закрываем класс
                xml_text += close_teg(2, tegs[1]) #закрываем день недели
                tegs[1] = find_teg(next_line) #обновляем день недели
    xml_text += close_teg(4, tegs[2]) #закрываем класс
    xml_text += close_teg(2, tegs[1]) #закрываем день
    xml_text += close_teg(0, tegs[0])
    return xml_text


if __name__ == '__main__':
    xml = ''
    with open('schedule.yml', 'r', encoding='utf-8') as f:
        in_yaml = f.read().split('\n')
    out_xml = yaml_to_xml_2(in_yaml)
    print(out_xml)
    with open('dop2.xml', 'w', encoding='utf-8') as f:
        f.write(out_xml)