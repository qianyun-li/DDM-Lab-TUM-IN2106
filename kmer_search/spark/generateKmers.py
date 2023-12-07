from random import choice

K = 4
NO_KMER = 1024
alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

with open("kmersDimension1024", "w") as f:
	for _ in range(NO_KMER):
		kmer = ''.join([choice(alphabet) for _ in range(K)])
		f.write(kmer + '\n')
