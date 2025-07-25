import yaml
import xmltodict


def yaml_to_xml_1(yaml_file):# Преобразование словаря в XML
    yaml_dict = yaml.safe_load(yaml_file)
    return xmltodict.unparse(yaml_dict, pretty=True)


if __name__ == '__main__':# Чтение YAML-файла
    with open('schedule.yml', 'r', encoding='utf-8') as yaml_file:
        yaml_data = yaml_to_xml_1(yaml_file.read())
    print(yaml_data)

    with open('dop1.xml', 'w', encoding='utf-8') as xml_file:
        xml_file.write(yaml_data)# Запись в файл
