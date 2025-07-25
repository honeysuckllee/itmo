def yaml_to_xml(text):
    xml = '<?xml version="1.0" encoding="UTF-8"?>\n'
    tag0 = 0 #schedule
    tag1 = 0 #день недели
    last_tag1 = 0
    tag2 = 0 #class
    tag = 0 #когда num_tag принимает значения от 3 до 10
    describe = 0 #когда num_tag принимает значения от 3 до 10
    cnt_teg = 0
    for i in text:
        cnt_space = len(i) - len(i.lstrip(' ')) #количество пробелов перед текстом(тегом)
        num_tag = cnt_space // 2 #номер тега
        i = i.split(':', maxsplit=1) #сплитим по : 1 раз потому что : также есть во времени
        if num_tag == 0:
            tag0 = i[0]
            xml += '<' + tag0 + '>' + "\n"
        elif num_tag == 1:
            tag1 = i[0].lstrip(' ')
            if last_tag1 == 0:
                last_tag1 = tag1
            else:
                if tag1 != last_tag1:
                    xml += ' ' * cnt_space + '</' + last_tag1 + '>' + "\n"
            xml += ' ' * cnt_space + '<' + tag1 + '>' + "\n"
        elif num_tag == 2:
            tag2 = i[0].lstrip(' ')
            xml += ' ' * cnt_space + '<' + tag2 + '>' + "\n"
        elif num_tag == 3:
            tag = i[0].lstrip(' ')
            describe = i[1]
            xml += ' ' * cnt_space + '<' + tag + '>' + describe.lstrip(' ') + '</' + tag + '>' + '\n'
            cnt_teg += 1
            if cnt_teg == 8: #проверяем пора ли нам закрывать класс
                xml += ' ' * (cnt_space - 2) + '</' + tag2 + '>' + '\n'
                cnt_teg = 0
    xml += ' ' * 2 + '</' + tag1 + '>' + '\n'
    xml += '</' + tag0 + '>'
    return xml


if __name__ == '__main__':
    in_yaml = ''
    with open('schedule.yml', 'r', encoding='utf-8') as f:
        in_yaml = f.read().split('\n')
    out_xml = yaml_to_xml(in_yaml)
    print(out_xml)
    with open('1.xml', 'w', encoding='utf-8') as f:
        f.write(out_xml)