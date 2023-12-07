#!/usr/bin/env python3

import sys
import string


def read_input(file):
    for line in file:
        yield line.translate(str.maketrans('', '', string.punctuation)).translate(str.maketrans('', '', string.digits)).split()


def main(separator='\t'):
    data = read_input(sys.stdin)
    for words in data:
        for word in words:
            print('%s%s%d' % (word, separator, 1))


if __name__ == "__main__":
    main()


