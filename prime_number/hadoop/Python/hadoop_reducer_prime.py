#!/usr/bin/env python3

import sys


def read_mapper_output(file):
    for line in file:
        yield int(line.strip())


def main():
    list = []
    Prime_Num = 150
    data = read_mapper_output(sys.stdin)
    for num in data:
        list.append(num)
    list.sort()
    for i in range(Prime_Num):
        print(list[i])


if __name__ == "__main__":
    main()


