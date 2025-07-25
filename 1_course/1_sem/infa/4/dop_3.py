import re


def dict_to_xml(data, indent=0):
    xml_str = ""
    indentation = "  " * indent # 2 пробела * количество

    if isinstance(data, dict):
        for key, value in data.items():
            xml_str += f"{indentation}<{key}>\n"
            xml_str += dict_to_xml(value, indent + 1)
            xml_str += f"{indentation}</{key}>\n"
    else:
        xml_str += f"{indentation}{data}\n"
    return xml_str


def parse_yaml_to_dict(text):
    result = {}
    stack = [] #список тегов нужен чтобы понимать на каком уровне находишься
    current_dict = result #промежуточный словарь
    levels = ['schedule']

    for line in text.splitlines():
        match = re.match(r'^(\s*)([a-zA-Z0-9_]+):\s*(.*)$', line)
        if match:
            spaces, key, value = match.groups()
            level = len(spaces) // 2 + 1

            while len(stack) >= level:
                current_dict = stack.pop()
                levels.pop()

            if value:
                current_dict[key] = value
            else:
                current_dict[key] = {}
                stack.append(current_dict)
                current_dict = current_dict[key]
                levels.append(key)
    return result


def yaml_to_xml_3(text):
    dict_data = parse_yaml_to_dict(text)
    first_line = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
    return first_line + dict_to_xml(dict_data)


if __name__ == '__main__':
    xml = ''
    with open('schedule.yml', 'r', encoding='utf-8') as f:
        in_yaml = f.read()
    out_xml = yaml_to_xml_3(in_yaml)
    print(out_xml)
    with open('dop3.xml', 'w', encoding='utf-8') as f:
        f.write(out_xml)


