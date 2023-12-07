import json

with open('wikimedia_txt', 'a+') as txt_file:
    with open('link_annotated_text.jsonl', 'r') as json_file:
        for line in json_file:
            sections = json.loads(line)['sections']
            for section in sections:
                if not section['text']:
                    continue
                txt_file.write(section['text'])
                txt_file.write('\n')
