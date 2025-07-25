import time
from base import yaml_to_xml
from dop_1 import yaml_to_xml_1
from dop_2 import yaml_to_xml_2
from dop_3 import yaml_to_xml_3

iterations = 100
yaml_file_name = 'schedule.yml'

# основной вариант
in_yaml = ''
with open(yaml_file_name, 'r', encoding='utf-8') as f:
    in_yaml = f.read().split('\n')
start_time = time.time()
for _ in range(iterations):
    yaml_to_xml(in_yaml)
end_time = time.time()
total_time = end_time - start_time
print(f"Function yaml_to_xml   took {total_time:.6f} seconds for {iterations} iterations.")

# вариант с использованием yaml и xmltodict
in_yaml1 = ''
with open(yaml_file_name, 'r', encoding='utf-8') as f1:
    in_yaml1 = f1.read()

start_time = time.time()
for _ in range(iterations):
    yaml_to_xml_1(in_yaml1)
end_time = time.time()
total_time = end_time - start_time
print(f"Function yaml_to_xml_1 took {total_time:.6f} seconds for {iterations} iterations.")

# вариант с использованием регулярных выражений
with open(yaml_file_name, 'r', encoding='utf-8') as f3:
    in_yaml = f3.read().split('\n')
start_time = time.time()
for _ in range(iterations):
    yaml_to_xml_2(in_yaml)
end_time = time.time()
total_time = end_time - start_time
print(f"Function yaml_to_xml_2 took {total_time:.6f} seconds for {iterations} iterations.")

# вариант с использованием формальных грамматик
with open(yaml_file_name, 'r', encoding='utf-8') as f2:
    in_yaml = f2.read()
start_time = time.time()
for _ in range(iterations):
    yaml_to_xml_3(in_yaml)
end_time = time.time()
total_time = end_time - start_time
print(f"Function yaml_to_xml_3 took {total_time:.6f} seconds for {iterations} iterations.")